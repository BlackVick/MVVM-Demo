package esw.learn.mvvmapplication.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import esw.learn.mvvmapplication.api.Api;
import esw.learn.mvvmapplication.api.RetrofitClient;
import esw.learn.mvvmapplication.api.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailsRepository {

    private Api apiService;

    public TvShowDetailsRepository() {
        apiService = RetrofitClient.getInstance().getApi();
    }

    public LiveData<TvShowResponse> getTvShowDetail(String tvShowId){

        MutableLiveData<TvShowResponse> data = new MutableLiveData<>();
        apiService.getTvShowDetails(tvShowId).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;

    }
}
