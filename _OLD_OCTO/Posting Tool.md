
- #### *IRB Protocol Details* Page  
  - [ ] We are missing the **Site/Local IRB Protocol Number**. I think this should only show up if the Site PI Organization is different from the Approving IRB Organization.  
- [ ] The external URL needs to accept the URL, [https://redcap.partners.org/redcap/surveys/?s=RDFREAYN3RWME8N7](https://redcap.partners.org/redcap/surveys/?s=RDFREAYN3RWME8N7), as valid  
- [ ] Researchers need to be able to update the "Headline" field indefinitely (requires re-publication)  
- [ ] Researchers need to be able to update the "studyUrl" field until the study is published.  
  - [ ] There used to be a bug, where updating the studyUrl field would lead to duplicate studies. Prevent this in the new tool.  
- [x] Prevent non-contract email address patterns for contact email and Site PI email  
- [x] It seems there are several IRB Detail fields that are still referenced in documentation that we are no longer collecting for custom IRB protocols:
![[Screenshot 2025-01-29 at 11.43.28.png|center|400]]
- [ ] Implement either locking or multi-user, synchronous editing  
#### *Tell Us About Your Recruitment* Page  
  - [ ] Add additional recruitment start/end date validation logic:  
    - [ ] The `Recruitment End Date` cannot be before the `Recruitment Start Date`  

#### Contacts
- [ ] Copy validations from [[Project Page (TOC)]]

#### Site PI
- [ ] Copy validations from [[Project Page (TOC)]]