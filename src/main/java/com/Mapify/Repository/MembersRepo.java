package com.Mapify.Repository;

import com.Mapify.Model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MembersRepo extends JpaRepository<Members, Integer> {


    Members findByIdUser1AndIdUser2(int idUser1 , int idUser2);


    List<Members> findByIdUser1AndStatus(int idUser1 , String status);


    List<Members> findByIdUser2AndStatus(Integer id, String pending);

    Long deleteByIdUser1AndIdUser2AndStatus(int id, Integer id1, String pending);


    @Query("SELECT m.idUser2 FROM Members m WHERE m.status = 'Accepted' AND m.idUser1 = :id")
    List<Integer> findMembers(Integer id);


    int deleteByIdUser1AndIdUser2(Integer id1, int id2);

}
