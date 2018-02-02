
public class sumTester {

	public static void main(String[] args) {
		
		//1-50
		int sum1 = 0;
		for (int i=0; i<=50; i++)
		{
			sum1 = sum1 + i;
		}
		System.out.println("Sum from 1 to 50: " + sum1);
		
		int sum2 = 0;
		for (int i=150; i<=450; i++)
		{
			sum2 = sum2 + i;
		}
		System.out.println("Sum from 150 to 450: " + sum2);
		
		sumTester sm = new sumTester();
		
		int sum3 = sm.sumMeth(40,160);
		System.out.println("Sum from 150 to 450 via method: " + sum3);
		
		
		SumNums sn = new SumNums(150,450);
		SumNums sn2 = new SumNums(185,315);
		
		System.out.println("Sum from 40 to 160 via class: " + sn.fullSum());
		System.out.println("skipSum from 40 to 160 via class: " + sn.skipSum());
		
		System.out.println("Sum from 185 to 315 via class: " + sn2.fullSum());
		System.out.println("skipSum from 185 to 315 via class: " + sn2.skipSum());
		
		
		
	}
	
	public int sumMeth(int low, int high)
	{
		int sum1 = 0;
		for (int i=low; i<=high; i++)
		{
			sum1 += i;
		}
		
		return sum1;
	}

}
