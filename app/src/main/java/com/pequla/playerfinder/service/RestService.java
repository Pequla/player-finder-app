package com.pequla.playerfinder.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RestService {

    private static RestService instance;

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public RestService() {
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static RestService getInstance() {
        if (instance == null) instance = new RestService();
        return instance;
    }

    public void getDataPaged(int page, int size, DialogCallback callback) {
        client.newCall(get("https://cache.samifying.com/api/data?page=" + page + "&size=" + size))
                .enqueue(callback);
    }

    public void getDataById(int id, DialogCallback callback) {
        client.newCall(get("https://cache.samifying.com/api/data/" + id))
                .enqueue(callback);
    }

    public void getServerStatus(String address, DialogCallback callback) {
        client.newCall(get("https://link.samifying.com/api/status/" + address))
                .enqueue(callback);
    }

    private Request get(String url) {
        return new Request.Builder().url(url).get().build();
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
