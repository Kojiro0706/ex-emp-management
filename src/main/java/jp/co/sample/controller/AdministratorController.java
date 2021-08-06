package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author kojiro0706
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private HttpSession session;

	@Autowired
	private AdministratorService administratorService;


	@ModelAttribute
	public InsertAdministratorForm setUpInsertadministratorForm() {

		return new InsertAdministratorForm();
	}

	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 管理者登録画面に遷移する.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form 管理者情報
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();

		BeanUtils.copyProperties(form, administrator);

		administratorService.insert(administrator);

		return "redirect:/";

	}

	/**
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {


		Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());

		if (administrator == null) {
			model.addAttribute("message", "メールアドレスまたはパスワードが不正です!");
			
			return toLogin();
		}
		
		session.setAttribute("administratorName",administrator.getName() );
		
		return "forward:/employee/showList";
	}

}
