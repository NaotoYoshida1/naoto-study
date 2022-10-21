package com.example.demo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Integer UserId;
	
	@Column(name="login_id")
	private String loginId;
	
	@Column(name="customer_name")
	private String Name;
	
	@Column(name="birthday")
	private Date Birthday;
	
	@Column(name="sex")
	private Integer Sex;
	
	@Column(name="register_date")
	private Timestamp register_date;
	
	@Column(name="passwords")
	private String pass;
	
	@Column(name="update_date")
	private Timestamp upd_date;
	
	
	// デフォルトコンストラクタ
	public UserEntity() {
		super();
	}
	
	/**
	// データ更新
	 */
	public UserEntity(Integer UserId, String pass){
		super();
		this.UserId = UserId;
		this.pass = pass;
	}
	
	/**
	// データ更新
	 */
	UserEntity(String loginId, String Name, Date Birthday, Integer Sex, Timestamp register_date){
		super();
		this.loginId = loginId;
		this.Name = Name;
		this.Birthday = Birthday;
		this.Sex = Sex;
		this.register_date = register_date;
	}
	
	/**
	// 新規登録コンストラクタ
	 */
	public UserEntity(Integer UserId, String loginId, String Name, Date Birthday, Integer Sex, Timestamp register_date, String pass) {
		this(loginId, Name, Birthday, Sex, register_date);
		this.UserId = UserId;
	}
	
	/**
	// ★新規登録コンストラクタ
	 */
	public UserEntity(Integer UserId, String loginId, String Name, Date Birthday, Integer Sex, Timestamp register_date, String pass, Timestamp update_date) {
		this(loginId, Name, Birthday, Sex, register_date);
		this.UserId = UserId;
		this.pass = pass;
		this.upd_date = update_date;
	}
	
	/**
	// 更新用コンストラクタ
	 */
	UserEntity(String loginId, String pass, Timestamp register_date){
		this.loginId = loginId;
		this.pass = pass;
		this.register_date = register_date;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Date getBirthday() {
		return Birthday;
	}

	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}

	public Integer getSex() {
		return Sex;
	}

	public void setSex(Integer sex) {
		Sex = sex;
	}

	public Timestamp getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Timestamp register_date) {
		this.register_date = register_date;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Timestamp getUpd_date() {
		return upd_date;
	}

	public void setUpd_date(Timestamp upd_date) {
		this.upd_date = upd_date;
	}

}
