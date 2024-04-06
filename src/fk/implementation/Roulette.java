package fk.implementation;

import java.util.Random;
import java.util.Scanner;

enum Bet{
	/*
	 *   ----------
	 * 	 |  | 0|  |   1st four 
	 *   |1 |2| 3 |   1st dozen; low
	 *   |4 |5 |6 |
	 *   |7 |8 |9 |
	 *   ...
	 *   |34|35|36| 3rd dozen; high
	 *   ----------
	 *   col1, col2, col3
	 */
	STRAIGHT,		// guess single number
	FIRST_FOUR,		// 0,1,2,3
	DOZEN,			// falls within 1st, 2nd, or 3rd dozen
	COLUMN,			// 
	LOW_HIGH,
	RED_BLACK,
	EVEN_ODD;
	
	int reward() {
		switch (this) {
			case STRAIGHT:
				return 36;
			case RED_BLACK:
			case EVEN_ODD:
			case LOW_HIGH:
				return 2;
			case DOZEN:
			case COLUMN:
				return 3;
			case FIRST_FOUR:
				return 9;
			default: 
				return 0;
		}
	}
}

// https://www.pokernews.com/casino/roulette/difference-american-european-roulette.htm

public class Roulette {
	
	private static Random random = new Random();
	private static Scanner scanner = new Scanner(System.in);
	Bet bet;
	int ball;
	Player player;
	
	public Roulette(Player player) {		
		bet = null;
		ball = throwBall();
		this.player = player;
		gameMenu();
	}
	
	public Roulette(Player player, Bet bet) {
		this.bet = bet;
		ball = throwBall();
		this.player = player;
		gameMenu();
	}
	
	private String drawRouletteTable(int ballValue) {
        String[] wheelNumbers = {"0", "32", "15", "19", "4", "21", "2", "25", "17", "34",
                                 "6", "27", "13", "36", "11", "30", "8", "23", "10", "5",
                                 "24", "16", "33", "1", "20", "14", "31", "9", "22", "18",
                                 "29", "7", "28", "12", "35", "3", "26"};

        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("  _______________________________________________\n");
        tableBuilder.append(" /                                               \\\n");
        tableBuilder.append("/         [ 3]   [ 6]   [ 9]  [ 12]  [ 15]         \\\n");
        tableBuilder.append("|           |     |     |     |     |              |\n");
        tableBuilder.append("|  0        |     |     |     |     |   0          |\n");
        tableBuilder.append("|     _____ |     |     |     |     |  ____        |\n");
        tableBuilder.append("|    |     ||_____|_____|_____|_____||    |       |\n");
        tableBuilder.append("|    |     ||     |     |     |     ||    |       |\n");
        tableBuilder.append("|    |     ||  2  |  5  |  8  |  11 ||    |       |\n");
        tableBuilder.append("|    |     ||_____|_____|_____|_____||    |       |\n");
        tableBuilder.append("|    |     ||     |     |     |     ||    |       |\n");
        tableBuilder.append("|    |     ||  1  |  4  |  7  |  10 ||    |       |\n");
        tableBuilder.append("|    |     ||_____|_____|_____|_____||    |       |\n");
        tableBuilder.append("|    |_____|___________________________|____|       |\n");
        tableBuilder.append("|___________|___________________________|___________|\n");
        tableBuilder.append(" \\________________________/ \\______________________/\n");
        tableBuilder.append("          |  _______  |\n");
        tableBuilder.append("          | /       \\ |\n");
        tableBuilder.append("          |/         \\|\n");
        tableBuilder.append("          ||    o    ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||         ||\n");
        tableBuilder.append("          ||_________||\n");
        tableBuilder.append("          \\_________/\n");

        // Find the position of the ball on the wheel
        int position = -1;
        for (int i = 0; i < wheelNumbers.length; i++) {
            if (Integer.parseInt(wheelNumbers[i]) == ballValue) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            tableBuilder.append("The ball landed on ").append(ballValue).append(" (").append(position).append(" on the wheel).\n");
        } else {
            tableBuilder.append("The ball value is not on the wheel.\n");
        }
        
        tableBuilder.append("Bet type: " + bet + "\n");
        tableBuilder.append("Player: " + player);
        
        

        return tableBuilder.toString();
    }
	
	@Override
	public String toString() {
		return drawRouletteTable(ball);
	}
	
	private void gameMenu() {
		System.out.println("Welcome to Roulette!");
		System.out.println();
		
		int amount = 0;
		do {
			System.out.print("How much would you like to bet? ");
			amount = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
		}while(!player.canWithdraw(amount));
		player.withdraw(amount);
		
		
		
		if(bet == null) {
			chooseBet();
		}
		
		System.out.println("Fun will now commence.");
		
		boolean winner = false;
		
		switch (bet) {
		case COLUMN:
			winner = playColumn();
			break;
		case EVEN_ODD:
			winner = playEvenOrOdd();
			break;
			
		case DOZEN:
			winner = playDozen();
			break;
			
		case FIRST_FOUR:
			winner = ball >= 0 && ball <= 4;
			break;
			
		case LOW_HIGH:
			winner = playLowOrHigh();
			break;
			
		case RED_BLACK:
			winner = playRedOrBlack();
			break;
			
		case STRAIGHT:
			System.out.println("What is your number? ");
			System.out.println();
			winner = ball == scanner.nextInt();
			scanner.nextLine();
			break;

		default:
			System.out.println("ERR: Wrong choice :(");
			winner = false;
			break;
		}
		
		System.out.println(drawRouletteTable(ball));
		
		if(winner) {
			System.out.println();
			System.out.println("Yay, you won!");
			System.out.println("You won " + bet.reward() * amount + ".");
			player.deposit(bet.reward() * amount);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("You lost :( ");
			System.out.println("Playing " + bet + " result was " + ball + ".");
			System.out.println();
		}
	}

	private boolean playColumn() {
		boolean winner;
		int column;
		do {
			System.out.print("Bet on column (1,2,3): ");
			column = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
		} while(column < 1 && column > 3);
		winner = isInColumn(ball, column -1);
		return winner;
	}

	private boolean playEvenOrOdd() {
		boolean winner;
		String choice;
		do {
			System.out.print("Do you think it will be even or odd? ");
			choice = scanner.next();
			System.out.println();
		} while(choice != "even" && choice == "odd");
		
		if(choice == "even") {
			winner =  isEven(ball);
		} else {
			winner = isOdd(ball);
		}
		return winner;
	}

	private boolean playDozen() {
		boolean winner;
		int dozen = 0;
		do {
			System.out.print("Which dozen are you interested in (1,2,3)?");
			dozen = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
		} while(dozen < 1 && dozen > 3);
		winner = isInDozen(ball, dozen);
		return winner;
	}

	private boolean playLowOrHigh() {
		boolean winner;
		String lowOrHigh;
		do {
			System.out.print("High or low? ");
			lowOrHigh = scanner.next().toLowerCase();
			System.out.println();
		} while(lowOrHigh != "low" && lowOrHigh != "high");
		if(lowOrHigh == "low") {
			winner = isEven(ball);
		} else {
			winner = isOdd(ball);
		}
		return winner;
	}

	private boolean playRedOrBlack() {
		boolean winner;
		String redOrBlack;
		do {
			System.out.print("Red or black? ");
			redOrBlack = scanner.next().toLowerCase();
			System.out.println();
		} while(redOrBlack != "red" && redOrBlack != "black");
		if(redOrBlack == "red") {
			winner = isRed(ball);
		} else {
			winner = isBlack(ball);
		}
		return winner;
	}

	private void chooseBet() {
		System.out.println("Available betting types:");
		
		Bet [] bets = Bet.values();
		for(int i = 0; i < bets.length; i++) {
			System.out.println("(" + i + ") " + bets[i] + ".");
		}
		
		System.out.print("Enter the number of your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		if (choice < 0 || choice >= bets.length) {
            System.out.println("Invalid choice.");
        } else {
            bet = bets[choice];
            System.out.println("You chose: " + bet);
        }
	}
	
	private int throwBall() {
		final int min = 0;
		final int max = 36;
		int value;
		
		do {
			value = random.nextInt(0, max + 1);
			//scanner.nextLine();
		} while (value <= min || value >= max);
		return value;
	}
	
	private boolean isEven(int value) {
		return (value % 2 == 0) && !isNaught(value);
	}
	
	private boolean isOdd(int value) {
		return !isEven(value) && !isNaught(value);
	}
	
	private static boolean isNaught(int value){
		return value == 0;
	}
	
	private static boolean isInArray(int value, int[] array) {
		for (int i : array) {
			if (value == i)
				return true;
		}
		return false;
	}
	
	//{[1-12], [13-24], [25-36]}
	private boolean isInDozen(int value, int dozen) {
		if (dozen <=3 && dozen >= 1) {
			if( value > (dozen -1) * 12 && value <= dozen * 12)
			return true;
		}
		return false;
	}
	
	// respektive kolumn [1,4,,...] = var tredje sifra i nummerordning eftersom de är indelade i rader {[1,2,3],[4,5,6], ...}
	private  boolean isInColumn(int value, int column) {
		if (column == 3) {
			column = 0; // 3%3 = 0;
		}
		if(column <= 2 && column >= 0) {
			return value % 3 == column ;
		}
		return false;
	}
	
	private boolean isRed(int value) {
		final int[] reds = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};	
		return isInArray(value, reds);
	}
	
	private boolean isBlack(int value){
		final int[] blacks = {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,31,33,35};
		return isInArray(value, blacks);
	}
	
	//end helpers
	
	// bets
	
	
	public static void main(String[] args) {
		Bet b = Bet.DOZEN;		
		Player p = new Player("John Doe", 100);
		do {
			System.out.println("Playing as : " + p);
			Roulette game = new Roulette(p);
			System.out.println("Would you like to play some more? (yes/no) ");
			System.out.println();
		}while (p.getSaldo() > 0 && scanner.next().toLowerCase().equals("yes"));
		System.out.println("Thank you for playing!");
	}

}
