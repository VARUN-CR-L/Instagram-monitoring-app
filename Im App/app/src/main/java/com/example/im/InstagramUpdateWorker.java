package com.example.im;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstagramUpdateWorker extends Worker {

    private static final String CHANNEL_ID = "ProfileUpdates";
    private static final int NOTIFICATION_ID = 1;
    private ApiService api;
    private MyRepository repository;

    public InstagramUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        api = ApiClient.getClient().create(ApiService.class);
        repository = new MyRepository((Application) context.getApplicationContext());
        createNotificationChannel();
    }

    @NonNull
    @Override
    public Result doWork() {

        List<Person> persons = repository.getAllPersonsSync();
        for (Person person : persons) {
            checkProfileUpdates(person.getUsername());
        }


        return Result.success();
    }

    private void checkProfileUpdates(String username) {
        Call<ProfileData> call = api.getProfileData(username);
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if (response.isSuccessful()) {
                    ProfileData newData = response.body();

                    LiveData<List<Person>> liveData = repository.getOldDetails(username);
                    liveData.observeForever(oldPersonData -> {
                        if (!oldPersonData.isEmpty()) {
                            Person oldData = oldPersonData.get(0);
                            if (hasProfileChanged(oldData, newData)) {

                                updateProfileData(oldData, newData);


                                sendNotification(username);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "boom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hasProfileChanged(Person oldData, ProfileData newData) {

        return !oldData.getBio().equals(newData.getBiography()) || Integer.parseInt(oldData.getFollowers_count()) != newData.getFollower_count() || !oldData.getUsername().equals(newData.getUsername());
    }

    private void updateProfileData(Person oldData, ProfileData newData) {

        oldData.setBio(newData.getBiography());
        oldData.setFollowers_count(String.valueOf(newData.getFollower_count()));
        oldData.setUsername(newData.getUsername());
        oldData.setFollowing_count(String.valueOf(newData.getFollowing_count()));
        oldData.setName(newData.getFull_name());
        repository.updatePerson(oldData);
    }

    private void sendNotification(String username) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Profile Update")
                .setContentText(username + "'s profile has changed!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Profile Updates";
            String description = "Notification channel for profile updates";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
