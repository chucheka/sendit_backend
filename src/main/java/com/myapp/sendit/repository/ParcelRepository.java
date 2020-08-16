package com.myapp.sendit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.sendit.model.Parcel;


@Repository
public interface ParcelRepository extends JpaRepository<Parcel,Long> {
 List<Parcel> findByParcelIdIn(List<Long> assignedParcelIds);
}
