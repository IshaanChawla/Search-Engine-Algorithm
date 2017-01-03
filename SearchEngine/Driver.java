import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Multi1 extends Thread
{
	public void run()
	{
		FrontEnd fe = new FrontEnd();
		String input = fe.search();
		
		BackEnd be = new BackEnd();
		String[] result = be.analysequery(input);
			
		//fe.display(result);
	}
}

class Multi2 extends Thread
{
	public void run()
	{
		BackEnd be = new BackEnd();
		try
		{
			File file = new File("./crawler/url.txt");
			if(file.exists() && !file.isDirectory())
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				while( (line = br.readLine()) != null)
					be.analyseurl(line);
				br.close();
			}
		}
		catch(Exception e)
		{
		};
	}
}
public class Driver
{
	public static void main(String[] args)
	{
		AccessFile.makebtrees();
		
		Multi1 m1 = new Multi1();
		//Multi2 m2 = new Multi2();
		
		m1.start();
		//m2.start();
	}
}