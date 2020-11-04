import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


public class Sql extends Website {
    public Sql(String name, int jobSearchInMonths) {
        super(name, jobSearchInMonths);
    }

    @Override
    public ArrayList<Topic> getTopics() {
        System.out.print("Parsing:");
        int z = 0;
        ArrayList<Topic> topics = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(name).get();
            ArrayList<String> pages = new ArrayList<>(Arrays.asList(doc.select("table[class=sort_options]").select("a").text().split(" ")));
            int count = Integer.parseInt(pages.get(pages.size() - 1));
            int y = 1;
            while (y <= count) { //count
                doc = Jsoup.connect(name + y).get();
                Elements forumTable = doc.select("table[class=forumTable]");
                Elements icon_cell = forumTable.select("tr");
                String nameOfTopic = null;
                String date;
                String link = null;
                for (int i = 4; i < icon_cell.size(); i++) {
                    Elements postslisttopic = icon_cell.get(i).select("td[class=postslisttopic]");
                    for (Element ee : postslisttopic) {
                        link = ee.selectFirst("a").attr("href");
                        nameOfTopic = ee.selectFirst("a").text();
                    }
                    date = icon_cell.get(i).select("td[class=altCol]").select("td[style=text-align:center]").text();
                    topics.add(new Topic(nameOfTopic, stringToDate(date), link));
                }
                y++;
                if ((int) ((double) y / (double) count * 100) > z) {
                    System.out.print(z + "% ");
                    z += 10;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("100%");
        System.out.println("");
        return topics;
    }

    @Override
    public ArrayList<Vacancy> getVacancies() {
        ArrayList<Topic> topics = getTopics();
        System.out.print("Обработка вакансий:");
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        Document doc = null;
        String text, date;
        Calendar dateSearch = Calendar.getInstance();
        Calendar tempDate;
        dateSearch.roll(Calendar.MONTH, -jobSearchInMonths);
        int i = 0;
        int z = 0;
        try {
            for (Topic topic : topics) {
                doc = Jsoup.connect(topic.getLink()).get();
                text = doc.select("td[class=msgBody]").text();
                date = doc.select("td[class=msgFooter]").text();
                tempDate = stringToDate(date);
                if (tempDate.after(dateSearch)) {
                    vacancies.add(new Vacancy(topic.getNameOfTopic(), tempDate, text));
                }

                if ((int) ((double) i / (double) topics.size() * 100) > z) {
                    System.out.print(z + "% ");
                    z += 10;
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("100%");
        System.out.println("");
        return vacancies;
    }

}
