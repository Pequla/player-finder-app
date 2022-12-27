package com.pequla.playerfinder.service;

import android.os.Environment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {

    private static final String EXTERNAL_APP_DATA_PREFIX = "PlayerFinderData";
    private static final String EXTERNAL_APP_DATA_FILE_NAME = "favourites.json";

    public static void saveFavourite(ArrayList<Integer> favourites) {
        File dir = new File(
                Environment.getExternalStorageDirectory() + File.separator + "Documents" + File.separator,
                EXTERNAL_APP_DATA_PREFIX
        );

        dir.mkdirs();
        File file = new File(dir.getAbsolutePath() + File.separator + EXTERNAL_APP_DATA_FILE_NAME);
        ObjectMapper mapper = RestService.getInstance().getMapper();
        try {
            mapper.writeValue(file, favourites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> loadFavourites() {
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "Documents" + File.separator,
                EXTERNAL_APP_DATA_PREFIX);
        File file = new File(dir.getAbsolutePath() + File.separator + EXTERNAL_APP_DATA_FILE_NAME);
        ObjectMapper mapper = RestService.getInstance().getMapper();

        try {
            if (!file.exists()) saveFavourite(new ArrayList<>());
            // Read file and load array from json
            return mapper.readValue(file, new TypeReference<ArrayList<Integer>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
