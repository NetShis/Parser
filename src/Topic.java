import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Topic {
    private String nameOfTopic;
    private Calendar date;
    private String link;

    public Topic(String nameOfTopic, Calendar date, String link) {
        this.nameOfTopic = nameOfTopic;
        this.date = date;
        this.link = link;
    }

    public Topic(String nameOfTopic, String date, String link) {
        this.nameOfTopic = nameOfTopic;
        this.link = link;

        if (date.contains("сегодня")) {
            this.date = new GregorianCalendar();
        } else if (date.contains("вчера")) {
            this.date = new GregorianCalendar();
            this.date.add(Calendar.DAY_OF_YEAR, -1);
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
                switch (matcher.group().substring(1)){
                    case "янв":
                        month=0;
                        break;
                    case "фев":
                        month=1;
                        break;
                    case "мар":
                        month=2;
                        break;
                    case "апр":
                        month=3;
                        break;
                    case "май":
                        month=4;
                        break;
                    case "июн":
                        month=5;
                        break;
                    case "июл":
                        month=6;
                        break;
                    case "авг":
                        month=7;
                        break;
                    case "сен":
                        month=8;
                        break;
                    case "окт":
                        month=9;
                        break;
                    case "ноя":
                        month=10;
                        break;
                    case "дек":
                        month=11;
                        break;
                }
            }
            matcher = yearPattern.matcher(date);
            if (matcher.find()) year = Integer.parseInt("20" + matcher.group().substring(0, 2));
            this.date = new GregorianCalendar(year, month, day);
        }
    }

    public String getNameOfTopic() {
        return nameOfTopic;
    }

    public void setNameOfTopic(String nameOfTopic) {
        this.nameOfTopic = nameOfTopic;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
