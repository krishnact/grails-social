import grails.plugin.springsecurity.oauth2.GrailsSocialOAuth2Controller
import grails.plugin.springsecurity.oauth2.LocalDbAuthStorageService
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler

// Place your Spring DSL code here

beans = {
    cookieClearingLogoutHandler(CookieClearingLogoutHandler, ['jwt'])
    authStorageService(LocalDbAuthStorageService){
        springSecurityOauth2BaseService = ref('springSecurityOauth2BaseService')
        springSecurityService = ref('springSecurityService')
    }
}
