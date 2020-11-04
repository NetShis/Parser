import java.util.Calendar;

public class Topic {
    private String nameOfTopic;
    private Calendar date;
    private String link;

    public Topic(String nameOfTopic, Calendar date, String link) {
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
