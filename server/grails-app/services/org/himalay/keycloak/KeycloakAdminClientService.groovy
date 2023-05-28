package org.himalay.keycloak

import com.github.scribejava.apis.KeycloakApi
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.admin.client.resource.RealmsResource
import org.keycloak.admin.client.resource.ServerInfoResource
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.info.ServerInfoRepresentation

import javax.annotation.PostConstruct;
import java.util.List;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@Service
public class KeycloakAdminClientService {

    private Keycloak keycloak;

    @Autowired
    private KeycloakAdminClientConfig keycloakAdminClientConfig;


    @PostConstruct
    void initThis(){
        keycloak = KeycloakBuilder.builder().
                serverUrl(keycloakAdminClientConfig.getServerUrl()).
                //realm(keycloakAdminClientConfig.getRealm()).
                realm("master").
                clientId(keycloakAdminClientConfig.getClientId()).
                clientSecret(keycloakAdminClientConfig.getClientSecret()).
                grantType(OAuth2Constants.CLIENT_CREDENTIALS).
                build();

//        keycloak = Keycloak.getInstance(
//                keycloakAdminClientConfig.getServerUrl(),
//                keycloakAdminClientConfig.getRealm(),
//                keycloakAdminClientConfig.getClientId(),
//                keycloakAdminClientConfig.getClientSecret()
//        );
//        keycloak.tokenManager();
//        keycloak.

    }
    public List<String> getCurrentUserRoles(String user) {
        RealmsResource rr = keycloak.serverInfo()
        log.info("Current Realms are " + rr);
        return rr.findAll().collect{it.getRealm()};

    }

    public ServerInfoRepresentation getServerInfo() {
        ServerInfoResource sir = keycloak.serverInfo()
        ServerInfoRepresentation serverInfoRepresentation = sir.getInfo();

        return serverInfoRepresentation;
    }

    public List<RoleRepresentation> getRoles() {
        return keycloak.realm(keycloakAdminClientConfig.getRealm()).roles().list();
    }
//    public Object getUserProfileOfLoggedUser() {
//
//        @SuppressWarnings("unchecked")
//        KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//        KeycloakAdminClientConfig keycloakAdminClientConfig = KeycloakAdminClientUtils.loadConfig(keycloakPropertyReader);
//        Keycloak kc = new Keycloak()
//        Keycloak keycloak = KeycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(), keycloakAdminClientConfig);
//
//        // Get realm
//        RealmResource realmResource = keycloak.realm(keycloakAdminClientConfig.getRealm());
//        UsersResource usersResource = realmResource.users();
//        UserResource userResource = usersResource.get(currentUserProvider.getCurrentUser().getUserId());
//        UserRepresentation userRepresentation = userResource.toRepresentation();
//
//        return userRepresentation;
//    }
}