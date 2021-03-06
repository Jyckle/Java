import java.util.*;

public class ComputePrimes 
{	
	private ArrayList<Integer> primes;
	private final static boolean debug = false;

	public ComputePrimes(int numPrimes)
	{
		primes = new ArrayList<Integer>();

		computeFirstPrimes(numPrimes);

	}


	private void computeFirstPrimes(int numPrimes)
	{
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

	}

	private int getLastPrime()
	{
		return primes.get(primes.size()-1);
	}

	private void computeNextPrimes(int upto)
	{
		int num = getLastPrime();

		while(num <= upto)
		{
			if (isprime(num))
			{
				primes.add(num);
				print_debug("Adding " + num);
			}
			num++;
		}

		//find one prime past upto
		while(true)
		{
			if (isprime(num))
			{
				primes.add(num);
				print_debug("Adding " + num);
				break;
			}
			num++;
		}

	}

	public String toString()
	{
		String outstr = "";

		outstr += "primes[] = ";
//		for (int i = 0; i < primes.size(); i++)
//			outstr += " " + primes.get(i);
		
		for (Integer p: primes)
			outstr += " " + p;


		return outstr + "\n";
	}

	public boolean isprime (int n)
	{
		//isprime checks whether the value is prime and returns true or false accordingly

		if (Math.sqrt(n) > getLastPrime())
			computeNextPrimes ((int) Math.sqrt(n));
			
		for (int i = 0; primes.get(i) <= Math.sqrt(n); i++)
			{
				print_debug("Checking " + primes.get(i));


				if (n % primes.get(i) == 0)
					return false;	
			}			

		return true;
	}
	
	public int get (int i)
	{
		if (i >= 0 && i <primes.size())
			return primes.get(i);
		else 
			return -1;
	}

	private void print_debug(String s)
	{
		if (debug)
			System.out.println(s);
	}

}
