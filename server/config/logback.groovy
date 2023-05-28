import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy
import java.nio.charset.StandardCharsets


conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = StandardCharsets.UTF_8

        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
                        '%clr(%5p) ' + // Log level
                        '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
                        '%clr(%-40.40logger{39}){cyan} %clr(:){faint} ' + // Logger
                        '%m%n%wex' // Message
    }
}

def targetDir = BuildSettings.TARGET_DIR
def logDir = './logs'
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            charset = StandardCharsets.UTF_8
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}

appender("Log_FILE", FileAppender) {
    file = "${logDir}/app.log"
    append = true
    encoder(PatternLayoutEncoder) {
        charset = StandardCharsets.UTF_8
        pattern =
                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}) ' + // Date
                        "%level %logger - %msg%n"
    }
}

//http://logback.qos.ch/manual/appenders.html
appender("ROLLING", RollingFileAppender) {
    file = "${logDir}/coormee.log"
    append = true
    encoder(PatternLayoutEncoder) {
        Pattern = '%d{yyyy-MM-dd HH:mm:ss.SSS} ' + // Date
                "%level{5} %logger{32} - %msg%n"
    }
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        FileNamePattern = "${logDir}/ceeConf-%d{yyyyMMdd000000}.%i.log"
        maxFileSize = '10MB'
        maxHistory = 50
        totalSizeCap ='1GB'
    }
}
root(DEBUG, ['STDOUT','ROLLING'])


logger("org.hibernate.orm.deprecation", ERROR)
logger("org.grails.web", INFO)
//logger('grails.plugin.formfields.FormFieldsTemplateService', DEBUG)
logger("org.reactivestreams", INFO)
logger("io.reactivex", INFO)
//logger("org.himalay", DEBUG)
logger("org.grails.plugins.rx.web.RxResultSubscriber", ERROR)
//logger("org.springsource.loaded", DEBUG)
//logger("org.springsecurity", DEBUG)
//logger("grails.plugin.springsecurity", DEBUG)
//logger("org.springframework.messaging", TRACE);
//logger("org.springframework.messaging.support", TRACE);
//logger("org.springframework.web", TRACE);
scan()
//jmxConfigurator()

