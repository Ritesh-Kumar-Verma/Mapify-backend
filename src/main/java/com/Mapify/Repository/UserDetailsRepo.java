package com.Mapify.Repository;

import com.Mapify.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserData,Integer> {

    @Query("SELECT u.username FROM UserData u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :usernamePart, '%'))")
    List<String> findUsernamesByUsernameContainingIgnoreCase(String usernamePart);

    UserData findByUsername(String username);

}
