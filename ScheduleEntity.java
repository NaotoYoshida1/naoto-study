package com.example.demo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class ScheduleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="schedule_id")
	private Integer schedule_id;
	
	@Column(name="sche_start_datetime")
	private Timestamp sche_start_datetime;
	
//	@Column(name="sche_start_time")
//	private Time sche_start_time;
	
	@Column(name="sche_end_datetime")
	private Timestamp sche_end_datetime;
	
//	@Column(name="sche_end_time")
//	private Time sche_end_time;
	
	@Column(name="asset_id")
	private Integer asset_id;
	
	@Column(name="customer_id")
	private Integer customer_id;
	
	@Column(name="number_of_people")
	private Integer number_of_people;
	
	@Column(name="purpose")
	private String purpose;
	
	@Column(name="register_date")
	private Timestamp register_date;
	
	@Column(name="schedule_upd")
	private Timestamp schedule_upd;
	
	@Column(name="stock")
	private Integer stock;
	
	// コンストラクタ
	ScheduleEntity(){
		
	}

	/*
	 * getter
	 */

	public Integer getSchedule_id() {
		return schedule_id;
	}

	public Timestamp getSche_start_datetime() {
		return sche_start_datetime;
	}

//	public Time getSche_start_time() {
//		return sche_start_time;
//	}

	public Timestamp getSche_end_datetime() {
		return sche_end_datetime;
	}

//	public Time getSche_end_time() {
//		return sche_end_time;
//	}

	public Integer getAsset_id() {
		return asset_id;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public Integer getNumber_of_people() {
		return number_of_people;
	}

	public String getPurpose() {
		return purpose;
	}

	public Timestamp getRegister_date() {
		return register_date;
	}

	public Timestamp getSchedule_upd() {
		return schedule_upd;
	}

	public Integer getStock() {
		return stock;
	}

	/*
	 * setter
	 */
	public void setSchedule_id(Integer schedule_id) {
		this.schedule_id = schedule_id;
	}

	public void setSche_start_datetime(Timestamp sche_start_datetime) {
		this.sche_start_datetime = sche_start_datetime;
	}

//	public void setSche_start_time(Time sche_start_time) {
//		this.sche_start_time = sche_start_time;
//	}

	public void setSche_end_datetime(Timestamp sche_end_datetime) {
		this.sche_end_datetime = sche_end_datetime;
	}

//	public void setSche_end_time(Time sche_end_time) {
//		this.sche_end_time = sche_end_time;
//	}

	public void setAsset_id(Integer asset_id) {
		this.asset_id = asset_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public void setNumber_of_people(Integer number_of_people) {
		this.number_of_people = number_of_people;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setRegister_date(Timestamp register_date) {
		this.register_date = register_date;
	}

	public void setSchedule_upd(Timestamp schedule_upd) {
		this.schedule_upd = schedule_upd;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	
	
}
