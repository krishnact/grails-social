package org.himalay.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component;



/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakAdminClientConfig {

    String serverUrl;
    String realm;
    String clientId;
    String clientSecret;
}