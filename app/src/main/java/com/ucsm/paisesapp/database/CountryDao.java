package com.ucsm.paisesapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    void insertAll(List<CountryEntity> list);

    @Query("SELECT * FROM countries")
    List<CountryEntity> getAll();

    @Query("DELETE FROM countries")
    void deleteAll();
}