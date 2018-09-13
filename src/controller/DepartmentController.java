package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Department;
import bean.Page;
import dao.DepartmentDao;
import dao.EmployeeDao;

@Controller
public class DepartmentController {

	@RequestMapping("showDepartment")
	public ModelAndView showDepartment() {
		List<Department> list = new ArrayList<>();
		List<Department> lists = new ArrayList<>();
		DepartmentDao deDao = new DepartmentDao();
		lists = deDao.search();
		list = deDao.search();
		Page page = new Page(1, 5, 5, 1, list.size());
		Department dep = new Department();
		List<Department> deps = new ArrayList<>();
		deps = deDao.search(dep, (page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

		ModelAndView mv = new ModelAndView("department/department");
		mv.addObject("p", page);
		mv.addObject("deps", deps);
		mv.addObject("lists", lists);

		return mv;
	}

	@RequestMapping("addDepartment")
	public String addDepartment(Department dep) {
		DepartmentDao depDao = new DepartmentDao();
		depDao.addDepartment(dep);
		return "redirect:showDepartment.do";

	}

	@RequestMapping("delDepartment")
	public String delDepartment(String ids) {
		DepartmentDao ed = new DepartmentDao();
		ed.del(ids);
		return "redirect:showDepartment.do";
	}
	
	@RequestMapping("updateDepartment")
	public void updateDepartment(@RequestBody List<Department> deps,PrintWriter out) {
		DepartmentDao ed = new DepartmentDao();

		ed.update(deps);
		out.print("true");
		//return "redirect:showDepartment.do";
	}
	
	@RequestMapping("showSearchByDepartment")
	public ModelAndView showSearch(Department dep,Integer nowpage) {
		if(nowpage==null) {
			nowpage=1;
		}
		
		DepartmentDao depDao = new DepartmentDao();
		List<Department> list = new ArrayList<>();
		List<Department> lists = new ArrayList<>();
		lists = depDao.search();
		list = depDao.search(dep);
		Page searchpage = new Page(nowpage, 5, 5, 1, list.size());

		List<Department> deps = new ArrayList<>();

		deps = depDao.search(dep, (searchpage.getNowpage() - 1) * searchpage.getPagesize(), searchpage.getPagesize());
		
		ModelAndView mv = new ModelAndView("department/department");
		mv.addObject("p", searchpage);
		mv.addObject("deps", deps);
		mv.addObject("inputemp", dep);
		mv.addObject("lists", lists);
		
		return mv;
		
	}
	
	
}
