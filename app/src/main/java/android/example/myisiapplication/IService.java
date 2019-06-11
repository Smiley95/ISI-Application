package android.example.myisiapplication;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IService {
    @POST("isi/oauth2/token")
    Call<Token> getTokenAcess(@Body JSONObject login);
    @GET("api/1.0/isi/case/start-cases")
    Call<List<ServiceItem>> getcase(@Header("Authorization:")String authHeader);
    @GET("steps")
    Call<List<Dynaform>> getStepUidObj(@Header("authorization:")String authHeader);
    @GET(".")
    Call<DYNContent> getContent(@Header("Authorization:")String authHeader);
}
