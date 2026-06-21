package com.ucsm.paisesapp.database;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class Converter {

    @TypeConverter
    public String fromList(List<String> list) {
        if (list == null) return "";
        return String.join(",", list);
    }

    @TypeConverter
    public List<String> toList(String data) {
        if (data == null || data.isEmpty()) return null;
        return Arrays.asList(data.split(","));
    }
}
