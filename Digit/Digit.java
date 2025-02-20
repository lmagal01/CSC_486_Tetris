import java.util.Random;
import java.util.Scanner;

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
        for (int i = 0; i < numbers.length; i++) {
            input();
        }
        System.out.println("Done 4");
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
        for (int i = 0; i < numbers.length; i++) {
            input();
        }
        System.out.println("Done 5");
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
        for (int i = 0; i < numbers.length; i++) {
            input();
        }
        System.out.println("Done 6");
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
        for (int i = 0; i < numbers.length; i++) {
            input();
        }
        System.out.println("Done 7");
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
        for (int i = 0; i < numbers.length; i++) {
            input();
        }
        System.out.println("Done 8");
    }

    public static void input() {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter next digit");

        String digitRead = myObj.nextLine();
    }
}
