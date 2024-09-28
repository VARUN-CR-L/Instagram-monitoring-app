package com.example.im;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.im.databinding.ActivityPrivateProfileBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PrivateProfileActivity extends AppCompatActivity implements MainSelectListener{

    private MyDataBase myDatabase;
    private ArrayList<Person> personArrayList = new ArrayList<>();

    private MainAdapter mainAdapter;
    private ActivityPrivateProfileBinding mainBinding;
    private FabClickHandler handler;
    private MyViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_profile);



        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_private_profile);
        handler = new FabClickHandler(this);



        mainBinding.setClickHandler(handler);


        RecyclerView recyclerView = mainBinding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        mainAdapter = new MainAdapter(personArrayList,this);

        myDatabase = MyDataBase.getInstance(this);


        viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        viewModel.getAllPerson().observe(this,
                new Observer<List<Person>>() {
                    @Override
                    public void onChanged(List<Person> people) {

                        personArrayList.clear();

                        for (Person p: people){
                            Log.v("TAGY",p.getName());
                            personArrayList.add(p);
                        }
                        mainAdapter.notifyDataSetChanged();
                    }
                });
        recyclerView.setAdapter(mainAdapter);



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                Person p = personArrayList.get(viewHolder.getAdapterPosition());

                viewModel.deletePerson(p);

            }
        }).attachToRecyclerView(recyclerView);




    }

    @Override
    public void onItemClicked(int position) {
        Intent i = new Intent(PrivateProfileActivity.this, Person_Info_Display.class);
        i.putExtra("personPosition",personArrayList.get(position).getId() );
        startActivity(i);
    }
}