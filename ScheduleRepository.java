package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ScheduleRepository extends JpaRepository<ScheduleEntity, Integer> {
	// SELECT
//	List<ScheduleEntity> findByLoginId(String schedule_id);
//	
//	// SELECT start_time FROM schedule
//	// asset_id, start_time, customer_id
////	Integer  findByAsset_id();
//	List<ScheduleEntity> findByAsset_id();
//	List<ScheduleEntity> findByStart_time();
//	Integer findByCustomer_id();
	
}
