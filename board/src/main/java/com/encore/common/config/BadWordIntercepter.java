package com.encore.common.config;


import com.encore.common.filter.BadWordFiltering;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class BadWordIntercepter implements HandlerInterceptor {
    private final BadWordFiltering badWordFiltering;

    public BadWordIntercepter(BadWordFiltering badWordFiltering) {
        this.badWordFiltering = badWordFiltering;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RequestMethod[] methods = hm.getMethodAnnotation(RequestMapping.class).method();
            if (Arrays.asList(methods).contains(RequestMethod.POST)){
                String contents = badWordFiltering.pre_change(request.getParameter("contents"));
                if (contents != null) {
                    contents = badWordFiltering.pre_change(contents);
                    if (badWordFiltering.check(contents)) {
                        contents = badWordFiltering.change(contents);
                        request.setAttribute("filteredContents", contents);
                    }
                }
            }
        }
        return true;
    }
}

