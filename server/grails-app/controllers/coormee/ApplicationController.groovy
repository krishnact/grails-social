package coormee

import grails.core.GrailsApplication
import grails.gorm.transactions.GrailsTransactionTemplate
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.oauth2.SpringSecurityOAuth2Controller
import grails.plugin.springsecurity.oauth2.SpringSecurityOauth2UrlMappings

import grails.plugins.*
import grails.spring.security.oauth2.google.GoogleOAuth2Service
import org.springframework.beans.factory.annotation.Value

class ApplicationController implements PluginManagerAware {

    @Value('${keycloak.clientId}')
    String clientId

    @Value('${keycloak.clientSecret}')
    String clientSecret

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        //SpringSecurityOauth2UrlMappings
        //SpringSecurityOAuth2Controller
        //GoogleOAuth2Service
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }

    def landingPage() {
        log.info("Client id ${clientId}, secret: ${clientSecret}" )
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
