
public class SumNums {

	private int low, high;
	public SumNums(int l, int h) {
		low = l;
		high = h;
	}

	public int fullSum()
	{
		int sum1 = 0;
		for (int i=low; i<=high; i++)
		{
			sum1 += i;
		}
		
		return sum1;
	}

	public int skipSum()
	{
		int sum2 = 0;
		for (int i=low; i <= high; i = i+2)
		{
			sum2 += i;
		}
		
		return sum2;
	}
}

