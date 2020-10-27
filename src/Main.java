public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Введите сайт для поиска и " +
                    "период поиска вакансий в месяцах от 1 до 12!");
        }
        try {
            int jobSearchInMonths = Integer.parseInt(args[1]);
            if (jobSearchInMonths <= 0 || jobSearchInMonths > 12) System.out.println("В месяцах от 1 до 12!");
        } catch (NumberFormatException ex) {
            System.out.println("Второй аргумент задает период поиска вакансий в месяцах от 1 до 12!");
        }
    }

}
