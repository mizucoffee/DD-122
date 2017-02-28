package net.mizucoffee.hatsuyuki_chinachu.chinachu.api;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.api.model.gamma.Recorded;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChinachuGammaApi {
    @GET("/api/recorded.json")
    Call<List<Recorded>> getRecorded();

    @GET("/api/recorded/{programId}/preview.png")
    void getRecordedPreview(@Path("programId") String programId, Callback<Response> callback);
}
