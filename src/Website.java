import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Website implements Parser {
    public String name;
    public int jobSearchInMonths;

    public Website(String name,int jobSearchInMonths) {
        this.name = name;
        this.jobSearchInMonths = jobSearchInMonths;
    }

    public void jobSearch(String nameOfVacancy) {
        ArrayList<Vacancy> vacancies = getVacancies();
        Pattern pattern = Pattern.compile(nameOfVacancy);
        Matcher matcher;

        for (Vacancy vacancy : vacancies) {
            matcher = pattern.matcher(vacancy.getNameOfVacancy());
            if (matcher.find()) {
                System.out.println(vacancy.getNameOfVacancy());
                System.out.println(vacancy.getText());
                System.out.println(vacancy.getDate().getTime());
                System.out.println("-------------------");
            }
        }
    }

    public Calendar stringToDate(String date) {
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
}
