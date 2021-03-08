package esw.learn.mvvmapplication.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import esw.learn.mvvmapplication.database.TVShowsDatabase;
import esw.learn.mvvmapplication.models.TvShow;
import io.reactivex.Flowable;

import java.util.List;

public class WatchListViewModel extends AndroidViewModel {

    private TVShowsDatabase tvShowsDatabase;

    public WatchListViewModel(@NonNull Application application){
        super(application);

        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public Flowable<List<TvShow>> loadWatchlist(){
        return tvShowsDatabase.tvShowDao().getWatchList();
    }
}
