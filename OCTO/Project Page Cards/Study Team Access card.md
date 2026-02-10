
### Tasks

- [x] The current `data.sql` replaces all `study_auth.role_id` with manager. 
- [x] Updating the PI in the [[Site Principal Investigator (PI) card]] doesn't immediately display in the [[Study Team Access card]]. It seems that we need `this.changeDetectorRef.detectChanges();`
- [x] Add logic on the server side to prevent deletion of PI (in case somebody knows how to emulate a web service call, and it's just good coding...)
- [x] Add new logic behind this card; make sure that protections from non-institutional email addresses are not MGB specific and will work with new contract/email-pattern rules. 
- [x] Write logic to potentially swap PI role in `study_auth` table if the PI changes
- [x] Prevent to deletion of the last permission (at least on permission record is required)
- [x] Something is causing permissions to replicate, I suspect on a "save".
![[Screenshot 2025-01-31 at 18.02.37.png|center|300]]


### Other Considerations

- [x] Study Contacts, from the [[Study Contacts card]] should _not_ be automatically added to this list (or have `study_auth` access)
- [ ] Site Principal Investigators _should_ be automatically added to the `study_auth` table if/when the PI has/creates an account.
	- [x] This is working in the [[Site Principal Investigator (PI) card]]
	- [ ] This still needs to be done in [[Publication Workflow]]

### Reference

##### Common users:

| id    | username                   |
| :---- | :------------------------- |
| 39210 | gae                        |
| 39211 | scl30                      |
| 39282 | slorenz.partners@gmail.com |
