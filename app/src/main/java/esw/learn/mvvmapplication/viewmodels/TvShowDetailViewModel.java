package esw.learn.mvvmapplication.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import esw.learn.mvvmapplication.api.TvResponse;
import esw.learn.mvvmapplication.api.TvShowResponse;
import esw.learn.mvvmapplication.database.TVShowsDatabase;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.repositories.TvShowDetailsRepository;
import io.reactivex.Completable;

public class TvShowDetailViewModel extends AndroidViewModel {

    private TvShowDetailsRepository tvShowDetailsRepository;
    private TVShowsDatabase tvShowsDatabase;

    public TvShowDetailViewModel(@NonNull Application application){
        super(application);

        tvShowDetailsRepository = new TvShowDetailsRepository();
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);

    }

    public LiveData<TvShowResponse> getTvShowDetails (String tvShowId){
        return tvShowDetailsRepository.getTvShowDetail(tvShowId);
    }

    public Completable addToWatchList(TvShow tvShow){
        return tvShowsDatabase.tvShowDao().addToWatchList(tvShow);
    }
}
