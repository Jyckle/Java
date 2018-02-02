
public class HelloWorld {
	public static void main (String[] args)   // like c++, static means it is independent of instance (only one copy can exist
	{
		String hello = "Hello, World";

		hello = hello.replaceFirst("World", args[0]);

		System.out.println(hello);
		//System.out.println("Hi, " + args[0]);
	}
}
