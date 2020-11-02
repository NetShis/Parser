import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Website implements Parser {
    private String name;

    public Website(String name) {
        this.name = name;
    }

    void jobSearch(String nameOfVacancy, int jobSearchInMonths) {
        ArrayList<Vacancy> vacancies = getVacancies();
        for (Vacancy vacancy : vacancies) {
            if (vacancy.getNameOfVacancy().contains(nameOfVacancy)) {
                System.out.println(vacancy.getNameOfVacancy());
                System.out.println(vacancy.getText());
                System.out.println(vacancy.getDate().getTime());
                System.out.println("-------------------");
            }
        }
    }

    private Calendar stringToDate(String date) {
        Calendar calendarDate;
        if (date.contains("сегодня")) {
            calendarDate = new GregorianCalendar();
        } else if (date.contains("вчера")) {
            calendarDate = new GregorianCalendar();
            calendarDate.add(Calendar.DAY_OF_YEAR, -1);
        } else {
            Matcher matcher;
            int day = 1;
            int month = 0;
            int year = 2020;
            Pattern dayPattern = Pattern.compile("^\\d{1,2}");
            Pattern monthPattern = Pattern.compile("\\s\\D{3}");
            Pattern yearPattern = Pattern.compile("\\d{2},");

            matcher = dayPattern.matcher(date);
            if (matcher.find()) day = Integer.parseInt(matcher.group());
            matcher = monthPattern.matcher(date);
            if (matcher.find()) {
                switch (matcher.group().substring(1)) {
                    case "янв":
                        month = 0;
                        break;
                    case "фев":
                        month = 1;
                        break;
                    case "мар":
                        month = 2;
                        break;
                    case "апр":
                        month = 3;
                        break;
                    case "май":
                        month = 4;
                        break;
                    case "июн":
                        month = 5;
                        break;
                    case "июл":
                        month = 6;
                        break;
                    case "авг":
                        month = 7;
                        break;
                    case "сен":
                        month = 8;
                        break;
                    case "окт":
                        month = 9;
                        break;
                    case "ноя":
                        month = 10;
                        break;
                    case "дек":
                        month = 11;
                        break;
                }
            }
            matcher = yearPattern.matcher(date);
            if (matcher.find()) year = Integer.parseInt("20" + matcher.group().substring(0, 2));
            calendarDate = new GregorianCalendar(year, month, day);
        }

        return calendarDate;
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
            while (y <= count) { //count y <= 5
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
        int i = 0;
        int z = 0;
        try {
            for (Topic topic : topics) {
                doc = Jsoup.connect(topic.getLink()).get();
                text = doc.select("td[class=msgBody]").text();
                date = doc.select("td[class=msgFooter]").text();
                vacancies.add(new Vacancy(topic.getNameOfTopic(), stringToDate(date), text));

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
