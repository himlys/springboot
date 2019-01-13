package com.spring.requestToViewNameTranslator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class UserServiceRequestToViewNameTranslatorConfiguration {
    @Bean("viewNameTranslator")
    public RequestToViewNameTranslator requestToViewNameTranslator(){
        RequestToViewNameTranslator requestToViewNameTranslator = new RequestToViewNameTranslator(){
            private UrlPathHelper urlPathHelper = new UrlPathHelper();
            private static final String SLASH = "/";
            private String prefix = "";
            private String suffix = "";
            private String separator = SLASH;
            @Override
            public String getViewName(HttpServletRequest request) throws Exception {
                String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
                int index =lookupPath.lastIndexOf(separator);
                String path = lookupPath.substring(index);
                return path;
            }
        };
        return requestToViewNameTranslator;
    }
}
