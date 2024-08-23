package random;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Calculator extends JFrame implements ActionListener {
    JTextField t1;
    JButton[] numberButtons = new JButton[10];
    JButton add, sub, divide, multiply, equals, clear;

    public Calculator() {
        super("Calculator App.");

        setSize(400, 300);
        setLayout(new BorderLayout(5, 5)); // Use BorderLayout with gaps

        t1 = new JTextField();
        t1.setEditable(false);
        t1.setFont(new Font("Arial", Font.BOLD, 24));
        t1.setHorizontalAlignment(JTextField.RIGHT);
        add(t1, BorderLayout.NORTH); // Add display field to the top

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5)); // 4 rows, 4 columns with gaps
        add(buttonPanel, BorderLayout.CENTER); // Add button panel to the center

        // Initialize number buttons
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
            numberButtons[i].setPreferredSize(new Dimension(80, 80));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setBorder(new EmptyBorder(10, 10, 10, 10));
            buttonPanel.add(numberButtons[i]);
        }

        add = new JButton("+");
        sub = new JButton("-");
        divide = new JButton("/");
        multiply = new JButton("*");
        equals = new JButton("=");
        clear = new JButton("C");

        JButton[] operators = {add, sub, multiply, divide, equals, clear};
        for (JButton btn : operators) {
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setPreferredSize(new Dimension(80, 80));
            btn.setBorder(new EmptyBorder(10, 10, 10, 10));
            btn.addActionListener(this);
            btn.setBackground(Color.LIGHT_GRAY);
        }

        equals.setBackground(Color.GREEN);
        clear.setBackground(Color.RED);

        // Add operators and equals button
        buttonPanel.add(add);
        buttonPanel.add(sub);
        buttonPanel.add(multiply);
        buttonPanel.add(divide);
        buttonPanel.add(equals);
        buttonPanel.add(clear);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("C")) {
            t1.setText("");
        } else if (e.getActionCommand().equals("=")) {
            try {
                String expression = t1.getText();
                Double result = Evaluate(expression);
                t1.setText(String.valueOf(result));
            } catch (NumberFormatException | ArithmeticException ex) {
                t1.setText("Error");
            }
        } else {
            String button = e.getActionCommand();
            String tf = t1.getText() + button;
            t1.setText(tf);
        }
    }

    private Double Evaluate(String expression) {
        // Split the expression into tokens (numbers and operators)
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");

        // Stack to store numbers
        Stack<Double> numbers = new Stack<>();

        // Stack to store operators
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[0-9.]+")) {  // If the token is a number
                numbers.push(Double.parseDouble(token));
            } else if (token.matches("[+*/-]")) {  // If the token is an operator
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    Double b = numbers.pop();
                    Double a = numbers.pop();
                    String op = operators.pop();
                    numbers.push(applyOperator(a, b, op));
                }
                operators.push(token);
            }
        }

        // Apply remaining operations
        while (!operators.isEmpty()) {
            Double b = numbers.pop();
            Double a = numbers.pop();
            String op = operators.pop();
            numbers.push(applyOperator(a, b, op));
        }

        return numbers.pop();
    }

    private int precedence(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    private Double applyOperator(Double a, Double b, String op) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}
