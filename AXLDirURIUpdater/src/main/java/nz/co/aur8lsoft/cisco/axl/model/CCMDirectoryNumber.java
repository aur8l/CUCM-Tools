package nz.co.aur8lsoft.cisco.axl.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cisco.axl.api._10.AddLineReq;
import com.cisco.axl.api._10.GetLineReq;
import com.cisco.axl.api._10.ObjectFactory;
import com.cisco.axl.api._10.RDirectoryUri;
import com.cisco.axl.api._10.RLine;
import com.cisco.axl.api._10.StandardResponse;
import com.cisco.axl.api._10.UpdateLineReq;
import com.cisco.axl.api._10.XDirectoryUri;
import com.cisco.axl.api._10.RLine.DirectoryURIs;
import com.cisco.axl.api._10.XFkType;
import com.cisco.axl.api._10.XLine;
import com.sun.xml.ws.client.ClientTransportException;

import nz.co.aur8lsoft.cisco.axl.AXLServiceHelper;

public class CCMDirectoryNumber implements CCMObject {

	static final Logger logger = LoggerFactory.getLogger(CCMDirectoryNumber.class);
	
	private AXLServiceHelper _service = null;
	private RLine cucmDirn = null;
	private String pattern;
	private String routePartitionName;
	private String alertingName;
	private String uuid;
	private DirectoryURIs directoryUris;
	LinkedHashMap<String, String> directoryUrisList = null;
	
	boolean isSynchronized = false;
	
	public CCMDirectoryNumber(AXLServiceHelper service) {
		_service = service;
	}
	
	public CCMDirectoryNumber(AXLServiceHelper service, String pattern, String routePartitionName) {
		_service = service;
		this.pattern = pattern;
		this.routePartitionName = routePartitionName;
		
		read();
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		isSynchronized = false;
	}

	public String getRoutePartitionName() {
		return routePartitionName;
	}

	public void setRoutePartitionName(String routePartitionName) {
		this.routePartitionName = routePartitionName;
		isSynchronized = false;
	}
	
	public void setAlertingName(String alertingName) {
		this.alertingName = alertingName;
		isSynchronized = false;
	}
	
	public String getAlertingName() {
		return this.alertingName;
	}
	
	public HashMap<String, String> getDirectoryUris() {
		if(directoryUris.getDirectoryUri() != null) {
			directoryUrisList = new LinkedHashMap<>();
			
			for(RDirectoryUri directoryUri : directoryUris.getDirectoryUri()) {
				directoryUrisList.put(directoryUri.getUri(),directoryUri.getPartition().getValue());
			}
		}
		
		return directoryUrisList;
	}
	
	public void addDirectoryUri(String uri, String partition, boolean isPrimary, boolean advertiseGlobally) {
		RDirectoryUri newUri = new RDirectoryUri();
		XFkType uriPartition = new XFkType();
		
		uriPartition.setValue(partition);
		newUri.setUri(uri);
		newUri.setPartition(uriPartition);
		newUri.setIsPrimary(isPrimary == true ? "t" : "f");
		newUri.setAdvertiseGloballyViaIls(advertiseGlobally == true ? "t" : "f");
		
		directoryUris.getDirectoryUri().add(newUri);
		
		isSynchronized = false;
	}
	
	public boolean removeDirectoryUri(String directoryUri, String partition) {
		for(RDirectoryUri uri : directoryUris.getDirectoryUri()) {
			if(uri.getUri().equals(directoryUri) && uri.getPartition().getValue().equals(partition)) {
				directoryUris.getDirectoryUri().remove(uri);
				isSynchronized = false;
				
				return true;
			}
		}
		
		return false;
	}


	@Override
	public boolean read() throws ClientTransportException {
		if(pattern != null && routePartitionName != null) {
			ObjectFactory factory = new ObjectFactory();
			XFkType routePartition = new XFkType();
			routePartition.setValue(routePartitionName);
			
			//A DN lookup needs a route partition 
			GetLineReq request = new GetLineReq();
			request.setPattern(pattern);
			request.setRoutePartitionName(factory.createGetLineReqRoutePartitionName(routePartition));
			
			try {
				cucmDirn = _service.getService().getLine(request).getReturn().getLine();
				init();
				
				return true;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(e instanceof ClientTransportException) {
					throw (ClientTransportException) e;
				}
				logger.error(ExceptionUtils.getStackTrace(e));
			}

		}
		
		return false;
	}

	@Override
	public boolean update() throws ClientTransportException {
		ObjectFactory factory = new ObjectFactory();
		UpdateLineReq request = new UpdateLineReq();
		
		request.setUuid(uuid);
		
		UpdateLineReq.DirectoryURIs newDirectoryUris = factory.createUpdateLineReqDirectoryURIs();
		
		for(RDirectoryUri directoryUri : directoryUris.getDirectoryUri()) {
			XDirectoryUri newDirectoryUri = new XDirectoryUri();
			newDirectoryUri.setAdvertiseGloballyViaIls(directoryUri.getAdvertiseGloballyViaIls());
			newDirectoryUri.setIsPrimary(directoryUri.getIsPrimary());
			newDirectoryUri.setPartition(factory.createXDirectoryUriPartition(directoryUri.getPartition()));
			newDirectoryUri.setUri(factory.createXDirectoryUriUri(directoryUri.getUri()));
			
			newDirectoryUris.getDirectoryUri().add(newDirectoryUri);
		}
		
		request.setDirectoryURIs(newDirectoryUris);
		request.setAlertingName(alertingName);
		request.setPattern(pattern);
		
		XFkType routePartition = new XFkType();
		routePartition.setValue(routePartitionName);
		request.setNewRoutePartitionName(factory.createUpdateLineReqNewRoutePartitionName(routePartition));
		
		try {
			StandardResponse response = _service.getService().updateLine(request);
			
			if(response.getReturn().equals(uuid)) {
				read();
				return true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(e instanceof ClientTransportException) {
				throw (ClientTransportException) e;
			}
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		
		return false;
	}

	@Override
	public boolean create() throws ClientTransportException {
		// TODO Auto-generated method stub
		if(pattern != null && routePartitionName != null) {
			ObjectFactory factory = new ObjectFactory();
			XFkType routePartition = new XFkType();
			routePartition.setValue(routePartitionName);
			
			XLine line = new XLine();
			line.setPattern(pattern);
			line.setRoutePartitionName(factory.createGetLineReqRoutePartitionName(routePartition));
			
			AddLineReq request = new AddLineReq();
			request.setLine(line);
			
			try {
				uuid = _service.getService().addLine(request).getReturn();
				read();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				if(e instanceof ClientTransportException) {
					throw (ClientTransportException) e;
				}
				logger.error(ExceptionUtils.getStackTrace(e));
			}
			
			return true;
		}

		return false;
	}
	
	private void init() {
		if(cucmDirn != null) {
			alertingName = cucmDirn.getAlertingName();
			pattern = cucmDirn.getPattern();
			uuid = cucmDirn.getUuid();
			directoryUris = cucmDirn.getDirectoryURIs();
			
			isSynchronized = true;
		}
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return isSynchronized;
	}

}
