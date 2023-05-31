import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler

// Place your Spring DSL code here

beans = {
    cookieClearingLogoutHandler(CookieClearingLogoutHandler, ['jwt'])
}
