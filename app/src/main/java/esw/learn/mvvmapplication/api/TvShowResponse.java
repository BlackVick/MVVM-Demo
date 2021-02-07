package esw.learn.mvvmapplication.api;

import esw.learn.mvvmapplication.models.TvShowDetail;

public class TvShowResponse {

    private TvShowDetail tvShow;

    public TvShowResponse() {
    }

    public TvShowResponse(TvShowDetail tvShow) {
        this.tvShow = tvShow;
    }

    public TvShowDetail getTvShow() {
        return tvShow;
    }

    public void setTvShow(TvShowDetail tvShow) {
        this.tvShow = tvShow;
    }
}
