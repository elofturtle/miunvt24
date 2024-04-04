package fk.examples;
import java.util.Scanner;

public class SimpleCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the first value
        System.out.print("Enter the first value: ");
        double firstValue = scanner.nextInt();

        // Prompt the user to enter the second value
        System.out.print("Enter the second value: ");
        double secondValue = scanner.nextInt();

        System.out.println("First value entered: " + firstValue);
        System.out.println("Second value entered: " + secondValue);

        
        System.out.print("Operation +-/* ?");
        String operation = scanner.next();
        
        switch(operation) {
        case "+":
        	System.out.println((firstValue + secondValue));
        	break;
        case "-":
        	System.out.println((firstValue - secondValue));
        	break;
        case "*":
        	System.out.println((firstValue * secondValue));
        	break;
        case "/":
        		if(secondValue != 0.0)
        			System.out.println((firstValue / secondValue));
        		else 
        			System.out.println("Diviion by zero not allowed");
        	
        	break;
        	default:
        		System.out.println("Fel val");
        		break;
        		
        }
        
        scanner.close();
    }
}
