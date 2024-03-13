package com.example.mydhakaproject.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mydhakaproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HotelView extends AppCompatActivity {

    ImageView hotel_image;
    TextView hotel_details,hotel_name,hotel_address;
    Button book,callButton,emailButton;
    String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_view);


        hotel_name = findViewById(R.id.hotel_name);
        hotel_image = findViewById(R.id.hotel_image);
        hotel_details = findViewById(R.id.hotel_details);
        hotel_address = findViewById(R.id.hotel_address);


        book = findViewById(R.id.book_button);
        callButton = findViewById(R.id.CALL_BUTTON);
        emailButton = findViewById(R.id.EMAIL_BUTTON);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id",0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String website = intent.getStringExtra("website");
        String image_url = intent.getStringExtra("image");
        user_email = intent.getStringExtra("user_email");







        hotel_name.setText(name);
        hotel_address.setText(address);
        Glide.with(this).load(image_url).into(hotel_image);


        bottomNavTask();


        book.setOnClickListener( v-> {
            Intent intent1 = new Intent(this,BookRoom.class);

            intent1.putExtra("id",id);
            intent1.putExtra("name",name);
            intent1.putExtra("address",address);
            intent1.putExtra("phone",phone);
            intent1.putExtra("email",email);
            intent1.putExtra("website",website);
            intent1.putExtra("image",image_url);
            intent1.putExtra("user_email",user_email);

            startActivity(intent1);

        });

//
//        emailButton.setOnClickListener( v-> {
//
//            Intent intent2 = new Intent(Intent.ACTION_SENDTO);
//            intent2.setData(Uri.parse("mailto:" + email));
//
//            if (intent2.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent2);
//            }
//        });

        callButton.setOnClickListener(v -> {

            Intent intent3 = new Intent(Intent.ACTION_DIAL);
            intent3.setData(Uri.parse("tel:" + phone));
            startActivity(intent3);

        });





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