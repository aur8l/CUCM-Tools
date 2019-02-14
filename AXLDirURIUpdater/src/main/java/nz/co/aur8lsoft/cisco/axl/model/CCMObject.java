package nz.co.aur8lsoft.cisco.axl.model;

public interface CCMObject {
	
	public boolean read();
	public boolean update();
	public boolean create();
	public boolean isSynchronized();
}
