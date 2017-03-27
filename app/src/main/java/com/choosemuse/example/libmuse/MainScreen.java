package com.choosemuse.example.libmuse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.choosemuse.example.libmuse.Constants.root;
import static com.choosemuse.example.libmuse.Constants.specificUser;
import static com.choosemuse.example.libmuse.Constants.userID;
import static com.choosemuse.example.libmuse.Constants.users;

public class MainScreen extends Activity {
    Button button;
    int temp_id;
    ArrayList<HashMap<Integer, Integer>> lst = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
            }
        });

    }

    //firebase

    public void checkUser(){ //see if user exists
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(
                "app", Context.MODE_PRIVATE);
       //
        // prefs.edit().clear().commit();
        String id = prefs.getString("id", null);
        if(id == null){
            getCurrentID();

            //create entry


            //update temp id in DB


        } else{
            //set idd
            userID = id;
            Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
            System.out.println(specificUser.getKey().toString());

                Intent r = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(r);


        }
    }

    public void getCurrentID(){

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    System.out.println("val" + d.getValue().toString());
                    if(d.getKey().equals("temp_id")){

                        String e = d.getValue().toString();
                        temp_id = Integer.parseInt(e);
                        System.out.println("val temp id  " + temp_id);
                        int new_val = temp_id + 1;
                        root.child("temp_id").setValue(new_val);
                        userID = e;
                        users.child(userID).child("DATA").child("ACTIVITIES").child("Achievement").child("Sample").setValue("sample");
                        users.child(userID).child("DATA").child("ACTIVITIES").child("Previous").child("previous").setValue("oe");
                        users.child(userID).child("DATA").child("ACTIVITIES").child("activity 1").child("concentration").setValue("1");
                        users.child(userID).child("DATA").child("ACTIVITIES").child("activity 1").child("time").setValue("2");
                        users.child(userID).child("DATA").child("Time Focus").setValue("Focus 30 minutes");
                        String gen = String.valueOf(temp_id);
                        SharedPreferences.Editor editor = getSharedPreferences("app", Context.MODE_PRIVATE).edit();
                        editor.putString("id", userID);
                        editor.commit();
                       Intent r = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(r);
                        return;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
