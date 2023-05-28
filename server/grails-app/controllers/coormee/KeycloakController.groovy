package coormee


import grails.rest.*
import grails.converters.*
import org.himalay.keycloak.KeycloakAdminClientService
import org.keycloak.admin.client.Keycloak
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class KeycloakController {

    KeycloakAdminClientService keycloakAdminClientService;

	static responseFormats = ['json', 'xml', 'html']
    /**
     * User is sent to this page after auth is successful
     */
    def register() {
        //keyclock.
        [roles: keycloakAdminClientService.getRoles()]

    }
}
