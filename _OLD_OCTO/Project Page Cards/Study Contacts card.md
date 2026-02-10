- [x] In edit mode, add the ability to edit content in multiple languages. (It is currently, effectively defaults-to/is-locked-on English). 
- [x] Think about how to design Study Contact email verification rules relative to (re)publishing a study.
- [x] Prevent non-contract email address patterns for contact email 
	- [x] Check if the contract supports a Person Lookup API:
		- [x] If so, use the API to lookup email address(es)
			- [x] Added the method: `edu.harvard.mgh.lcs.rally.service.ContractServiceImpl#getDomainIdsWithPersonLookupSupportFromContract` to help with this.
		- [x] If not, add `contract_email_pattern_map` table and refer to this if the contract doesn't support person lookup API:
			```SQL 
		SELECT pattern_matches  
		FROM (  
				 SELECT DISTINCT TRUE AS pattern_matches  
				 FROM contract_email_pattern_map cepm  
				 WHERE cepm.contract_id = :contractId  
				   AND  :email ~ cepm.email_pattern  
		UNION  
			SELECT CASE WHEN count(*) = 0 THEN TRUE ELSE FALSE END  
			FROM contract_email_pattern_map cepm_sub  
			WHERE cepm_sub.contract_id = :contractId  
				 ) AS pattern_matches  
		ORDER BY 1 DESC  
		LIMIT 1;
			```

- [x] Add "email verification" field to re-type email address (prevent pasting)   
- [x] Prevent saving if validation fails  
- [x] Fix CSS grid layout issue when the screen resolution is narrow (also an issue on other cards):
![[Screenshot 2025-02-07 at 16.11.42.png|center|300]]
- [ ] Email verification badge is missing (was there):![[Screenshot 2025-02-11 at 12.01.09.png|center|300]]

  
  