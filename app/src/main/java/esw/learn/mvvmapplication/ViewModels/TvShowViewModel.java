package esw.learn.mvvmapplication.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import esw.learn.mvvmapplication.Api.TvResponse;
import esw.learn.mvvmapplication.Repositories.TvShowsRepository;

public class TvShowViewModel extends ViewModel {

    private TvShowsRepository tvShowsRepository;

    public TvShowViewModel(){

        tvShowsRepository = new TvShowsRepository();

    }

    public LiveData<TvResponse> getTvShows(int page){
        return tvShowsRepository.getTvShows(page);
    }
}
