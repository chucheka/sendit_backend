package com.myapp.sendit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.sendit.model.PasswordResetToken;



public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken,Long>{

	Optional<PasswordResetToken> findByToken(String token);
	
}