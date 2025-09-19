package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";

    private EditText editCityName;
    private EditText editProvinceName;
    private AddCityDialogListener listener;
    private City existingCity;
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, String newName, String newProvince);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    // Static factory method for creating an instance for adding a new city
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    // Static factory method for creating an instance for editing an existing city
    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city); // City must be Serializable
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        editCityName = view.findViewById(R.id.edit_text_city_text);
        editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (getArguments() != null && getArguments().containsKey(ARG_CITY)) {
            existingCity = (City) getArguments().getSerializable(ARG_CITY);
            if (existingCity != null) {
                editCityName.setText(existingCity.getName());
                editProvinceName.setText(existingCity.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(existingCity == null ? "Add a city" : "Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(existingCity == null ? "Add" : "Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (cityName.isEmpty() || provinceName.isEmpty()) {
                        return; // Prevent adding/editing with empty fields
                    }

                    if (existingCity == null) {
                        // Adding a new city
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        // Editing an existing city
                        listener.editCity(existingCity, cityName, provinceName);
                    }
                })
                .create();
    }
}
