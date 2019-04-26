package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/joinform", method = RequestMethod.GET)
	public String joinForm() {
		System.out.println("joinform 요청");
		
		return "user/joinForm";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("join 요청");
		
		int count = userService.join(userVo);
		System.out.println("성공 횟수: " + count);
		return "user/joinSuccess";
	}
	
	@RequestMapping(value = "/loginform", method = RequestMethod.GET)
	public String loginForm() {
		System.out.println("loginform 요청");
		
		return "user/loginForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	public boolean idCheck(@RequestParam("id") String id) {
		System.out.println("idcheck 요청");
		
		boolean result = userService.idcheck(id);
		return result;
	}
}
