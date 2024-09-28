package com.example.im;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRepository {

    ExecutorService executorService;
    Handler handler;
    private final PersonDAO personDAO;

    public MyRepository(Application application) {

        MyDataBase myDatabase = MyDataBase.getInstance(application);
        this.personDAO = myDatabase.personDAO();

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

    }

    public void insertPerson(Person person){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                personDAO.insert(person);
            }
        });

    }

    public void deletePerson(Person person){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                personDAO.delete(person);
            }
        });
    }

    public void updatePerson(Person person){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                personDAO.update(person);
            }
        });
    }

    public LiveData<List<Person>> getAllPersons(){
        return personDAO.getAllPerson();
    }

    public LiveData<List<Person>> getPersonDetails(int position){
        return personDAO.getPersonDatails(position);
    }

    public LiveData<List<Person>> getOldDetails(String username){
        return personDAO.getOldDetails(username);
    }

    public synchronized List<Person> getAllPersonsSync(){
        return personDAO.getAllDetails();
    }




}
