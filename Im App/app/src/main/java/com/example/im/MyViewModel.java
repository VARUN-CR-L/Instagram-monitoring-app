package com.example.im;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private MyRepository myRepository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.myRepository = new MyRepository(application);
    }

    private LiveData<List<Person>> allPerson;

    public LiveData<List<Person>> getAllPerson(){
        allPerson = myRepository.getAllPersons();
        return allPerson;
    }

    private LiveData<List<Person>> PersonDetails;
    public LiveData<List<Person>> getPersonDetails(int position){
        PersonDetails = myRepository.getPersonDetails(position);
        return PersonDetails;
    }


    private LiveData<List<Person>> OldDetails;
    public LiveData<List<Person>> getOldDetails(String username){
        OldDetails = myRepository.getOldDetails(username);
        return OldDetails;
    }




    public void addNewPerson(Person person){
        myRepository.insertPerson(person);
    }

    public void deletePerson(Person person){
        myRepository.deletePerson(person);
    }

    public void updatePerson(Person person){
        myRepository.updatePerson(person);
    }


}
