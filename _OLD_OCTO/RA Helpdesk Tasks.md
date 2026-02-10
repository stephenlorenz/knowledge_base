
#### Labels

- [x] Implement help desk ticket labels similar to GitHub issues in both the front and backend. The labels should be specific to the contract, so each contract can have its own set of labels. You can pre-populate a contract's with the labels, "Bug", "Feature", "Task", "Question". Each label should have a system assigned distinct color and when viewing a ticket in both the modal and page, the labels should appear in both a dropdown on the same line as the status, priority, and assigned to drop downs.
- [x] display labels on main listing along with ability to filter
- [x] add sorting to the help desk ticket table
- [x] should the main listing table, also update via SSE, with values and with new/deleted tickets?
- [x] In the activity stream, comments have the same icon/avatar from other events. Can you display a different avatar for comments?
- [x] Changes to labels should also be stored as events in the activity stream.
- [x] The labels should also be filterable like the status, priority, and assigned to.
- [x] New Helpdesk labels can be added, modified, or deleted directly from the Ticket Detail page or modals.
- [x] Make all help desk pages and modals more responsive.
- [x] Fix label badge location on ticket page(s)

#### Fix/Replace Database Env specific IDs with codes

- [x] The ContractID should be the contract code instead, e.g. "MGB" and not "2"
- [x] The assignedTO should be an array of usernames/domain_codes, e.g. "scl30/mgb" or "slorenz.partners@gmail.com/local"

### Update Email templates

 - [ ] Email template for public users should not display all the statuses and priorities, etc. nor a link back to the ticket.

### Add watching ability

- [ ] Add ability to watch a ticket without being assigned to a ticket
- [ ] Add a new help desk user designation/group/role where you can specify tickets that need to be triaged (aka new tickets). This role should, optionally, support a contract designation, so new tickets can be only be sent to people with this role within the contract
	- [ ] Once this new designation is created, these people should be notified when a new ticket arrives.
### Notifications

- [ ] Allow the Super/Contract admin to only send message to team members
- [ ] When a team member is assigned to a ticket, notify them
- [ ] Should we notify team member followers of all status changes?





