package edu.harvard.mgh.lcs.rally.security;

import edu.harvard.mgh.lcs.rally.data.jpa.model.UserProfileEntity;
import edu.harvard.mgh.lcs.rally.data.jpa.model.UsersEntity;
import edu.harvard.mgh.lcs.rally.data.jpa.model.UsersRoleEntity;
import edu.harvard.mgh.lcs.rally.enumeration.AuditType;
import edu.harvard.mgh.lcs.rally.http.RallySamlHttpServletRequestWrapper;
import edu.harvard.mgh.lcs.rally.service.AuditService;
import edu.harvard.mgh.lcs.rally.service.SecurityService;
import edu.harvard.mgh.lcs.rally.service.UserProfileServiceImpl;
import edu.harvard.mgh.lcs.rally.serviceInterface.BrandService;
import edu.harvard.mgh.lcs.rally.serviceInterface.MessagingService;
import edu.harvard.mgh.lcs.rally.serviceInterface.UserService;
import edu.harvard.mgh.lcs.rally.to.AuthSamlTO;
import edu.harvard.mgh.lcs.rally.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.common.SAMLException;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.xml.encryption.DecryptionException;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml.SAMLAuthenticationToken;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;

import java.util.*;

@Slf4j
public class PRPSAMLProcessingFilter extends SAMLProcessingFilter {

    private final UserService userService;

    private final UserProfileServiceImpl userProfileService;

    private final SecurityService securityService;

    private final BrandService brandService;

    private final AuditService auditService;

    private final MessagingService messagingService;

    private final WebSSOProfileConsumer webSSOProfileConsumer;

    public PRPSAMLProcessingFilter(UserService userService, UserProfileServiceImpl userProfileService, SecurityService securityService, BrandService brandService, AuditService auditService, MessagingService messagingService, WebSSOProfileConsumer webSSOProfileConsumer) {
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.securityService = securityService;
        this.brandService = brandService;
        this.auditService = auditService;
        this.messagingService = messagingService;
        this.webSSOProfileConsumer = webSSOProfileConsumer;
    }

    public Authentication attemptAuthentication(HttpServletRequest requestOrg, HttpServletResponse response) throws AuthenticationException {
        try {

            RallySamlHttpServletRequestWrapper request = new RallySamlHttpServletRequestWrapper(requestOrg);

//            log.debug(String.format("Attempting SAML2 authentication using profile %s", this.getProfileName()));
//            log.debug("PRPSAMLProcessingFilter.attemptAuthentication.request.getMethod(): " + request.getMethod() + " ==> " + request.getRequestURL().toString());

//            log.debug("Attempting SAML2 authentication using profile {}", this.getProfileName());

//            PRPHttpServletRequestAdapter prpHttpServletRequestAdapter

            SAMLMessageContext context = this.contextProvider.getLocalEntity(request, response);


            AuthSamlTO authSamlTO = null;
            String brandShortCode = brandService.getBrandShortCode(request);

            if (StringUtils.isFull(brandShortCode)) {
                authSamlTO = brandService.getBrandAuthSamlConfig(brandShortCode);
            }

//            if (authSamlTO != null) {
//                List<Endpoint> endpoints = context.getLocalEntityRoleMetadata().getEndpoints()
//
//                Endpoint endpoint = new Endpoint();
//
//                for (Endpoint endpoint : endpoints) {
//                    String binding = getBindingForEndpoint(endpoint);
//                    // Check that destination and binding matches
//                    if (binding.equals(messageBinding)) {
//                        if (endpoint.getLocation() != null && uriComparator.compare(endpoint.getLocation(), requestURL)) {
//                            log.debug("Found endpoint {} for request URL {} based on location attribute in metadata", endpoint, requestURL);
//                            return endpoint;
//                        } else if (endpoint.getResponseLocation() != null && uriComparator.compare(endpoint.getResponseLocation(), requestURL)) {
//                            log.debug("Found endpoint {} for request URL {} based on response location attribute in metadata", endpoint, requestURL);
//                            return endpoint;
//                        }
//                    }
//                }
//
//            }

//            log.debug("PRPSAMLProcessingFilter.attemptAuthentication.context: " + context);

            this.processor.retrieveMessage(context);

//            log.debug("attemptAuthentication 1");

            context.setCommunicationProfileId(this.getProfileName());

//            log.debug("attemptAuthentication 2");

            context.setLocalEntityEndpoint(RallySAMLUtil.getEndpoint(context.getLocalEntityRoleMetadata().getEndpoints(), context.getInboundSAMLBinding(), context.getInboundMessageTransport(), securityService.getValidSAMLEndpoints()));

//            log.debug("attemptAuthentication 3");

            SAMLAuthenticationToken token = new SAMLAuthenticationToken(context);

//            log.debug("attemptAuthentication 4");

//            return this.getAuthenticationManager().authenticate(token);

/*
            HttpSession httpSession = request.getSession(true);
            request.setAttribute("test123", "test123");
            if (httpSession != null) {
                httpSession.setAttribute("samlLogoutURL", "TODO: Fill me in...");
//                httpSession.setAttribute("samlLogoutURL", logoutUrlPartners);
            }
*/


            return this.authenticate(request, token);
        } catch (SAMLException var5) {
            log.error("attemptAuthentication (var5): Incoming SAML message is invalid: " + var5.getMessage());
            log.error("Incoming SAML message is invalid", var5);
            throw new AuthenticationServiceException("Incoming SAML message is invalid", var5);
        } catch (MetadataProviderException var6) {
            log.error("attemptAuthentication: Error determining metadata contracts");

            log.error("Error determining metadata contracts", var6);
            throw new AuthenticationServiceException("Error determining metadata contracts", var6);
        } catch (MessageDecodingException var7) {
            log.error("attemptAuthentication: Error decoding incoming SAML message");

            log.error("Error decoding incoming SAML message", var7);
            throw new AuthenticationServiceException("Error decoding incoming SAML message", var7);
        } catch (SecurityException var8) {
            log.error("attemptAuthentication (var8): Incoming SAML message is invalid: " + var8.getMessage());

            log.error("Incoming SAML message is invalid", var8);
            throw new AuthenticationServiceException("Incoming SAML message is invalid", var8);
        } catch (Exception var8) {

            log.debug("attemptAuthentication: x.");

            var8.printStackTrace();

            log.debug("Failed to authenticate.", var8);
            throw new AuthenticationServiceException("Failed to authenticate.", var8);
        }
    }

    public void setWebSSOProfileConsumer (WebSSOProfileConsumer webSSOProfileConsumer) {
        this.webSSOProfileConsumer = webSSOProfileConsumer;
    }

//    private UsernamePasswordAuthenticationToken authenticate(SAMLAuthenticationToken token) {
    private Authentication authenticate(HttpServletRequest request, SAMLAuthenticationToken token) {

        log.debug("978: SAMLAuthenticationToken = " + token);

        log.debug("PRPSAMLProcessingFilter.authenticate.token.getPrincipal(): " + token.getName());

        SAMLMessageContext context = token.getCredentials();
        SAMLCredential credential = null;


        HttpSession httpSession = request.getSession(false);

//        log.debug("PortalController.manage.httpSession: " + httpSession);

// 978       String rallySavedLoginEmail = null;
//        String rallySavedRequest = null;

//        if (httpSession != null) {
//            rallySavedRequest = (String) httpSession.getAttribute("RALLY_SAVED_REQUEST");
// 978            rallySavedLoginEmail = (String) httpSession.getAttribute("RALLY_SAVED_LOGIN_EMAIL");
//        }

//        String email = context.getRelayState();
//        log.debug("978: PRPSAMLProcessingFilter.authenticate.rallySavedLoginEmail: " + rallySavedLoginEmail);
//        log.debug("978: PRPSAMLProcessingFilter.authenticate.rallySavedRequest: " + rallySavedRequest);

        try {
            credential = webSSOProfileConsumer.processAuthenticationResponse(context);
//            log.debug("PRPSAMLProcessingFilter.authenticate.credential: " + credential);
//            log.debug("PRPSAMLProcessingFilter.authenticate.credential: " + credential);
        } catch (SAMLException e) {
            throw new AuthenticationServiceException("Error validating SAML message 123", e);
        } catch (ValidationException e) {
            throw new AuthenticationServiceException("Error validating SAML message signature", e);
        } catch (org.opensaml.xml.security.SecurityException e) {
            throw new AuthenticationServiceException("Error validating SAML message signature", e);
        } catch (DecryptionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NameID subjectName = (NameID) context.getSubjectNameIdentifier();
        String name = subjectName.getValue();

        log.debug("PRPSAMLProcessingFilter.authenticate.credential: " + credential);

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PARTNERS"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MGB"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));

        PrpUserDetails prpUserDetails = new PrpUserDetails();

//        log.debug("PRPSAMLProcessingFilter.authenticate.credential.getAdditionalData(): " + credential.getAdditionalData());
//        List<Attribute> attributeList = credential.getAttributes();
//        for (Attribute attribute : attributeList) {
//            log.debug("PRPSAMLProcessingFilter.authenticate.attribute: " + attribute);
//            log.debug("PRPSAMLProcessingFilter.authenticate.attribute.getName(): " + attribute.getName());
//        }

        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                log.debug("978: Header: " + request.getHeader(headerNames.nextElement()));
            }
        }

        credential.getAdditionalData();

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        log.debug("978: ipAddress" + ipAddress);
        log.debug("978: SAML credentials: credential = " + credential);

        StringBuilder credentialAttributes = new StringBuilder();

        if (credential != null && credential.getAttributes() != null) {
            for (Attribute attribute : credential.getAttributes()) {
                try {
                    if (credentialAttributes.length() > 0) credentialAttributes.append("; ");
                    credentialAttributes.append(String.format("%s ==> %s", attribute.getName(), credential.getAttributeAsString(attribute.getName())));
                } catch (Exception ignore) {}
            }
        }

        log.debug("credentialAttributes.t = " + credentialAttributes);

// 978        if (StringUtils.isFull(rallySavedLoginEmail) && domainService.isDomainEmailInDomain("partners", rallySavedLoginEmail)) {
// 978            prpUserDetails.setEmail(rallySavedLoginEmail);
// 978        } else {
            prpUserDetails.setEmail(credential.getAttributeAsString("email"));
// 978        }

        prpUserDetails.setUsername(name);
        prpUserDetails.setDomain("partners");
        if (StringUtils.isFull(credential.getAttributeAsString("displayName"))) {
            prpUserDetails.setDisplayName(credential.getAttributeAsString("displayName"));
        } else if (StringUtils.isFull(credential.getAttributeAsString("displayname"))) {
            prpUserDetails.setDisplayName(credential.getAttributeAsString("displayname"));
        }
        prpUserDetails.setFirstName(credential.getAttributeAsString("firstname"));
        prpUserDetails.setLastName(credential.getAttributeAsString("lastname"));
        prpUserDetails.setOktaLoggedIn(true);

        log.debug("978: PRPSAMLProcessingFilter.authenticate.prpUserDetails: " + prpUserDetails);

        auditService.audit(prpUserDetails.getUsername(), prpUserDetails.getDomain(), AuditType.LOGIN, "OKTA_LOGIN", credentialAttributes.toString());

        UsersEntity usersEntity = userService.seedNewUserFromPrpUserDetails(prpUserDetails);

        if (StringUtils.isFull(prpUserDetails.getEmail()) && usersEntity != null && usersEntity.getId() != null) {
            messagingService.claimDanglingMessages(prpUserDetails.getEmail(), prpUserDetails.getDomain(), usersEntity.getId());
        }

        UserProfileEntity userProfileEntity = userProfileService.seedUserProfile(usersEntity, prpUserDetails);

        if (userProfileEntity != null) {
            if (StringUtils.isFull(userProfileEntity.getDisplayName())) {
                prpUserDetails.setDisplayName(userProfileEntity.getDisplayName());
            }
            if (StringUtils.isFull(userProfileEntity.getFirstName())) {
                prpUserDetails.setFirstName(userProfileEntity.getFirstName());
            }
            if (StringUtils.isFull(userProfileEntity.getLastName())) {
                prpUserDetails.setLastName(userProfileEntity.getLastName());
            }
        }

        boolean researcherInd = false;

        if (usersEntity.getRoles() != null && usersEntity.getRoles().size() > 0) {
            for (UsersRoleEntity usersRoleEntity : usersEntity.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(usersRoleEntity.getRole().getCode()));
                if (usersRoleEntity.getRole().getCode().equals("ROLE_RESEARCHER")) {
                    researcherInd = true;
                }
            }
        }

        if (!researcherInd) {
            if (securityService.isResearcher(usersEntity.getId())) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_RESEARCHER"));
                userService.saveRole(usersEntity, "ROLE_RESEARCHER");
            }
        }

        Collection<GrantedAuthority> grantedAuthoritiesTmp = securityService.getDatabaseRoles(prpUserDetails);
        if (grantedAuthoritiesTmp != null && grantedAuthoritiesTmp.size() > 0) {
            grantedAuthorities.addAll(grantedAuthoritiesTmp);
        }
        prpUserDetails.setGrantedAuthorities(grantedAuthorities);


        prpUserDetails.setUserId(usersEntity.getId());

        Set<Long> userBrands = brandService.getBrandIdsFromPermissions(usersEntity);
        if (userBrands != null && !userBrands.isEmpty()) {
            Set<String> brandShortCodes = new HashSet<String>();
            for (Long brandId : userBrands) {
                brandShortCodes.add(brandService.getBrandShortCode(brandId));
            }
            prpUserDetails.setBrandShortCodes(brandShortCodes);
        }
        prpUserDetails.setBrandIds(userBrands);

        if (usersEntity.getDomain() != null) {
            prpUserDetails.setDomain(usersEntity.getDomain().getCode());
        }


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(prpUserDetails, null, grantedAuthorities);
        usernamePasswordAuthenticationToken.setDetails(prpUserDetails);

        userService.updateLastLogin(usersEntity.getId());


//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            usernamePasswordAuthenticationToken.setDetails(objectMapper.writeValueAsString(prpUserDetails));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        log.debug("PRPSAMLProcessingFilter.authenticate.usernamePasswordAuthenticationToken: " + usernamePasswordAuthenticationToken);
        log.debug("PRPSAMLProcessingFilter.authenticate.prpUserDetails: " + prpUserDetails);

        return usernamePasswordAuthenticationToken;

    }

}


