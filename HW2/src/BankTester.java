import java.util.*;

public class BankTester {

	public static void main(String[] args) {
		ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
		accounts.add(new BankAccount("John", 0));
		accounts.add(new BankAccount("Brian", 125000));
		accounts.add(new BankAccount("Scott", 69000));
		accounts.add(new BankAccount("Luke", 1500000));
		
		Collections.sort(accounts);
		for (BankAccount a: accounts) {
			System.out.println(a.getName() + ": " + a.getBalance());
		}
	}

}
