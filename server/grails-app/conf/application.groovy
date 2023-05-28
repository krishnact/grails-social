//grails.plugin.springsecurity.filterChain.chainMap = [
//        //Stateless chain
//        [
//                pattern: '/**',
//                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
//        ],
//
//        //Traditional, stateful chain
//        [
//                pattern: '/stateful/**',
//                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
//        ]
//]


grails {
        dbconsole{
                enabled = true
        }
        plugin {
                springsecurity {
                        rest {
                                token {
                                        storage {
                                                jwt {
                                                        secret = 'pleaseChangeThisSecretForANewOne-CoorMee'
                                                        expiration = 150
                                                }
                                        }
                                }
                        }
                        useSecurityEventListener = true //https://alvarosanchez.github.io/grails-spring-security-rest/3.0.1/docs/index.html#events
                        securityConfigType = "InterceptUrlMap"  // <1>
                        filterChain {
                                chainMap = [
                                        [pattern: '/api/v1/public/**'     , filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'],
                                        [pattern: '/api/v1/app/**'        , filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'],  // For app to invoke calls
                                        [pattern: '/api/v1/secure/**'     , filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],// <2>
                                        [pattern: '/browser/stomp/**'     , filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'],
                                        [pattern: '/rest/stomp/**'        , filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],// <2>
                                        [pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'] // <3>
                                ]
                        }
                        userLookup {
//                                userDomainClassName = 'org.himalay.springsec.ldap.User'//'org.spring.security.User' // <4>
//                                authorityJoinClassName = 'org.himalay.springsec.ldap.UserRole'//'org.spring.security.UserSecurityRole' // <4>
                                userDomainClassName =  'org.himalay.coormee.auth.User' //'org.spring.security.User' // <4>
                                authorityJoinClassName = 'org.himalay.coormee.auth.UserRole' //'org.spring.security.UserSecurityRole' // <4>

                        }
                        authority {
                                //className = 'org.himalay.springsec.ldap.Role'//'org.spring.security.SecurityRole' // <4>
                                className = 'org.himalay.coormee.auth.Role' // <4>
                        }
                        oauth2{
                                domainClass = 'org.himalay.coormee.auth.OAuthID'
                        }
                        successHandler {
                                defaultTargetUrl = '/application/index'
                        }
                        interceptUrlMap = [
                                [pattern: '/',                             access: ['permitAll']],
                                [pattern: '/favicon.ico',                  access: ['permitAll']],
                                [pattern: '/error',                        access: ['permitAll']],
                                [pattern: '/index',                        access: ['permitAll']],
                                [pattern: '/index.gsp',                    access: ['permitAll']],
                                [pattern: '/shutdown',                     access: ['permitAll']],
                                [pattern: '/assets/**',                    access: ['permitAll']],
                                [pattern: '/**/js/**',                     access: ['permitAll']],
                                [pattern: '/**/css/**',                    access: ['permitAll']],
                                [pattern: '/**/images/**',                 access: ['permitAll']],
                                [pattern: '/**/favicon.ico',               access: ['permitAll']],
                                [pattern: '/webjars/**',                   access: ['permitAll']],
                                [pattern: '/apidoc',                       access: ['permitAll']],
                                [pattern: '/keycloak/register',            access: ['permitAll']],
                                [pattern: '/login/**',                     access: ['permitAll']],
                                [pattern: '/oauth2/**',                    access: ['permitAll']], // <5>
                                [pattern: '/logout',                       access: ['permitAll']],
                                [pattern: '/register/*',                   access: ['permitAll']],
                                [pattern: '/logout/**',                    access: ['permitAll']],
                                [pattern: '/api/v1/public/**',             access: ['permitAll','ROLE_ANONYMOUS'], httpMethod: 'GET'],
                                [pattern: '/api/v1/public/**',             access: ['permitAll','ROLE_ANONYMOUS'], httpMethod: 'PUT'],
                                [pattern: '/api/v1/public/**',             access: ['permitAll','ROLE_ANONYMOUS'], httpMethod: 'POST'],
                                [pattern: '/*',                            access: ['ROLE_ADMIN', 'ROLE_USER']],
                                [pattern: '/*/index',                      access: ['ROLE_ADMIN', 'ROLE_USER']],  // <6>
                                [pattern: '/*/dashboard',                  access: ['ROLE_ADMIN', 'ROLE_USER']],
                                [pattern: '/*/create',                     access: ['ROLE_ADMIN', 'ROLE_READWRITE']],
                                [pattern: '/*/save',                       access: ['ROLE_ADMIN', 'ROLE_READWRITE']],
                                [pattern: '/*/update/**',                  access: ['ROLE_ADMIN', 'ROLE_READWRITE']],
                                [pattern: '/*/delete/*',                   access: ['ROLE_ADMIN']],
                                [pattern: '/*/edit/*',                     access: ['ROLE_ADMIN','ROLE_READWRITE']],
                                [pattern: '/*/show/**',                    access: ['ROLE_ADMIN', 'ROLE_USER']],
                                [pattern: '/*/search/**',                  access: ['ROLE_ADMIN', 'ROLE_USER']],
                                [pattern: '/swImage/install/*',            access: ['ROLE_ADMIN']],
                                [pattern: '/api/login',                    access: ['ROLE_ANONYMOUS']], // <7>
                                [pattern: '/api/v1/app/**',                access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'GET'],
                                [pattern: '/api/v1/app/**',                access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'PUT'],
                                [pattern: '/api/v1/app/**',                access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'POST'],
                                [pattern: '/*/stomp/**',                     access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'GET'],
                                [pattern: '/*/stomp/**',                     access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'PUT'],
                                [pattern: '/*/stomp/**',                     access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'POST'],
                                [pattern: '/api/v1',                       access: ['ROLE_ADMIN', 'ROLE_USER'], httpMethod: 'GET'],  // <9>
                                [pattern: '/api/v1/secure/**',             access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'GET'],
                                [pattern: '/api/v1/secure/**',             access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'DELETE'],
                                [pattern: '/api/v1/secure/**',             access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'POST'],
                                [pattern: '/api/v1/secure/**',             access: ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_APIUSER'], httpMethod: 'PUT'],
                                [pattern: "/oauth/access_token",          access: ["permitAll"], httpMethod: 'POST'],
                                [pattern: "/dataTables/getData/**",        access: ["ROLE_USER"]],
                                [pattern: "/dataTables/getReport/**",      access: ["ROLE_USER"]]
                        ]
                }
        }
}

swagger {
        info {
                description = "CeeConf swagger API Documentation"
                version = "1.0.0"
                title = "CeeConf Swagger API"
                baseUrl = '/'
                termsOfServices = "http://www.rewara.com"
                contact {
                        name = "Krishna T"
                        url = ""
                        email = "krishna.tripathi"
                }
                license {
                        name = ""
                        url = ""
                }
        }
        schemes = [io.swagger.models.Scheme.HTTPS,io.swagger.models.Scheme.HTTP]
        consumes = ["application/json"]
}


grails.plugin.datatables.tableDefaults =        [
        hideWhenEmpty:                          true,
        reportButton:                           true,
        stateSave:                              true,
        processing:                             true,
        reportFunction:                         "downloadReport"
]
grails.plugin.datatables.jQueryUI=true


