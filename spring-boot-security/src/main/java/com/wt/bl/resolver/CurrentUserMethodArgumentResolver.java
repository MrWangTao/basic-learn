package com.wt.bl.resolver;

import com.wt.bl.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 当前用户自定义注解解析器，增加方法注入，将自定义注解@CurrentUser 注解的方法参数注入当前的用户
 * 即：如何使自定义注解生效
 *
 * @author WangTao
 *         Created at 18/10/9 下午5:08.
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 用于判断是否有声明此注解
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        // 获取当前web请求的属性信息
        return webRequest.getAttribute("loginUser", RequestAttributes.SCOPE_REQUEST);
    }
}
