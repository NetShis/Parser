import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlSite implements Parser {
    private String url;

    public SqlSite(String url) {
        this.url = url;
    }

    @Override
    public Topic[] topics() {
        Topic topic = null;
        Topic[] topics = new Topic[10000];
        int i = 0;
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
            Elements forumTable = doc.select("table[class=forumTable]");
            Elements icon_cell = forumTable.select("tr");
            String nameOfTopic = null;
            String date;
            String link = null;
            for (Element tr : icon_cell) {
                Elements postslisttopic = tr.select("td[class=postslisttopic]");
                for (Element ee : postslisttopic) {
                    link = ee.selectFirst("a").attr("href");
                    nameOfTopic = ee.selectFirst("a").text();
                }
                date = tr.select("td[class=altCol]").select("td[style=text-align:center]").text();
                topic = new Topic(nameOfTopic, date, link);
                topics[i] = topic;
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return topics;
    }




    /*

     */

}
