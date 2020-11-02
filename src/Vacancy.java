import java.util.Calendar;

public class Vacancy {
    private String nameOfVacancy;
    private Calendar date;
    private String text;

    public Vacancy(String nameOfVacancy, Calendar date, String text) {
        this.nameOfVacancy = nameOfVacancy;
        this.date = date;
        this.text = text;
    }

    public String getNameOfVacancy() {
        return nameOfVacancy;
    }

    public void setNameOfVacancy(String nameOfVacancy) {
        this.nameOfVacancy = nameOfVacancy;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
