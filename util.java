import java.util.Scanner;
public class util {
    public static void MassIndex(double weight, double height) {
        double bmi = weight / (height * height);

        if (bmi < 18.5) {
            System.out.println("体重过轻：低于18.5");
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            System.out.println("正常：在18.5到24.9之间");
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            System.out.println("超重：在25到29.9之间");
        } else if (bmi >= 30.0) {
            System.out.println("肥胖：大于30");
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入您的身高：");
        double height = scanner.nextDouble();

        System.out.print("请输入您的体重：");
        double weight = scanner.nextDouble();
        scanner.close();
        MassIndex(height, weight);
    }
}