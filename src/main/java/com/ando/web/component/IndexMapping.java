package com.ando.web.component;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * https://www.cnblogs.com/yuananyun/archive/2014/08/25/3934371.html
 * <p>
 * Created by changbao on 2019/9/11 星期三 .
 */
@RestController
public class IndexMapping {

    @RequestMapping(value = "/requestMapping", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null) {
            return null;
        }
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMappings.values()) {
            if (handlerMapping instanceof AbstractUrlHandlerMapping) {
                AbstractUrlHandlerMapping abstractUrlHandlerMapping= (AbstractUrlHandlerMapping) handlerMapping;
                //todo 2019年9月11日 17:15:27  AbstractUrlHandlerMapping  https://blog.csdn.net/can_L/article/details/78793964
            }


            //本项目只需要RequestMappingHandlerMapping中的URL映射 -> AbstractHandlerMapping
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

                    final RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
                    final RequestMethod requestMethod = setFirst(methodCondition.getMethods());
                    final String requestType = requestMethod == null ? "" : requestMethod.name();

                    final PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String requestUrl = setFirst(patternsCondition.getPatterns());
                    if (requestUrl == null) {
                        requestUrl = "";
                    }

                    String controllerName = mappingInfoValue.getBeanType().toString();
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                    RequestToMethodItem item = new RequestToMethodItem(
                            requestUrl, requestType, controllerName, requestMethodName, methodParamTypes);

                    requestToMethodItemList.add(item);
                }
                break;
            }


        }
        return new ModelAndView("requestMapping").addObject("MethodList", requestToMethodItemList);
    }

    private <T> T setFirst(Set<T> sets) {
        if (sets != null && !sets.isEmpty()) {
            Iterator<T> it = sets.iterator();
            return it.next();
        }
        return null;
    }


    private static class RequestToMethodItem {

        public String controllerName;
        public String methodName;
        public String requestType;
        public String requestUrl;
        public Class<?>[] methodParamTypes;

        public RequestToMethodItem(String requestUrl, String requestType, String controllerName, String requestMethodName,

                                   Class<?>[] methodParamTypes) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
            this.controllerName = controllerName;
            this.methodName = requestMethodName;
            this.methodParamTypes = methodParamTypes;
        }
    }
}
