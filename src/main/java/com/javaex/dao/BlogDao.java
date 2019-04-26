package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("blog.insert", userVo);
	}

	public BlogVo selectById(String id) {
		return sqlSession.selectOne("blog.selectById", id);
	}

	public int update(BlogVo blogVo) {
		return sqlSession.update("blog.update", blogVo);
	}
}
