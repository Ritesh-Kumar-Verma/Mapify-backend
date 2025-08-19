package com.Mapify.Repository;

import com.Mapify.Model.UsersLoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepo extends JpaRepository<UsersLoginDetails, Integer> {

    UsersLoginDetails findByUsername(String username);
}
