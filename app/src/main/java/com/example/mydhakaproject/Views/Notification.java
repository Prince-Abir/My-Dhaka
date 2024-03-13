package com.example.mydhakaproject.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mydhakaproject.Adapters.NotificationAdapter;
import com.example.mydhakaproject.Models.NotificationModel;
import com.example.mydhakaproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    RecyclerView recyclerView;
    List<NotificationModel> list;

    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        list = new ArrayList<>();


        recyclerView = findViewById(R.id.notificationRecyclerView);

        getData();
        bottomNavTask();


    }

    public void getData() {


        String url = "https://mydhaka.000webhostapp.com/My%20Dhaka/notification.json";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                list.clear();


                try {
                    JSONArray jsonArray = response.getJSONArray("notification");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String text = jsonObject.getString("text");
                        String date = jsonObject.getString("date");


                        NotificationModel notificationModel = new NotificationModel(name, text, date);

                        list.add(notificationModel);

                    }


                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                }

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
                notificationAdapter = new NotificationAdapter(getApplicationContext(), Notification.this, list);
                recyclerView.setAdapter(notificationAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(jsonObjectRequest);

    }



    public void bottomNavTask() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.notification_btn);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                String Item  = item.getTitle().toString();

                if (Item.contains("Home")){

                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                    finish();

                }
                else if(Item.contains("Category"))
                {
                    Intent intent = new Intent(getApplicationContext(),Category.class);
                    startActivity(intent);
                    finish();
                }
                else if (Item.contains("Notification"))
                {

                    Intent intent = new Intent(getApplicationContext(),Notification.class);
                    startActivity(intent);
                    finish();

                }

                else if (Item.contains("About"))
                {

                    Intent intent = new Intent(getApplicationContext(),About.class);
                    startActivity(intent);
                    finish();

                }
                return true;
            }
        });
    }
}