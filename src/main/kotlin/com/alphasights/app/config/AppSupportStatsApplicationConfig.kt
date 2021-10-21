package com.alphasights.app.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppSupportStatsApplicationConfig {

    @Bean
    @ConditionalOnProperty(value = ["server.log-http-requests"], havingValue = "true", matchIfMissing = true)
    open fun servletContainer(): TomcatServletWebServerFactory {
        val tomcat = TomcatServletWebServerFactory()
        tomcat.addContextValves(ch.qos.logback.access.tomcat.LogbackValve().apply { filename = "logback-access.xml" })
        return tomcat
    }
}
