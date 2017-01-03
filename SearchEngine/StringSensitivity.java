public class StringSensitivity 
{
	public boolean matchstring(String a,String b)
	{
		int x;
		if(a == b)
			return true;
		if(a.equalsIgnoreCase(b))
			return true;
		if(a.length() == b.length()+1)
		{
			x=a.length();
			if(a.charAt(a.length()-1) != 's')
				return false;
		}
		else if(b.length() == a.length()+1)
		{
			x=b.length();
			if(b.charAt(b.length()-1) != 's')
				return false;
		}
		else
			return false;
		for(int i=0;i<x-1;i++)
		{
			int a_ascii = a.charAt(i), b_ascii = b.charAt(i);
			if(a_ascii != b_ascii && a_ascii != b_ascii - 32 && b_ascii != a_ascii - 32)
				return false;
		} 
		return true;
	}
	public String[] lowercase(String[] str)
	{
		int count = 0;
		for(@SuppressWarnings("unused") String i : str)
			count++;
		String[] str1 = new String[count];
		count = 0;
		for(String i : str)
		{
			for(int j=0;j<i.length();j++)
				if(i.charAt(j)>=65 && i.charAt(j)<=90)
					i = i.substring(0,j) + (char)((int)i.charAt(j) + 32) + i.substring(j+1);	
			str1[count++] = new String(i);
		}
		return str1;
	}
}