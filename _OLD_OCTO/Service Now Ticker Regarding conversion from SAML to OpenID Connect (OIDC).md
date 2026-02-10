

The MGB Rally web application currently uses Okta for authentication for MGB users. We are exploring the possibility of migrating our application's authentication mechanism from our current SAML configuration to OpenID Connect (OIDC) on Okta.

We are hoping that somebody can reach out to us with the following information for our two Rally environments, TEST (current SAML entity id: org:researchally:dev:test) and PROD (current SAML entity id: org:partners:prp:prod). Specifically, we are interested in the following settings:

- The oauth2 issuer URI, e.g. https://partnershealthcare.oktapreview.com/oauth2/...?
- Our application's client ids (TEST and PROD) [see https://developer.okta.com/docs/guides/find-your-app-credentials/main/]
- Our application's client secrets (TEST and PROD) [see https://developer.okta.com/docs/guides/find-your-app-credentials/main/]

I understand you should probably not embed this information in a ServiceNow ticket. Please feel free to reach out to me to discuss the best way to transfer this information.

If it helps in assigning this ticket, in the past we have worked with Lee Kelley (lkelley2@mgb.org) on these configurations.

Of course, please let me know if you have any questions or concerns about this.

Thank you.