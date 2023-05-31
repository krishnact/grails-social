package coormee

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.oauth2.OAuth2CreateAccountCommand
import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.plugin.springsecurity.oauth2.token.OAuth2SpringToken
import org.himalay.coormee.auth.User

@Transactional
class ApplicationService {
    def springSecurityOauth2BaseService
    def springSecurityService

    def createUser(String socialId, String providerName) {
        User user = new User(socialId: socialId, providerName: providerName)
        user.createUserName();

        user.setPassword('not used')
        user.save(flush: true);
    }


    OAuth2SpringToken createAccount(OAuth2SpringToken oAuth2SpringToken, String userName, String password) {
        //OAuth2SpringToken oAuth2SpringToken = session[SPRING_SECURITY_OAUTH_TOKEN] as OAuth2SpringToken
        if (!oAuth2SpringToken) {
            log.warn "createAccount: OAuthToken not found in session"
            throw new OAuth2Exception('Authentication error')
        }


        def commandValid = true ;//command.validate()
        def User = springSecurityOauth2BaseService.lookupUserClass()
        boolean created = commandValid && User.withTransaction { status ->
            def user = springSecurityOauth2BaseService.lookupUserClass().newInstance()

            user.username = userName
            user.password = password
            user.enabled = true

            user.addTooAuthIDs(provider: oAuth2SpringToken.providerName, accessToken: oAuth2SpringToken.socialId, socialId: oAuth2SpringToken.socialId,  user: user)
            //if (!user.validate() || !user.save()) {
            if (!user.save(flush: true)) {
                status.setRollbackOnly()
                false
            }
            def UserRole = springSecurityOauth2BaseService.lookupUserRoleClass()
            def Role = springSecurityOauth2BaseService.lookupRoleClass()
            def roles = springSecurityOauth2BaseService.roleNames
            for (roleName in roles) {
                log.debug("Creating role " + roleName + " for user " + user.username)
                // Make sure that the role exists.
                UserRole.create user, Role.findOrSaveByAuthority(roleName), true
            }
            user.refresh()
            // make sure that the new roles are effective immediately
            springSecurityService.reauthenticate(user.username)
            oAuth2SpringToken = springSecurityOauth2BaseService.updateOAuthToken(oAuth2SpringToken, user)
            return true
        }
//        if (created) {
//            authenticateAndRedirect(oAuth2SpringToken, getDefaultTargetUrl())
//            return
//        }

        return created?oAuth2SpringToken:null;
    }


}
