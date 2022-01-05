import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public final class Util {
    public static boolean getConfirmation(){
        // Check if the user wants to do something
        Scanner scan = new Scanner(System.in);
        System.out.print("y/N > ");
        return scan.nextLine().equalsIgnoreCase("y");
    }
    public static double[] generateRandoms(int sum, int count) {

        double[] numbers;
        do {
            Random rand = new Random();
            numbers = new double[count];

            // Generate X numbers
            for (int i = 0; i < count; i++) {
                numbers[i] = rand.nextDouble();
            }

            double total = Arrays.stream(numbers).sum();

            // Divide the numbers by the sum of generated numbers {total} and multiply them by the target sum {sum}
            for (int i = 0; i < count; i++) {
                numbers[i] = Math.round((numbers[i] / total) * sum);
            }

            // In situations where the numbers don't add to make the target sum, try again (caused by rounding)
        } while (Arrays.stream(numbers).sum() != sum);

        return numbers;
    }
}
