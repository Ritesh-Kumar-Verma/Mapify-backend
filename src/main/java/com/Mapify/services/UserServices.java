package com.Mapify.services;


import com.Mapify.Model.Members;
import com.Mapify.Model.UserData;
import com.Mapify.Model.UsersLoginDetails;
import com.Mapify.Repository.MembersRepo;
import com.Mapify.Repository.UserDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<UserData> user2 = userDetailsRepo.findByUsername(username);
        if(user2.isPresent()){
            if(membersRepo.findByIdUser1AndIdUser2(id, user2.get().getId()) !=null ){
                return null;
            }
            Members members = new Members();
            members.setIdUser1(id);
            members.setIdUser2(user2.get().getId());
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
        Optional<UserData> user2 = userDetailsRepo.findByUsername(username);
        if(user2.isEmpty()){
            return "Not Found";
        }
        Members m = membersRepo.findByIdUser1AndIdUser2( user2.get().getId(), usersLoginDetails.getId() );
        if(m.getStatus().equals("Pending")){
            m.setStatus("Accepted");
            membersRepo.save(m);
            return "Accepted";
        }
        return "Not Found";

    }

    @Transactional
    public String rejectRequest(Integer id, String username) {
        Optional<UserData> user2 = userDetailsRepo.findByUsername(username);
        if(user2.isPresent()){
           Long n = membersRepo.deleteByIdUser1AndIdUser2AndStatus(user2.get().getId() , id , "Pending");
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
        Optional<UserData> user = userDetailsRepo.findByUsername(username);
        List<Double> result =new ArrayList<>();
        if(user.isPresent()) {
            result.add(user.get().getLatitude());
            result.add(user.get().getLongitude());
            return result;
        }
        return result;
    }
    @Transactional
    public int deleteMember(int id1, String username) {
        Optional<UserData> user2= userDetailsRepo.findByUsername(username);
        if(user2.isPresent()){
            int id2 = user2.get().getId();
            int i= membersRepo.deleteByIdUser1AndIdUser2(id1,id2);
            membersRepo.deleteByIdUser1AndIdUser2(id2 , id1);
            return i;
        }
        return 0;
    }
}
