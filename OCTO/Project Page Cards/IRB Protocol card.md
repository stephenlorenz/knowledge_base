
#### Tasks

- [ ] Changes to the **Institutional Review Board (IRB) Organizations and Protocols** section of the card should transform depending on:
	- [ ] the relationship between the Local IRB Org and Approving
	- [ ] if the `Approving IRB Organization` has an API
	- [ ] if `Other` organization is picked
- [ ] Need to dynamically determine required documentation
- [ ] Stop automatically creating/sending IRB Protocol Queue requests. Use the manual  "Submit for Verification" button instead.
- [ ] Add new "Submit for Verification" button, disabled until all IRB validation passes
	- [ ] Lock down certain fields after study is published![[Screenshot 2025-02-06 at 16.56.57.png|center|300]]
- [ ] Add custom organization workflow to this "card", as it appears in the Posting Tool 
- [x] Change "Approving IRB Organization" field to a searchable pick list (https://www.npmjs.com/package/ngx-mat-select-search)
- [x] Generically, prioritize MGB for `Approving IRB Organization` when the Site PI is part of one of the MGB hospitals
- [x] Add "Ad Approval Letter"
- [x] Add `name` and `description` field to `v_contract_irb_queue_document_type` table
- [ ] Add virus checking to uploads




#### Steps/Strategy

- [ ] Work through each IRB scenario/story and make sure it works as intended:
	- [x] MGH PI and MGB AIRB
	- [ ] MGH PI and Advarra (Ceded review)
	- [ ] BCH PI and MGB AIRB
	- [ ] BCH PI and Other (new)
	- [ ] BCH PI and Advarra

#### IRB Documentation Requirement Rules
- if approving organization has an API (e.g. MGB):
	- if site and approving IRB organizations are "linked" (e.g. MGH <=> MGB), then no documentation is needed
	- if site and approving IRG organizations are _not_ "linked", then Cede Review Documentation is needed
- if approving organization does _not_ have an API (e.g. Adverra)
	- Approval Letter documentation is needed
	- if site and approving IRB organizations are "linked", then no additional documentation is needed
	- if site and approving IRG organizations are _not_ "linked", then Cede Review Documentation is needed
	- Once the IRB protocol has been verified by the contract admin:
		- It is no longer possible to replace the Approval Letter and Cede review documentation
		- It is now possible to upload Amendment documentation, as needed
		- It is now possible to upload Continuing Review documentation

	


#### Revised Thoughts (No longer implementing)

- [x] The IRB Card "edit button" (pencil) works the same whether there's an API lookup or the IRB is maintained manually inside OCTO.
	- The main job of the IRB Card edit functionality is to link the project/study with an existing IRB protocol number 
		- The "existing IRB protocol number" might exist in one of two places:
			- An API (e.g. MGB Insight)
			- The `organization_irb` table
	- In addition, the IRB Card also serves to display alerts, notifications, and reminders about the state of the IRB protocol and its relationship with the project/study.
- [x] All IRB protocols are maintained outside of the Posting Tool/Project IRB Card:
	- Manual IRB protocols are maintained in a new separate tab on the researcher's main "/manage" page
	- API based IRB protocols are maintained in external systems, e.g. Insight
	- This achieves a "separation of concerns" model, which is a more accurate, real-life representation of the relationship between a project/study and an IRB protocol
- [x] Manual IRB protocols are loosely linked to Projects/Studies and can be "reassigned" to other maintainers
	- Introduce a `irb_protocol_auth` table to support this relationship?



