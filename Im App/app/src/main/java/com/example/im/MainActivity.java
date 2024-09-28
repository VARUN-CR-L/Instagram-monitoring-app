package com.example.im;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.im.databinding.ActivityMainBinding;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;



import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private SubmitClickHandler handler;
    private Person person;
    private MyViewModel viewModel;


    /*private Button b;
    private TextView u1;
    private TextView u2;
    private TextView b1;
    private TextView f;
    //private EditText e;
    //private Button b2;
    private TextView f1;

     */

    private String user;
    private String fullName;
    private String bio;
    private String followers;
    private String following;
    //private Button b5;

    private ActivityMainBinding binding;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*b = findViewById(R.id.button);
        u1 = findViewById(R.id.user);
        u2 = findViewById(R.id.name);
        b1 = findViewById(R.id.bio);
        f = findViewById(R.id.follow);
        //e = findViewById(R.id.editText);
        //b2 = findViewById(R.id.button2);
        f1 = findViewById(R.id.textView);

        b5 = findViewById(R.id.button4);

         */
        /*
        try {
            mSocket.connect();

        } catch (Exception e) {
            Toast.makeText(this, "fuck connected", Toast.LENGTH_SHORT).show();
        }

         */





        person = new Person();

        viewModel = new ViewModelProvider(MainActivity.this).get(MyViewModel.class);

        handler = new SubmitClickHandler(person,MainActivity.this,viewModel);

        binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);

        binding.setName(person);
        binding.setClickHandler(handler);



        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        

        binding.button.setOnClickListener(view -> {




            /*JSONObject data = new JSONObject();
            try {
                data.put("username", "cevin"); // Provide the Instagram username
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.emit("fetch_instagram_data", data);


            mSocket.on("instagram_data", onInstagramData);*/
            String username = binding.editText.getText().toString();
            fetchInstagramProfile(username);
        });

        binding.button4.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, PrivateProfileActivity.class);
            startActivity(i);
        });

        /*

        b2.setOnClickListener(view -> {

            submit();
        });

         */



        PeriodicWorkRequest updateRequest = new PeriodicWorkRequest.Builder(
                InstagramUpdateWorker.class,5, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueue(updateRequest);

    }





    /*

    private final Emitter.Listener onInstagramData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                Log.d("InstagramData", data.toString());


                try {
                    String username = data.getString("username");
                    String fullName = data.getString("full_name");
                    String bio = data.getString("biography");
                    int followers = data.getInt("follower_count");

                    // Use the data in your app (e.g., display it in UI)
                    u1.setText(username);
                    u2.setText(fullName);
                    b1.setText(bio);
                    f.setText(String.valueOf(followers));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();


        mSocket.off("instagram_data", onInstagramData);
        mSocket.disconnect();
    }

     */


    private void fetchInstagramProfile(String username) {
        ApiService api = ApiClient.getClient().create(ApiService.class);

        Call<ProfileData> call = api.getProfileData(username);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if (response.isSuccessful()) {
                    ProfileData data = response.body();


                    if (data != null) {
                        user = data.getUsername();
                        fullName = data.getFull_name();
                        bio = data.getBiography();
                        followers = String.valueOf(data.getFollower_count());
                        following = String.valueOf(data.getFollowing_count());


                        binding.userr.setText(user);
                        binding.name.setText(fullName);
                        binding.bio.setText(bio);
                        binding.follow.setText(String.valueOf(followers));
                        binding.fing.setText(String.valueOf(following));
                    } else {
                        Toast.makeText(MainActivity.this, "No data received", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", t.getMessage());
            }
        });
    }

    /*
    private void submit() {

        if (user == null || fullName == null || bio == null || followers == null || following == null){
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }

        else{


        }

    }

     */
}
