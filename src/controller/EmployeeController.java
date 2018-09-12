package controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bean.Department;
import bean.Employee;
import bean.Page;
import dao.DepartmentDao;
import dao.EmployeeDao;

@Controller
public class EmployeeController {
	List<Employee> allemps;
	
	@RequestMapping("showEmployee")
	public ModelAndView showEmployee() {
		EmployeeDao ed = new EmployeeDao();
		DepartmentDao de = new DepartmentDao();
		List<Employee> allemps;
		// 查询全部值计算出全部页数
		List<Department> deps = new ArrayList<>();
		deps = de.search();
		allemps = ed.search();
		Page page = new Page(1, 10, 5, 1, allemps.size());

		// 根据初始值查出数据并返回
		List<Employee> emps = ed.search((page.getNowpage() - 1) * page.getPagesize(), page.getPagesize());

		ModelAndView mv = new ModelAndView("employee/employee");
		mv.addObject("p", page);
		mv.addObject("emps", emps);
		mv.addObject("deps", deps);
		
		return mv;
	}
	
	
	@RequestMapping("showAdd")
	public ModelAndView showAdd() {
		List<Department> deps = new ArrayList<>();
		DepartmentDao depDao = new DepartmentDao();
		deps = depDao.search();
		
		ModelAndView mv = new ModelAndView("employee/add");
		mv.addObject("deps", deps);
		
		return mv;
		
	}
	
	
	@RequestMapping("delEmployee")
	public String delEmployee(String ids) {
		EmployeeDao ed = new EmployeeDao();
		ed.del(ids);
		return "redirect:showEmployee.do";
	}
	
	@RequestMapping("page")
	public ModelAndView Page(Integer nowpage) {
		Page p = new Page(nowpage, 10, 5, 1, allemps.size());
		EmployeeDao ed = new EmployeeDao();

		// 根据传来的页码查询结果并返回
		List<Employee> emps = ed.search((nowpage - 1) * p.getPagesize(), p.getPagesize());

		ModelAndView mv = new ModelAndView("employee/employee");
		mv.addObject("p", p);
		mv.addObject("emps", emps);
		
		return mv;
		
	}
	
	@RequestMapping("showSearchByEmployee")
	public ModelAndView showSearch(Employee emp,Integer nowpage) {
		if(nowpage==null) {
			nowpage=1;
		}
		
		List<Department> deps = new ArrayList<>();
		DepartmentDao depDao = new DepartmentDao();
		deps = depDao.search();
		
		EmployeeDao eDao = new EmployeeDao();
		List<Employee> searchemps = eDao.search(emp);
		Page searchpage = new Page(nowpage, 10, 5, 1, searchemps.size());

		List<Employee> pageemps = eDao.search(emp, (searchpage.getNowpage() - 1) * searchpage.getPagesize(),
				searchpage.getPagesize());
		
		
		ModelAndView mv = new ModelAndView("employee/employee");
		mv.addObject("p", searchpage);
		mv.addObject("inputemp", emp);
		mv.addObject("emps", pageemps);
		mv.addObject("deps", deps);
		
		return mv;
	}
	
	
	@RequestMapping("showUpdate")
	public ModelAndView showUpdate(String ids) {
		
		EmployeeDao ed = new EmployeeDao();
		List<Department> deps = new ArrayList<>();
		DepartmentDao depDao = new DepartmentDao();
		deps = depDao.search();
		List<Employee> emps = ed.find(ids);
		
		ModelAndView mv = new ModelAndView("employee/update");
		mv.addObject("emps", emps);
		mv.addObject("deps", deps);
		
		return mv;
		
		
	}
	
	@RequestMapping("updateEmployee")
	public void updateEmployee(@RequestBody List<Employee> emps,PrintWriter out) {
		EmployeeDao ed = new EmployeeDao();
		ed.update(emps);
		out.print("true");
		//return "redirect:showEmployee.do";
	}
	
}
