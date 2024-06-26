package com.uniroma3.siw.freshgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uniroma3.siw.freshgame.model.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long> {

}
