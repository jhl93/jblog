package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CommentVo;

@Repository
public class CommentDao {

	@Autowired
	private SqlSession sqlSession;
	
	public int insertComment(CommentVo commentVo) {
		return sqlSession.insert("comment.insert", commentVo);
	}

	public CommentVo selectComment(CommentVo commentVo) {
		return sqlSession.selectOne("comment.selectOne", commentVo);
	}

	public List<CommentVo> selectCommList(CommentVo commentVo) {
		return sqlSession.selectList("comment.selectList", commentVo);
	}

	public int deleteComment(CommentVo commentVo) {
		return sqlSession.delete("comment.delete", commentVo);
	}

}
