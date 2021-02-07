package esw.learn.mvvmapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import esw.learn.mvvmapplication.api.TvResponse;
import esw.learn.mvvmapplication.repositories.TvShowsRepository;

public class TvShowViewModel extends ViewModel {

    private TvShowsRepository tvShowsRepository;

    public TvShowViewModel(){

        tvShowsRepository = new TvShowsRepository();

    }

    public LiveData<TvResponse> getTvShows(int page){
        return tvShowsRepository.getTvShows(page);
    }
}
