public class Kmp
{
	private static void computelps(int lps[],int m,String pat)
	{
		int len = 0,i = 1;
		lps[0] = 0;
		while(i < m)
		{
			if(pat.charAt(i) == pat.charAt(len))
				lps[i++]=++len;
			else
			{
				if(len != 0)
					len = lps[len-1];
				else
				{
					lps[i]=0;
					i++;
				}
			}
		}
	}
	public static int kmpfirstindexsearch(String text,String pattern)
	{
		int m = pattern.length(),n = text.length(),i=0,j=0;
		int[] lps = new int[m];
		computelps(lps,m,pattern);
		while(i<n)
		{
			if(pattern.charAt(j) == text.charAt(i))
			{
				i++;
				j++;
			}
			else
			{ 
				if(j != 0)
					j = lps[j-1];
				else
					i++;
			}
			if(j == m)
				return i;
		}
		return -1;
	}
	public static double kmpsearch(String text,String pattern,ZoneIndexing table)
	{
		int m = pattern.length(),n = text.length(),i=0,j=0;
		double zoneweight=0,weight=0;
		int[] lps = new int[m];
		computelps(lps,m,pattern);
		StackSb stk = new StackSb();
		StringBuilder s = new StringBuilder();
		while(i<n)
		{
			if(text.charAt(i) == '<')
			{
				while(text.charAt(i) != '>')
				{
					if(text.charAt(i) == '<')
					{
						stk.push(s);
						s = new StringBuilder();
					}
					s.append(text.charAt(i));
					i++;
				}
				s.append(text.charAt(i));
				if(s.toString().contains("</"))
				{
					s = stk.pop();
					if(s == null)
						zoneweight = 0;
					else
						zoneweight = table.getzoneweight(s.toString());
				}
				else
					zoneweight = table.getzoneweight(s.toString());
			}
			if(pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) - 32 == text.charAt(i))
			{
				i++;
				j++;
			}
			else
			{
				if(j != 0)
					j = lps[j-1];
				else
					i++;
			}
			if(j == m)
			{
				weight += zoneweight; 
				j = lps[j-1];
			}
		}
		return weight;
	}
	public static int kmpzonesearch(String text,String pattern)
	{
		int m = pattern.length(),n = text.length(),i=0,j=0,count=0;
		int[] lps = new int[m];
		computelps(lps,m,pattern);
		AfterSearch as = new AfterSearch();
		while(i<n)
		{
			if(pattern.charAt(j) == text.charAt(i))
			{
				i++;
				j++;
			}
			else
			{ 
				if(j != 0)
					j = lps[j-1];
				else
					i++;
			}
			if(j == m)
			{
				int x = i - 1;
				while(text.charAt(x)!='>')
					x++;
				int end = x+1;
				while(!(text.charAt(end) == '<' && text.charAt(end+1) == '/' && text.charAt(end+2) == pattern.charAt(1)))
					end++;
				count += as.keywordcountretrival(text,x,end,' ');
				j = lps[j-1];
			}
		}
		return count;
	}
}