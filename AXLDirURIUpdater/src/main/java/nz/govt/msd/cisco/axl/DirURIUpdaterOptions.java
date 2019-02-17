package nz.govt.msd.cisco.axl;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class DirURIUpdaterOptions extends OptionsBase {
	
	@Option(
			name = "help",
			abbrev = 'h',
			help = "Prints usage info.",
			defaultValue = "true"
	)
	public boolean help;
	
	@Option(
			name = "host",
			abbrev = 'o',
			help = "The Call Manager (publisher) host.",
			category = "startup",
			defaultValue = ""
	)
	public String host;
	
	@Option(
		    name = "port",
		    abbrev = 'p',
		    help = "The Call Manager AXL port.",
		    category = "startup",
		    defaultValue = "-1"
    )
	public int port;
	
	
	@Option(
			name = "file",
			abbrev = 'f',
			help = "The data used for bulk updating.",
			category = "startup",
			defaultValue = ""
	)
	public String file;
	
	@Option(
			name = "user",
			abbrev = 'u',
			help = "The AXL user name.",
			category = "startup",
			defaultValue = ""
	)
	public String username;
	
	@Option(
			name = "password",
			abbrev = 'k',
			help = "The AXL user password.",
			category = "startup",
			defaultValue = ""
	)
	public String password;
	
	@Option(
			name = "wipe",
			help = "Are you wiping the exisiting Directory URIs for DNs and users.",
			category = "startup", 
			defaultValue = "true"
	)
	public boolean wipe;
}
