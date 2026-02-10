

### To manually upload Maven artifacts to GitHub Packages

```bash
curl -X PUT \
"https://maven.pkg.github.com/rallyforresearch/researchally-rally-manager-dashboard/org/researchally/rally-manager-dashboard/2.1.1.0-SNAPSHOT/rally-manager-dashboard-2.1.1.0-SNAPSHOT.jar" \
-H "Authorization: token {{token-from-~/.m2/settings.xml}}" \
--upload-file "rally-manager-dashboard-2.1.1.0-SNAPSHOT.jar" -vvv

```