package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author kojiro0706
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧情報を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員情報
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {

		List<Employee> employeeList = employeeService.showList();

		model.addAttribute("employeeList", employeeList);

		return "employee/list.html";

	}

	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {

		int showDetailId = Integer.parseInt(id);

		Employee employee = employeeService.showDetail(showDetailId);

		model.addAttribute("employee", employee);

		return "employee/detail";

	}

	/**
	 * 扶養人数の更新.
	 * 
	 * @param form フォーム
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		int showDetailId = Integer.parseInt(form.getId());
		Employee employee = employeeService.showDetail(showDetailId);
		int dependentsCount = Integer.parseInt(form.getDependentsCount());
		employee.setDependentsCount(dependentsCount);

		employeeService.Update(employee);
		return "redirect:/employee/showList";
	}
}
