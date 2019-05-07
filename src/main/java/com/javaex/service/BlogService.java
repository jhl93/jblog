package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.dao.CategoryDao;
import com.javaex.dao.CommentDao;
import com.javaex.dao.PostDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.CommentVo;
import com.javaex.vo.FileVo;
import com.javaex.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CommentDao commentDao;
	
	public Map<String, Object> getMain(String id, int cateNo, int postNo, int crtPage) {
		BlogVo blogVo = blogDao.selectById(id);
		List<CategoryVo> categoryList = categoryDao.selectById(id);

		int listCnt = 3;

		// 현재 페이지
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);

		// 시작글 번호
		int startRnum = (crtPage - 1) * listCnt; // 1page -> 0

		// 끝글 번호
		int endRnum = startRnum + listCnt; // 1page -> 10

		List<PostVo> postList = null;
		PostVo postVo = null;
		if(cateNo == 0) {
			System.out.println("가장 최신 post List");
			postVo = postDao.selectLast(id);
			if(postVo != null) {
				postList = postDao.selectList(postVo, startRnum, endRnum);
				postVo = postList.get(0);
				cateNo = postVo.getCateNo();
			} 
		} else {
			System.out.println("선택된 카테고리 post List");
			postList = postDao.selectListByCate(cateNo, startRnum, endRnum);
			System.out.println(postList);
			
			if(!postList.isEmpty() && postNo == 0) {
				System.out.println("최근 post");
				postVo = postList.get(0);
			} else {
				System.out.println("선택된 post");
				/*
				 * for(PostVo vo : postList) { if(vo.getPostNo() == postNo) { postVo = vo; } }
				 */
				postVo = postDao.selectOne(postNo);
			}
		}

		// 페이징 계산
		// 전체 글 갯수
		int totalCount = postDao.totalCount(cateNo);

		// 페이지당 버튼 갯수
		int pageBtnCount = 5;

		// 마지막 버튼 번호
		// 1 -> 1~5
		// 2 -> 1~5
		// 3 -> 1~5

		// 7 -> 6~10
		// 10 -> 6~10
		int endPageBtnNo = (int) Math.ceil(crtPage / (double) pageBtnCount) * pageBtnCount;
		System.out.println(endPageBtnNo);

		// 시작 버튼 번호
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);

		// 다음 화살표 유무
		boolean next = false;
		if (endPageBtnNo * listCnt < totalCount) { // 10 * 10 < 107
			next = true;
		} else {
			endPageBtnNo = (int) Math.ceil(totalCount / (double) listCnt);
		}

		// 이전 화살표 유무
		boolean prev = false;
		if (startPageBtnNo != 1) {
			prev = true;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("postList", postList);
		map.put("postVo", postVo);
		map.put("blogVo", blogVo);
		map.put("categoryList", categoryList);
		map.put("startPageBtnNo", startPageBtnNo);
		map.put("endPageBtnNo", endPageBtnNo);
		map.put("next", next);
		map.put("prev", prev);
		
		return map;
	}

	public BlogVo getBlog(String id) {
		return blogDao.selectById(id);
	}

	public int modify(BlogVo blogVo, MultipartFile file) {
		if (!file.getOriginalFilename().equals("")) {
			String saveDir = "/home/bituser/upload";

			// 오리지날 파일명
			String orgName = file.getOriginalFilename();
			System.out.println("orgName: " + orgName);

			// 확장자
			String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			System.out.println("exName: " + exName);

			// 저장할 파일명
			String saveName = System.currentTimeMillis() + UUID.randomUUID().toString();
			System.out.println("saveName: " + saveName);

			// 파일패스
			String filePath = saveDir + "/" + saveName;
			System.out.println("filePath: " + filePath);

			// 파일사이즈
			long fileSize = file.getSize();
			System.out.println("fileSize: " + fileSize);

			FileVo fileVo = new FileVo(orgName, exName, saveName, filePath, fileSize);
			System.out.println(fileVo.toString());

			// 서버 파일 복사
			try {
				byte[] fileData = file.getBytes();
				OutputStream out = new FileOutputStream(saveDir + "/" + saveName);
				BufferedOutputStream bout = new BufferedOutputStream(out);

				bout.write(fileData);

				if (bout != null) {
					bout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// fileVo DB저장
			blogVo.setLogoFile(saveName);
		}
		System.out.println(blogVo.toString());
		int count = blogDao.update(blogVo);

		return count;
	}

	public List<CategoryVo> getCateList(String id) {
		return categoryDao.selectById(id);
	}

	@Transactional
	public CategoryVo addCategory(CategoryVo categoryVo) {
		int count = categoryDao.insertCate(categoryVo);
		System.out.println("카테고리 추가: " + count);
		return categoryDao.selectOne(categoryVo);
	}

	public int delCategory(CategoryVo categoryVo) {
		return categoryDao.delete(categoryVo);
	}

	public int writePost(PostVo postVo) {
		return postDao.insert(postVo);
	}

	/*
	 * @Transactional public List<PostVo> getLastPost(String id) { PostVo postVo =
	 * postDao.selectLast(id);
	 * 
	 * if(postVo != null) { List<PostVo> postList = postDao.selectList(postVo);
	 * return postList; } else { return null; } }
	 */

	/*
	 * public List<PostVo> getPostList(int cateNo) { return
	 * postDao.selectListByCate(cateNo); }
	 */

	@Transactional
	public CommentVo addComment(CommentVo commentVo) {
		int count = commentDao.insertComment(commentVo);
		System.out.println("댓글 쓰기: " + count);
		return commentDao.selectComment(commentVo);
	}

	public List<CommentVo> getComment(CommentVo commentVo) {
		return commentDao.selectCommList(commentVo);
	}

	public int delComment(CommentVo commentVo) {
		return commentDao.deleteComment(commentVo);
	}

	public Map<String, Object> getBlogCate(String id) {
		BlogVo blogVo = blogDao.selectById(id);
		List<CategoryVo> categoryList = categoryDao.selectById(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogVo", blogVo);
		map.put("categoryList", categoryList);
		return map;
	}

}
