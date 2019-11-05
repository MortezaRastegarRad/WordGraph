package cs.sbu.Processing;

public class Url {
    private String url;
    private long id;

    public Url(String url, long id) {
        this.url = url;
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
