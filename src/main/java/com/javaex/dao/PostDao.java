package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PostVo;

@Repository
public class PostDao {

	@Autowired
	private SqlSession sqlSession;

	public int insert(PostVo postVo) {
		return sqlSession.insert("post.insert", postVo);
	}

	public PostVo selectLast(String id) {
		return sqlSession.selectOne("post.selectLast", id);
	}

	public List<PostVo> selectList(PostVo postVo, int startRnum, int endRnum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cateNo", postVo.getCateNo());
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		
		return sqlSession.selectList("post.selectList", map);
	}

	public List<PostVo> selectListByCate(int cateNo, int startRnum, int endRnum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cateNo", cateNo);
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		
		return sqlSession.selectList("post.selectListByCate", map);
	}

	public int totalCount(int cateNo) {
		return sqlSession.selectOne("post.totalCount", cateNo);
	}

	public PostVo selectOne(int postNo) {
		return sqlSession.selectOne("post.selectOne", postNo);
	}

}
