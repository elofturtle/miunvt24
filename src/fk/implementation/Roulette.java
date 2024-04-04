package fk.implementation;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;


// https://www.pokernews.com/casino/roulette/difference-american-european-roulette.htm

public class Roulette {

	private static Random random;
	private Scanner scanner;
	private int currentValue;
	
	private static final int[] reds = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};	
	private static final int[] blacks = {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
	private static final int min = 0;
	private static final int max = 36;
	
	private static final int rewardStraight = 36; //gissa rätt tal
	private static final int rewardEven = 2; // udda eller ojämnt;
	private static final int rewardColour = 2; // rätt färg
	
	public Roulette() {
		random = new Random();
		scanner = new Scanner(System.in);
		currentValue = -1;
	}
	
	//helper
	private static boolean inRange(int value) {
		
		if (value >= min && value <= max) {
			return true;
		}
		else {
			throw new IllegalArgumentException("Value " + value + " is not between" + min + " and " + max + "!");
		}
	}
	
	/* Inside bets
	 * straight - single number
	 * split - two numbers - simplified trust user input
	 * street - three numbers - simplified trust user input
	 * corner - four numbers - simplified trust user input
	 * first four - 0,1,2,3
	 * sixline - six numbers - simplified trust user input
	*/
	// End inside bets
	
/*	Outside bets
 * dozen - twelve numbers
 * column - twelve numbers
 * low/high
 * red/black
 * even/odd
 */
	
	//End outside bets
	
	
	
	private static boolean isEven(int value) {
		inRange(value);
		return (value % 2 == 0) && !isNaught(value);
	}
	
	private static boolean isOdd(int value) {
		return !isEven(value) && !isNaught(value);
	}
	
	private static boolean isNaught(int value){
		return value == 0;
	}
	
	//helper
	private static boolean isInArray(int value, int[] array) {
		return Arrays.asList(array).contains(value);
	}
	
	//{[1-12], [13-24], [25-36]}
	private static boolean isInDozen(int value, int dozen) {
		inRange(value);
		if (dozen <=3 && dozen >= 1) {
			if( value > (dozen -1) * 12 && value <= dozen * 12)
			return true;
		}
		return false;
	}
	
	// respektive kolumn [1,4,,...] = var tredje sifra i nummerordning eftersom de är indelade i rader {[1,2,3],[4,5,6], ...}
	private static boolean isInColumn(int value, int column) {
		inRange(value);
		if (column == 3) {
			column = 0; // 3%3 = 0;
		}
		if(column <= 2 && column >= 0) {
			return value % 3 == column ;
		}
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
