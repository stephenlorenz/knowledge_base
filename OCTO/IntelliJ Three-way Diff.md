Three-way diff: Current branch vs v3.0.0.2 (HEAD) vs commit da06382179af998838304e372b3382376d293fba in IntelliJ IDEA  
  
This guide shows two approaches:  
- A. IntelliJ IDEA (recommended)  
- B. Git CLI helpers (fallback/automation)  
  
Prerequisites  
- You have this repository opened as a Git project in IntelliJ IDEA.  
- The remote that contains tag/branch v3.0.0.2 and commit da0638217... is fetched.  
- Your working tree is clean (no uncommitted changes) for best results.  
  
A. IntelliJ IDEA: Three-way comparison using Git | Compare with...  
1) Ensure refs are available  
   - In IDEA, open the Git tool window (View | Tool Windows | Git).  
   - Click Fetch to ensure you have the latest refs.  
  
2) Compare Current Branch ↔ v3.0.0.2  
   - Git tool window → Branches dropdown → select the v3.0.0.2 branch under Remotes.  
   - Right-click v3.0.0.2 → Compare with Current.  
   - This opens the dedicated Compare branches view (list of changed files). Double-click any file to view a two-pane diff.  
  
3) Add a third revision (da0638217...)  
   - In the Compare branches view, click the gear icon (or use the context menu) and choose “Open Diff as Merge.”  
   - In the merge dialog, set the revisions:  
     • Left: da06382179af998838304e372b3382376d293fba (paste the hash)  
     • Base: Git will auto-detect merge-base; you can change it if required.  
     • Right: v3.0.0.2 (or Current Branch depending on which side you want)  
   - Press OK to open a 3-pane diff/merge viewer where you can review differences among all three.  
  
Alternate approach inside IDEA (file-by-file arbitrary 3-way):  
- Navigate to any file in the Project tool window.  
- Right-click → Git → Compare With...  
- In the dialog, select one revision (e.g., HEAD of v3.0.0.2), then click the three-dots icon to switch to a 3-way compare and add the second revision (the commit hash da0638...).  
- Repeat for any other file you need to compare.  
  
Notes  
- You can swap left/right to align the panes as you prefer.  
- Use the navigation gutter (arrows) to jump between changes.  
- The 3-way diff is read-only unless you explicitly start a merge. Do not accept changes unless you are intentionally merging.  
  
B. Git CLI fallback (quick review outside IDEA)  
If you can’t use IDEA’s 3-way viewer, run our helper script to generate comparable outputs:  
  
scripts/three_way_diff.sh current v3.0.0.2 da06382179af998838304e372b3382376d293fba  
  
This will:  
- Verify refs exist.  
- Print the merge-base between the three.  
- Produce two unified diffs in out/:  
  • diff_current_vs_v3.0.0.2.patch  
  • diff_current_vs_da0638217.patch  
- Optionally invoke your configured difftool or mergetool if present.  
  
Troubleshooting  
- v3.0.0.2 not found: Run `git fetch --all --tags` in terminal, or check if it’s a branch vs tag in your remote.  
- Commit hash not found: Confirm the hash exists in your remotes, then fetch.  
- No “Open Diff as Merge” option: Ensure you’re on IntelliJ IDEA Ultimate or a version that supports three-way diff in the Compare window. As an alternative, use the per-file Compare With... dialog and choose three revisions.  
  
Reference  
- JetBrains docs: Version Control | Compare: https://www.jetbrains.com/help/idea/comparing-files-and-folders.html  
- Git difftool/mergetool: `git help difftool`, `git help mergetool`