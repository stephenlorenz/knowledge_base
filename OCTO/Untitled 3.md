Here are some test cases to verify this brand/contract immutability fix:                                                                                                                                                                                             
Test 1: Verify New Study Creation Sets Brand/Contract Correctly                                                                                                                                                                                   
Objective: Confirm brand_id and contract_id are set during initial study creation                                                                                                                                                                 
Steps:                                                                                                                                                                                                                                            
1. Log in as a researcher
2. Create a new study via the Posting Tool (e.g., for brand "rally")
3. Fill in required fields and save the study
4. Database Verification: Query the study table to confirm both brand_id and contract_id are populated:        

```sql
SELECT id, study_key, brand_id, contract_id
FROM study
WHERE study_key = '[your_study_key]';
```

5. Verify contract_id matches the brand_id (check contract_brand_map table)
6. Verify the brand and contract match the brand and contract you were on when you created the study                                                                                                                                                
Expected Result: Both brand_id and contract_id are set and match the brand/contract you are currently on

---

Test 2: Verify Immutability - Cannot Change Brand on Existing Study

Objective: Confirm brand_id cannot be changed after study creation

Steps:

1. Reopen study from Test 1 (or repeat steps from Test 1)
2. Make some changes to the study and save the study
3. Database Verification: Query the study table to confirm both brand_id and contract_id are populated:  

```sql
SELECT id, study_key, brand_id, contract_id
FROM study
WHERE study_key = '[your_study_key]';
```

5. Verify contract_id matches the brand_id (check contract_brand_map table)
6. Verify the brand and contract match the brand and contract you were on when you created the study

Expected Result: Both brand_id and contract_id are set and match the brand/contract you are currently on

---

Test 3: Verify Immutability - Cannot Change Brand on Existing Study by Editing Study as Super Admin from Admin Tools in same Brand

Objective: Confirm brand_id cannot be changed after study creation

Steps:

1. Login as a Super Admin on the **same brand** as the study you created in Test 1
2. Navigate to the Super Admin tools and open the `Study Administration` tool
3. Find your study from Test 1
4. Edit the study and save your changes.
5. Database Verification: Query the study table to confirm both brand_id and contract_id are populated:  

```sql
SELECT id, study_key, brand_id, contract_id
FROM study
WHERE study_key = '[your_study_key]';
```

5. Verify contract_id matches the brand_id (check contract_brand_map table)
6. Verify the brand and contract match the brand and contract you were on when you created the study

Expected Result: Both brand_id and contract_id are set and match the brand/contract you are currently on

---

Test 4: Verify Immutability - Cannot Change Brand on Existing Study by Editing Study as Super Admin from Admin Tools in different Brand

Objective: Confirm brand_id cannot be changed after study creation

Steps:

1. Login as a Super Admin on a **different brand** as the study you created in Test 1
2. Navigate to the Super Admin tools and open the `Study Administration` tool
3. Find your study from Test 1
4. Edit the study and save your changes.
5. Database Verification: Query the study table to confirm both brand_id and contract_id are populated:  

```sql
SELECT id, study_key, brand_id, contract_id
FROM study
WHERE study_key = '[your_study_key]';
```

5. Verify contract_id matches the brand_id (check contract_brand_map table)
6. Verify the brand and contract match the brand and contract you were on when you created the study

Expected Result: Both brand_id and contract_id are set and match the brand/contract you are currently on

---

Test 5: Verify Immutability - Cannot Change Brand on Existing Study by Publishing the study as Super Admin from Admin Tools in same Brand

Objective: Confirm brand_id cannot be changed after study creation

Steps:

1. Login as a Super Admin on the **same brand** as the study you created in Test 1
2. Navigate to the Super Admin tools and open the `Study Administration` tool
3. Find your study from Test 1
4. Publish your study.
5. Database Verification: Query the study publication tables to confirm the brands match between the study (draft) and the study published

```sql
SELECT  
    b.short_code as study_draft_brand,  
    spf.filter_value as study_published_brand  
FROM study s  
    LEFT JOIN brand b ON b.id = s.brand_id  
    LEFT JOIN study_publication sp ON sp.study_id = s.id  
    LEFT JOIN study_publication_filter spf ON spf.study_publication_id = sp.id  
    AND spf.filter_type = 'BRAND'  
WHERE s.published_ind = TRUE  
  AND s.study_key = '[your_study_key]';
```


Expected Result: The `study_draft_brand` and `study_published_brand` values should match

---

Test 6: Verify Immutability - Cannot Change Brand on Existing Study by Publishing the study as Super Admin from Admin Tools in different Brand

Objective: Confirm brand_id cannot be changed after study creation

Steps:

1. Login as a Super Admin on a **different brand** as the study you created in Test 1
2. Navigate to the Super Admin tools and open the `Study Administration` tool
3. Find your study from Test 1
4. Publish your study.
5. Database Verification: Query the study publication tables to confirm the brands match between the study (draft) and the study published

```sql
SELECT  
    b.short_code as study_draft_brand,  
    spf.filter_value as study_published_brand  
FROM study s  
    LEFT JOIN brand b ON b.id = s.brand_id  
    LEFT JOIN study_publication sp ON sp.study_id = s.id  
    LEFT JOIN study_publication_filter spf ON spf.study_publication_id = sp.id  
    AND spf.filter_type = 'BRAND'  
WHERE s.published_ind = TRUE  
  AND s.study_key = '[your_study_key]';
```


Expected Result: The `study_draft_brand` and `study_published_brand` values should match

