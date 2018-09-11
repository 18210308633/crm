package cn.itcast.core.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.Page;
import cn.itcast.common.utils.Result;
import cn.itcast.core.bean.School;
import cn.itcast.core.service.SchoolService;
@Controller
public class SchoolController {
	
	//依赖注入
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(value = "/school")
	public String showSchool() {
		return "redirect:/school/list.action";
	}
	
	
	// 客户列表
		@RequestMapping(value = "/school/list")
		public String list(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, 
				String score, Model model) {
			Page<School> school = schoolService.findSchoolList(page, rows, score);
			model.addAttribute("page", school);
			//参数回显
			model.addAttribute("score", score);
			return "school";
		}
		
		@RequestMapping("/school/edit")
		@ResponseBody
		public School getSchoolById(int id) {
			School School = schoolService.getSchoolById(id);
			return School;
		}
		
		@RequestMapping("/school/update")
		@ResponseBody
		public Result schoolUpdate(School school) {
			try {
				schoolService.updateSchool(school);
				return new Result(1,"修改成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return new Result(0,"修改失败");
		}
		
		@RequestMapping("/school/delete")
		@ResponseBody
		public Result schoolDelete(int id) {
			try {
				schoolService.deleteSchool(id);
				return new Result(1,"删除成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());
				
			}
			return new Result(0,"删除失败");
		}
		
		@RequestMapping("/school/insert")
		@ResponseBody
		public Result schoolInsert(School school) {
			try {
				schoolService.insertSchool(school);
				return new Result(1,"增加成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println(e.getMessage());				
			}
			return new Result(0,"增加失败");
		}

}
