package com.example.demo;

import java.sql.Time;

public class MainViewEntity {
	
	private String asset_name;
	private Time sche_start_datetime;
	private String Name;
	
	/*
	 * getter
	 */
	public String getAsset_name() {
		return asset_name;
	}
	public Time getSche_start_datetime() {
		return sche_start_datetime;
	}
	public String getName() {
		return Name;
	}
	
	/*
	 * setter
	 */
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public void setSche_start_datetime(Time sche_start_datetime) {
		this.sche_start_datetime = sche_start_datetime;
	}
	public void setName(String customer_name) {
		this.Name = customer_name;
	}

}
