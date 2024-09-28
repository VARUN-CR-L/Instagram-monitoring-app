package com.example.im;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.im.databinding.ActivityPersonInfoDisplayBinding;

import java.util.List;

public class Person_Info_Display extends AppCompatActivity {

    private MyViewModel viewModel;
    private ActivityPersonInfoDisplayBinding binding;
    Person p;

    int position;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info_display);

        binding = ActivityPersonInfoDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        viewModel = new ViewModelProvider(this).get(MyViewModel.class);



        if (getIntent().getExtras() != null){
            position = getIntent().getIntExtra("personPosition",-1);


            if (position != -1){
                viewModel.getPersonDetails(position).observe(this, new Observer<List<Person>>() {
                            @Override
                            public void onChanged(List<Person> info) {
                                if (info != null && info.size() > 0){
                                    binding.setPersonInfo(info.get(0));
                                }
                            }
                        }


                );
            }
        }

    }
}