import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlSite implements Parser {
    private String url;

    public SqlSite(String url) {
        this.url = url;
    }

    @Override
    public ArrayList<Topic> topics() {
        Topic topic = null;
        ArrayList<Topic> topics = new ArrayList<>();
        Document doc = null;
        int day, month, year;

        try {
            doc = Jsoup.connect(url).get();
            Elements forumTable = doc.select("table[class=forumTable]");
            Elements icon_cell = forumTable.select("tr");
            String nameOfTopic = null;
            String date;
            String link = null;
            for (int i = 4; i < icon_cell.size(); i++) { //Element tr : icon_cell)
                Elements postslisttopic = icon_cell.get(i).select("td[class=postslisttopic]");
                for (Element ee : postslisttopic) {
                    link = ee.selectFirst("a").attr("href");
                    nameOfTopic = ee.selectFirst("a").text();
                }
                date = icon_cell.get(i).select("td[class=altCol]").select("td[style=text-align:center]").text();
                topics.add(new Topic(nameOfTopic, date, link));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return topics;
    }




    /*

     */

}
