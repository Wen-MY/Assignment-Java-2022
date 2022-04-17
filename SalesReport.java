

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SalesReport extends Report {
	private double total_sales=0;
	private ArrayList <Receipt> sales;
	public SalesReport(Date date,ArrayList <Receipt> sales)
	{
		super(date);
		this.sales=sales;
	}
	public SalesReport(Date date1,Date date2,ArrayList <Receipt> sales)
	{
		super(date1,date2);
		this.sales=sales;
	}
	public void CreateReport()
	{
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		for( int i=0;i<this.sales.size();i++)
		{
			Date sales_date = new Date(sales.get(i).getDate().getTime());
			if(date2==null)
			{
				if((sdfrmt.format(sales_date).equals(sdfrmt.format(date))))
				{
					total_sales+=sales.get(i).getGrandTotal();
				}
				
			}
			else
			{
				if((sales_date.after(date) && sales_date.before(date2)||(sdfrmt.format(sales_date).equals(sdfrmt.format(date))||sdfrmt.format(sales_date).equals(sdfrmt.format(date2)))))
				{
					System.out.println((sdfrmt.format(sales_date))+"|"+sales.get(i).getGrandTotal());
					total_sales+=sales.get(i).getGrandTotal();
				}
				
			}
		}
		if(date2==null)
			System.out.println("Total Sales for "+(sdfrmt.format(date)) + " is " + total_sales);
		else
			System.out.println("Total Sales from "+(sdfrmt.format(date)) +" to "+(sdfrmt.format(date2)) + " is " + total_sales);
	}
	
	
	
}
