
public class BankAccount implements Comparable<BankAccount> {
	
	//declare instance variables
	private String name;
	private double balance;
	
	
	public BankAccount(String name, double balance) {
		this.name = name;
		this.balance = balance;		
	}
	
	public String getName() {
		return name;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public int compareTo(BankAccount other) {
		if (balance < other.balance) return -1;
		if (balance > other.balance) return 1;
		return 0;
	}
}
