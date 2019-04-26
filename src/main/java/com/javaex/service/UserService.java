package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private CategoryDao categoryDao;

	@Transactional
	public int join(UserVo userVo) {
		int count = userDao.insert(userVo);
		System.out.println("회원가입: " + count);
		int count2 = blogDao.insert(userVo);
		System.out.println("블로그 생성: " + count2);
		int count3 = categoryDao.insert(userVo);
		System.out.println("기본 카테고리 생성: " + count3);
		return count;
	}

	public boolean idcheck(String id) {
		UserVo userVo = userDao.selectById(id);

		if (userVo == null) { // 데이터가 없으면 true -> 가입 가능
			return true;
		} else {
			return false;
		}
	}

	public UserVo login(String id, String password) {
		return userDao.select(id, password);
	}

}
