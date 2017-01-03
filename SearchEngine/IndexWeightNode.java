public class IndexWeightNode 
{
	public int[] index;
	public int count = 0;
	public int n;
	public double[] weight;
	public IndexWeightNode(int e)
	{
		n = e;
    	for(int y=0;y<e;y++)
    	{
    		index = new int[e];
    		weight = new double[e];
    	}
	}
}