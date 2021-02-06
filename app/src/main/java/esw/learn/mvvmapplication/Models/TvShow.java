package esw.learn.mvvmapplication.Models;

public class TvShow {

    private int id;
    private String name;
    private String permalink;
    private String start_date;
    private String end_date;
    private String country;
    private String network;
    private String status;
    private String image_thumbnail_path;

    public TvShow() {
    }

    public TvShow(int id, String name, String permalink, String start_date, String end_date, String country, String network, String status, String image_thumbnail_path) {
        this.id = id;
        this.name = name;
        this.permalink = permalink;
        this.start_date = start_date;
        this.end_date = end_date;
        this.country = country;
        this.network = network;
        this.status = status;
        this.image_thumbnail_path = image_thumbnail_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public void setImage_thumbnail_path(String image_thumbnail_path) {
        this.image_thumbnail_path = image_thumbnail_path;
    }
}
