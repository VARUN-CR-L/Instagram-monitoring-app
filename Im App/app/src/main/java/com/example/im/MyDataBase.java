package com.example.im;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Person.class,version = 1)
public abstract class MyDataBase extends RoomDatabase {

    public abstract PersonDAO personDAO();

    private  static MyDataBase myDbInstance;

    public static synchronized MyDataBase getInstance(Context context){
        if (myDbInstance == null){
            myDbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class,
                    "my_database").fallbackToDestructiveMigration().build();
        }
        return myDbInstance;
    }
}
