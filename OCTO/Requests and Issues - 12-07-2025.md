### 1. Account and Database Access

We request the following access for our team1:

- **Production Admin Account**: We require one dedicated admin account for the production environment (it can be read-only if there are concerns, but it would be great to be able to read access to everything, including the db).

> The rrawal1@mgh.harvard.edu account now has the admin role in both the production and test environments.

- **Database Access (Test/Production)**: To facilitate detailed analysis and comparisons, we need access to the current Test or Production database snapshots3. Direct access to these snapshots within the Test environment would be ideal, assuming both systems (prod and test) are structurally identical.

> I have created a new beaumont database user in the DSC2U and Sprout2pt0 databases. Specifically, there are four databases: dsc2u_prod, dsc2u_test, sprout2pt0_prod, and sprout2pt0_test. The beaumont user has read access to these four databases. You will need to connect to a database by first connecting to the MGB VPN. From there you can use the host `lcs106.partners.org` and the port `45432`. For authentication, the database uses RDS IAM authentication.  You will need an aws_access_key_id and aws_secret_access_key, which I will send to you separately. Once you have these, you should install the AWS CLI and add the aws_access_key_id and aws_secret_access_key to your AWS credential profile.
>
> Once these steps are complete, you can use the following bash script to launch to launch psql, where beaumont-dsc2u-sprout2pt0 is the name of your AWS profile (you can leave out the --profile.... if you use the default profile):

```bash
#!/bin/bash

export PGPASSWORD=`aws rds generate-db-auth-token --hostname database-1.c3e0m2x2zq1s.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username beaumont --profile beaumont-dsc2u-sprout2pt0 | tr -d '\n'`

psql "host=lcs106.partners.org port=45432 dbname=postgres user=beaumont sslmode=require"
```

    

### 2. Third-Party Service Access

To verify transaction and tax processing end-to-end, we need read-only access to the following third-party service accounts:

- **Authorize.net Developer Access**: We request access to the Authorize.net developer portal to monitor and verify test transactions6. While we are receiving successful API responses, direct observation within the Authorize.net environment will help us gain familiarity and confirm full processing integrity.

> You should email the DSP program for access to Authorize.net.
    
- **TaxJar Account Access**: Similarly, we request access to the TaxJar account to ensure that all tax calculations and API integrations are functioning as expected.

> You should email the DSP program for access to TaxJar.

### 3. Workflow and Process Clarifications

We require clarification on the following operational workflows:

- Per our discussion, there is a bug in the current flow while trying to apply coupons for geographies that have applicable non-zero taxes, the validation seems to be failing which results into a generic error and the user cannot purchase the subscription, we will improve this in the new app.

> Thank you for finding this bug. The fix has been deployed to the TEST environment for testing. Once complete, we will deploy to PROD.
    
- Per our discussion, we will leave the validation of the zipcode based tax calculation lose as it is today in production, where upon entering an invalid zipcode (e.g., 12345 or 11111), the tax calculation is zero and the user is still allowed to proceed with the purchase11. This is a tradeoff to allow the account creation from other countries (except for the US).

> Perfect
    
- **Payment for Purchase Orders (POs)**: Per our discussion, while generating/purchasing the coupons, credit card payment is marked as optional, however, if that is the case the coupons generated at the end of that workflow are never activated13. The only way to generate the activated coupons is to add the credit card detail and complete the purchase.
    
    - During the meeting, we found out that there is a default credit card that gets used every time the coupons are generated with the billing address containing 125 Nashua Street.
        
    - Once we verify this, we can remove the word optional from the payment details.
        
    - And we can also remove the **Generate PO** part, as that is not required if adding the credit card info is mandatory17.
        
> Many of the admin features that you see were not spec'ed by the team and were developed organically as the need arose. The entire reporting module falls into this category as does the **Generate PO** functionality. We never needed to build the frontend logic to enable coupons created via PO.
### 4. Tax Calculation and Validation

We need clarification on tax behavior observed in the current test instance18:

- **State Tax Exemptions**: We have observed 0% tax being applied to transactions originating from California. Please confirm if certain states, including California, are configured to be exempt from taxes. We might be able to see this once we get developer access to TaxJar.

> I dug up an old email about this. I found this from a July 1, 2020 email where the same question was raised:

>> "For some states, if you don’t have a physical presence in the state, you don’t have to pay taxes until you cross a threshold in sales, at which point you are considered to have a virtual nexus and have to pay taxes. The threshold for this is usually fairly high (e.g. $500,000/yr for California; $100,000/yr for South Dakota) and sometimes applies to sales everywhere and sometimes to sales just in that state."
    
### 5. Credentials to invoke the API that verified MGB Insurance

To verify insurance we are calling the below API with the mentioned username and password:

- **EDI_USERNAME** = "3RWE" 
    
- **EDI_PASSWORD** = "HokTi$5601\*Es$V"
    
- **EDI_URL** = "[https://api.gatewayedi.com/v2/CORE](https://www.google.com/search?q=https://api.gatewayedi.com/v2/CORE)_CAQH/soap"

> The real EDI settings are located in the dsc2u_[prod]/[test].settings table. The password changes about once a year. We are notified by a system administrator, Duke Rhoden (drhoden@mgb.org), when the change is about to occur.

We found those credentials from the existing codebase that you have shared but we are getting **EDI ERROR: UnAuthorized => Invalid site ID or password**. Is this the correct id or password?

Also, we are currently supporting MGH insurance only so based on that we need some test data that will return an appropriate discount from the API which we can test in the sign-up flow.

> I don't know how we want to go about testing this. In the past we have each used our own insurance information, which didn't seem strange, because we were the people testing. I wonder if Duke would have test insurance ID numbers you could use. Please let me know if you'd like Jeanhee or me to introduce you (virtually, of course).

**cURL Request Example:**

Bash

```
curl 'https://test.dsc2u.org/rest/verify/insurance' \
-X POST \
-H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv: 145.0) Gecko/20100101 Firefox/145.0' \
-H 'Accept: application/json, text/plain, /' \
-H 'Accept-Language: en-US,en;q $I=0.5^{\prime}$' \
-H 'Accept-Encoding: gzip, deflate, br, zstd' \
-H 'Content-Type: application/json' \
-H 'Origin: https://test.dsc2u.org' \
-H 'Connection: keep-alive' \
-H 'Referer: https://test.dsc2u.org/payment' \
-H 'Cookie:_pk_id.03650317-c54e-494f-a589-915afbf75f47.df44-524411f418551473.1762882872.4.1764857902.1764842389.; ppms_privacy_03650317-c54e-494f-a589-915afbf75f47={%22visitorld%22:%22e248c221-3665-4b6e-9219-93d2813ed8fb%22%2C%22domain%22: {%22normalized%22:%22test.dsc2u.org%22%2C%22isWildcard%22:false%2C%22pattern%22 :%22test.dsc2u.org%22%2C%22consents%22:%22analytics%22: {%22status%22:1%2C%22updatedAt%22:%222025-11-11T17:41:16.078Z%22}}%2C%22stale Checkpoint%22:%222025-11-11T17:41:12.738Z%22}; _pk_ses.03650317-c54e-494f-a589-915afbf75f47.df44=*; SESSION=12e1ddc9-694e-410d-a38e-19f4f92cd558' \
-H 'Sec-Fetch-Dest: empty' \
-H 'Sec-Fetch-Mode: cors' \
-H 'Sec-Fetch-Site: same-origin' \
-H 'Priority: $u=0^{\prime}$' \
--data-raw '{"policyNumber":"1321", "policy Dob":"20291002","policy HolderFirstName":"Random","policyHol derLastName":"Person","insurance Code":"ALLWAYS"}'
```

SOAP Request Example:

The web service endpoint is $wsEndPoint = "[https://api.gatewayedi.com/v2/CORE](https://api.gatewayedi.com/v2/CORE)\_CAQH/soap"$.

XML

```
<?xml version $=^{\prime\prime}1.0^{\prime\prime}$ encoding="utf-8"?>
<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xmlns:xsd="http://www.w3.org/2001/XMLSchema">
 <soap:Header>
 <wsse: Security soapenv:mustUnderstand="true"
 xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-
 wssecurity-secext-1.0.xsd"
 xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
 <wsse: UsernameToken wsu: Id="UsernameToken-1538899148"
 xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-
 wss-wssecurity-utility-1.0.xsd">
 <wsse: Username>3RWE</wsse: Username>
 <wsse: Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-
 200401-wss-username-token-profile-
 1.0#PasswordText">HokTi\$5601*Es\$V</wsse: Password>
 </wsse: UsernameToken>
 </wsse: Security>
 </soap: Header>
 <soap:Body>
 <COREEnvelopeRealTimeRequest
 xmlns="http://www.caqh.org/SOAP/WSDL/CORERule2.2.0.xsd">
 <PayloadType xmlns="">X12_270_Request_005010X279A1</PayloadType>
 <ProcessingMode xmlns="">RealTime</ProcessingMode>
 <PayloadID xmlns="">MGHLCS EDI</PayloadID>
 <TimeStamp xmlns="">2025-12-08T11:17:28Z</TimeStamp>
 <SenderID xmlns="">3RWE</SenderID>
 <ReceiverID xmlns="">N4222
 </ReceiverID>
 <CORERuleVersion xmlns="">2.2.0</CORERuleVersion>
 <Payload xmlns=""><![CDATA[ISA*00* *00* *ZZ*3RWE
 *ZZ*N4293
 *251208*1117*^*00501*000000001*1*P*:~GS*HS*3RWE 3F8P*20251208*1117*1*X*00501
 0X279A1~ST*270*0001*005010X279A1~BHT*0022*13*MGHLCS
 EDI*20251208*1117~HL*1**20*1~NM1*PR*2*AllWays Health
 Partners*****PI*N4293~HL*2*1*21*1~NM1*1P*2*Mass
 General*****XX*1023049236~HL*3*2*22*0~NM1*IL*1*Person*Random**MI*1321~DMG*D8
 *20291002*M~EQ*30~SE*1*0001~GE*1*1~IEA*1*P~]]></Payload>
 </COREEnvelopeRealTimeRequest>
 </soap:Body>
</soap:Envelope>
```

**API Response Example:**

JSON

```
{
"id": null,
"insuranceCode": "ALLWAYS",
"insuranceName": null,
"policyNumber": "1321",
"policyDob": "20291002",
"policy HolderFirstName": "Random",
"policyHolderLastName": "Person",
"policy HolderNameAssertion": null,
"policy HolderNameVerified": null,
"claim Ticket": null,
"disbursementAmount": null,
"disbursementCalculation Method": null,
"insurance Group": null,
"valid": false,
"errorInd": true,
"message": "EDI ERROR: UnAuthorized => Invalid site ID or password."
}
```

Do you have any other requests or need clarification on a specific section of this document?