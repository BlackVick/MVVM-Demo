package esw.learn.mvvmapplication.models;

import java.util.List;

public class TvShowDetail {

    private int id;
    private String name;
    private String permalink;
    private String url;
    private String description;
    private String description_source;
    private String start_date;
    private String end_date;
    private String country;
    private String status;
    private int runtime;
    private String network;
    private String youtube_link;
    private String image_path;
    private String image_thumbnail_path;
    private String rating;
    private String rating_count;
    private CountdownData countdown;
    private String[] genres;
    private String[] pictures;
    private List<EpisodeData> episodes;

    public TvShowDetail() {
    }

    public TvShowDetail(int id, String name, String permalink, String url, String description, String description_source, String start_date, String end_date, String country, String status, int runtime, String network, String youtube_link, String image_path, String image_thumbnail_path, String rating, String rating_count, CountdownData countdown, String[] genres, String[] pictures, List<EpisodeData> episodes) {
        this.id = id;
        this.name = name;
        this.permalink = permalink;
        this.url = url;
        this.description = description;
        this.description_source = description_source;
        this.start_date = start_date;
        this.end_date = end_date;
        this.country = country;
        this.status = status;
        this.runtime = runtime;
        this.network = network;
        this.youtube_link = youtube_link;
        this.image_path = image_path;
        this.image_thumbnail_path = image_thumbnail_path;
        this.rating = rating;
        this.rating_count = rating_count;
        this.countdown = countdown;
        this.genres = genres;
        this.pictures = pictures;
        this.episodes = episodes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getDescription_source() {
        return description_source;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getNetwork() {
        return network;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public String getRating() {
        return rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public CountdownData getCountdown() {
        return countdown;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public List<EpisodeData> getEpisodes() {
        return episodes;
    }
}
