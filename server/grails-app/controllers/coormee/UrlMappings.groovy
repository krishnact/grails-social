package coormee

//import grails.plugin.springsecurity.SpringSecurityUtils
//import grails.plugin.springsecurity.oauth2.exception.OAuth2Exception
import grails.util.Holders

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")
        get "/$controller/$action(.$format)?"()
        post "/logout" (controller: 'logout', action: 'index')
        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')

//        def active = Holders.grailsApplication.config.grails?.plugin?.springsecurity?.oauth2?.active
//        def controllerName = Holders.grailsApplication.config.grails?.plugin?.springsecurity?.oauth2?.controllerName
//        if (controllerName == null){
//            controllerName = 'springSecurityOAuth2' //'grailsSocialOAuth2'
//        }
//        def enabled = (active instanceof Boolean) ? active : true
//        if (enabled && SpringSecurityUtils.securityConfig?.active) {
//            "/oauth2/$provider/authenticate"(controller: controllerName, action: 'authenticate')
//            "/oauth2/$provider/callback"(controller: controllerName, action: 'callback')
//            "/oauth2/$provider/success"(controller: controllerName, action: 'onSuccess')
//            "/oauth2/$provider/failure"(controller: controllerName, action: 'onFailure')
//            "/oauth2/ask"(controller: controllerName, action: 'ask')
//            "/oauth2/linkaccount"(controller: controllerName, action: 'linkAccount')
//            "/oauth2/createaccount"(controller: controllerName, action: 'createAccount')
//            '500'(controller: 'login', action: 'auth', exception: OAuth2Exception)
//        }
    }
}
