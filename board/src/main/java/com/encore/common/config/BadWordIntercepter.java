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

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RequestMethod[] methods = hm.getMethodAnnotation(RequestMapping.class).method();
            if (Arrays.asList(methods).contains(RequestMethod.POST) || Arrays.asList(methods).contains(RequestMethod.PATCH)) {
                String paramType = null;
                System.out.println("request = " + request.getParameter("contents"));
                System.out.println("request.getParameter(comment) = " + request.getParameter("comment"));
                // 요청 파라미터를 확인하여 어떤 종류의 데이터인지 확인합니다.
                if (request.getParameter("contents") == null) {
                    paramType = "comment";
                }else {
                    paramType = "contents";
                }
                System.out.println(paramType);

                // 요청 파라미터의 종류에 따라 작업을 수행합니다.
                switch (paramType) {
                    case "contents":
                        String contents = request.getParameter("contents");
                        if (contents != null) {
                            contents = badWordFiltering.export_html(contents);
                            request.setAttribute("filteredContents", contents);
                        }
                        break;

                    case "comment":
                        String comment = request.getParameter("comment");
                        if (comment != null) {
                            comment = badWordFiltering.change(comment);
                            request.setAttribute("filteredComments", comment);
                        }
                        break;

                    default:
                        // 기본 작업
                        break;
                }
            }
        }
        return true;
    }
}
