package com.javaex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("login interceptor");
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo userVo = userService.login(id, password);
		
		if(userVo == null) {
			response.sendRedirect(request.getContextPath() + "/user/loginform?result=fail");
			return false;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("authUser", userVo);
		response.sendRedirect(request.getContextPath() + "/");
		return false;
	}
	
}
