package com.example.fblaschoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BreakfastItems extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<Food> bfastItems;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_items);

        floatingActionButton = findViewById(R.id.floatingActionBtnBfastItems);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BreakfastItems.this, LunchMenu.class));
            }
        });
        recyclerView = findViewById(R.id.recyclerViewBfast);
        bfastItems = new ArrayList<>();


        extractData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, bfastItems);
        recyclerView.setAdapter(adapter);


    }

    private void extractData() {
        String json;


        try {
            InputStream inputStream = getAssets().open("bfastData.json");
            int size = inputStream.available();
            System.out.println("This is the size" + size);
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Food food = new Food();
                food.setFoodTitle(object.getString("Title").toString());
                food.setCalories(object.getString("Calories").toString());
                food.setImageURL(object.getString("ImageURL").toString());
                bfastItems.add(food);

            }



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }
}