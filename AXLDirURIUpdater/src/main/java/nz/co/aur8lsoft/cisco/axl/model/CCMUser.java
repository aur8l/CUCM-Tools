package nz.co.aur8lsoft.cisco.axl.model;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cisco.axl.api._10.GetUserReq;
import com.cisco.axl.api._10.ObjectFactory;
import com.cisco.axl.api._10.RUser;
import com.cisco.axl.api._10.RUser.AssociatedDevices;
import com.cisco.axl.api._10.StandardResponse;
import com.cisco.axl.api._10.UpdateUserReq;
import com.sun.xml.ws.client.ClientTransportException;

import nz.co.aur8lsoft.cisco.axl.AXLServiceHelper;

public class CCMUser implements CCMObject {
	
	static final Logger logger = LoggerFactory.getLogger(CCMUser.class);
	
	AXLServiceHelper _service = null;
	String username = null;
	RUser cucmUser = null;
	String uuid;
	String directoryUri = "";
	
	AssociatedDevices associatedDevices = null;
	
	boolean isSynchronized = false;
	
	public CCMUser(AXLServiceHelper service) {
		_service = service;
	}
	
	public CCMUser(AXLServiceHelper service, String username) {
		_service = service;
	}
	
	public List<String> getAssociatedDevices() {
		return associatedDevices.getDevice();
	}
	
	public void addAssociatedDevice(String device) {
		associatedDevices.getDevice().add(device);
		isSynchronized = false;
	}
	
	public void setUsername(String username) {
		this.username = username;
		isSynchronized = false;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getDirectoryUri() {
		return directoryUri;
	}
	
	public void setDirectoryUri(String directoryUri) {
		this.directoryUri = directoryUri;
		isSynchronized = false;
	}
	
	@Override
	public boolean update() throws ClientTransportException {
		ObjectFactory factory = new ObjectFactory();
		UpdateUserReq request = new UpdateUserReq();
		
		request.setUuid(uuid);
		
		UpdateUserReq.AssociatedDevices newAssociatedDevices = factory.createUpdateUserReqAssociatedDevices();
		
		for(String device : associatedDevices.getDevice()) {
			newAssociatedDevices.getDevice().add(device);
		}
		
		request.setAssociatedDevices(newAssociatedDevices);
		request.setDirectoryUri(factory.createUpdateUserReqDirectoryUri(directoryUri));
		
		try {
			StandardResponse response = _service.getService().updateUser(request);
			
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
	public boolean create() {
		return false;
	}
	
	/**
	 * Reads a phone object from CUCM provided the name has been set
	 * @throws ClientTransportException 
	 */
	@Override
	public boolean read() throws ClientTransportException {
		if(username != null) {
			GetUserReq request = new GetUserReq();
			request.setUserid(username);
			try {
				cucmUser = _service.getService().getUser(request).getReturn().getUser();
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

	private void init() {
		if(cucmUser != null) {
			//do some crap
			associatedDevices = cucmUser.getAssociatedDevices();
			uuid = cucmUser.getUuid();
			directoryUri = cucmUser.getDirectoryUri();
			isSynchronized = true;
		}
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return isSynchronized;
	}
}
