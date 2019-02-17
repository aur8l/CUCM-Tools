package nz.govt.msd.cisco.axl.model;

public interface CCMObject {
	
	public boolean read();
	public boolean update();
	public boolean create();
	public boolean isSynchronized();
}
