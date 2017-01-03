import java.io.*;
public class BackEnd
{
	public String analyseurlindex(int t)
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("./urlindex.txt"));
			String line = br.readLine();
			while((line = br.readLine()) != null)
			{
				int y = 0;
		    	while(line.charAt(y) != ' ')
		    		y++;
		    	String s1 = line.substring(0,y);
		    	int z = Integer.parseInt(s1);
		    	if(z == t)
		    	{
		    		String s2 = line.substring(y+1);
		    		br.close();
		    		return s2;
		    	}
			}
			br.close();
		}
		catch(Exception e)
		{
		}
		return null;
}
	public String analysefile(File file)
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = new String();
			while((line = br.readLine()) != null)
				sb.append(line + "\n");
			br.close();
		}
		catch(Exception e)
		{
		}
		return sb.toString();
	}
	public String[] analysequery(String query)
	{
		String[] str,keywords;
		int count = 1;
		for(int i=0;i<query.length();i++)
			if(query.charAt(i) == ' ')
				count++;
		keywords = new String[count];
		int z = 0;
		for(int i=0;i<count;i++)
		{
			StringBuilder sb  = new StringBuilder();
			int j=z;
			while(query.charAt(j) != ' ')
			{
				sb.append(query.charAt(j));
				if(j == query.length()-1)
					break;
				j++;
			}
			z = j+1;
			keywords[i] = new String(sb.toString());
		}
		StringBuilder build = new StringBuilder();
		count = 0;
		for(String i : keywords)
		{
			int c = (int)i.charAt(0),index;
			if(c>=48 && c<=57)
				index = c-48;
			else if(c>=97 && c<=122)
				index = c-87;
			else
				index = 36;
			BtreeNode bn = AccessFile.btreerootarray[index].search(i);
			if(bn != null)
			{
				count++;
				File file = null;
				int m = 0;
				while (m < bn.count && bn.keys[m].keyword.compareTo(i)<0)
				    m++;
				if(m<2*bn.t-1 && bn.keys[m].keyword != null)
				if (bn.keys[m].keyword.compareTo(i) == 0)
				    file = bn.keys[m].pointer;
				if(file != null)
				{
					String onefile = analysefile(file);
					build.append(onefile);
				}
			}
		}
		String[] lines = build.toString().split("\\n");
		int var = 0,size = lines.length-count,sum=0,temp = 0;
		IndexWeightNode iwn = new IndexWeightNode(size);
		IndexWeightNode iwninter = new IndexWeightNode(size);
		double idf = 0,total = 0;
		File file = new File("./urlindex.txt");
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			int y = 0;
	    	while(line.charAt(y) != ' ')
	    		y++;
	    	String s1 = line.substring(0,y);
	    	total = Integer.parseInt(s1);
	    	br.close();
	    	for(String s: lines)
			{
	    		if(var == 0)
					sum += temp;
				int x = 0;
		    	while(s.charAt(x) != ' ')
		    		x++;
		    	s1 = s.substring(0,x);
		    	String s2 = s.substring(x+1);
		    	int e = Integer.parseInt(s1);
				double d = Double.parseDouble(s2);
			    if(var == 0)
			    {
			    	idf = Math.log10(total/e);
			    	var = e;
			    	temp = e;
			    }
			    else
			    {
			    	int m = 0;
	    			for(int k=0;k<iwninter.count;k++)
	    				if(e == iwninter.index[k])
	    				{
	    					m=1;
	    					iwninter.weight[k] += idf * d;
	    					break;
	    				}
	    			if(m==0)
	    			{
	    				int xy = 0;
	    				for(int j=0;j<sum;j++)
	    					if(iwn.index[j] == e)
	    					{
	    						iwninter.index[iwninter.count] = e;
	    						iwninter.weight[iwninter.count] = d * idf + iwn.weight[j];
	    						iwninter.count++;
	    						xy=1;
	    						break;
	    					}
	    				if(xy == 0)
	    				{
	    					iwn.index[iwn.count] = e;
	    					iwn.weight[iwn.count] = d * idf;
	    					iwn.count++;
	    				}
	    			}
			    	var--;
			    }
			}
	    	Mergesort.sort(iwninter,0,iwninter.count-1);
	    	Mergesort.sort(iwn,0,iwn.count-1);
			str = new String[iwn.count];
			int j=0;
			System.out.println("\n\n\nWith more than 1 keyword Pages : ");
			for(int i=0;i<iwninter.count;i++)
			{
				str[j] = analyseurlindex(iwninter.index[i]);
				System.out.println(str[j]);
				j++;
			}
			System.out.println("\n\n\nWith 1 keyword Pages : ");
			for(int i=0;i<iwn.count;i++)
			{
				int m = 0;
    			for(int k=0;k<iwninter.count;k++)
    				if(iwn.index[i] == iwninter.index[k])
    				{
    					m=1;
    					break;
    				}
    			if(m==0)
    			{
    				str[j] = analyseurlindex(iwn.index[i]);
    				System.out.println(str[j]);
    				j++;
    			}
			}
			return str;
		}
		catch(Exception f)
		{
		}
		return null;
	}
	public void analyseurl(String url)
	{
		String t;
		ZoneIndexing table = new ZoneIndexing();
		String ind = AccessFile.urlindexfile(url);
		if(ind != null)
		{
			try
			{
				t = GetSrcPage.getsrc(url);
				AccessFile.makesrcfile(ind, t);
				table.computeratios(t);
				AccessFile.zoneindexfiles(table,ind);
				GivingWeights.assign(t,ind);
			}
			catch(Exception e)
			{
			}
		}
	}
}