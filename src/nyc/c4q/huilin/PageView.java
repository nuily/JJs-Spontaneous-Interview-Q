package nyc.c4q.huilin;

public class PageView {
    String visitorId;
    long connectionTimeMS;

    public PageView(String visitorId, long connectionTimeMS) {
        this.visitorId = visitorId;
        this.connectionTimeMS = connectionTimeMS;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public long getConnectionTimeMS() {
        return connectionTimeMS;
    }

    public void setConnectionTimeMS(long connectionTimeMS) {
        this.connectionTimeMS = connectionTimeMS;
    }
}