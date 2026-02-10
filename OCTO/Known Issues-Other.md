
- [ ] Roster is no longer working, across the board.  

### Other  
- [x] when a email invited to a `study_auth` table, we need to make sure that __when__ the account is created we update the `study_auth` table with users.users_id. This is no longer relevant; a user *must* first exist in OCTO before they can be added to a study.
- [ ] Add contract_id and brand_id to saved search table so we can more easily limit which saved searches appear in user_profile