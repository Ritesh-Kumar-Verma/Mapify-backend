package com.Mapify.services;

import com.Mapify.Model.UserData;
import com.Mapify.Model.UsersLoginDetails;
import com.Mapify.Repository.MembersRepo;
import com.Mapify.Repository.UserDetailsRepo;
import com.Mapify.Repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserLoginService {
    private boolean loginStatus;


    @Autowired
    UserLoginRepo userLoginRepo;


    @Autowired
    UserDetailsRepo userDetailsRepo;


    public boolean getLoginStatus(){
        return loginStatus;
    }

    public UsersLoginDetails addUserLoginDetails(UsersLoginDetails usersLoginDetails) {
        try {
            UsersLoginDetails savedUser = userLoginRepo.save(usersLoginDetails);
            loginStatus = true;


            UserData userData = new UserData();
            userData.setId(savedUser.getId());
            userData.setUsername(savedUser.getUsername());
            userData.setEmail(savedUser.getEmail());
            userDetailsRepo.save(userData);

            return savedUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public UsersLoginDetails verifyUser(UsersLoginDetails usersLoginDetails) {
        UsersLoginDetails user1= userLoginRepo.findByUsername(usersLoginDetails.getUsername());
        if(user1 != null && user1.getPassword().equals(usersLoginDetails.getPassword())){
            loginStatus=true;
            return user1;
        }
        return null;
    }






}
