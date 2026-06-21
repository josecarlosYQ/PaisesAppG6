package com.ucsm.paisesapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ucsm.paisesapp.R;
import com.ucsm.paisesapp.adapter.CountryAdapter;
import com.ucsm.paisesapp.api.CountryApi;
import com.ucsm.paisesapp.api.RetrofitClient;
import com.ucsm.paisesapp.database.AppDatabase;
import com.ucsm.paisesapp.database.CountryEntity;
import com.ucsm.paisesapp.database.DatabaseClient;
import com.ucsm.paisesapp.model.Country;
import com.ucsm.paisesapp.model.CountryResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CountryAdapter adapter;

    List<CountryEntity> list;

    AppDatabase db;
    CountryApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerCountries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = DatabaseClient.getInstance(this);
        api = RetrofitClient.getClient().create(CountryApi.class);

        // IMPORTANTE: inicializar una sola lista
        list = new ArrayList<>();
        adapter = new CountryAdapter(this, list);
        recyclerView.setAdapter(adapter);

        loadFromRoom();   // primero offline
        loadFromApi();    // luego actualiza
    }

    private void loadFromApi() {

        api.getCountries().enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call,
                                   Response<CountryResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Country> countries = response.body().data;

                    List<CountryEntity> entities = new ArrayList<>();

                    for (Country c : countries) {

                        CountryEntity e = new CountryEntity();
                        e.country = c.country;

                        if (c.cities != null) {
                            e.cities = String.join(",", c.cities);
                        } else {
                            e.cities = "";
                        }

                        entities.add(e);
                    }

                    db.countryDao().deleteAll();
                    db.countryDao().insertAll(entities);

                    Log.d("API", "Datos guardados en Room");

                    loadFromRoom(); // refrescar UI
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void loadFromRoom() {

        List<CountryEntity> data = db.countryDao().getAll();

        list.clear();
        list.addAll(data);

        adapter.notifyDataSetChanged();

        Log.d("ROOM", "Datos cargados: " + data.size());
    }
}