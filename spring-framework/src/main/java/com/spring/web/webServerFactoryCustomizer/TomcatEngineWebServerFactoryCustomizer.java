package com.spring.web.webServerFactoryCustomizer;

import org.apache.catalina.Host;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.ServletException;
import java.io.IOException;

@Configuration
public class TomcatEngineWebServerFactoryCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>, Ordered {
    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addEngineValves(new ValveBase() {
            @Override
            public void invoke(Request request, Response response) throws IOException, ServletException {

                // Select the Host to be used for this Request
                Host host = request.getHost();
                if (host == null) {
                    // HTTP 0.9 or HTTP 1.0 request without a host when no default host
                    // is defined. This is handled by the CoyoteAdapter.
                    return;
                }
                if (request.isAsyncSupported()) {
                    request.setAsyncSupported(host.getPipeline().isAsyncSupported());
                }

                if (this.next != null) {
                    System.out.println("i have a next engineValue and myrequest is " + request.getRequestURI());
                    this.getNext().invoke(request, response);
                } else {
                    System.out.println("i'm the last engineValve and myrequest is " + request.getRequestURI());
                }
            }
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
