package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Integer>  {
	
//	List<AssetEntity> findByLoginId(String asset_id);
//	
//	// SELECT login_id FROM customer WHERE login_id = loginId
//	// SELECT asset_name FROM schedule
//	List<AssetEntity> findByAsset_name(String asset_name);
//	
//	// SELECT asset_name FROM asset WHERE asset_id = ?;
//	List<AssetEntity> findByAsset_idEquals(Integer asset_id);
	
	
	// SELECT customer_name FROM schedule
	// SELECT compunion_name FROM schedule
	
	
}
