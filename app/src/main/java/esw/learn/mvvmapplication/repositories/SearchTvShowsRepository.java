package esw.learn.mvvmapplication.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import esw.learn.mvvmapplication.api.Api;
import esw.learn.mvvmapplication.api.RetrofitClient;
import esw.learn.mvvmapplication.api.TvResponse;
import esw.learn.mvvmapplication.api.TvSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowsRepository {

    private Api apiService;

    public SearchTvShowsRepository() {
        apiService = RetrofitClient.getInstance().getApi();
    }

    public LiveData<TvSearchResponse> searchTvShow(String query, int page) {

        MutableLiveData<TvSearchResponse> data = new MutableLiveData<>();
        apiService.searchTv(query, page).enqueue(new Callback<TvSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvSearchResponse> call, @NonNull Response<TvSearchResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvSearchResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
