package nz.govt.msd.cisco.axl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.devtools.common.options.OptionsParser;
import com.sun.xml.ws.client.ClientTransportException;

import nz.govt.msd.cisco.axl.model.CCMDirectoryNumber;
import nz.govt.msd.cisco.axl.model.CCMPhone;
import nz.govt.msd.cisco.axl.model.CCMUser;

public class DirURIUpdater {
	
	/**
	 * Will hold a representation of a directory uri that will be read from the xml file
	 * @author aplan002
	 *
	 */
	private class DirURI {
		private String uri;
		private String partition;
		private boolean primary;
		private boolean advertise;
		
		DirURI() {}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getPartition() {
			return partition;
		}

		public void setPartition(String partition) {
			this.partition = partition;
		}

		public boolean isPrimary() {
			return primary;
		}

		public void setPrimary(boolean primary) {
			this.primary = primary;
		}

		public boolean isAdvertise() {
			return advertise;
		}

		public void setAdvertise(boolean advertise) {
			this.advertise = advertise;
		}
		
	}
	
	
	static final Logger logger = LoggerFactory.getLogger(DirURIUpdater.class);
	
	private static final String USERID_DIRECTIVE = "%userid%";
	private static final String DN_DIRECTIVE = "%dn%";
	private static final String PHONE_DIRECTIVE = "%phone%";
	
	private static int totalUsers = 0;
	private static int usersWithoutDevices = 0;
	private static int totalDnsUpdated = 0;
	private static List<String> dnsNotUpdated;
	private static boolean wipe = false;
	private static ArrayList<DirURI> directoryUris;
	
	private File reportFile = new File(System.getProperty("user.dir") + "\\report.txt");
	private File xmlConfig = new File(System.getProperty("user.dir") + "\\configuration.xml");
	
	public DirURIUpdater() {
		directoryUris = new ArrayList<>();
		dnsNotUpdated = new ArrayList<>();
	}
	
	public boolean readXmlConfiguration() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlConfig);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("directoryUri");
			
			for(int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) nNode;
					DirURI directoryUri = new DirURI();
					directoryUri.setAdvertise(element.getElementsByTagName("advertiseGlobally").item(0).getTextContent() == "true" ? true : false);
					directoryUri.setPrimary(element.getElementsByTagName("primary").item(0).getTextContent() == "true" ? true : false);
					directoryUri.setUri(element.getElementsByTagName("uri").item(0).getTextContent());
					directoryUri.setPartition(element.getElementsByTagName("partition").item(0).getTextContent());
					
					directoryUris.add(directoryUri);
				}
			}
			
			return true;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			logger.error(ExceptionUtils.getMessage(e));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			logger.error(ExceptionUtils.getMessage(e));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(ExceptionUtils.getMessage(e));
		}
		
		return false;
	}
	
	public void report() {
		try {
			if(reportFile.exists()) {
				reportFile.delete();
			}
			
			FileUtils.touch(reportFile);
			if(reportFile.exists()) {
				logger.info("Writing report file to " + reportFile.getPath());
				FileUtils.writeStringToFile(reportFile, "Total of users to process in file: " + totalUsers + "\r\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Total of users successfully processed: " + (totalUsers - usersWithoutDevices) + "\r\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Number of Directory Numbers updated: " + totalDnsUpdated + "\r\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Directory Numbers failed: \r\n", StandardCharsets.UTF_8, true);
				
				for(String dn : dnsNotUpdated) {
					FileUtils.writeStringToFile(reportFile, dn + "\r\n", StandardCharsets.UTF_8, true);
				}
			} else {
				logger.error("Couldn't create the report file. Reporting to console.");
				logger.info("Total of users successfully processed: " + (totalUsers - usersWithoutDevices));
				logger.info("Total of users to process in file: " + totalUsers);
				logger.info("Number of Directory Numbers updated: " + totalDnsUpdated);
				logger.info("Directory Numbers failed:");
				
				for(String dn : dnsNotUpdated) {
					logger.info(dn);
				}
			}
			
		} catch (IOException e) {
			logger.error("Couldn't create the report file. Reporting to console.");
			logger.info("Total of users successfully processed: " + (totalUsers - usersWithoutDevices));
			logger.info("Total of users to process in file: " + totalUsers);
			logger.info("Number of Directory Numbers updated: " + totalDnsUpdated);
			logger.info("Directory Numbers failed:");
			
			for(String dn : dnsNotUpdated) {
				logger.info(dn);
			}
		}
	}
		
	private static void printUsage(OptionsParser parser) {
	    logger.info("Usage: java -jar diruriupdater.jar OPTIONS");
	    logger.info(parser.describeOptions(Collections.<String, String>emptyMap(),
	                                              OptionsParser.HelpVerbosity.LONG));
	 }
	
	public static void main(String[] args) {
	
		OptionsParser optParser = OptionsParser.newOptionsParser(DirURIUpdaterOptions.class);
		optParser.parseAndExitUponError(args);
		DirURIUpdaterOptions options = optParser.getOptions(DirURIUpdaterOptions.class);
		
		if(options.host.isEmpty() || options.port < 0 || options.password.isEmpty() || options.password.isEmpty() || options.file.isEmpty()) {
			printUsage(optParser);
			
			return;
		}
		
		if(options.wipe) {
			wipe = true;
		}
		
		logger.info("Bulk updater for Directory URI is starting...");
		
		DirURIUpdater updater = new DirURIUpdater();
		updater.readXmlConfiguration();
		
		/**
		 * Creates the AXL helper for us to interact with AXL
		 */
		AXLServiceHelper axlService = new AXLServiceHelper();
		axlService.setUcHost(options.host);
		axlService.setUcPort(Integer.toString(options.port));
		axlService.setUcAdmin(options.username);
		axlService.setUcPassword(options.password);
		axlService.initialize();
		
		File inputFile = new File(options.file);
		try {
			//read lines from file (the first line is the header - csv format)
			List<String> lines = FileUtils.readLines(inputFile, "UTF-8");
			totalUsers = lines.size();
			
			/**
			 * Parsing the header so we know wich coloumn index holds the username
			 */
			String[] headers = lines.get(0).split(",");
			
			int headersIndex = 0;
			int userColumnIndex = -1;
			for(String header : headers) {
				if(header.startsWith(" ")) {
					headers[headersIndex] = header.substring(1);
				}
				
				if(headers[headersIndex].equals("USER ID")) {
					userColumnIndex = headersIndex;
					break; //we just need the user id index in the file (i.e. know which coumn holds the user id)
				}
				headersIndex++;
			}
			
			if(userColumnIndex == -1) {
				logger.info("File doesn't contain column USERID. Cannot continue. Please export file form Call Manager.");
				
				return;
			}
			
			for(int i = 1; i < lines.size(); i++) {
				String[] lineAsArray = lines.get(i).split(",");
				String userid = lineAsArray[userColumnIndex];
				
				logger.info("Processing: " + userid);
				
				CCMUser user = new CCMUser(axlService);
				user.setUsername(userid);
				
				logger.debug("Retrieving user object from Call Manager...");
				if(user.read()) {
					
					if(!user.getDirectoryUri().equals("")) {
						logger.info("Wiping user's directory URI: " + user.getDirectoryUri());
						
						user.setDirectoryUri("");
						user.update();
					}
					
					logger.debug("Retrieving associated device(s) for "+ userid + "...");
					

					if(!user.getAssociatedDevices().isEmpty()) {
						String device = user.getAssociatedDevices().get(0);
						
						logger.debug("Found primary device: " + device);
						
						CCMPhone phone = new CCMPhone(axlService);
						phone.setName(device);
						
						logger.debug("Retrieving phone object from Call Manager...");
						if(phone.read()) {
							logger.debug("Retrieving associated DN(s) for "+ device + "...");
							
							if(!phone.getDirectoryNumber().isEmpty()) {
								Entry<String, String> dnEntry = phone.getDirectoryNumber().entrySet().iterator().next();
								String dn = dnEntry.getKey();
								String routePartition = dnEntry.getValue();
								
								logger.debug("Will now update DN " + dn + "...");
								CCMDirectoryNumber directoryNumber = new CCMDirectoryNumber(axlService);
								directoryNumber.setPattern(dn);
								directoryNumber.setRoutePartitionName(routePartition);

								if(directoryNumber.read()) {
									
									if(directoryNumber.getDirectoryUris() != null && wipe) {
										logger.info("Wiping existing directory URI for DN.");
										
										for(Entry<String, String> uri : directoryNumber.getDirectoryUris().entrySet()) {
											directoryNumber.removeDirectoryUri(uri.getKey(), uri.getValue());
										}
									}
									
									
									
									
									
									
									
									//loop through directoryUris and add individuals
									//remember to swap the directives with the correct values
									for(DirURI dirUri : directoryUris) {
										String uri = dirUri.getUri().replace(USERID_DIRECTIVE, userid);
										uri = uri.replace(DN_DIRECTIVE, dn);
										uri = uri.replace(PHONE_DIRECTIVE, device);
										directoryNumber.addDirectoryUri(uri, dirUri.getPartition(), dirUri.isPrimary(), dirUri.isAdvertise());
									}
									
									
									
									
									
									
									
									
									
									//TODO if the above doesn't work uncomment
									//directoryNumber.addDirectoryUri(userid + "@uat.msd.govt.nz", "msd_uri", false, true);
									if(directoryNumber.update()) {
										logger.debug("Success.");

										totalDnsUpdated++;
									} else {
										logger.error("There was an issue updating DN " + dn);
										
										dnsNotUpdated.add(dn);
									}
								} else {
									logger.error("There was an issue retrieving DN from Call Manager: " + dn);
								}
							}
							
						} else {
							logger.error("There was an issue retrieving phone from Call Manager: " + device);
						}
					} else {
						logger.warn(userid + " has no associated devices!");
						usersWithoutDevices++;
					}
				} else {
					logger.warn(userid + " not found in Call Manager! Skipping entry.");
				}
				

			}
			updater.report();
			
		} catch (IOException | ClientTransportException e) {
			if(e instanceof ClientTransportException) {
				logger.error("There was an error connecting to Call Manager.");
			} else {
				logger.error("There was an error reading the file. The expected format is an export of users from Call Manager.");
			}
			
			logger.error(ExceptionUtils.getMessage(e));
		}
		
		
		
		logger.info("Complete.");
	}
	
}
