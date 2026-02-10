

## OIDC tasks
- [ ] logout (waiting on Paul)
- [x] redirect after login
- [x] use the MGB username, not the email, to create new accounts

## Focused priorities

- [x] Super Admin Users Tool
	- [x] Figure out how to maintain roles with contracts in UI
	- [x] update user creation linking current contract to user role for that contract
	- [x] update user login, linking current contract to user role for that contract
	- [x] Organizations
		- [x] remove IRB maintenance
	- [x] Create new Contract Management
	- [x] Create new IRB Management tool
	- [x] Augment Brand Management tool
- [x] Migrate scl30 database
	- [x] first rename urls from *.scl30.org to *.rocto.org
- [x] Can't click on study cards on homepage, works on search page
- [x] Can't search on /manage page
- [x] We should also support /manage/projects URL (should be the same as /manage)
- [x] We should add support for /manage/project/{studyKey} as well as
- [ ] Add /admin/sessions as official Angular admin tool, not just mustache template
- [x] Call Lisa
- [x] Investigate /admin/ngproxy....why?
- [x] Pay bills:
	- [x] Town water bill
	- [x] Eastern Gas bill
- [x] Call town about trash can
- [x] Remove StudyImportTO.studyImportStatus
- [x] Create new Rally RESTful API Documentation/Proposal
- [x] Work on Study refactorings:
	- [x] Change study.metadata column to study.model
		- [x] Remove "FormGroup" from each of the primary elements of the new model column
	- [x] Investigate using converter on Study table/entity
- [ ] 



# Still TODO for OCTO

- [x] Group Tools should just appear for MGB (at the moment)
- [x] In posting tool and in PI widget, the PI email domain should be limited to contract/brand patterns or API lookup. Look in Study Contacts modal on project page for error message "This email is not permitted. Please use an authorized email address."
- [x] Should PI credentials be required?
- [x] PI lookup from API email address not working
- [x] Test new organization request
- [x] In Super Admin Organization Edit Modal, the Brand and Contract Associations are not working.
- [x] Markdown not working in Group Tools editor, just displays `[object Promise]`
- [ ] Why are there two of the same document in protocol # 20234508
- [ ] Confirm custom IRB migration process
- [ ] Brand Admin Tool
- [ ] Contract Admin Tool
- [ ] Experiment with CMS Tool
- [ ] Evaluate and implement more brand/contract caching
- [ ] Work on creating new Contract & Brand
	- [ ] SQL scripts (if necessary)
	- [ ] AWS API to create new Route 53 CNAME(s)



## Sanity Check on BCH demo site

- [x] IRB locked on new study
- [x] uploaded image not displaying (still using canned image)
	- [x] 4.1M image was too large (after some experimenting, less than 1M works)
	- [x] Provide image too large feedback to Posting Tool user
- [x] study_auth was not populated for the new study
	- [x] It might be populated on original save (which I thought I fixed) but it is disappearing later on
- [x] On the project page, the `Publish` button is available too soon. For example, it is not taking into account the IRB approval process.
- [x] On a new study, after picking an API IRB protocol (e.g. from Insight), the "Next" button doesn't work to move to the next page of the Posting Tool. You can work around this by leaving and returning to Posting Tool.
- [x] Not able to send PI publication request email
	- [x] "Failed to generate email from template."
- [x] Find all instances of {{brand.brandName}} in templates and change to {{{brand.brandName}}}
- [x] A single quote character in the brand.name appears as `&#x27;` in received email subject for `PI_PUBLICATION_REQUEST_NOTIFICATION` email
- [x] Test the Super Admin Template Tool. It seems that when editing the `PI_PUBLICATION_REQUEST_NOTIFICATION` subject template, the model was broken. It might not be a problem, but I should just check.


- [x] Why does Holly see contract admin for octo-dev?
- [x] Investigate why BCH contract is not limited by 
- [ ] Required documentation needs to be reviewed.
- [x] Why do some fields show up as having errors before you have a chance to edit them
- [x] On publication, remove check about recruitment end date being beyond https://github.com/rallyforresearch/rally/issues/2204




## Search
- [x] Affiliate Organization search filter, needs to be brand aware (only display organizations affiliated with brand) (1 day)
- [x] Make sure that projects associated with brands configured to be excluded from OCTO, are in fact excluded. (1 day)

## Saved Search 
- [x] Saved Search modal missing buttons and modal window failed to auto-resize to fit content (1 day)
- [x] Legacy Saved Searches need to be updated to include MGB as brand. Going forward the same brand rules apply as to the original query, so brand will already be considered in save search (1 day)

## User Profile

##### Saved Searches
- [x] Limit display of Saved Searches to specific Brand (if OCTO brand, show all) in reverse chronological order based on creation (not run) date
- [x] Add link out to OCTO Brand Profile for complete list of Saved Searches
- [x] When not OCTO, display **all** saved searches

##### My Projects
- [x] Limit display of My Projects to specific Brand (if OCTO brand, show all), in reverse chronological order of form submission
- [x] Add link out to OCTO Brand Profile for complete list of My Projects
- [x] If roster entry has been downloaded, display under status: “Note: This team may be tracking your status outside of this system.”

## Researcher Tools
- [x] Add "Request Researcher Access" to menu for users who do not already have researcher access.
- [x] Only list Projects for that Brand. Link out available on OCTO.
- [x] Finish study publication 
- [ ] Speed up /manage page (it loads slowly because of Posting Tool validation)
- [x] Disable project management from OCTO brand, but provide links to brand pages where researcher has access.

## Posting Tool
- [ ] Review more closely
	- [ ] Known issues
		- [x] For new projects IRB Protocol Details section is always "locked"
		- [x] After you complete a required field, it remains red
		- [x] Study should never save without the model
			- [x] throw exception, hard stop
		- [x] When starting a new project, when you quit the posting tool, you are navigated to /manage/project/undefined (which breaks)
		- [x] With new study, after enrollment page you loose access to the original pages. This should only happen after a study has been published. I think this is happening after the event "continueToPostingTool" is posted
		- [x] When creating a posting until bch.rocto.org, it was still assigned the rally.rocto.org brand. Why? **Answer:** this was due to running behind Angular proxy service; every request appears to come from localhost
		- [x] Now on new studies, you cannot get past the `Tell us about your Recruitment` page
		- [ ] Missing Custom IRB Required Documentation button
			- [ ] If you leave the posting tool and return, the required documentation options appear
		- [x] Returning to the Posting Tool is not placing you on the correct page (where you left last time)
	- [x] Look into old bug (not sure if it still exists) where if you change the study_key after a the first save, it creates a duplicate study

## Homepage
- [x] Featured studies not appearing to honor contract.exclusive_ind and is displaying MGB studies on OCTO homepage

## Super Admin
- [ ] Complete updated Brand Admin tool
- [ ] Create Contract Admin tool

### Publication Issues
- [x] For the study http://rally.rocto.org:8080/manage/project/4bfe432f-fffe-4ec8-8ec0-f887ad8ba685, the "Publish Changes" button will not go away, despite publishing
- [x] Sometimes I can publish without sending to PI, other times it does
- [x] Need to implement logic behind "THIS PROJECT IS AWAITING PI APPROVAL" message. It aways appears now regardless of project publication state


## General
- [x] I can't login via the www.rocto.org site, but rally.rocto.org and bch.rocto.org work fine.
- [ ] Research adding SAML2 back into project (not necessary because we can use wildcards, e.g. rally.massgeneralbrigham.org, *.researchally.org)
- [x] This URL does not redirect after login (instead it goes to the homepage): http://rally.rocto.org:8080/manage/study/publish/4bfe432f-fffe-4ec8-8ec0-f887ad8ba685    but this does: http://rally.rocto.org:8080/profile.  Why?



## octo-app migration
- [x] secure messaging library, master-detail-lib might need work because it has it's own internal router-outlet
- [x] if we have styling problems, look for //@import '../../../../styles/mixins' throughout (path might differ slightly depending on the file)
- [x] i18n translations will need to be converted
- [x] Consolidate Apps:
	- [x] contract-admin
	- [x] super-admin
	- [x] researcher-tools (formerly rally-manager-dashboard)
		- [x] messaging tab search doesn't work
		- [x] `Enrollment Progress` panel
		- [x] `Recuiting` panel empty (should include at least a message)
		- [x] Why can't I publish? Why `Fix Errors` for http://rally.scl30.org:4200/manage/project/21fd8468-ae84-4394-bc48-6c93fcea5612
		- [x] Compare conversation.ts between versions and make sure new *ngIf conditionals are not breaking any logic
	- [x] rally-groups
	- [x] rally-group-manager-dashboard
		- [x] /group/management/edit/ layout with side-nav
		- [x] image editing not working (non-existent)
		- [x] text editing not visible
		- [x] color picker not working
	- [x] rally-login-lite
	- [x] rally-user-dashboard
	- [x] rally-secure-messaging
	- [x] rally-notification-dashboard
	- [x] rally-mailing-list-survey
	- [x] developer-tools-documentation (/Users/scl30/workspace/ResearchAllyDeveloperToolsDocumentation)
	- [x] ~~rally-web-components
- [x] Incorporate consolidated apps
	- [x] rally-login-lite
		- [x] (re)add support for "pickupEmail" on login
		- [x] CSR issue
		- [x] login modal closing issue
	- [x] rally-user-dashboard
	- [x] rally-groups
	- [x] rally-group-manager-dashboard
	- [x] rally-admin
	- [x] rally-posting-tool (the same as rally-manager-dashboard)
	- [x] rally-secure-messaging
	- [x] rally-manager-dashboard
	- [x] rally-notification-dashboard
	- [x] rally-mailing-list-survey
	- [x] developer-tools-documentation
- [x] Consider migrating i18n to transloco...
- [x] Slow /manage (manage projects page load)
- [x] Feedback page needs to be brand aware
	- [x] pull feedback email from brand tables
	- [x] XSRF-TOKEN on POST
- [x] Super Admin not forcing re-authentication



## Level 2 Priorities
 
 - [x] Work on National Grid Net Metering Schedule Z

## Tips

- Command-` switches projects in IntelliJ
- 


## Spring v2.7.x to v3.4.4 Migration

### The following needs more consideration

#### Security Tasks

- [x] SAML stuff (mostly due to javax to jakarta breaking change):
	- [x] PRPSAMLProcessingFilter.java
	- [x] RallySAMLEntryPoint.java
	- [x] RallySAMLUtil.java
- [x] JMS stuff
- [x] Consider enabling CSRF (look in WebSecurityConfig.java): this will require all POSTS/PUTS/DELETES include special header (might be out-of-scope)
- [x] Developer quick login
- [x] Masquerade not working
- [x] Angular development not working with authentication (CORS error)
- [x] @EnableMethodSecurity not working (I can access methods with @Secured({"ROLE_ADMIN"}) without needing to login)
- [ ] Okta login (awaiting guidance from MGB IS): opened ServiceNow ticket
- [x] The follow web security rule is needed for messaging to work, but it currently breaks log across the board: `http.addFilterBefore(new RallyLoginInterceptFilter(messagingService), UsernamePasswordAuthenticationFilter.class)`


#### Other Tasks

- [ ] Terms of Use retrieval from MGB website which uses an old version of Apache Commons HttpClient is broken
- [ ] Change study.metadata column to study.model
	- [ ] Remove "FormGroup" from each of the primary elements of the new model column
	- [ ] Investigate using converter on Study table/entity




## Consider refactoring study and study_builder

- [ ] backup database
	- [ ] practice db restoration
- [x] make a copy of the study table:
```sql
CREATE TABLE study_legacy AS SELECT * FROM study;
```
- [ ] trim study* table DDL
- [ ] are saved search emails audited (so we can track how many saved searches) are triggered?
## Refactoring studyId (study_id) to studyKey (study_key)

- [x] Java code refactoring
	- [x] look for :studyId parameters in JDBC queries/statements
	- [x] fix Search
	- [x] `StudyImportTOResponse.java`  and `StudyUpdateRecruitingTO` are weird rallyStudyId => studyKey
- [x] mustache templates
	- [x] manage/preview/embed
	- [x] manage/index
- [x] AngularJS apps
	- [x] studyApp
- [ ] email templates, e.g.: (add studyKey, studyUuid, and brandShortCode)
	- [ ] REQUEST_APPROVED
	- [ ] AUTH_REQUEST
	- [ ] ROSTER_EMAIL
	- [ ] ROSTER_EMAIL_COLD_CALL
	- [ ] CONTRACT_IRB_MESSAGE_RESPONSE
	- [ ] FORM_EMAIL_RE_VERIFICATION
	- [ ] CONTRACT_IRB_REQUEST_STATUS_CHANGE_NOTIFICATION
	- [ ] PUBLICATION_MESSAGE
	- [ ] PUBLICATION_API_MESSAGE
	- [ ] FORM_EMAIL_RE_VERIFICATION
```Java
Map<String, Object> parameters = emailTemplateService.getEmailTemplateParameters(templateCode);  
parameters.put("studyKey", study.getStudyKey());  
parameters.put("studyUuid", study.getStudyUuid());  
parameters.put("brandShortCode", brandService.getBrandShortCode());  
parameters.put("studyTitle", study.getTitleApproved(LocaleUtils.getLocaleCode()));  
emailTemplateMessageTO.setParameters(parameters);
```
- [ ] Study Page Caches need to take brand into account
	- [ ] studyPageModelCache
	- [ ] restStudyPageCache
- [x] Angular httpClient (XHR) calls
- [ ] Database change
	- [ ] Need to convert all published study models from studyId to studyKey
- [ ] All `findByStudyKey` (and variations, thereof) need to take brand into consideration
- [x] Test WebUtils.sanitizeUserInput and WebUtils.safeUrl once I can compile the project again(!)
- [ ] Look into preventing form loading/submitting if the form is marked as eligibility and the study is not recruiting
- [ ] Probably for Priya:
	- [ ] update these email markup generation functions:
		- [ ] p_broadcast_email_body_text_json
		- [ ] public_email_body_json


## Octo Angular App


> [!Work Around for Chrome and Angular Material Stylesheets] Work Around for Chrome and Angular Material Stylesheets
> There appears to be an insidious bug with the way Chrome pulls Angular Material stylesheets while the Chrome DevTools panel is open. You can get around this by disabling the "Enable Local Overrides" checkbox from the DevTools `Sources` tab on the  style.scss/css file.

- [x] Change method behind studyUrlBase (/admin/study/preview): `${this.studyUrlBase}?s=${study.id}&p=${this.published}` to accept studyId instead of studyKey



## Working Tasks...


- [x] Study admin free text search currently looks at several study table fields, e.g. title, title_approved, summary, etc. These might be the wrong place to look, we probably need to look into the study_builder.metadata for this. https://github.com/rallyforresearch/rally/blob/4ef699db6b10359a6b1ebc27cd06e7da7b719a89/core/src/main/java/edu/harvard/mgh/lcs/rally/service/StudyServiceImpl.java#L3832-L3931
- [x] Create JSON demo for Insight study team
- [x] Look into Rally 3.0.0.0 "issues" and Rally-MGB-Bridge app
- [x] Make sure we populate the `study.investigator_organization_id` field for "Insight" (really APIv#) studies. This is needed when publishing other studies to confirm that the IRB usage is unique.
- [x] Changing the recruiting end date in the posting tool does _not_ change the date on the Project page, Recruiting card
- [x] Posting Tool issues
	- [x] The "Save and Quit" button takes you to the project listing. It should take you back to the project page.
	- [x] Image Rights doesn't display in "Problems" (but is still considered missing when attempting to publish)
	- [x] IRB Contact Email/Phone "missing" but are unaccessible in the posting tool and also doesn't display in "Problems"
	- [x] The Site PI credentials should be from a dropdown and reflective of the lookup. Actually, this is not true.
	- [x] Add "At which institutions is the project being conducted?" to the Posting Tool.
	- [ ] Look into bug where changing the study url (before study is published) creates a new study
- [x] Find all places where I create a `StudyES` or `StudyESRO` and update to use `StudyBuilder` metadata
- [x] Do an inventory of all methods which use study.study_id (a.k.a. "studyKey") to select/update a study. These need to be replaced either with:
	- [x] a combination of study_id and brand
	- [x] or, study.id
	- [x] we would need to convert the models in all study_publications to use studyKey instead of studyId
- [x] Consider renaming `study.study_id` to `study.study_key`
- [x] Consider merging *all* Angular apps into one uber app
- [x] Work on other Project Management tabs: Roster, Manage Forms, Preview Post, (Messaging seems fine, at the moment)
- [ ] Should we disable access to a study eligibility form if the study is not recruiting? This would prevent people from bookmarking forms are completing them after recruitment has stopped.


##### Study Publication Process
- [x] `AD End Date` is causing the missingRequiredFields to fail the "Fix Errors" validation check
- [ ] the main difference is the Principal Investigator (PI) verification workflow
	- [ ] this doesn't apply if the PI is the user publishing
- [ ] Investigate locking options for [[Study Contacts card]] and [[Site Principal Investigator (PI) card]]


##### IRB Protocol Verification Tool
- [ ] Implement changes to [[IRB Protocol Verification Tool]]
	- [x] Hide `Communication` tab from open view
	- [x] When IRB protocol is locked, only display the `Close` button in the View modal
	- [x] Change button text from `Deny` to `Return to Researcher`
	- [x] Force explanation when "returning to researcher"; make optional for `Approve/Publish`
	- [x] Increase the height of the viewer modal (it's hard to read PDF's otherwise)
	- [x] Look into # Pro00070743
	- [x] The Site PI Institution list needs to reflect organizations available to the actual study/project contract/brand (not necessarily the contract/brand of the contact admin using the Contract Admin tool)
	- [x] Limit file types to PDF in HTML markup
	- [x] Implement more user friendly document upload file size limit error
	- [x] Do we need to support multiple "continuing review" and "amendment" documents? If so, how? (This current breaks the model of requiring documents behind the `protocol/documents` endpoint)
	- [x] `protocol/documents`  and the `IrbDocumentTO.java` class should take into account whether or not the document is _required_. For example, amendments and continuing review documents will not block (perhaps depending on rule logic).
		- [x] We will need to update the front-end (in Angular) `documentationComplete()` method
	- [x] Work on the order of the /researcher/protocols page. It should be ordered by most recent changes.
	- [x] Add "Problems" drop-down to the Project page IRB Protocol Modal (mimic the same problems drop-down from the Contract Admin IRB Protocol tool)
	- [x] Who receives "Return to Research" and "Approval" notifications. It should be sent to all users with authorization for study/project
	- [x] Saving updates to the Contract IRB Queue (at least to the Sponsor field) is not updating the values in the study_builder.metadata.irbDetailsFormGroup. For example, when I change the Sponsor field in the Contract IRB Queue, my updates don't show up on the project edit IRB modal
	- [x] Work on updating statuses, i.e. confirm that the statuses make sense as implemented. Make changes as needed.
	- [x] Work on (formerly known as) "deny" functionality
		- [x] Make sure the workflow works both ways (especially regarding notifications)
	- [x] The Posting Tool just wiped out the Sponsor, IRB Contact, IRB Contact Email, and IRB Contact Phone fields (after the IRB has already been verified....)
	- [x] Work on "accept" functionality
		- [x] how it relates to subsequent updates (after acceptance)
		- [x] how it should create a record in the `organization_irb` table
		- [x] how, once it reaches the `organization_irb` table, the study/project can be published (but not before)
	- [ ] When a protocol has been updated by a researcher, would it be possible to display the differences, so it is easier for the contract admin to see what has changed?
	- [x] once a protocol has been published:
		- [x] allow continuing review documentation to be uploaded
			- [x] is it possible to have logic to determine if this is a required document at some point?
		- [x] allow amendments to be uploaded
	- [x] Add optimistic locking to "Submit for Verification" process










### Completed Tasks


- [x] Call Main Street Auto about Highlander
- [x] Call Bodewell about washer/dryer
- [x] replace:
	- [x] RESTful /admin → /rest/admin
	- [x] /admin/app → /admin
- [x] Look into upgrading Spring Boot to v3.4.x
- [x] In Form Template Management, update the name of form template when updated
- [x] In Concept Management tool, the apex "x" needs alignment
- [x] dsc2u SSL certificate renewal (awaiting feedback)
- [x] change MGB password
- [x] Scan all mustache templates for references to studyKey
- [x] create separate certificate for test.dsc2u.org? (skip unless asked again)
- [x] install new cert on test.dsc2u.org
- [x] Reply to Peter about security vulnerabilities
- [x] Send APIv1 documentation to Jeanhee ~~Nate!~~
	- [x] APIv1 documentation
	- [x] concept code mappings (available on current /developer page)
- [x] Rally 3.0 tasks
	- [x] https://github.com/rallyforresearch/rally/issues/2103#issuecomment-2740441232

- [x] Add virus checking to uploaded files
- [x] Enforce PDF file type for IRB documents
	- [x] Is there a way to confirm that the document is indeed a PDF after upload (instead of just having .pdf file extension)?
- [x] Give Dyan Sprout/Sprout Test access (awaiting Xiaofeng) to be able to run the following statement in the `enrollment_permissions` database:
```sql
INSERT INTO enrollment_permissions.dbo.group_members (username, group_id)
VALUES (N'drb3', 1);
```
- [x] Migrate `ContractIrbQueueLockService` to use the `studyBuilderId` instead of the `conractIrbQueueId` and the reference id for locking. (We can't use the `contractIrbQueueId` because it doesn't exist until the IRB protocol is submitted for verification.)
- [x] Implement new [[Posting Tool]] locking logic
- [x] Implement Contract IRB Queue locking between the contract admin(s) and the research team
- [x] Work on `Submit for Verificaiton` logic
	- [x] copy documents from `study_builder_document` table to `contract_irb_queue_document`
	- [x] Send notification to contract admin (enabled for any status that is not SUBMITTED or PENDING), which should send only relevant status changes.
	- [x] Update status of `contract_irb_queue` record to display `Pending`
		- [x] In the case of ceded review, where the approving organization has an API, we need to display 2 statuses:
			- [x] status of the API protocol
			- [x] status of the ceded review/contract-irb-queue protocol
- [x] Something is causing previously verified study contact emails to lose their verified status: (I can't figure out how to replicate this). ![[Screenshot 2025-03-05 at 09.32.45.png|center|300]]
- [x] Work on IRB Locking
- [x] Consider this, [[Screenshot 2025-03-03 at 15.42.19.png]]
- [x] Convert all project page edit screens to modals
	- [x] IRB Protocol
		- [x] Compare with Posting Tool IRB logic (especially in which fields are editable)
	- [x] Recruiting
		- [x] Add date relative range validation
		- [x] Re-calculate Ad end date on save
		- [x] Display Ad end date
	- [x] Enrollment Progress
	- [x] Site Principal Investigator
	- [x] Study Contacts
- [x] Add validation logic around start and end dates to make sure the start date is _before_ the end date
- [x] The `edu.harvard.mgh.lcs.rally.service.OrganizationServiceImpl#getOrganizationsWithIrb` query is returning organizations out of order
- [x] Remove All of Us

### Other Considerations

- [ ] Is it possible to automatically authenticate in Angular dev environment


### Home Tasks

- [x] Maple syrup
- [x] Bread
- [x] Laundry
- [x] BP
- [x] Chickens
- [ ] Clean barn
