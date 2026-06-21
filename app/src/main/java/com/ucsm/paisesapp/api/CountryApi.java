package com.ucsm.paisesapp.api;

import com.ucsm.paisesapp.model.Country;
import com.ucsm.paisesapp.model.CountryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryApi {

    @GET("countries")
    Call<CountryResponse> getCountries();
}