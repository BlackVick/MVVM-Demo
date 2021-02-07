package esw.learn.mvvmapplication.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import esw.learn.mvvmapplication.api.Api;
import esw.learn.mvvmapplication.api.RetrofitClient;
import esw.learn.mvvmapplication.api.TvResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsRepository {

    private Api apiService;

    public TvShowsRepository() {
        apiService = RetrofitClient.getInstance().getApi();
    }

    public LiveData<TvResponse> getTvShows(int page) {

        MutableLiveData<TvResponse> data = new MutableLiveData<>();
        apiService.getTvShows(page).enqueue(new Callback<TvResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvResponse> call, @NonNull Response<TvResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
