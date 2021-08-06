package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

	
	/**
	 * 全件検索を行う.
	 * @return 管理者情報
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address"
				+ ",zip_code,address,telephone,salary,characteristics,"
				+ "dependents_count FROM employees ORDER BY hire_date;";

		List<Employee> employeeList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		if (employeeList.size() == 0) {
			return null;
		} else {
			return employeeList;
		}
	}

	/**
	 * 主キー検索を行う.
	 * @param id
	 * @return 管理者情報
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address"
				+ ",zip_code,address,telephone,salary,characteristics,"
				+ "dependents_count FROM employees WHERE id = :id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

	}
	
	
	/**
	 * 渡した従業員情報を更新する.
	 * @param employee
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String updateSql = "UPDATE employees SET id=:id,name = :name,image=:image,gender=:gender,"
				+ "hire_date=:hireDate,mail_address=:mailAddress,zip_code=:zipCode,address=:address,"
				+ "telephone=:telephone,salary=:salary,characteristics=:characteristics,"
				+ "dependents_count=:dependentsCount WHERE id = :id;";
		
		template.update(updateSql, param);
		
	}

}
