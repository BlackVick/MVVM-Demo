package esw.learn.mvvmapplication.listeners;

import esw.learn.mvvmapplication.models.TvShow;

public interface WatchlistListener {

    void onTvShowClicked(TvShow tvShow);

    void removeTvShowFromWatchlist(TvShow tvShow, int position);

}
