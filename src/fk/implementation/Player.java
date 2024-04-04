package fk.implementation;

public class Player {
	private int saldo;
	String name;
	
	public Player(String name, int saldo) {
		this.saldo = saldo;
		this.name = name;
	}
	
	public Player(String name) {
		this.saldo = 0;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getSaldo() {
		return saldo;
	}
	
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	public void deposit(int amount) {
		this.saldo += amount;
	}
	
	private boolean canWithdraw(int amount) {
		return amount <= this.saldo;
	}
	
	public boolean withdraw(int amount) {
		if (canWithdraw(amount)) {
			this.saldo -= amount;
			return true;
		}
		return false;
			
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName() + " has SEK" + getSaldo() + " available";
	}
	
	public static void main(String[] args) {
		Player p = new Player("Olof", 100);
		
		System.out.println("Expected: 100 (no-op)");
		System.out.println(p);
		System.out.println();
		
		p.withdraw(50);
		System.out.println("Expected: 50 (-50)");
		System.out.println(p);
		System.out.println();
		
		System.out.println("Expected: 50 (overdraw, denied");
		p.withdraw(100);
		System.out.println(p);
		System.out.println();

		p.deposit(200);
		System.out.println("Expected: 250 (+200)");
		System.out.println(p);
		System.out.println();
	}
}
