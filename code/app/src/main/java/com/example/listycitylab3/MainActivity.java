package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements
        AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }
    @Override
    public void editCity(City cityToEdit, String newName, String newProvince) {
        if (dataList.contains(cityToEdit)) {
            cityToEdit.setName(newName);
            cityToEdit.setProvince(newProvince);
            cityAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Calgary", "Montreal"};
        String[] provinces = {"AB", "BC", "ON", "AB", "QC"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Setup FloatingActionButton for adding a new city
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            AddCityFragment.newInstance().show(getSupportFragmentManager(), "ADD_CITY_DIALOG");
        });

        // Set up item click listener for editing a city
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selectedCity = dataList.get(position);
            AddCityFragment.newInstance(selectedCity).show(getSupportFragmentManager(), "EDIT_CITY_DIALOG");
        });
    }
}
