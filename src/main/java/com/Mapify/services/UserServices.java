package com.Mapify.services;


import com.Mapify.Model.Members;
import com.Mapify.Model.UserData;
import com.Mapify.Model.UsersLoginDetails;
import com.Mapify.Repository.MembersRepo;
import com.Mapify.Repository.UserDetailsRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    MembersRepo membersRepo;


    public UserData updateSelfLocation(UserData userData) {

        try{
            UserData user = userDetailsRepo.save(userData);
            return user;
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<String> searchUser(String username) {
        return userDetailsRepo.findUsernamesByUsernameContainingIgnoreCase(username);
    }

    public Members sendRequest(Integer id, String username) {
        UserData user2 = userDetailsRepo.findByUsername(username);
        if(user2 != null){
            if(membersRepo.findByIdUser1AndIdUser2(id, user2.getId()) !=null ){
                return null;
            }
            Members members = new Members();
            members.setIdUser1(id);
            members.setIdUser2(user2.getId());
            members.setStatus("Pending");

            return membersRepo.save(members);
        }
        return null;
    }

    public List<String> getPendingRequestsFromMe(int id) {

        List<Members> membersList = membersRepo.findByIdUser1AndStatus(id,"Pending");
        List<String> result = new ArrayList<>();

        for(Members m : membersList){
            Optional<UserData> userData = userDetailsRepo.findById(m.getIdUser2());
            if(userData.isPresent()){
                result.add(userData.get().getUsername());
            }
        }
        return result;

    }


    public List<String> getPendingRequests(Integer id) {
        List<Members> memberList = membersRepo.findByIdUser2AndStatus(id,"Pending");
        List<String> result = new ArrayList<>();
        for(Members m : memberList){
            Optional<UserData> userData = userDetailsRepo.findById(m.getIdUser1());
            if(userData.isPresent()){
                result.add(userData.get().getUsername());
            }
        }
        return result;

    }

    public String acceptRequest(UsersLoginDetails usersLoginDetails, String username) {
        UserData user2 = userDetailsRepo.findByUsername(username);
        if(user2 == null){
            return "Not Found";
        }
        Members m = membersRepo.findByIdUser1AndIdUser2( user2.getId(), usersLoginDetails.getId() );
        if(m.getStatus().equals("Pending")){
            m.setStatus("Accepted");
            membersRepo.save(m);
            return "Accepted";
        }
        return "Not Found";

    }

    @Transactional
    public String rejectRequest(Integer id, String username) {
        UserData user2 = userDetailsRepo.findByUsername(username);
        System.out.println("i am running");
        if(user2 != null){
            System.out.println("i am running too");
           Long n = membersRepo.deleteByIdUser1AndIdUser2AndStatus(user2.getId() , id , "Pending");
           if(n>=1){
               return "Rejected";
           }
        }
        return "Not Found";
    }






    public List<String> getMembersList(Integer id) {
        List<Integer> membersId = membersRepo.findMembers(id);

        List<String> membersList = new ArrayList<>();
        for(int i : membersId){
            membersList.add(userDetailsRepo.findById(i).get().getUsername());
        }
        return membersList;


    }


    public List<Double> getLocation(String username) {
        UserData user = userDetailsRepo.findByUsername(username);
        List<Double> result =new ArrayList<>();
        if(user != null) {
            result.add(user.getLatitude());
            result.add(user.getLongitude());
            return result;
        }
        return result;
    }
}
