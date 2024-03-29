package com.example.mydhakaproject.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.mydhakaproject.Adapters.BloodBankAdapter;
import com.example.mydhakaproject.Models.Model;
import com.example.mydhakaproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BloodBank extends AppCompatActivity {
    List<Model> list;
    RecyclerView recyclerView;

    BloodBankAdapter bloodBankAdapter;
    ProgressBar progressBar;
    SearchView searchView;

    TextView recycler_title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_banks);

        list = new ArrayList<>();

        progressBar = findViewById(R.id.blood_bank_progress_bar);

        recyclerView = findViewById(R.id.BloodBankRecyclerView);

        searchView = findViewById(R.id.blood_bank_search_view);

        recycler_title = findViewById(R.id.blood_bank_recycler_title);


        getData();
        ImageSlider();
        filteredItem();
        bottomNavTask();



    }

    public void getData() {


        String url = "https://mydhaka.000webhostapp.com/My%20Dhaka/blood_banks.json";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                list.clear();
                progressBar.setVisibility(View.GONE);
                recycler_title.setText("Blood Agency:");


                try {
                    JSONArray jsonArray = response.getJSONArray("blood_banks");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Model model = new Model();
                            model.setName(jsonObject.getString("name"));
                            model.setAddress(jsonObject.getString("address"));
                            model.setPhone1(jsonObject.getString("phone1"));
                            model.setPhone2(jsonObject.getString("phone2"));
                            model.setCallButton(R.raw.call);
                            list.add(model);


                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(), "Please wait..", Toast.LENGTH_SHORT).show();
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
                bloodBankAdapter = new BloodBankAdapter(getApplicationContext(),BloodBank.this, list);
                recyclerView.setAdapter(bloodBankAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                recycler_title.setText("");
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void filteredItem() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Model> searchList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getName().toLowerCase().contains(newText.toLowerCase()) || list.get(i).getAddress().toLowerCase().contains(newText.toLowerCase())) {
                        searchList.add(list.get(i));
                        bloodBankAdapter.filteredList(searchList);
                    }
                }

                return true;
            }
        });

    }


    public void ImageSlider() {

        ArrayList<SlideModel> ItemList = new ArrayList<>();

        ImageSlider imageSlider = findViewById(R.id.blood_bank_image_slider);

        ItemList.add(new SlideModel(R.drawable.bloodagency1, ScaleTypes.FIT));
        ItemList.add(new SlideModel(R.drawable.bloodagency2, ScaleTypes.FIT));
        ItemList.add(new SlideModel(R.drawable.bloodagency3, ScaleTypes.FIT));
        ItemList.add(new SlideModel(R.drawable.bloodagency4, ScaleTypes.FIT));
        ItemList.add(new SlideModel(R.drawable.bloodagency5, ScaleTypes.FIT));
        ItemList.add(new SlideModel(R.drawable.bloodagency6, ScaleTypes.FIT));

        imageSlider.setImageList(ItemList);


    }

    public void bottomNavTask() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);


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



