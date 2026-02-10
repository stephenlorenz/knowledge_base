
>Action taken: User opens message notification email  
Expected behavior: EMAIL_VIEWED logged  
Current behavior: EMAIL_VIEWED logged twice on first runthrough with user without account, logged only once on second when taking the exact same steps, logged twice on third runtrhough with user with account not logged in on form submission  
Resolution: Unclear why it logged twice with 30 millisecond difference on first runthrough. Logging twice is better than not logging, if this needs to stay this way; however, additional events triggering EMAIL_VIEWED event logging (see below) introduce need to differentiate emails. Change event to MESSAGE_NOTIFICATION_EMAIL_VIEWED`

I can't replicate. I only receive one EMAIL_VIEWED, unless I keep opening the email.

> Action taken: User receives email verification code email  
Expected behavior: nothing logged  
Current behavior: EMAIL_VIEWED logged before email has been opened  
Resolution: Change event to VERIFICATION_CODE_EMAIL_VIEWED and only log after email is opened

I can't replicate. Nothing was logged for me.

> Action taken: User requests call again for phone verification (first call did not go through  
Expected behavior: AWS_PINPOINT_VOICE_MESSAGE logged in auditing log and message auditing log, as it is for initial phone call  
Current behavior: AWS_PINPOINT_VOICE_MESSAGE logged only in auditing log  
Resolution: Log in both places (and verify the same for text message resends)

Resolved.

> Action taken: User receives forgot password email  
Expected behavior: nothing logged  
Current behavior: EMAIL_VIEWED logged before email has been opened  
Resolution: Change event to FORGOT_PASSWORD_EMAIL_VIEWED and only log after email is opened

I can't replicate. EMAIL_VIEWED only occurred at the appropriate place, when the email was viewed. I received an FORGOT_PASSWORD_REQUEST event when I clicked on the Forgot password link. Opening the "Rally | Reset your password" email logged nothing.

> Action taken: User enters information on screen for reentering email, entering password and does not click next  
Expected behavior: Nothing logged until hitting Next, then SIGNUP_EMAIL_RENETERED / SIGNUP_PASSWORD_CREATED logged  
Current behavior: SIGNUP_EMAIL_RENETERED / SIGNUP_PASSWORD_CREATED logged after info is entered, before clicking next  
Resolution: [@jeanhee](https://github.com/jeanhee) reopening this issue, if it can be corrected easily, otherwise ok but note that it is incorrect

Punt on this for now.

> Action taken: various (opening email, re-entering email)  
Expected behavior: Events log only once  
Current behavior: Some events logging twice inconsistently when exact same steps are taken with same email (accounts deleted in between), on same study. On first runthrough, email viewed logged twice. On second, email viewed logged only once but signup email reentered logged twice. On third attempt, used same email and study but this time user has account and is not logged in, email viewed logged twice again.  
Resolution: Logging twice is better than not logging, but explore inconsistencies.

I can't replicate. I am experiencing consistent and expected audit events.

Is it possible that several people were testing at the same time and opening emails