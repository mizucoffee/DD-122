package net.mizucoffee.hatsuyuki_chinachu.chinachu;

import net.mizucoffee.hatsuyuki_chinachu.chinachu.model.Program;
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import io.reactivex.*;

public interface ChinachuApi {
    @GET("/api/recorded.json")
    Call<List<net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program.Program>> getRecorded();

    @GET("/api/schedule.json")
    Observable<List<ScheduleModel>> getAllPrograms();

    @GET("/api/schedule/broadcasting.json")
    Call<List<Program>> getBroadcasting();
//    @GET("/api/recorded/{programId}/preview.png")
//    void getRecordedPreview(@Path("programId") String programId, Callback<Response> callback);

    //?ext=mp4&c%3Av=h264&s=1280x720&b%3Av=1M&b%3Aa=128k&ss=0
//    @Streaming
//    @GET("/api/recorded/{programId}/watch.mp4{parameter}")
//    Observable<Response> downloadVideo(@Path("programId") String programId,@Path("parameter") String parameter, int id);
}
