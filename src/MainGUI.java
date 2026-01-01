import javax.swing.*;
import java.awt.*;

import dateCalculation.DateCalculatorGUI;
import geometricCalculation.GeometricCalculatorGUI;
import unitsConversion.UnitConverterGUI;

public class MainGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("多功能计算器");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 设置中文字体
            Font font = new Font("Microsoft YaHei", Font.PLAIN, 15);
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("Menu.font", font);
            UIManager.put("MenuItem.font", font);

            // 窗口基本属性
            frame.setSize(350, 400);
            frame.setLocationRelativeTo(null); // 居中显示
            frame.setLayout(new BorderLayout());

            // 创建中间按钮面板
            JPanel buttonPanel = getJPanel(args, font);

            frame.add(buttonPanel, BorderLayout.CENTER);

            // 创建菜单栏
            JMenuBar menuBar = getJMenuBar(font, frame);
            frame.setJMenuBar(menuBar);

            // 底部标签（可选）
            JLabel footerLabel = new JLabel("请选择一个功能开始使用", SwingConstants.CENTER);
            footerLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
            footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            frame.add(footerLabel, BorderLayout.SOUTH);

            frame.setVisible(true);
        });
    }

    private static JMenuBar getJMenuBar(Font font, JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        JMenu aboutMenu = new JMenu("关于");
        aboutMenu.setFont(font);

        JMenuItem aboutItem = new JMenuItem("关于本程序");
        aboutItem.setFont(font);
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                frame,
                "多功能计算器 v0.1.3\n\n包含以下功能：\n- 日期计算\n- 几何计算\n- 单位换算\n\n爱来自kk3TWT",
                "关于",
                JOptionPane.INFORMATION_MESSAGE
        ));

        aboutMenu.add(aboutItem);
        menuBar.add(aboutMenu);
        return menuBar;
    }

    private static JPanel getJPanel(String[] args, Font font) {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50)); // 增加边距

        JButton dateCalculation = new JButton("日期计算");
        JButton geometricCalculation = new JButton("几何计算");
        JButton unitsConversion = new JButton("单位转换");

        // 设置按钮字体
        dateCalculation.setFont(font);
        geometricCalculation.setFont(font);
        unitsConversion.setFont(font);

        // 设置按钮大小（缩小）
        Dimension buttonSize = new Dimension(120, 40);
        dateCalculation.setPreferredSize(buttonSize);
        geometricCalculation.setPreferredSize(buttonSize);
        unitsConversion.setPreferredSize(buttonSize);

        // 添加按钮监听
        dateCalculation.addActionListener(e -> DateCalculatorGUI.main(args));
        geometricCalculation.addActionListener(e -> GeometricCalculatorGUI.main(args));
        unitsConversion.addActionListener(e -> UnitConverterGUI.main(args));

        // 将按钮添加到面板
        buttonPanel.add(dateCalculation);
        buttonPanel.add(geometricCalculation);
        buttonPanel.add(unitsConversion);
        return buttonPanel;
    }
}