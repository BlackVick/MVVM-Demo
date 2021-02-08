package esw.learn.mvvmapplication.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("most-popular")
    Call<TvResponse> getTvShows (
            @Query("page") int page
    );

    @GET("show-details")
    Call<TvShowResponse> getTvShowDetails (
            @Query("q") String q
    );

    @GET("search")
    Call<TvSearchResponse> searchTv (
            @Query("q") String q,
            @Query("page") int page
    );
}
