public class Main {
    public static void main(String[] args) {
        String vacancyTitle = "Java EE";
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
        }
    }
}
