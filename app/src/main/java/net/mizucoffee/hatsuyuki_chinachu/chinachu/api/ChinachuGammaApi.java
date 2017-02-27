package net.mizucoffee.hatsuyuki_chinachu.chinachu.api;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChinachuGammaApi {
    @GET("/api/recorded.json")
    Call<List<Recorded>> getRecorded();
}
