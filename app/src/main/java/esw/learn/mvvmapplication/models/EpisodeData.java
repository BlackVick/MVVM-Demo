package esw.learn.mvvmapplication.models;

public class EpisodeData {

    private String season;
    private String episode;
    private String name;
    private String air_date;

    public EpisodeData() {
    }

    public EpisodeData(String season, String episode, String name, String air_date) {
        this.season = season;
        this.episode = episode;
        this.name = name;
        this.air_date = air_date;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }
}
