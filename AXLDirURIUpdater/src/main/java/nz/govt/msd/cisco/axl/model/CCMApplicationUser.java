package nz.govt.msd.cisco.axl.model;

import nz.govt.msd.cisco.axl.AXLServiceHelper;

public class CCMApplicationUser implements CCMObject {

	private AXLServiceHelper _service;

	CCMApplicationUser(AXLServiceHelper service) {
		this._service = service;
	}
	
	@Override
	public boolean read() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean create() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSynchronized() {
		// TODO Auto-generated method stub
		return false;
	}

}
