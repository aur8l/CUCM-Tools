package nz.co.aur8lsoft.cisco.axl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	
	static final Logger logger = LoggerFactory.getLogger(Test.class);
	
	private static int totalUsers = 100;
	private static int usersWithoutDevices = 10;
	private static int totalDnsUpdated = 90;
	private static List<String> dnsNotUpdated;
	
	private static File reportFile = new File(System.getProperty("user.dir") + "\\report.txt");

	public static void report() {
		try {
			FileUtils.touch(reportFile);
			if(reportFile.exists()) {
				logger.info("Writing report file to " + reportFile.getPath());
				FileUtils.writeStringToFile(reportFile, "Total of users to process in file: " + totalUsers + "\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Total of users successfully processed: " + (totalUsers - usersWithoutDevices) + "\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Number of Directory Numbers updated: " + totalDnsUpdated + "\n", StandardCharsets.UTF_8, true);
				FileUtils.writeStringToFile(reportFile, "Directory Numbers failed: \n", StandardCharsets.UTF_8, true);
				
				for(String dn : dnsNotUpdated) {
					FileUtils.writeStringToFile(reportFile, dn + "\n", StandardCharsets.UTF_8, true);
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
			// TODO Auto-generated catch block
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
	
	public static void main(String[] args) {
		dnsNotUpdated = new LinkedList<>();
		
		dnsNotUpdated.add("12345");
		dnsNotUpdated.add("54321");
		dnsNotUpdated.add("11111");
		
		report();
		
	}
}
