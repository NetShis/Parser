import java.time.LocalDate;

public class Topic {
    private String nameOfTopic;
    private LocalDate date;
    private String link;

    public Topic(String nameOfTopic, LocalDate date, String link) {
        this.nameOfTopic = nameOfTopic;
        this.date = date;
        this.link = link;
    }

    public String getNameOfTopic() {
        return nameOfTopic;
    }

    public void setNameOfTopic(String nameOfTopic) {
        this.nameOfTopic = nameOfTopic;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
