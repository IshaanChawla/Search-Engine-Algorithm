import java.util.Scanner;
public class FrontEnd 
{
	public String search()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Query : ");
		String input = s.nextLine();
		s.close();
		return input;
	}
	public void display(String[] str)
	{
		for(String i : str)
			System.out.println(i);
	}
}