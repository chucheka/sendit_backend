package com.myapp.sendit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.sendit.model.Role;
import com.myapp.sendit.model.RoleName;


public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleName roleUser);

}
