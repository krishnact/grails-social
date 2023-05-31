package coormee

import com.sun.istack.internal.Nullable
import grails.core.GrailsApplication
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth2.OAuth2CreateAccountCommand
import grails.plugin.springsecurity.oauth2.SpringSecurityOAuth2Controller
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import grails.plugins.*
import org.himalay.coormee.auth.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder

class ApplicationController implements PluginManagerAware {

    @Value('${keycloak.clientId}')
    String clientId

    @Value('${keycloak.clientSecret}')
    String clientSecret

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager
    SpringSecurityService springSecurityService
    def applicationService

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

    def register(){
        OAuth2SpringToken auth2SpringToken = (OAuth2SpringToken)session[SpringSecurityOAuth2Controller.SPRING_SECURITY_OAUTH_TOKEN];
        log.info("Registering ${auth2SpringToken.socialId} from ${auth2SpringToken.providerName}")

        //GoogleOauth2SpringToken googleOauth2SpringToken = (GoogleOauth2SpringToken)session[SPRING_SECURITY_OAUTH_TOKEN];
        //redirect(url: "http://www.blogjava.net/BlueSUN")
        User user = new User(socialId: auth2SpringToken.socialId, providerName: auth2SpringToken.providerName)
        String userName =  auth2SpringToken.socialId + '@' + auth2SpringToken.providerName
        String pw = ''

        if (!springSecurityService.loggedIn) {
            auth2SpringToken = applicationService.createAccount(auth2SpringToken,userName,pw);
            if (auth2SpringToken){
                this.authenticateAndRedirect(auth2SpringToken, getDefaultTargetUrl());
                return
            }
        }
        //applicationService.createUser(auth2SpringToken.socialId, auth2SpringToken.providerName)
        //redirect(controller: 'book', action: 'list')
        redirect(url: "/login/auth")
    }

    protected Map getDefaultTargetUrl() {
        def config = SpringSecurityUtils.securityConfig
        def savedRequest = SpringSecurityUtils.getSavedRequest(session)
        def defaultUrlOnNull = '/'
        if (savedRequest && !config.successHandler.alwaysUseDefault) {
            return [url: (savedRequest.redirectUrl ?: defaultUrlOnNull)]
        }
        return [uri: (config.successHandler.defaultTargetUrl ?: defaultUrlOnNull)]
    }

    /**
     * Set authentication token and redirect to the page we came from
     * @param oAuthToken
     * @param redirectUrl
     */
    protected void authenticateAndRedirect(@Nullable OAuth2SpringToken oAuthToken, Map redirectUrl) {
        session.removeAttribute SpringSecurityOAuth2Controller.SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken
        redirect(redirectUrl instanceof Map ? redirectUrl : [uri: redirectUrl])
    }

}
