
public class Liebniz {
	public static void main(String[] args) {
		
		int acc = 50;
		double piApprox = 0.0;
		for (int i = 0; i< acc; i++)
		{
			double next = ((-1)^i) * 1/(2.0*i + 1.0);
			piApprox += next;
			System.out.println(piApprox);
		}
		
		piApprox = piApprox * 4;
		
		System.out.println("Pi is roughly equal to: " + piApprox + " with " + acc + " iterations");
	}
}
