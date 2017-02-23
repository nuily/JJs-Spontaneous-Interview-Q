package nyc.c4q.huilin;

import java.util.*;
import java.util.concurrent.TimeUnit;

/*
* Return an array containing the follow:
* [1] Total number of Page Views
* [2] Unique number of visitors
* [3] Unique number of sessions
*/
public class Main {
    // [6, 2, 5]
    public static void main(String[] args) {
        SiteAnalytics analytics = new SiteAnalytics(
                new ArrayList<>(Arrays.asList(
                        new PageView("100", System.currentTimeMillis()),
                        new PageView("100", System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(31, TimeUnit.MINUTES)),
                        new PageView("100", System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(62, TimeUnit.MINUTES)),
                        new PageView("101", System.currentTimeMillis()),
                        new PageView("101", System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES)),
                        new PageView("101", System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(61, TimeUnit.MINUTES)))
                ),
                new HashMap<>());
        Iterator<PageView> pageViews = analytics.getPageViewList().iterator();
        System.out.println(Arrays.toString(analytics.analyzePageViews(pageViews)));
    }
}

class SiteAnalytics {
    private List<PageView> pageViewList;
    private Map<String, Long> pageViewsMap;

    public SiteAnalytics(List<PageView> pageViewList, Map<String, Long> pageViewsMap) {
        this.pageViewList = pageViewList;
        this.pageViewsMap = pageViewsMap;
    }

    // [1] Count the number of pageView.hasNext()
    // [2] Put visitorIds into
    public int[] analyzePageViews(Iterator<PageView> pageViews) {
        int totalViews = 0;
        PageView currentItem;
        String visitorId;
        long connectionTimeMS;
        int totalSessions = 0;
        while (pageViews.hasNext()) {
            totalViews++;
            currentItem = pageViews.next();
            visitorId = currentItem.getVisitorId();
            System.out.println("ID is " + visitorId);
            connectionTimeMS = currentItem.getConnectionTimeMS();
            System.out.println("Time is " + connectionTimeMS);
            if (!pageViewsMap.containsKey(visitorId)) {
                pageViewsMap.put(visitorId, connectionTimeMS);
                totalSessions++;
            } else {
                long prevSessionMS = pageViewsMap.get(visitorId);
                System.out.println("Last login was " + prevSessionMS);
                long timeDiff = Math.abs(prevSessionMS - connectionTimeMS);
                long thirtyInMS = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
                System.out.println("The time difference is " + timeDiff);
                System.out.println("30 mins in MS is " + thirtyInMS);
                if (timeDiff > thirtyInMS) {
                    totalSessions++;
                }
                pageViewsMap.put(visitorId, connectionTimeMS);
            }
            System.out.println("Total sessions: " + totalSessions);
            System.out.println();
        }
        return new int[] {totalViews, pageViewsMap.size(), totalSessions};
    }

    public List<PageView> getPageViewList() {
        return pageViewList;
    }
}
