import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Sql {
    private static String urlOfSite = "https://www.sql.ru/forum/job-offers/";

    static public List<Vacancy> jobSearch(String nameOfVacancy, int jobSearchInMonths) {
        int percent = 0;
        ArrayList<Topic> topics = new ArrayList<>();
        LocalDate tempDate;
        Document doc = null;
        System.out.print("Parsing:");
        try {
            doc = Jsoup.connect(urlOfSite).get();
            ArrayList<String> pages = new ArrayList<>(Arrays.asList(doc.select("table[class=sort_options]").select("a").text().split(" ")));
            int count = Integer.parseInt(pages.get(pages.size() - 1));
            int y = 1;
            String nameOfTopic = null;
            String date;
            String link = null;

            while (y <= count) {
                doc = Jsoup.connect(urlOfSite + y).get();
                Elements forumTable = doc.select("table[class=forumTable]");
                Elements icon_cell = forumTable.select("tr");
                for (int i = 4; i < icon_cell.size(); i++) {
                    Elements postslisttopic = icon_cell.get(i).select("td[class=postslisttopic]");
                    for (Element ee : postslisttopic) {
                        link = ee.selectFirst("a").attr("href");
                        nameOfTopic = ee.selectFirst("a").text();
                    }
                    date = icon_cell.get(i).select("td[class=altCol]").select("td[style=text-align:center]").text();
                    tempDate = stringToDate(date);
                    if (tempDate.isAfter(LocalDate.now().minusMonths(jobSearchInMonths))) {
                        topics.add(new Topic(nameOfTopic, tempDate, link));
                    }
                }
                y++;
                if ((int) ((double) y / (double) count * 100) > percent) {
                    System.out.print(percent + "% ");
                    percent += 10;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("100%");
        System.out.println("");

        System.out.print("Обработка вакансий:");
        percent = 0;
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        try {
            String text, date;
            int i = 0;
            for (Topic topic : topics) {
                doc = Jsoup.connect(topic.getLink()).get();
                text = doc.select("td[class=msgBody]").text();
                date = doc.select("td[class=msgFooter]").text();
                tempDate = stringToDate(date);
                if (tempDate.isAfter(LocalDate.now().minusMonths(jobSearchInMonths))) {
                    vacancies.add(new Vacancy(topic.getNameOfTopic(), tempDate, text));
                }

                if ((int) ((double) i / (double) topics.size() * 100) > percent) {
                    System.out.print(percent + "% ");
                    percent += 10;
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("100%");
        System.out.println("");

        Pattern pattern = Pattern.compile(nameOfVacancy);
        Matcher matcher;
        return vacancies.stream().filter(vacancy -> pattern.matcher(vacancy.getNameOfVacancy()).find()).collect(Collectors.toList());
    }

    private static LocalDate stringToDate(String date) {
        LocalDate calendarDate;
        if (date.contains("сегодня")) {
            return calendarDate = LocalDate.now();
        } else if (date.contains("вчера")) {
            calendarDate = LocalDate.now();
            return calendarDate = calendarDate.minusDays(1);
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
                        month = 1;
                        break;
                    case "фев":
                        month = 2;
                        break;
                    case "мар":
                        month = 3;
                        break;
                    case "апр":
                        month = 4;
                        break;
                    case "май":
                        month = 5;
                        break;
                    case "июн":
                        month = 6;
                        break;
                    case "июл":
                        month = 7;
                        break;
                    case "авг":
                        month = 8;
                        break;
                    case "сен":
                        month = 9;
                        break;
                    case "окт":
                        month = 10;
                        break;
                    case "ноя":
                        month = 11;
                        break;
                    case "дек":
                        month = 12;
                        break;
                }
            }
            matcher = yearPattern.matcher(date);
            if (matcher.find()) year = Integer.parseInt("20" + matcher.group().substring(0, 2));
            return calendarDate = LocalDate.of(year, month, day);
        }
    }
}
