import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String vacancyTitle = "Python";
        int jobSearchInMonths = 12;

        if (args.length != 2 && args.length != 0) {
            System.out.println("Введите сайт для поиска и " +
                    "период поиска вакансий в месяцах от 1 до 12!");
        } else {
            if (args.length != 0) {
                try {
                    jobSearchInMonths = Integer.parseInt(args[1]);
                    if (jobSearchInMonths <= 0 || jobSearchInMonths > 12) System.out.println("В месяцах от 1 до 12!");
                } catch (NumberFormatException ex) {
                    System.out.println("Второй аргумент задает период поиска вакансий в месяцах от 1 до 12!");
                }
            }

            Path path = Paths.get("./Sql.txt");
            List<Vacancy> vacancies = Sql.jobSearch(vacancyTitle, jobSearchInMonths);

            try {
                BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset());
                vacancies.stream().forEach(vacancy -> {
                    try {
                        writer.write(vacancy.getNameOfVacancy() +
                                "\n" + vacancy.getDate().toString() +
                                "\n" + vacancy.getText() +
                                "\n" + "------------------" +
                                "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Результат записан в файл " + path.getFileName());
        }
    }
}
