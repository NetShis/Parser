import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    /**
     * На вход программе передается два параметра: первый название вакансии,
     * а второй период поиска вакансии в месяцах. Допустим вариант пез параметров,
     * тогда поиск будет задан по умолчанию (значения переменных vacancyTitle и jobSearchInMonths)
     *
     * @param args
     */
    public static void main(String[] args) {
        String vacancyTitle = "Java";
        int jobSearchInMonths = 12;

        if (args.length != 2 && args.length != 0) {
            System.out.println("Введите название вакансии для поиска и " +
                    "период поиска вакансий в месяцах от 1 до 12!");
        } else if (args.length != 0) {
            try {
                jobSearchInMonths = Integer.parseInt(args[1]);
                if (jobSearchInMonths <= 0 || jobSearchInMonths > 12) System.out.println("В месяцах от 1 до 12!");
                else Main.search(args[0], jobSearchInMonths);
            } catch (NumberFormatException ex) {
                System.out.println("Второй аргумент задает период поиска вакансий в месяцах от 1 до 12!");
            }
        } else Main.search(vacancyTitle, jobSearchInMonths);
    }

    /**
     * Спрашиваем у пользователя на каком сайте искать вакансию.
     * Вызываем статический метод соответствующего класса, где реализован алгоритм парсера вакансий.
     * Для каждого сайта свой класс со статический методом.
     *
     * @param vacancyTitle
     * @param jobSearchInMonths
     */
    private static void search(String vacancyTitle, int jobSearchInMonths) {
        Map<Integer, String> siteSelection = new HashMap<>();
        siteSelection.put(1, "SQL");
        siteSelection.put(2, "hh");
        System.out.println("Выберете сайт для поиска вакансий:");
        siteSelection.forEach((k, v) -> System.out.println(k + "-" + v));
        System.out.println("Введите сайт для поиска(цифру)");
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1: {
                    List<Vacancy> vacancies = Sql.jobSearch(vacancyTitle, jobSearchInMonths);
                    writeToFile(vacancies, "./Sql.txt");
                    break;
                }
                case 2: {
                    System.out.println("Поиск по сайту не реализован");
                    break;
                }
                default: {
                    System.out.println("Такой цифры нет.");
                }

            }
        } catch (NumberFormatException ex) {
            System.out.println("Необходимо набирать цифру.");
        }
    }

    /**
     * Записываем результат в файл, в текущую директорию откуда был запущен jar(ник)
     * Файл и имя для каждого сайта будет свое.
     * @param vacancies
     * @param siteName
     */
    private static void writeToFile(List<Vacancy> vacancies, String siteName) {
        Path path = Paths.get(siteName);
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
