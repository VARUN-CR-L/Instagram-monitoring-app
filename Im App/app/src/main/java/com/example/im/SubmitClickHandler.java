package com.example.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class SubmitClickHandler {

    Person person;
    Context context;

    MyViewModel viewModel;



    public SubmitClickHandler() {
    }

    public SubmitClickHandler(Person person, Context context, MyViewModel viewModel) {
        this.person = person;
        this.context = context;
        this.viewModel = viewModel;

    }




    public void onSubmitClicked(View view) {
        if (person.getUsername() == null || person.getBio() == null || person.getName() == null || person.getFollowers_count() == null || person.getFollowing_count() == null) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {



            Intent i = new Intent(context, PrivateProfileActivity.class);
            i.putExtra("Name", person.getUsername());
            i.putExtra("DOB", person.getBio());
            i.putExtra("phNumber", person.getName());
            i.putExtra("Email", person.getFollowers_count());
            i.putExtra("Emaill", person.getFollowing_count());





            Person p = new Person(person.getUsername(), person.getBio(), person.getName(), person.getFollowers_count(), person.getFollowing_count());


            Toast.makeText(context.getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();


            viewModel.addNewPerson(p);

            context.startActivity(i);


        }
    }
}


