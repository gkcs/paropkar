# paropkar
Complaint Filing System


Paropkar is a project to help customers file complaints to their local Municipal Corporation.

The complaints are maintained in a MySQL database and external platforms like Twitter are integrated to allow users to share their complaints.

Abbreviations:

OTP: One Time Password

UIDAI: Unique Identification Authority of India

KYC: Know Your Customer

MC: Municpal Corporation


Registration:

1) The user enters his/her aadhaar_number.

2) We send the number to UIDAI requesting for an OTP to the user's registered mobile number.

3) The user now enters the OTP to complete registration (This process can be automated by reading the user's SMS through app). The OTP is sent to UIDAI to get the KYC details of the user. In case the OTP is valid, the user details are added to the system and registration is completed. Else, the error code from UIDAI is forwarded to user in a readable format.

File Complaint:

1) The user fills a form for complaint details.

2) The user clicks submit. An option of submitting after signing with UIDAI is presented.

3) In case the user wants to submit with a UIDAI signature, an OTP request is made to UIDAI. On correct entering of OTP, UIDAI signs the complaint form document.

4) The complaint is sent to MC for investigation. 

5) The user can now view the complaint on his list of complaints. He/She can share, generate reports and monitor complaints.



The project folder (paropkar) contains

1) The Class diagram

2) ER diagram 

3) Use Case Diagram

4) The source folder containing code.



Inside the src/main/java/paropkar/ contains dao and model packages.

The dao package contains classes to get and update database objects. The DataAccessor class connects and interacts with the database. Implementation of the abstract methods is to be completed.

The model package contains classes to map database entities to java objects.

Database used for transactions and request processing: MySQL


Database to be used for analytics: Cassandra
