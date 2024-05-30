package com.example.repo;



import java.time.LocalDate;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



import com.example.entity.Users;



@Repository

public interface user_repo extends JpaRepository<Users, Integer> {



    public Users findByEmail(String email);
    
    public Users findByUsername(String username); 



    public List<Users> findByFirstName(String firstName);



    public List<Users> findByLastName(String lastName);



    public List<Users> findByDateOfBirth(LocalDate dateOfBirth);
    
    boolean existsByEmail(String email);



}