package Concept_View;
import java.io.Serializable;
import java.util.HashMap;
import java.sql.Timestamp;
public class Sales implements Serializable{

	private static final long serialVersionUID = 1L;
	private int Sales_ID;
	private HashMap<Product,Integer> Item = new HashMap<Product,Integer>();
	private double total;
	private Timestamp timestamp;
	
	public int getSalesID()
	{
		return Sales_ID;
	}
	public HashMap<Product,Integer> getItem()
	{
		return Item;
	}
	public double getTotal()
	{
		return total;
	}
	public Timestamp getTime()
	{
		return timestamp;
	}
	
	
	
	public Sales(int Sales_ID,HashMap<Product,Integer> Item, double total)
	{
		this.Sales_ID = Sales_ID;
		this.Item =Item;
		this.total =total;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	@Override
	public String toString()
	{
		return "Sales ID: "+Sales_ID+"\tTotal: "+total+"\nItem Purchased :\n"+Item.keySet()+"\nTime: "+timestamp;
	}
}
