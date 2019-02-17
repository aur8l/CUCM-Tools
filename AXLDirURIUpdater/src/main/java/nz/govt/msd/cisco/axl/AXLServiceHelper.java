package nz.govt.msd.cisco.axl;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import com.cisco.axl.api._10.LLine;
import com.cisco.axl.api._10.ListLineReq;
import com.cisco.axl.api._10.ListLineReq.SearchCriteria;
import com.cisco.axl.api._10.ListLineRes;
import com.cisco.axl.api._10.XFkType;
import com.cisco.axlapiservice.AXLAPIService;
import com.cisco.axlapiservice.AXLError;
import com.cisco.axlapiservice.AXLPort;

import nz.govt.msd.cisco.axl.model.CCMDirectoryNumber;

public class AXLServiceHelper {
	String ucHost;
	String ucPort;
	String ucAdmin;
	String ucPassword;
	
	boolean initialized = false;
	
	AXLAPIService axlService = null;
	AXLPort axlPort = null;
	
	public AXLServiceHelper() {}

	public void setUcHost(String ucHost) {
		this.ucHost = ucHost;
	}

	public void setUcPort(String ucPort) {
		this.ucPort = ucPort;
	}

	public void setUcAdmin(String ucAdmin) {
		this.ucAdmin = ucAdmin;
	}

	public void setUcPassword(String ucPassword) {
		this.ucPassword = ucPassword;
	}
	
	public boolean isInitialized() {
		return initialized;
	}
	
	public void initialize() {
		axlService = new AXLAPIService();
		axlPort = axlService.getAXLPort();
		
		String validatorUrl = "https://" + this.ucHost + ":" + this.ucPort + "/axl/";
		
		((BindingProvider) axlPort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, validatorUrl);
		((BindingProvider) axlPort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, this.ucAdmin);
        ((BindingProvider) axlPort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.ucPassword);
        
        initialized = true;
	}
	
	public AXLPort getService() {
		return axlPort;
	}
	
	//TODO test that crap
	public List<?> retrieveMultiple(Class<?> objectType) {
		try {
			if(objectType.equals(CCMDirectoryNumber.class)) {
				ListLineReq request = new ListLineReq();
				LLine value = new LLine();
				value.setPattern("");
				XFkType ble = new XFkType();
				ble.setValue("");
				value.setRoutePartitionName(ble);
				SearchCriteria criteria = new SearchCriteria();
				criteria.setRoutePartitionName("%");
				request.setReturnedTags(value);
				request.setSearchCriteria(criteria);
				System.out.println(System.currentTimeMillis());
				ListLineRes response = axlPort.listLine(request);
				
				ArrayList<CCMDirectoryNumber> listDn = new ArrayList<>();
				for(LLine dn : response.getReturn().getLine()) {
					CCMDirectoryNumber newDn = new CCMDirectoryNumber(this);
					newDn.setPattern(dn.getPattern());
					newDn.setRoutePartitionName(dn.getRoutePartitionName().getValue());
					listDn.add(newDn);
				}
				System.out.println(System.currentTimeMillis());
				
				return listDn;
			} 			
		} catch (AXLError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
