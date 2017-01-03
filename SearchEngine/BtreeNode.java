import java.io.*;
public class BtreeNode 
{
	public KeyNode[] keys;					
	public BtreeNode[] child;
	public int count = 0;
	public boolean leaf;
	int t;
	public BtreeNode(int t,boolean leaf)
	{
		this.t = t;
		keys = new KeyNode[2*this.t-1];
		for(int i=0;i<2*this.t-1;i++)
			keys[i] = new KeyNode();
		child = new BtreeNode[2*this.t];
		this.leaf=leaf;
	}
	public void splitchild(int i, BtreeNode y)
	{
		BtreeNode z = new BtreeNode(t,y.leaf);
		z.count = t-1;
		for(int j=0;j<t-1;j++)
		{
			z.keys[j].keyword = y.keys[j+t].keyword;
			z.keys[j].pointer = y.keys[j+t].pointer;
		}
		if(!y.leaf)
		{
			for(int j=0;j<t;j++)
				z.child[j] = y.child[j+t];
		}
		y.count = t-1;
		for(int j=count;j>=i+1;j--)
			child[j+1] = child[j];
		child[i+1] = z;
		for(int j=count-1;j>=i;j--)
		{
			keys[j+1].keyword = keys[j].keyword;
			keys[j+1].pointer = keys[j].pointer;
		}
		keys[i].keyword = y.keys[t-1].keyword;
		keys[i].pointer = y.keys[t-1].pointer;
		count++;
	}
	public void insertNonFull(String kw,File f)
	{
		int i = count-1;
		if(leaf)
		{
			while(i>=0 && keys[i].keyword.compareTo(kw)>0)
			{
				keys[i+1].keyword=keys[i].keyword;
				keys[i+1].pointer=keys[i].pointer;
				i--;
			}
			keys[i+1].keyword = kw;
			keys[i+1].pointer = f;
			count++;
		}
		else
		{
			while(i>=0 && keys[i].keyword.compareTo(kw)>0)
				i--;
			if(child[i+1].count == 2*t - 1)
			{
				splitchild(i+1,child[i+1]);
				if(keys[i+1].keyword.compareTo(kw)<0)
					i++;
			}
			child[i+1].insertNonFull(kw,f);
		}
	}
	public BtreeNode search(String kw)
	{
		int i = 0;
		while (i < count && keys[i].keyword.compareTo(kw)<0)
			i++;
		if(i<2*t-1 && keys[i].keyword != null)
	    	if (keys[i].keyword.compareTo(kw) == 0)
	    		return this;
	    if (leaf == true)
	        return null;
	    return child[i].search(kw);
	}
	public void traversal()
	{
	    int i;
	    for (i = 0; i < count; i++)
	    {
	        if (leaf == false)
	            child[i].traversal();
	        System.out.println(keys[i].keyword);
	    }
	    if (leaf == false)
	        child[i].traversal();
	}
}