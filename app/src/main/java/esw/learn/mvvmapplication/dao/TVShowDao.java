package esw.learn.mvvmapplication.dao;

import androidx.room.*;
import esw.learn.mvvmapplication.models.TvShow;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShows")
    Flowable<List<TvShow>> getWatchList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TvShow tvShow);

    @Delete
    void removeFromWatchList(TvShow tvShow);
}
