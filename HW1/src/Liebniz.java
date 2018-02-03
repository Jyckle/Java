
public class Liebniz {
	public static void main(String[] args) {
		
		int acc = 1000;
		double piApprox = 0.0;
		for (double i = 0; i< acc; i++)
		{
			double next = Math.pow(-1.0,i) * 1/(2.0*i + 1.0);
			piApprox += next;
		}
		
		piApprox = piApprox * 4;
		
		System.out.println("Pi is roughly equal to: " + piApprox + " with " + acc + " iterations");
	}
}
