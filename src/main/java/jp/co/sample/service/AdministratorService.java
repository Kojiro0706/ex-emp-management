package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository administratorRepository;
	
	/**
	 *  管理者情報を挿入するメソッド
	 */
	public void insert(Administrator administrator) {
		
		administratorRepository.insert(administrator);
	}
	
	/**
	 * 管理者のログイン処理をするメソッド
	 * 
	 * @param mailAddress メールアドレス
	 * @param password　パスワード
	 * @return　管理者情報
	 */
	public Administrator login(String mailAddress, String password) {

		return administratorRepository.findByMailAddressAndPassword(mailAddress, password);

	}

}
