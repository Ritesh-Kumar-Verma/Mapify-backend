package com.Mapify.Controller;


import com.Mapify.Model.Members;

import com.Mapify.Model.UsersLoginDetails;
import com.Mapify.services.UserLoginService;
import com.Mapify.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MembersController {

    @Autowired
    UserServices userServices;

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/search")
    public ResponseEntity<List<String>> searchUser(@RequestBody UsersLoginDetails usersLoginDetails, @RequestParam String username){
        UsersLoginDetails user = userLoginService.verifyUser(usersLoginDetails);
        if(user!=null && username != null && !username.isEmpty()){
            List<String> result = userServices.searchUser(username);
            result.removeIf(name -> name.equals(usersLoginDetails.getUsername()));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
    }




    @PostMapping("/sendrequest")
    public ResponseEntity<Members> sendRequest(@RequestBody UsersLoginDetails usersLoginDetails ,@RequestParam String username){
        try{
            if(usersLoginDetails.getUsername().equalsIgnoreCase(username)){
               return new ResponseEntity<>(null , HttpStatus.NOT_ACCEPTABLE);
            }
            UsersLoginDetails user1 = userLoginService.verifyUser(usersLoginDetails);
            if(user1 != null) {
                Members members = userServices.sendRequest(user1.getId(), username);
                return new ResponseEntity<>(members, HttpStatus.OK);
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(null , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/acceptrequest")
    public ResponseEntity<String> acceptRequest(@RequestBody UsersLoginDetails usersLoginDetails, @RequestParam String username){
        UsersLoginDetails user1 =  userLoginService.verifyUser(usersLoginDetails);
        if(user1 != null){
            String msg = userServices.acceptRequest(user1, username);
            if(msg.equals("Not Found")){
                return new ResponseEntity<>("Not Found", HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>("Accepted" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
    }




    @PostMapping("/pendingrequestsfromme")
    public ResponseEntity<List<String>> getPendingRequestsFromMe(@RequestBody UsersLoginDetails usersLoginDetails) {
        UsersLoginDetails user1 = userLoginService.verifyUser(usersLoginDetails);
        if (user1 != null) {
            List<String> result = userServices.getPendingRequestsFromMe(user1.getId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/pendingrequests")
    public ResponseEntity<List<String>> getPendingRequest(@RequestBody UsersLoginDetails usersLoginDetails){
        UsersLoginDetails user1 = userLoginService.verifyUser(usersLoginDetails);
        if(user1 != null){
            List<String> result = userServices.getPendingRequests(user1.getId());
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/rejectrequest")
    public ResponseEntity<String> rejectRequest(@RequestBody UsersLoginDetails usersLoginDetails , @RequestParam String username){
        UsersLoginDetails user1 = userLoginService.verifyUser(usersLoginDetails);
        if(user1 != null){
            String msg = userServices.rejectRequest(user1.getId() , username);
            if(msg.equals("Rejected")){
            return new ResponseEntity<>("Rejected" , HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Wrong Details",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/getmemberslist")
    public ResponseEntity<List<String>> getMembersList(@RequestBody UsersLoginDetails usersLoginDetails){
        UsersLoginDetails user = userLoginService.verifyUser(usersLoginDetails);
        if(user != null){
            List<String> result = userServices.getMembersList(user.getId());
            return new ResponseEntity<>(result , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null , HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/getlocation")
    public ResponseEntity<List<Double>> getLocation(@RequestBody UsersLoginDetails usersLoginDetails , @RequestParam String username){
        UsersLoginDetails user = userLoginService.verifyUser(usersLoginDetails);
        if(user != null){
            List<Double> result = userServices.getLocation(username);
            if(result.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        return new ResponseEntity<>(null , HttpStatus.UNAUTHORIZED);
    }






}

