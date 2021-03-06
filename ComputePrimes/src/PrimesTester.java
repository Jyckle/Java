import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PrimesTester 
{

	public static void main(String[] args) 
	{
		int numPrimes = 25;
		
//		if (args.length > 0)
//		{
//			numPrimes = Integer.parseInt(args[0]);
//		}
//		else
//		{
//			numPrimes = 25;
//		}
		
		
//		Scanner in = new Scanner(System.in); 
		Scanner in;
		try {
			in = new Scanner(new FileReader ("input.txt"));
			numPrimes = in.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		System.out.print("How many primes? ");
		
		System.out.println("Generating " + numPrimes + " primes");

		ComputePrimes cp = new ComputePrimes(numPrimes);
		
		System.out.println ("ComputePrimes toString() output: " + cp);
		
		System.out.println("is 97 prime? " + cp.isprime(97));
		System.out.println("is 1000001 prime? " + cp.isprime(1000001));

		//cp.isprime(1000001);
	}
	
}
