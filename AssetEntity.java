package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="asset")
public class AssetEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="asset_id")
	private Integer asset_id;
	
	@Column(name="asset_name")
	private String asset_name;
	
	@Column(name="asset_place")
	private String asset_place;
	
	@Column(name="admin_name")
	private String admin_name;
	
	@Column(name="asset_data")
	private String asset_data;
	
	@Column(name="register_date")
	private String register_date;
	
	
	// コンストラクタ
	AssetEntity(){
		
	}
	
	/*
	 * ゲッター
	 */
	public Integer getAsset_id() {
		return asset_id;
	}

	public String getAsset_name() {
		return asset_name;
	}

	public String getAsset_place() {
		return asset_place;
	}

	public String getAdmin_name() {
		return admin_name;
	}

	public String getAsset_data() {
		return asset_data;
	}

	public String getRegister_date() {
		return register_date;
	}

	public void setAsset_id(Integer asset_id) {
		this.asset_id = asset_id;
	}

	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

	public void setAsset_place(String asset_place) {
		this.asset_place = asset_place;
	}

	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}

	public void setAsset_data(String asset_data) {
		this.asset_data = asset_data;
	}

	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}

}
