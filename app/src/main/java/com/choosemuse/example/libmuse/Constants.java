package com.choosemuse.example.libmuse;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Aakarsh on 3/25/17.
 */

public class Constants {

    public static String userID = "";
    public static DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    public static DatabaseReference users = root.child("Users");
    public static DatabaseReference specificUser = users.child(userID);


}
