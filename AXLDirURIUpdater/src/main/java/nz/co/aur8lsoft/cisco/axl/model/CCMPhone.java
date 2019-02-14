package nz.co.aur8lsoft.cisco.axl.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.axl.api._10.GetPhoneReq;
import com.cisco.axl.api._10.NameAndGUIDRequest;
import com.cisco.axl.api._10.RPhone;
import com.cisco.axl.api._10.RPhoneLine;
import com.cisco.axl.api._10.StandardResponse;
import com.sun.xml.ws.client.ClientTransportException;

import nz.co.aur8lsoft.cisco.axl.AXLServiceHelper;

public class CCMPhone implements CCMObject {
	
	static final Logger logger = LoggerFactory.getLogger(CCMPhone.class);
	
	String name = null;
	LinkedHashMap<String, String> directoryNumbers = null;
	String directoryUri = null;
	String directoryPartition = null;
	String uuid = "";
	
	RPhone cucmPhone;
	
	AXLServiceHelper _service = null;
	
	boolean isSynchronized = false;
	
	/**
	 * Create an empty CCMPhone to interact (create, read, update) with Phone objects in CUCM
	 * @param service
	 */
	public CCMPhone(AXLServiceHelper service) {
		_service = service;
		directoryNumbers = new LinkedHashMap<>();
	}
	
	/**
	 * Create a CCMphone as a reference of an existing Phone object in CUCM
	 * @param service
	 * @param name
	 */
	public CCMPhone(AXLServiceHelper service, String name) {
		_service = service;
		directoryNumbers = new LinkedHashMap<>();
		isSynchronized = false;
	}
	
	public Map<String, String> getDirectoryNumber() {
		return directoryNumbers;
	}

	public void setName(String name) {
		this.name = name;
		isSynchronized = false;
	}
	
	public String getName() {
		return name;
	}
	
	//TODO Test this shit
	public boolean wipe() {
		NameAndGUIDRequest request = new NameAndGUIDRequest();
		request.setName(name);
		request.setUuid(uuid);
		try {
			StandardResponse response = _service.getService().wipePhone(request);
			if(response.getReturn().equals(uuid)) {
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

	/**
	 * @return 
	 * 
	 */
	@Override
	public boolean update() {
		return false;
	}
	
	@Override
	public boolean create() {
		return false;
	}
	
	/**
	 * Reads a phone object from CUCM provided the name has been set
	 */
	@Override
	public boolean read() throws ClientTransportException {
		if(name != null) {
			GetPhoneReq request = new GetPhoneReq();
			request.setName(name);
			try {
				cucmPhone = _service.getService().getPhone(request).getReturn().getPhone();
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
		if(cucmPhone != null) {
			name = cucmPhone.getName();
			for(RPhoneLine line : cucmPhone.getLines().getLine()) {
				directoryNumbers.put(line.getDirn().getPattern(), line.getDirn().getRoutePartitionName().getValue());
			}
			uuid = cucmPhone.getUuid();
			
			isSynchronized = false;
		}
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return isSynchronized;
	}
	
}
