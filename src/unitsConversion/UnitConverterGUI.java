// UnitConverterGUI.java
package unitsConversion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitConverterGUI extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    // 各个换算面板
    private JPanel lengthPanel;
    private JPanel areaPanel;
    private JPanel volumePanel;
    private JPanel massPanel;
    private JPanel numSystemPanel;
    private JPanel speedPanel;
    private JPanel temperaturePanel;
    private JPanel storagePanel;

    public UnitConverterGUI() {
        setTitle("单位换算器");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();

        setContentPane(mainPanel);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // 创建各个功能面板
        createLengthPanel();
        createAreaPanel();
        createVolumePanel();
        createMassPanel();
        createNumSystemPanel();
        createSpeedPanel();
        createTemperaturePanel();
        createStoragePanel();

        // 添加标签页
        tabbedPane.addTab("长度换算", lengthPanel);
        tabbedPane.addTab("面积换算", areaPanel);
        tabbedPane.addTab("体积换算", volumePanel);
        tabbedPane.addTab("质量换算", massPanel);
        tabbedPane.addTab("进制换算", numSystemPanel);
        tabbedPane.addTab("速度换算", speedPanel);
        tabbedPane.addTab("温度换算", temperaturePanel);
        tabbedPane.addTab("存储单位换算", storagePanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // 添加说明
        JLabel infoLabel = new JLabel(
                "<html><center>输入数值并选择输入单位，系统将自动计算所有单位的换算结果<br>" +
                        "部分类型的换算支持国际单位、英制单位和市制单位之间的换算</center></html>",
                SwingConstants.CENTER
        );
        infoLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(infoLabel, BorderLayout.SOUTH);
    }

    private void createLengthPanel() {
        lengthPanel = new JPanel(new BorderLayout(10, 10));
        lengthPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("长度换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.LENGTH_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("长度换算结果"));

        lengthPanel.add(inputPanel, BorderLayout.NORTH);
        lengthPanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.length(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    // 移除多余的零
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.LENGTH_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createAreaPanel() {
        areaPanel = new JPanel(new BorderLayout(10, 10));
        areaPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("面积换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.AREA_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("面积换算结果"));

        areaPanel.add(inputPanel, BorderLayout.NORTH);
        areaPanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.area(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.AREA_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createVolumePanel() {
        volumePanel = new JPanel(new BorderLayout(10, 10));
        volumePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("体积换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.VOLUME_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("体积换算结果"));

        volumePanel.add(inputPanel, BorderLayout.NORTH);
        volumePanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.volume(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.VOLUME_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createMassPanel() {
        massPanel = new JPanel(new BorderLayout(10, 10));
        massPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("质量换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.MASS_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("质量换算结果"));

        massPanel.add(inputPanel, BorderLayout.NORTH);
        massPanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.mass(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.MASS_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createNumSystemPanel() {
        numSystemPanel = new JPanel(new BorderLayout(10, 10));
        numSystemPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("进制换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("进制:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.NUM_SYSTEM_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"进制", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("进制换算结果"));

        numSystemPanel.add(inputPanel, BorderLayout.NORTH);
        numSystemPanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                String value = valueField.getText().trim();
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                String[] results = Converts.numSystem(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%s", results[i]);

                    Object[] rowData = {
                            Converts.NUM_SYSTEM_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createSpeedPanel() {
        speedPanel = new JPanel(new BorderLayout(10, 10));
        speedPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("速度换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.SPEED_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("单位换算结果"));

        speedPanel.add(inputPanel, BorderLayout.NORTH);
        speedPanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.speed(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.SPEED_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createTemperaturePanel() {
        temperaturePanel = new JPanel(new BorderLayout(10, 10));
        temperaturePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("温度换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.TEMPERATURE_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("单位换算结果"));

        temperaturePanel.add(inputPanel, BorderLayout.NORTH);
        temperaturePanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.temperature(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.TEMPERATURE_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void createStoragePanel() {
        storagePanel = new JPanel(new BorderLayout(10, 10));
        storagePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 输入面板 - 修改布局
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("数据单位换算输入"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 第一列：数值输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("数值:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JTextField valueField = new JTextField();
        inputPanel.add(valueField, gbc);

        // 第二列：单位选择
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        inputPanel.add(new JLabel("单位:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        JComboBox<String> unitCombo = new JComboBox<>(Converts.STORAGE_UNITS);
        inputPanel.add(unitCombo, gbc);

        // 第三列：按钮
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton convertButton = new JButton("换算");
        JButton clearButton = new JButton("清空");
        buttonPanel.add(convertButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        inputPanel.add(buttonPanel, gbc);

        // 结果表格
        String[] columnNames = {"单位", "换算结果"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBorder(new TitledBorder("单位换算结果"));

        storagePanel.add(inputPanel, BorderLayout.NORTH);
        storagePanel.add(scrollPane, BorderLayout.CENTER);

        // 事件处理
        convertButton.addActionListener(e -> {
            try {
                double value = Double.parseDouble(valueField.getText().trim());
                int selectedIndex = unitCombo.getSelectedIndex();
                byte unitChoice = (byte)(selectedIndex + 1);

                double[] results = Converts.storage(unitChoice, value);

                // 清空表格
                tableModel.setRowCount(0);

                // 添加结果
                for (int i = 0; i < results.length; i++) {
                    String formattedResult = String.format("%.10f", results[i]);
                    formattedResult = formattedResult.replaceAll("0*$", "").replaceAll("\\.$", "");

                    Object[] rowData = {
                            Converts.STORAGE_UNITS[i],
                            formattedResult
                    };
                    tableModel.addRow(rowData);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字！", "错误", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "换算过程中出现错误！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearButton.addActionListener(e -> {
            valueField.setText("");
            tableModel.setRowCount(0);
        });

        // 回车键转换
        valueField.addActionListener(e -> convertButton.doClick());
    }

    private void setupLayout() {
        // 设置统一的字体
        Font titleFont = new Font("微软雅黑", Font.BOLD, 16);
        Font labelFont = new Font("宋体", Font.PLAIN, 14);
        Font buttonFont = new Font("宋体", Font.BOLD, 14);
        Font tableFont = new Font("宋体", Font.PLAIN, 13);

        // 设置所有组件的字体
        Component[] components = tabbedPane.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                setComponentFont((JPanel) comp, labelFont, buttonFont, tableFont);
            }
        }
    }

    private void setComponentFont(JPanel panel, Font labelFont, Font buttonFont, Font tableFont) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setFont(labelFont);
            } else if (comp instanceof JButton) {
                comp.setFont(buttonFont);
            } else if (comp instanceof JComboBox) {
                comp.setFont(labelFont);
            } else if (comp instanceof JTextField) {
                comp.setFont(labelFont);
            } else if (comp instanceof JTable) {
                comp.setFont(tableFont);
            } else if (comp instanceof JScrollPane) {
                // 处理滚动面板中的组件
                Component view = ((JScrollPane) comp).getViewport().getView();
                if (view instanceof JTable) {
                    view.setFont(tableFont);
                }
            } else if (comp instanceof JPanel) {
                setComponentFont((JPanel) comp, labelFont, buttonFont, tableFont);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 设置系统外观
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // 设置UI样式
                UIManager.put("TabbedPane.selected", new Color(230, 240, 255));

            } catch (Exception e) {
                e.printStackTrace();
            }

            UnitConverterGUI gui = new UnitConverterGUI();
            gui.setVisible(true);
        });
    }
}