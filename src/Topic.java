import lombok.Data;
import java.util.Calendar;


public class Topic {
    private String nameOfTopic;
    private String date;
    private String link;

    public Topic(String nameOfTopic, String date, String link) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
