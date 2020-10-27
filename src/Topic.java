import lombok.Data;
import java.util.Calendar;

@Data
public class Topic {
    String nameOfTopic;
    private Calendar date;
    String link;
}
