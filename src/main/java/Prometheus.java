import java.util.Scanner;


public class Prometheus {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Prometheus");
        System.out.println("What can I do for you?");
        System.out.println("_____");


        Scanner scanner = new Scanner(System.in);
        String input;


        while(true) {
            input = scanner.nextLine();
            System.out.println(input);
            System.out.println("_____");
            if (input.equalsIgnoreCase("bye")) {
                break;
            }
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}

