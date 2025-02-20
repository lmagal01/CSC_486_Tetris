import java.util.Random;

public class Digit {
    public static void main(String[] args) {
        Random random = new Random();
        int[] numbers = new int[4];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        for (int number : numbers) {
            System.out.println(number);
        }
        System.out.println("Done");
        five();
    }

    public static void five() {
        Random random = new Random();
        int[] numbers = new int[5];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        for (int number : numbers) {
            System.out.println(number);
        }
        System.out.println("Done");
        six();
    }

    public static void six() {
        Random random = new Random();
        int[] numbers = new int[6];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        for (int number : numbers) {
            System.out.println(number);
        }
        System.out.println("Done");
        seven();
    }

    public static void seven() {
        Random random = new Random();
        int[] numbers = new int[7];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }

        for (int number : numbers) {
            System.out.println(number);
        }
        System.out.println("Done");
        eight();
    }

    public static void eight() {
        Random random = new Random();
        int[] numbers = new int[8];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(10);
        }
        for (int number : numbers) {
            System.out.println(number);
        }
        System.out.println("Done");

    }
}
