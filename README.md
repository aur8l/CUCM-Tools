# CUCM Tools

This repository is hosting all the Cisco Call Manager tools I have come up with. See Wiki for description and usage.

## AXLDirURIUpdater
An 'en masse' updater for DNs directory URIs. This tool will take an users export from CCM in input, and use Cisco AXL to update directory uris for the first dn of the first associated device.
This project was initiated following a client need for updating directory uris for all the main extension of all users declared in Call Manager. However, after quickly realizing the BAT was not able to perform this operation and that manually doing it would not be sustainable, a solution had to be found.
This application is Java 1.8 based and uses Cisco AXL 10.5. It should be compatible for 8.5 - 10.5.

### Usage:
java -jar diruriupdater.jar OPTIONS
Options category 'misc':
  --[no]help [-h] (a boolean; default: "true")
    Prints usage info.

Options category 'startup':
  --file [-f] (a string; default: "")
    The data used for bulk updating.
  --host [-o] (a string; default: "")
    The Call Manager (publisher) host.
  --password [-k] (a string; default: "")
    The AXL user password.
  --port [-p] (an integer; default: "-1")
    The Call Manager AXL port.
  --user [-u] (a string; default: "")
    The AXL user name.
  --[no]wipe (a boolean; default: "true")
    Are you wiping the exisiting Directory URIs for DNs and users.

### To-do:
- Make the application compatible with Call Manager 12.5.
- Either clean up the WSDL to remove unnecessary operations or use Apache CXF to generate the Java sources. This will allow removing the warning message about WSDL import AND speed up the creation of the AXLPort used to communicate with AXL.


