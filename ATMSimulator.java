import java.util.Scanner;

public class ATMSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double balance = 1000.0; // 初始余额
        int choice;

        // 循环显示菜单
        do {
            System.out.println("\n1. 查询余额");
            System.out.println("2. 取款");
            System.out.println("3. 存款");
            System.out.println("0. 退出");
            System.out.print("请选择操作：");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // 查询余额
                    System.out.printf("当前余额：%.1f\n", balance);
                    break;

                case 2:
                    // 取款
                    System.out.print("输入金额：");
                    double withdrawAmount = scanner.nextDouble();

                    if (withdrawAmount <= 0) {
                        System.out.println("错误：金额必须为正数。");
                    } else if (withdrawAmount % 100 != 0) {
                        System.out.println("错误：金额必须是100的整数倍。");
                    } else if (withdrawAmount > balance) {
                        System.out.printf("错误：余额不足。当前余额：%.1f\n", balance);
                    } else {
                        balance -= withdrawAmount;
                        System.out.println("取款成功。");
                        System.out.printf("新余额：%.1f\n", balance);
                    }
                    break;

                case 3:
                    // 存款
                    System.out.print("输入金额：");
                    double depositAmount = scanner.nextDouble();

                    if (depositAmount <= 0) {
                        System.out.println("错误：金额必须为正数。");
                    } else if (depositAmount % 100 != 0) {
                        System.out.println("错误：金额必须是100的整数倍。");
                    } else {
                        balance += depositAmount;
                        System.out.println("存款成功。");
                        System.out.printf("新余额：%.1f\n", balance);
                    }
                    break;

                case 0:
                    System.out.println("退出ATM，谢谢使用！");
                    break;

                default:
                    System.out.println("无效选项，请重试。");
            }

        } while (choice != 0); // 选择0才退出循环

        scanner.close();
    }
}