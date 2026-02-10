
#### Remediation

- clear the user_submission_log table (feel free to make a copy first)
	- or do this:
		```SQL
		UPDATE user_submission_log
		SET template_id = 0
		WHERE template_id = 5;
		```

- 