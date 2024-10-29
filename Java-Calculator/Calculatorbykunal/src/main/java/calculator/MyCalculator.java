package calculator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCalculator extends JFrame {
    private boolean start = true;
    private double result = 0;
    private String command = "=";
    private JTextField jTextField;
    private JPanel jPanel = new JPanel();
    private JButton[] jButtons;

    public MyCalculator() {
        this.setTitle("Scientific Calculator");
        this.setSize(600, 300);
        this.setLocationRelativeTo(null);

        jTextField = new JTextField(30);
        jTextField.setText("");
        jTextField.setEditable(true);
        this.add(jTextField, "North");

        jPanel.setLayout(new GridLayout(5, 7, 3, 3));
        String[] name = {
            "+/-", "PI", "1/X", "C", "/", "*", "Back", "X^2", "X^3",
            "X^y", "7", "8", "9", "-", "X!", "√X", "3^√X", "4", "5",
            "6", "+", "sin", "cos", "tan", "1", "2", "3", "%",
            "Binary", "Decimal", "cot", "time", "0", ".", "="
        };
        jButtons = new JButton[name.length];
        MyActionListener actionListener = new MyActionListener();

        for (int i = 0; i < name.length; i++) {
            jButtons[i] = new JButton(name[i]);
            jButtons[i].addActionListener(actionListener);
            jButtons[i].setBackground(Color.lightGray);
            if (name[i].equals("="))
                jButtons[i].setBackground(Color.RED);
            else if ((int) name[i].charAt(0) >= 48 && (int) name[i].charAt(0) <= 57 
                     && name[i].length() == 1)
                jButtons[i].setBackground(Color.WHITE);
            else if (name[i].equals("Back"))
                jButtons[i].setBackground(Color.GRAY);

            jPanel.add(jButtons[i]);
        }

        this.add(jPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            if (start) {
                if ((int) input.charAt(0) >= 48 && (int) input.charAt(0) <= 57 && input.length() == 1) {
                    jTextField.setText("" + input);
                }
                if (input.equals("+/-")) {
                    jTextField.setText("-");
                }
                if (input.equals("PI")) {
                    jTextField.setText("" + Math.PI);
                }
                start = false;
                if (input.equals("C"))
                    jTextField.setText("");
            } else if ((int) input.charAt(0) >= 48 && (int) input.charAt(0) <= 57
                    && input.length() == 1 || input.equals(".")) {
                jTextField.setText(jTextField.getText() + input);
            } else if (input.equals("C"))
                jTextField.setText("");
            else if (input.equals("Back")) {
                if (jTextField.getText().length() > 0) {
                    jTextField.setText(jTextField.getText().substring(0, jTextField.getText().length() - 1));
                }
            } else if (input.equals("sin")) {
                result = Math.sin(Double.parseDouble(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("cos")) {
                result = Math.cos(Double.parseDouble(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("cot")) {
                result = 1.0 / Math.tan(Double.parseDouble(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("tan")) {
                result = Math.tan(Double.parseDouble(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("Binary")) {
                String result2 = Integer.toBinaryString(Integer.parseInt(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(result2));
                start = true;
            } else if (input.equals("Decimal")) {
                try {
                    String result2 = Integer.valueOf(jTextField.getText(), 2).toString();
                    jTextField.setText("" + getPrettyNumber(result2));
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Sorry, invalid number. Please try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                } finally {
                    start = true;
                }
            } else if (input.equals("1/X")) {
                result = 1 / Double.parseDouble(jTextField.getText());
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("X^2")) {
                result = Math.pow(Double.parseDouble(jTextField.getText()), 2);
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("X^3")) {
                result = Math.pow(Double.parseDouble(jTextField.getText()), 3);
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("X!")) {
                if (Double.parseDouble(jTextField.getText()) < 0) {
                    JOptionPane.showMessageDialog(null, "Sorry, factorial cannot be negative", "Error!", JOptionPane.ERROR_MESSAGE);
                    jTextField.setText("Factorial cannot be negative");
                    start = true;
                } else {
                    int sum = factorial(Integer.parseInt(jTextField.getText()));
                    jTextField.setText(Integer.toString(sum));
                    start = true;
                }
            } else if (input.equals("%")) {
                result = Double.parseDouble(jTextField.getText()) / 100.0;
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("√X")) {
                result = Math.sqrt(Double.parseDouble(jTextField.getText()));
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("3^√X")) {
                result = Math.pow(Double.parseDouble(jTextField.getText()), 1.0 / 3);
                jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                start = true;
            } else if (input.equals("time")) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jTextField.setText(df.format(System.currentTimeMillis()));
                start = true;
            } else {
                if (!start) {
                    if (command.equals("+"))
                        result += Double.parseDouble(jTextField.getText());
                    else if (command.equals("-"))
                        result -= Double.parseDouble(jTextField.getText());
                    else if (command.equals("*"))
                        result *= Double.parseDouble(jTextField.getText());
                    else if (command.equals("/")) {
                        if (Double.parseDouble(jTextField.getText()) != 0) {
                            result /= Double.parseDouble(jTextField.getText());
                        } else {
                            jTextField.setText("Error: Division by zero");
                            JOptionPane.showMessageDialog(null, "Error: Division by zero", "Error!", JOptionPane.ERROR_MESSAGE);
                            command = "=";
                            start = true;
                        }
                    } else if (command.equals("=")) 
                        result = Double.parseDouble(jTextField.getText());
                    else if (command.equals("X^y"))
                        result = Math.pow(result, Double.parseDouble(jTextField.getText()));
                    
                    jTextField.setText("" + getPrettyNumber(Double.toString(result)));
                    command = input;
                    start = true;
                }
            }
        }
    }

    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number)).stripTrailingZeros().toPlainString();
    }

    public static int factorial(int num) {
        int sum = 1;
        for (int i = 1; i <= num; i++) {
            sum *= i;
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            new MyCalculator();
        } catch
