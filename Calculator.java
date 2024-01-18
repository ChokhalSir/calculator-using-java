package fourth;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class Calculator extends JFrame implements ActionListener {
    JButton btn[] = new JButton[10];
    // Initialize the array
    JTextField t1;
    JButton add,sub,divide,multiply,equals,clear;

    public Calculator() {
        setSize(200, 300);
        setLayout(new FlowLayout()); // Set the layout before adding components

        t1 = new JTextField(14);
        t1.setEditable(false);
        add(t1);

        for (int i = 0; i <= 9; i++) {
            sub = new JButton(Integer.toString(i)); // Use Integer.toString(i) to set the button label
            add(sub);
            sub.addActionListener(this);
        }
        add= new JButton("+");
        sub= new JButton("-");
        divide= new JButton("/");
        multiply = new JButton("*");
        equals = new JButton("=");
        clear = new JButton("clear");
        
        add.addActionListener(this);
        sub.addActionListener(this);
        multiply.addActionListener(this);
        equals.addActionListener(this);
        divide.addActionListener(this);
        clear.addActionListener(this);
        add(add);
        add(sub);
        add(multiply);
        add(divide);
        add(equals);
        add(clear);
        add.setBackground(Color.LIGHT_GRAY); 
        sub.setBackground(Color.LIGHT_GRAY); 
        divide.setBackground(Color.LIGHT_GRAY); 
        multiply.setBackground(Color.LIGHT_GRAY); 

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator();
        });
    }

    
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals("clear")) {
    	   t1.setText("");	
    	}
    	else if(e.getActionCommand().equals("=")) {
  		try { 
  			
  			String Expression= t1.getText();
  			Double Result = Evaluate( Expression);
  		
  			t1.setText(String.valueOf(Result));
  			
  				
  					
  					
  		}
  		catch (NumberFormatException | ArithmeticException ex) {
            t1.setText("Error");
        }
  	}
  	else
  		{String Button= e.getActionCommand();
    	String tf=   t1.getText() +Button;
         t1.setText(tf);
       
}
  	
}

	private Double  Evaluate(String Expression) {
		String[] token = Expression.split("(?<=\\d)(?=[+*/-])|(?<=[+*/-])(?=\\d)");
		double a= Double.parseDouble(token[0]);
		String op =token[1];
		double b= Double.parseDouble(token[2]);
	
		
		if(t1.getText().equals(Expression)) {
			
		
	
		
			switch(op) {
		
		case "+": 
			return a+b;
			
		case "-": 
			return a-b;
		case "*":
			return a*b;
		case "/": 
			if (b == 0) {
                throw new ArithmeticException("Cannot divide by zero");
            }
            return a /b ;
        default:
            throw new IllegalArgumentException("Invalid operator");
		}
		
	}
		else{
			
			Double Result = Evaluate(Expression);
            return Result;
		
		}
	}
	
}
