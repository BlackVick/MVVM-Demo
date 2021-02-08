package esw.learn.mvvmapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import esw.learn.mvvmapplication.api.TvResponse;
import esw.learn.mvvmapplication.api.TvShowResponse;
import esw.learn.mvvmapplication.repositories.TvShowDetailsRepository;

public class TvShowDetailViewModel extends ViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;

    public TvShowDetailViewModel(){

        tvShowDetailsRepository = new TvShowDetailsRepository();

    }

    public LiveData<TvShowResponse> getTvShowDetails (String tvShowId){
        return tvShowDetailsRepository.getTvShowDetail(tvShowId);
    }
}
