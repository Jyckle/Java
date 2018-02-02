import java.util.*;

public class ComputePrimes 
{	
	private ArrayList<Integer> primes;
	private final static boolean debug = false;

	public ComputePrimes(int numPrimes)
	{
		primes = new ArrayList<Integer>();
		
		int i = 0;
		int num = 2;
		
		primes.add(num);
		print_debug("Adding " + num);
		
		num++;
		i++;
		
		while(i <numPrimes)
		{
			if (isprime(num))
			{
				primes.add(num);
				print_debug("Adding " + num);
				i++;
			}
			num++;
		}
		
//		//print primes array
//		System.out.print("primes[] = ");
//		for (i =0; i<primes.size(); i++)
//		{
//			System.out.print(" " + primes.get(i));
//		}
//		
//		System.out.println();

	}

	public String toString()
	{
		String outstr = "";
		
		outstr += "primes[] = ";
		for (int i = 0; i < primes.size(); i++)
			outstr += " " + primes.get(i);
		
		return outstr + "\n";
	}

	public boolean isprime (int n)
	{
		//isprime checks whether the value is prime and returns true or false accordingly

		for (int i = 0; primes.get(i) <= Math.sqrt(n); i++)
		{
			print_debug("Checking " + primes.get(i));

			
			if (n % primes.get(i) == 0)
				return false;	
		}		

		return true;
	}
	
	private void print_debug(String s)
	{
		if (debug)
			System.out.println(s);
	}

}
