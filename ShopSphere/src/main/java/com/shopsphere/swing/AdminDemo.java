package com.shopsphere.swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/** Small Swing MVC demonstration, intentionally independent of the web database. */
public final class AdminDemo {
    private AdminDemo() { }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) { }
            new ProductWindow().setVisible(true);
        });
    }
    private static final class ProductWindow extends JFrame {
        private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Product", "Category", "Available"}, 0);
        private final JTextField name = new JTextField(16);
        private final JCheckBox available = new JCheckBox("Available", true);
        ProductWindow() {
            super("ShopSphere · Admin demo");
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(620, 390); setLocationRelativeTo(null);
            JTable table = new JTable(model);
            JComboBox<String> category = new JComboBox<>(new String[]{"Audio", "Computing", "Home"});
            JRadioButton standard = new JRadioButton("Standard", true);
            JRadioButton featured = new JRadioButton("Featured");
            ButtonGroup group = new ButtonGroup(); group.add(standard); group.add(featured);
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
            form.add(new JLabel("Name")); form.add(name); form.add(category); form.add(standard); form.add(featured); form.add(available);
            JButton add = new JButton("Add product");
            add.addActionListener(event -> {
                String value = name.getText().trim();
                if (value.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a product name."); return; }
                model.addRow(new Object[]{value, category.getSelectedItem(), available.isSelected()});
                name.setText("");
            });
            JButton remove = new JButton("Remove selected");
            remove.addActionListener(event -> { int row = table.getSelectedRow(); if (row >= 0) model.removeRow(row); });
            JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT)); actions.add(remove); actions.add(add);
            add(form, BorderLayout.NORTH); add(new JScrollPane(table), BorderLayout.CENTER); add(actions, BorderLayout.SOUTH);
            model.addRow(new Object[]{"Studio Wireless Headphones", "Audio", true});
        }
    }
}
