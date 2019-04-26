package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CategoryVo;
import com.javaex.vo.UserVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo userVo) {
		return sqlSession.insert("category.insert", userVo);
	}

	public List<CategoryVo> selectById(String id) {
		return sqlSession.selectList("category.selectListById", id);
	}

	public int insertCate(CategoryVo categoryVo) {
		return sqlSession.insert("category.insertCate", categoryVo);
	}

	public CategoryVo selectOne(CategoryVo categoryVo) {
		return sqlSession.selectOne("category.selectOne", categoryVo);
	}

	public int delete(CategoryVo categoryVo) {
		return sqlSession.delete("category.delete", categoryVo);
	}

}
