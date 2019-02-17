package nz.govt.msd.voiceteam.AXLDirURIUpdater;

import java.util.ArrayList;
import java.util.Map.Entry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import nz.govt.msd.cisco.axl.AXLServiceHelper;
import nz.govt.msd.cisco.axl.model.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	AXLServiceHelper service = null;
	CCMUser user = null;
	CCMPhone phone = null;
	CCMDirectoryNumber dn = null;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	service = new AXLServiceHelper();
    	service.setUcAdmin("CCMAdmin");
    	service.setUcHost("tddcv020.ssi.govt.nz");
    	service.setUcPort("8443");
    	service.setUcPassword("St31nL@g3r");
    	service.initialize();
    	
    	assertTrue(service.getService() != null);
    	
//    	user = new CCMUser(service);
//    	user.setUsername("ocowa001");
//    	
//    	boolean result = user.read();
//    	assertTrue(result);
//    	assertEquals(user.getUsername(), "ocowa001");	//optionnal we know it is equals if the above is true
//    	assertTrue(user.getDirectoryUri().equals(""));
//    	user.setDirectoryUri("boobies@big");
//    	user.update();
//    	assertTrue(user.getDirectoryUri().equals("boobies@big"));
//    	
//    	user.setDirectoryUri("");
//    	user.update();
//    	assertEquals(user.getDirectoryUri(),"");
//    	
//    	phone = new CCMPhone(service);
//    	phone.setName(user.getAssociatedDevices().get(0));
//    	result = phone.read();
//    	assertTrue(result);
//    	
//    	dn = new CCMDirectoryNumber(service);
//    	Entry<String, String> dnEntry = phone.getDirectoryNumber().entrySet().iterator().next();
//		String pattern = dnEntry.getKey();
//		String routePartition = dnEntry.getValue();
//    	dn.setPattern(pattern);
//    	dn.setRoutePartitionName(routePartition);
//    	result = dn.read();
//    	assertTrue(result);
//    	
//    	dn.removeDirectoryUri("testicles@big", "ICT");
//    	dn.update();
//    	
//    	dn.getDirectoryUris().forEach((uri, partition) -> {
//    		if(uri.equals("testicles@big") && partition.equals("ICT")) {
//    			assertFalse(true);
//    		}
//    	});
//    	
//    	
//    	dn.addDirectoryUri("testicles@big", "ICT", false, true);
//    	dn.update();
//    	assertTrue(dn.getDirectoryUris().containsKey("testicles@big") && dn.getDirectoryUris().get("testicles@big").equals("ICT"));
//    	
//    	
//    	ArrayList<CCMDirectoryNumber> list = (ArrayList<CCMDirectoryNumber>) service.retrieveMultiple(CCMDirectoryNumber.class);
//    	
//    	list.forEach((dn) -> {
//    		System.out.println(dn.getPattern());
//    	});
//    	
//    	
    	
    	phone = new CCMPhone(service);
    	phone.setName("SEP0CD0F8F445AD");
    	boolean result = phone.read();
    	phone.wipe();
    	
    	System.out.println("Complete.");
    	
    }
}
