package edu.harvard.mgh.lcs.rally.security;

import edu.harvard.mgh.lcs.rally.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import org.opensaml.common.SAMLException;
import org.opensaml.common.binding.decoding.BasicURLComparator;
import org.opensaml.common.binding.decoding.URIComparator;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.transport.InTransport;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.util.DatatypeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.util.SAMLUtil;

import java.util.List;
import java.util.Set;

public class RallySAMLUtil extends SAMLUtil {

    private final AuditService auditService;

    /** The URIComparator implementation to use. */
    private static final URIComparator uriComparator = new BasicURLComparator();

    private static Logger logger = LoggerFactory.getLogger(RallySAMLUtil.class.getName());

    public RallySAMLUtil(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * Method helps to identify which endpoint is used to process the current message. It expects a list of potential
     * endpoints based on the current profile and selects the one which uses the specified binding and matches
     * the URL of incoming message.
     *
     * @param endpoints      endpoints to check
     * @param messageBinding binding
     * @param inTransport      transport which received the current message
     * @param <T>            type of the endpoint
     * @return first endpoint satisfying the requestURL and binding conditions
     * @throws SAMLException in case endpoint can't be found
     */
    public static <T extends Endpoint> T getEndpoint(List<T> endpoints, String messageBinding, InTransport inTransport, Set<String> validEndpoints) throws SAMLException {
        HttpServletRequest httpRequest = ((HttpServletRequestAdapter)inTransport).getWrappedRequest();
        String requestURL = DatatypeHelper.safeTrimOrNullString(httpRequest.getRequestURL().toString());
        for (T endpoint : endpoints) {
            String binding = getBindingForEndpoint(endpoint);
            // Check that destination and binding matches
            if (binding.equals(messageBinding)) {
                if (endpoint.getLocation() != null && validEndpoints.contains(endpoint.getLocation())) {
                    logger.debug("Found endpoint {} for request URL {} based on location attribute in metadata", endpoint, requestURL);
                    return endpoint;
                } else if (endpoint.getResponseLocation() != null && validEndpoints.contains(endpoint.getResponseLocation())) {
                    logger.debug("Found endpoint {} for request URL {} based on response location attribute in metadata", endpoint, requestURL);
                    return endpoint;
                }
            }
        }
        throw new SAMLException("Endpoint with message binding " + messageBinding + " and URL " + requestURL + " wasn't found in local metadata");
    }
}


