package com.example.im;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class FabClickHandler {

    Context context;



    public FabClickHandler(Context context) {
        this.context = context;
    }

    public void onFabClicked(View view){


        intent();






    }

    private void intent() {





        Intent broadcastIntent = new Intent(context.getApplicationContext(), MainActivity.class);

        context.startActivity(broadcastIntent);

        Toast.makeText(context.getApplicationContext(), "Switched", Toast.LENGTH_SHORT).show();
    }


}
