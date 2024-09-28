package com.example.im;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void  delete(Person person);

    @Query("SELECT * FROM Person")
    LiveData<List<Person>> getAllPerson();

    @Query("SELECT * FROM Person Where id = :id")
    LiveData<List<Person>> getPersonDatails(int id);

    @Query("SELECT * FROM Person Where username = :username")
    LiveData<List<Person>> getOldDetails(String username);

    @Query("SELECT * FROM Person")
    List<Person> getAllDetails();


}
