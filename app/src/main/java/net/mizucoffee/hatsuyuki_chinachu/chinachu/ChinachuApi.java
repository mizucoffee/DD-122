package net.mizucoffee.hatsuyuki_chinachu.chinachu;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.recorded.Recorded;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChinachuApi {
    @GET("/api/recorded.json")
    Call<List<Recorded>> getRecorded();

    @GET("/api/recorded/{programId}/preview.png")
    void getRecordedPreview(@Path("programId") String programId, Callback<Response> callback);
}
