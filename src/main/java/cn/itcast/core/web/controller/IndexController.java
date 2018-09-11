package cn.itcast.core.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.Page;
import cn.itcast.core.bean.School;
import cn.itcast.core.service.SchoolService;
@Controller
public class IndexController {
	
	//依赖注入
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(value = "/index")
	public String showSchool() {
		return "redirect:/index/list.action";
	}
	
	
	   //学校信息列表--后台信息维护
		@RequestMapping(value = "/index/list")
		public String list(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="10")Integer rows, 
				String score, Model model) {
			Page<School> school = schoolService.findSchoolList(page, rows, score);
			model.addAttribute("page", school);
			//参数回显
			model.addAttribute("score", score);
			return "index";
		}
		
		

}
