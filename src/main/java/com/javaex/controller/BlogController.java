package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.interceptor.Auth;
import com.javaex.interceptor.AuthUser;
import com.javaex.service.BlogService;
import com.javaex.util.JSONResult;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CategoryVo;
import com.javaex.vo.CommentVo;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String main(@PathVariable String id,
					   @RequestParam(value = "cateNo", required = false, defaultValue = "0") int cateNo,
					   @RequestParam(value = "postNo", required = false, defaultValue = "0") int postNo,
					   @RequestParam(value = "crtPage", required = false, defaultValue = "1") int crtPage,
					   Model model) {
		System.out.println("blog main 요청");

		Map<String, Object> map = blogService.getMain(id, cateNo, postNo, crtPage);
		System.out.println(map.toString());
		
		model.addAttribute("blogVo", map.get("blogVo"));
		model.addAttribute("categoryList", map.get("categoryList"));
		model.addAttribute("postVo", map.get("postVo"));
		model.addAttribute("postList", map.get("postList"));
		model.addAttribute("map", map);
		model.addAttribute("crtPage", crtPage);

		return "blog/blog-main";
	}

	@Auth
	@RequestMapping(value = "/{id}/admin/basic", method = RequestMethod.GET)
	public String adminBasic(@PathVariable String id, @AuthUser UserVo authUser, Model model) {
		System.out.println("blog-admin-basic 요청");

		if (id.equals(authUser.getId())) {
			BlogVo blogVo = blogService.getBlog(id);
			System.out.println(blogVo.toString());
			model.addAttribute("blogVo", blogVo);

			return "blog/admin/blog-admin-basic";
		} else {
			return "redirect:/" + id;
		}
	}

	@Auth
	@RequestMapping(value = "/{id}/admin/basic/modify", method = RequestMethod.POST)
	public String adminBasicModify(@PathVariable String id, @ModelAttribute BlogVo blogVo,
			@RequestParam("file") MultipartFile file, @AuthUser UserVo authUser) {
		System.out.println("blog-admin-basic modify 요청");

		if (id.equals(authUser.getId())) {
			blogVo.setId(id);
			int count = blogService.modify(blogVo, file);
			System.out.println("수정 성공 횟수: " + count);
		}
		return "redirect:/" + id + "/admin/basic";
	}

	@Auth
	@RequestMapping(value = "/{id}/admin/category", method = RequestMethod.GET)
	public String adminCategory(@PathVariable String id, @AuthUser UserVo authUser, Model model) {
		System.out.println("blog-admin-category 요청");

		if (id.equals(authUser.getId())) {
			BlogVo blogVo = blogService.getBlog(id);
			System.out.println(blogVo.toString());
			model.addAttribute("blogVo", blogVo);

			return "blog/admin/blog-admin-cate";
		} else {
			return "redirect:/" + id;
		}
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/{id}/admin/categoryList", method = RequestMethod.POST)
	public List<CategoryVo> categoryList(@PathVariable String id, @AuthUser UserVo authUser) {
		System.out.println("categoryList 요청");

		if (id.equals(authUser.getId())) {
			List<CategoryVo> categoryList = blogService.getCateList(id);
			System.out.println(categoryList.toString());

			return categoryList;
		} else {
			return null;
		}
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/{id}/admin/categoryAdd", method = RequestMethod.POST)
	public CategoryVo categoryAdd(@PathVariable String id, @AuthUser UserVo authUser,
			@RequestBody CategoryVo categoryVo) {
		System.out.println("categoryAdd 요청");

		if (id.equals(authUser.getId())) {
			categoryVo.setId(id);
			categoryVo = blogService.addCategory(categoryVo);
			System.out.println(categoryVo.toString());

			return categoryVo;
		} else {
			return null;
		}
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/{id}/admin/categoryDel", method = RequestMethod.POST)
	public boolean categoryDel(@PathVariable String id, @AuthUser UserVo authUser, @RequestBody CategoryVo categoryVo) {
		System.out.println("categoryDel 요청");

		if (id.equals(authUser.getId())) {
			int count = blogService.delCategory(categoryVo);
			System.out.println("카테고리 삭제: " + count);

			if (count == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	@Auth
	@RequestMapping(value = "/{id}/admin/write", method = RequestMethod.GET)
	public String adminWrite(@PathVariable String id, @AuthUser UserVo authUser, Model model) {
		System.out.println("blog-admin-write 요청");

		if (id.equals(authUser.getId())) {
			Map<String, Object> map = blogService.getBlogCate(id);
			System.out.println(map.toString());
			model.addAttribute("blogVo", map.get("blogVo"));
			model.addAttribute("categoryList", map.get("categoryList"));

			return "blog/admin/blog-admin-write";
		} else {
			return "redirect:/" + id;
		}
	}

	@Auth
	@RequestMapping(value = "/{id}/admin/writePost", method = RequestMethod.POST)
	public String writePost(@PathVariable String id, @AuthUser UserVo authUser, @ModelAttribute PostVo postVo) {
		System.out.println("writePost 요청");

		if (id.equals(authUser.getId())) {
			int count = blogService.writePost(postVo);
			System.out.println("글쓰기 성공 횟수: " + count);
		}

		return "redirect:/" + id + "/admin/write";
	}

	@Auth
	@ResponseBody
	@RequestMapping(value = "/{id}/addComment")
	public JSONResult addComment(@PathVariable String id, @RequestBody CommentVo commentVo) {
		System.out.println("addComment 요청");
		
		commentVo = blogService.addComment(commentVo);
		JSONResult jsonResult = JSONResult.success(commentVo);
		
		return jsonResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}/getComment")
	public JSONResult getComment(@PathVariable String id, @RequestBody CommentVo commentVo) {
		System.out.println("getComment 요청");
		
		List<CommentVo> commentList = blogService.getComment(commentVo);
		JSONResult jsonResult = JSONResult.success(commentList);
		
		return jsonResult;
	}
	
	@Auth
	@ResponseBody
	@RequestMapping(value = "/{id}/delComment")
	public JSONResult delComment(@PathVariable String id, @RequestBody CommentVo commentVo) {
		System.out.println("delComment 요청");
		
		int count = blogService.delComment(commentVo);
		int cmtNo = commentVo.getCmtNo();
		
		JSONResult jsonResult;
		if(count != 0) {
			jsonResult = JSONResult.success(cmtNo);
		} else {
			jsonResult = JSONResult.error(null);
		}
		
		return jsonResult;
	}
}
