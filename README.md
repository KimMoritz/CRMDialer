# CRMDialer

This is an Android app demonstrating how:
   i. calls can be answered or dialed at a custom screen (since Android 7.0, Nougat)
   ii. CRM information such as that of Salesforce can be received and passed to the incoming call's splash screen
   iii. Google's Firebase can be used as a backend for data transport.
   
I am not actively developing this application, but it serves a good example of how to achieve the above goals. If you decide to use this code, remember to fill out the application.properties config file. Furthermore, you should probably move the Salesforce authentication to the backend or implement a more secure and customizable way of connecting, as this code was only used as a quick way of demonstrating certain features of the Android OS in concert with an experimental VoLTE setup where a call notification could be pushed to a server simultaneously as it was shown to a user on the phone.

For full functionality, you need the application running, a CRM Server (such as that posted on my github user's page), a Salesforce account and a Firebase account, but you could of course use any part of the code as an implementation example of any particular technique.

Kim
