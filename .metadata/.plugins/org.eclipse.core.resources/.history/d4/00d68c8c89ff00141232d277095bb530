public class GivingWeights 
{
	public static void assign(String t,String ind)
	{
		ZoneIndexing table = new ZoneIndexing();
		String[] str,str1;
		StringSensitivity ss = new StringSensitivity();
		AfterSearch as = new AfterSearch();
		try
		{
			table.fetchvalues(ind);
			int index = Kmp.kmpfirstindexsearch(t,"meta name=\"keywords\" content=\"");
			if(index>0)
			{
				str = as.keywordretrival(t,index,'\"',',');
				str = ss.lowercase(str);
				for(String i : str)
				{
					double count = Kmp.kmpsearch(t,i,table);
					if(count>0)
						AccessFile.addkeyword(i,count,ind);
				}
			}
			else
				str = null;
			int index1 = Kmp.kmpfirstindexsearch(t,"<title>");	
			if(index1>0)
			{
				str1 = as.keywordretrival(t,index1,'<',' ');
				str1 = ss.lowercase(str1);
				int indi = 0;
				for(String i : str1)
				{
					int k=0,l=0;
					if(index>0)
						for(String j : str)
						{
							if(ss.matchstring(i,j))
								k++;
						}
					int indj = 0;
					for (String j : str1)
					{
						if(ss.matchstring(i,j) && indi < indj)
							l++;
						indj++;
					}
					if(k == 0 && i.length()>=4 && l == 0)
					{
						double count = Kmp.kmpsearch(t,i,table);
						if(count>0)
							AccessFile.addkeyword(i,count,ind);
					}
					indi++;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		};
	}
}