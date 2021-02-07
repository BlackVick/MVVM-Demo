package esw.learn.mvvmapplication.api;

import java.util.List;

import esw.learn.mvvmapplication.models.TvShow;

public class TvSearchResponse {

    private String total;
    private int page;
    private int pages;
    private List<TvShow> tv_shows;

    public TvSearchResponse() {
    }

    public TvSearchResponse(String total, int page, int pages, List<TvShow> tv_shows) {
        this.total = total;
        this.page = page;
        this.pages = pages;
        this.tv_shows = tv_shows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<TvShow> getTv_shows() {
        return tv_shows;
    }

    public void setTv_shows(List<TvShow> tv_shows) {
        this.tv_shows = tv_shows;
    }
}
