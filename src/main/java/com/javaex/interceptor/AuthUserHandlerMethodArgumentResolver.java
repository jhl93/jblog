package com.javaex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.javaex.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser 안 붙어 있으면
		if(authUser == null) {
			return false;
		}
		
		// 파라미터 타입이 UserVo 아니면
		if(parameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if(supportsParameter(parameter) == false) {
			// 쓸 수 있는 파라미터 형식이 아니다. @AuthUser(X) UserVo(X)
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// @AuthUser(O) UserVo(O)
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		if(session == null) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// @AuthUser(O) UserVo(O) 세션(O)
		return session.getAttribute("authUser");
	}

}
