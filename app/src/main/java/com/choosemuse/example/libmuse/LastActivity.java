package com.choosemuse.example.libmuse;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.choosemuse.example.libmuse.Constants.specificUser;
import static com.choosemuse.example.libmuse.Constants.userID;
import static com.choosemuse.example.libmuse.Constants.users;

public class LastActivity extends Activity {
    HashMap<Double, Double> map = new LinkedHashMap<>();
    LineGraphSeries<DataPoint> series;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
         series = new LineGraphSeries<DataPoint>();

        graph = (GraphView) findViewById(R.id.graph);

//        graph = (GraphView) findViewById(R.id.lastgraph);

        getPreviousDetail();


    }

    public void getPreviousDetail(){
        users.child(userID).child("DATA").child("ACTIVITIES").child("Previous").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    if("previous".equals(d.getKey())){

                    } else{
                        map.put(Double.parseDouble(d.getKey()), Double.parseDouble(d.getValue().toString()));
                    }

                }
                generatePoints();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void generatePoints(){
        int c = 0;
            for(double key: map.keySet()){
                if(c%2 == 1){
                    c++; continue;
                } else{
                    c++;
                }
                double val = map.get(key);
                   //imt key = ley
                //(x, y) = (key, val);
                series.appendData(new DataPoint(key, val), true, 10);

            }
        graph.addSeries(series);
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(false);
        viewport.setXAxisBoundsManual(false);
        viewport.setScalable(true);
        viewport.setMinY(0);
        viewport.setScrollable(true);


    }
}
