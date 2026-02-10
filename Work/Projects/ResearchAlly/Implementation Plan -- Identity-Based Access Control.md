## Phase 1: Cognito User Pool & Choice-Based Auth

This phase moves you from the "Lite" tier to "Essentials" to unlock Passkeys and choice-based login.

### 1.1 Create the User Pool

- [ ] **Navigation:** Open the **Cognito Console** → **User pools** → **Create user pool**.
    
- [ ] **Step 1 (Configure sign-in experience):** * Select **Email** and **Phone number** as sign-in identifiers.
    
    - Click **Next**.
        
- [ ] **Step 2 (Configure security requirements):** * **MFA:** Select **Optional MFA**.
    
    - **MFA methods:** Check **SMS message** and **Email message**.
        
    - Click **Next**.
        
- [ ] **Step 3 (Configure sign-up experience):** Leave defaults and click **Next**.
    
- [ ] **Step 4 (Configure message delivery):** * Choose **Send email with Cognito** (for testing) or **Amazon SES** (recommended for production).
    
    - Click **Next**.
        
- [ ] **Step 5 (Integrate your application):** * **User pool name:** `Test-Env-Pool`.
    
    - **Hosted UI:** Check **Use the Cognito Managed Login**.
        
    - **Domain:** Enter a unique prefix (e.g., `stephen-test-auth`).
        

### 1.2 Enable "Choice-Based" Passwordless Login

- [ ] **Navigation:** **User pools** → Select `Test-Env-Pool` → **Sign-in experience** tab.
    
- [ ] **Options for choice-based sign-in:** Click **Edit**.
    
- [ ] **Select Methods:** Check **Email message one-time password**, **SMS message one-time password**, and **Passkey**.
    
- [ ] **Passkey Settings:** Set "User verification mode" to **Preferred**. Enter your test site's domain (e.g., `site2.example.com`) as the **Relying Party ID**.
    
- [ ] Click **Save changes**.
    

### 1.3 Configure the App Client (For ALB)

- [ ] **Navigation:** **User pools** → Select `Test-Env-Pool` → **App integration** tab → **App clients** section.
    
- [ ] **Create App Client:** Click **Create app client**.
    
- [ ] **Settings:**
    
    - **App client name:** `ALB-Client`.
        
    - **Client secret:** Select **Generate a client secret** (Crucial: ALB requires this).
        
    - **Authentication flows:** Select **`ALLOW_USER_AUTH`**.
        
- [ ] **Managed Login Settings:** Click **Edit**.
    
    - **Allowed callback URLs:** `https://site2.example.com/oauth2/idpresponse`.
        
    - **OAuth 2.0 grant types:** Ensure **Authorization code grant** is checked.
        
    - **OpenID Connect scopes:** Check **openid**, **email**, and **profile**.
        
- [ ] Click **Save changes**.
    

---

## Phase 2: Authorization & Governance

This phase sets up your private "Tester" list so you don't have to call the Okta admins.

### 2.1 Create the Management Group

- [ ] **Navigation:** **User pools** → Select `Test-Env-Pool` → **Groups** tab.
    
- [ ] Click **Create group**.
    
- [ ] **Group name:** `AuthorizedTesters`.
    
- [ ] Click **Create**.
    

### 2.2 The Lambda Gatekeeper

- [ ] **Navigation:** Open the **Lambda Console** → **Functions** → **Create function**.
    
    - **Name:** `Cognito-Gatekeeper`.
        
    - **Runtime:** Python 3.12 (or latest).
        
- [ ] **Permissions:** In the **Configuration** tab → **Permissions** → Click the Role Name. In IAM, click **Add permissions** → **Create inline policy**:
    
    JSON
    
    ```
    {
        "Version": "2012-10-17",
        "Statement": [{
            "Effect": "Allow",
            "Action": "cognito-idp:AdminListGroupsForUser",
            "Resource": "*" 
        }]
    }
    ```
    
- [ ] **Code:** Paste the Python code provided in the previous plan and click **Deploy**.
    
- [ ] **Link to Cognito:** Go back to **Cognito** → `Test-Env-Pool` → **User pool properties** tab → **Lambda triggers** section → **Add Lambda trigger**.
    
    - **Trigger type:** Select **Pre-authentication**.
        
    - **Lambda function:** Select `Cognito-Gatekeeper`.
        
    - Click **Add trigger**.
        

---

## Phase 3: Application Load Balancer (ALB)

Wiring the "Identity Gate" to your specific subdomain.

- [ ] **Navigation:** Open the **EC2 Console** → **Load Balancing** → **Load Balancers**.
    
- [ ] Select your ALB → **Listeners and rules** tab → Select your **HTTPS:443** listener.
    
- [ ] Click **Manage rules** → **Add rule**.
    
- [ ] **Step 1 (Conditions):** Click **Add condition** → **Host header** → Enter `site2.example.com`.
    
- [ ] **Step 2 (Actions):** * Click **Add action** → **Authenticate**.
    
    - **Authenticate via:** Select **Amazon Cognito**.
        
    - **User pool:** Select your pool ID.
        
    - **App client:** Select your client ID.
        
    - **Advanced Settings:** Set **Session cookie name** to something unique like `AWSELBAuthSessionCookie-Test`.
        
- [ ] **Step 3 (Forward):** Click **Add action** → **Forward to target group** → Select your test site target group.
    
- [ ] Click **Save**.
    

---

## Phase 4: Branding & SMS Compliance

Final polish and regulatory hurdles.

- [ ] **Branding Navigation:** **Cognito** → `Test-Env-Pool` → **Branding** menu → **Managed Login**.
    
- [ ] Click **Launch branding editor**.
    
    - Upload your **Logo**.
        
    - Adjust the **Primary color** (this changes the "Sign In" button).
        
    - Click **Save and publish**.
        
- [ ] **SMS Sandbox Navigation:** Open the **SNS Console** → **Mobile** → **Text messaging (SMS)**.
    
- [ ] **Sandbox Destination Numbers:** Click **Add phone number** to verify your own phone for testing.
    
- [ ] **Support Request:** Click **Request increase** for "SMS Monthly Spend" to ensure the messages keep flowing.
    

---

### Verification Checklist

- [ ] Try visiting `site1.example.com` (Should be public).
    
- [ ] Try visiting `site2.example.com` (Should redirect to your branded Cognito page).
    
- [ ] Create a user manually in the Cognito Console.
    
- [ ] **Failure Test:** Attempt to log in with that user (Should fail because they aren't in the group yet).
    
- [ ] **Success Test:** Add the user to `AuthorizedTesters` and log in again (Should succeed with an SMS/Email code).