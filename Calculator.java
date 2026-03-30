import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "0";
    private String operator = "";
    private double firstNumber = 0;
    private boolean startNewInput = true;

    public Calculator() {
        // 窗口基础配置（精简合并）
        setTitle("Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        // 显示屏（保留所有原样式）
        display = new JTextField("0");
        display.setFont(new Font("Segoe UI", Font.PLAIN, 70));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(display, BorderLayout.NORTH);

        // 按钮面板（精简GridBagConstraints初始化）
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // 批量添加按钮（合并重复循环，删除冗余addButton方法）
        addButtonRow(buttonPanel, gbc, 0, new String[]{"MC", "MR", "MS", "M+", "M-"},
                Color.WHITE, Color.BLACK, Color.GREEN);
        // 第二行特殊样式：前4绿，最后1橙红
        addButtonRow(buttonPanel, gbc, 1, new String[]{"CLR", "DEL", "±", "%"},
                Color.WHITE, Color.BLACK, Color.GREEN);
        addSingleButton(buttonPanel, gbc, 1, 4, "÷", Color.WHITE, Color.RED, Color.ORANGE);
        // 数字+运算键行（3数字+1运算）
        addNumOpRow(buttonPanel, gbc, 2, "789", "×");
        addNumOpRow(buttonPanel, gbc, 3, "456", "-");
        addNumOpRow(buttonPanel, gbc, 4, "123", "+");
        // 最后一行（0占两格、.、=）
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        addSingleButton(buttonPanel, gbc, 5, 0, "0", Color.WHITE, Color.BLACK, Color.RED);
        gbc.gridwidth = 1;
        addSingleButton(buttonPanel, gbc, 5, 2, ".", Color.WHITE, Color.BLACK, Color.RED);
        addSingleButton(buttonPanel, gbc, 5, 3, "=", Color.WHITE, Color.BLUE, Color.BLUE);
    }

    // 统一创建按钮（保留原样式）
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

    // 批量添加单行同样式按钮
    private void addButtonRow(JPanel panel, GridBagConstraints gbc, int y, String[] texts, Color bg, Color fg, Color border) {
        gbc.gridy = y;
        for (int i = 0; i < texts.length; i++) {
            gbc.gridx = i;
            panel.add(createButton(texts[i], bg, fg, border), gbc);
        }
    }

    // 添加单个按钮
    private void addSingleButton(JPanel panel, GridBagConstraints gbc, int y, int x, String text, Color bg, Color fg, Color border) {
        gbc.gridy = y;
        gbc.gridx = x;
        panel.add(createButton(text, bg, fg, border), gbc);
    }

    // 批量添加数字+运算键行（适配3数字+1运算的固定样式）
    private void addNumOpRow(JPanel panel, GridBagConstraints gbc, int y, String nums, String op) {
        gbc.gridy = y;
        // 添加3个数字键
        for (int i = 0; i < 3; i++) {
            gbc.gridx = i;
            panel.add(createButton(nums.charAt(i) + "", Color.WHITE, Color.BLACK, Color.RED), gbc);
        }
        // 添加运算键
        gbc.gridx = 3;
        panel.add(createButton(op, Color.WHITE, Color.RED, Color.ORANGE), gbc);
    }

    // 自适应字体（保留原逻辑）
    private void adjustFontSize() {
        int len = display.getText().length();
        display.setFont(new Font("Segoe UI", Font.PLAIN, len > 8 ? 70 - (len - 8) * 5 : 70));
    }

    // 抽离重复的数字格式化逻辑（去掉.0，简化多处重复代码）
    private String formatNum(double num) {
        String s = String.valueOf(num);
        return s.endsWith(".0") ? s.substring(0, s.length() - 2) : s;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        // 数字输入
        if (cmd.matches("\\d")) {
            currentInput = startNewInput ? cmd : currentInput + cmd;
            startNewInput = false;
        }
        // 小数点
        else if (cmd.equals(".")) {
            currentInput = startNewInput ? "0." : (currentInput.contains(".") ? currentInput : currentInput + ".");
            startNewInput = false;
        }
        // 清空
        else if (cmd.equals("CLR")) {
            currentInput = "0";
            operator = "";
            firstNumber = 0;
            startNewInput = true;
        }
        // 删除
        else if (cmd.equals("DEL")) {
            currentInput = currentInput.length() > 1 ? currentInput.substring(0, currentInput.length() - 1) : "0";
        }
        // 正负号
        else if (cmd.equals("±")) {
            currentInput = formatNum(-Double.parseDouble(currentInput));
        }
        // 百分号
        else if (cmd.equals("%")) {
            currentInput = formatNum(Double.parseDouble(currentInput) / 100);
        }
        // 运算符
        else if (cmd.matches("[+\\-×÷]")) {
            firstNumber = Double.parseDouble(currentInput);
            operator = cmd;
            startNewInput = true;
            return; // 无需更新显示
        }
        // 等号计算
        else if (cmd.equals("=")) {
            double second = Double.parseDouble(currentInput);
            double result = 0;
            switch (operator) {
                case "+": result = firstNumber + second; break;
                case "-": result = firstNumber - second; break;
                case "×": result = firstNumber * second; break;
                case "÷":
                    if (second == 0) {
                        display.setText("Error");
                        adjustFontSize();
                        return;
                    }
                    result = firstNumber / second;
                    break;
            }
            currentInput = formatNum(result);
            startNewInput = true;
        }
        // 统一更新显示+调整字体（避免多处重复调用）
        display.setText(currentInput);
        adjustFontSize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}




