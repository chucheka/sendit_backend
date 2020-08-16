package com.myapp.sendit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.sendit.model.Rider;



@Repository
public interface RiderRepository extends JpaRepository<Rider,Long>{



	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	List<Rider> findByAddressContaining(String cityName);
	
}