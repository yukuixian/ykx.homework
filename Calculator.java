import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private String currentInput = "0";
    private String operator = "";
    private double firstNumber = 0;
    private boolean startNewInput = true;

    public Calculator() {
        setTitle("Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 显示屏（白色背景，自适应字体大小）
        display = new JTextField("0");
        display.setFont(new Font("Segoe UI", Font.PLAIN, 70));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(display, BorderLayout.NORTH);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1;
        gbc.weighty = 1;

        // 第一行：MC MR MS M+ M-（绿色边框）
        String[] row1 = {"MC", "MR", "MS", "M+", "M-"};
        gbc.gridy = 0;
        for (int i = 0; i < 5; i++) {
            gbc.gridx = i;
            gbc.gridwidth = 1;
            JButton btn = createButton(row1[i], Color.WHITE, Color.BLACK, Color.GREEN);
            buttonPanel.add(btn, gbc);
        }

        // 第二行：CLR DEL ± % ÷（前四个绿色，最后一个橙色）
        String[] row2 = {"CLR", "DEL", "±", "%", "÷"};
        gbc.gridy = 1;
        for (int i = 0; i < 5; i++) {
            gbc.gridx = i;
            gbc.gridwidth = 1;
            Color borderColor = (i == 4) ? Color.ORANGE : Color.GREEN;
            Color fgColor = (i == 4) ? Color.RED : Color.BLACK;
            JButton btn = createButton(row2[i], Color.WHITE, fgColor, borderColor);
            buttonPanel.add(btn, gbc);
        }

        // 第三行：7 8 9 ×（前三个红色，最后一个橙色）
        gbc.gridy = 2;
        addButton(buttonPanel, gbc, 0, "7", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 1, "8", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 2, "9", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 3, "×", Color.WHITE, Color.RED, Color.ORANGE);

        // 第四行：4 5 6 -（前三个红色，最后一个橙色）
        gbc.gridy = 3;
        addButton(buttonPanel, gbc, 0, "4", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 1, "5", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 2, "6", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 3, "-", Color.WHITE, Color.RED, Color.ORANGE);

        // 第五行：1 2 3 +（前三个红色，最后一个橙色）
        gbc.gridy = 4;
        addButton(buttonPanel, gbc, 0, "1", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 1, "2", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 2, "3", Color.WHITE, Color.BLACK, Color.RED);
        addButton(buttonPanel, gbc, 3, "+", Color.WHITE, Color.RED, Color.ORANGE);

        // 第六行：0 . =（0红色，.红色，=蓝色）
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton zeroBtn = createButton("0", Color.WHITE, Color.BLACK, Color.RED);
        buttonPanel.add(zeroBtn, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 1;
        JButton dotBtn = createButton(".", Color.WHITE, Color.BLACK, Color.RED);
        buttonPanel.add(dotBtn, gbc);

        gbc.gridx = 3;
        JButton equalBtn = createButton("=", Color.WHITE, Color.BLUE, Color.BLUE);
        buttonPanel.add(equalBtn, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JButton createButton(String text, Color bg, Color fg, Color border) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(border, 3));
        btn.addActionListener(this);
        return btn;
    }

    private void addButton(JPanel panel, GridBagConstraints gbc, int x, String text, Color bg, Color fg, Color border) {
        gbc.gridx = x;
        gbc.gridwidth = 1;
        JButton btn = createButton(text, bg, fg, border);
        panel.add(btn, gbc);
    }

    // 自适应字体大小，确保数字全部显示
    private void adjustFontSize() {
        String text = display.getText();
        int textLength = text.length();
        int baseSize = 70;

        if (textLength > 8) {
            display.setFont(new Font("Segoe UI", Font.PLAIN, baseSize - (textLength - 8) * 5));
        } else {
            display.setFont(new Font("Segoe UI", Font.PLAIN, baseSize));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.matches("\\d")) {
            if (startNewInput) {
                currentInput = cmd;
                startNewInput = false;
            } else {
                currentInput += cmd;
            }
            display.setText(currentInput);
            adjustFontSize(); // 调整字体
        } else if (cmd.equals(".")) {
            if (startNewInput) {
                currentInput = "0.";
                startNewInput = false;
            } else if (!currentInput.contains(".")) {
                currentInput += ".";
            }
            display.setText(currentInput);
            adjustFontSize();
        } else if (cmd.equals("CLR")) {
            currentInput = "0";
            operator = "";
            firstNumber = 0;
            startNewInput = true;
            display.setText(currentInput);
            adjustFontSize();
        } else if (cmd.equals("DEL")) {
            if (currentInput.length() > 1) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            } else {
                currentInput = "0";
            }
            display.setText(currentInput);
            adjustFontSize();
        } else if (cmd.equals("±")) {
            double num = Double.parseDouble(currentInput);
            num = -num;
            currentInput = String.valueOf(num);
            if (currentInput.endsWith(".0")) {
                currentInput = currentInput.substring(0, currentInput.length() - 2);
            }
            display.setText(currentInput);
            adjustFontSize();
        } else if (cmd.equals("%")) {
            double num = Double.parseDouble(currentInput);
            num /= 100;
            currentInput = String.valueOf(num);
            if (currentInput.endsWith(".0")) {
                currentInput = currentInput.substring(0, currentInput.length() - 2);
            }
            display.setText(currentInput);
            adjustFontSize();
        } else if (cmd.matches("[+\\-×÷]")) {
            firstNumber = Double.parseDouble(currentInput);
            operator = cmd;
            startNewInput = true;
        } else if (cmd.equals("=")) {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "×": result = firstNumber * secondNumber; break;
                case "÷":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        adjustFontSize();
                        return;
                    }
                    break;
            }

            // 格式化结果，去掉多余的 .0
            currentInput = String.valueOf(result);
            if (currentInput.endsWith(".0")) {
                currentInput = currentInput.substring(0, currentInput.length() - 2);
            }
            display.setText(currentInput);
            adjustFontSize(); // 计算后调整字体
            startNewInput = true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }
}


