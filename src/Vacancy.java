import java.time.LocalDate;

public class Vacancy {
    private String nameOfVacancy;
    private LocalDate date;
    private String text;

    public Vacancy(String nameOfVacancy, LocalDate date, String text) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
