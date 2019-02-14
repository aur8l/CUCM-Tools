# CUCM Tools

This repository is hosting all the Cisco Call Manager tools I have come up with. See Wiki for description and usage.

## AXLDirURIUpdater
An 'en masse' updater for DNs directory URIs. This tool will take an users export from CCM in input, and use Cisco AXL to update directory uris for the first dn of the first associated device.
This project was initiated following a client need for updating directory uris for all the main extension of all users declared in Call Manager. However, after quickly realizing the BAT was not able to perform this operation and that manually doing it would not be sustainable, a solution had to be found.
This application is Java 1.8 based and uses Cisco AXL 10.5. It should be compatible for 8.5 - 10.5.

### To-do:
- Make the application compatible with Call Manager 12.5.


