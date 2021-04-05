package com.example.mareu.di;

import com.example.mareu.service.ApiService;
import com.example.mareu.service.DummyApiService;

public class DI {

    private static ApiService service = new DummyApiService();

    /**
     * Get an instance on @{@link ApiService}
     * @return
     */
    public static ApiService getApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link ApiService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }

}
