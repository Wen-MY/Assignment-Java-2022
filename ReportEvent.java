import java.util.Scanner;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportEvent {
	
	private ArrayList <Receipt> sales;
	private HashMap <Timestamp,String> userlogfile;
	Scanner input = new Scanner(System.in);
	public ReportEvent(ArrayList <Receipt> sales,HashMap <Timestamp,String> userlogfile)
	{
		this.sales=sales;
		this.userlogfile=userlogfile;
	}
	public void Event() {
		boolean loop=true;
		do{
		System.out.println("1.Sales Report\n2.Stock Report\n3.User Report\n0.Exit to main menu");
		try {
		int opt = Integer.parseInt(input.nextLine());
		if(opt!=0)
		{
			System.out.println("1.Today's Report\n2.Report By Date\n3.Report By Date Interval");
			int filter;
			String strdate=null;
			String strdate2=null;
			do{
				filter =Integer.parseInt(input.nextLine());
				if(filter ==1)
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
					strdate = dtf.format(LocalDate.now());
				}
				else if(filter==2)
				{	
					do{
					System.out.println("Date (Format must in DD/MM/YYYY):");
					strdate = input.nextLine();
					}while(!validateDate(strdate));
					
				}
				else if(filter==3)
				{
					do{
						System.out.println("From Date (Format must in DD/MM/YYYY):");
						strdate = input.nextLine();
						}while(!validateDate(strdate));
					do{
						System.out.println("To Date (Format must in DD/MM/YYYY):");
						strdate2 = input.nextLine();
						}while(!validateDate(strdate2));
				}
				else
					System.out.println("Invalid Option");
				}while(filter<1||filter>3);
			try{
			Date date=new SimpleDateFormat("dd/MM/yyyy").parse(strdate);
			switch(opt)
			{
			case 1://sales report
				if(strdate2==null)
				{
					SalesReport sales_rep = new SalesReport(date,sales);
					sales_rep.CreateReport();
					System.out.printf("\nPress Enter to continue...");
	                input.nextLine();
				}
				else
				{
					Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(strdate2);//only parse strdate2 to date format when it is not null
					SalesReport sales_rep = new SalesReport(date,date2,sales);
					sales_rep.CreateReport();
					System.out.printf("\nPress Enter to continue...");
	                input.nextLine();
				}
				break;
			case 2://stock report
				System.out.println("Invalid Option");
				break;
			case 3://user report
				if(strdate2==null)
				{
					UserReport user_rep = new UserReport(date,userlogfile);
					user_rep.CreateReport();
					System.out.printf("\nPress Enter to continue...");
	                input.nextLine();
				}
				else
				{
					Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(strdate2);//only parse strdate2 to date format when it is not null
					UserReport user_rep = new UserReport(date,date2,userlogfile);
					user_rep.CreateReport();
					System.out.printf("\nPress Enter to continue...");
	                input.nextLine();
				}
				break;
			default:
				System.out.println("Invalid Option");
			}
			}catch(ParseException pe)
			{
				System.out.println(pe.getMessage());
			}
			
		}else
		{
			loop=false;
		}
		}catch(NumberFormatException nfe)
		{
			nfe.getMessage();
			System.out.println("Invalid Option");
		}
		
		}while(loop);
	}
	public HashMap <Timestamp,String> getUserlogfile(){
        return userlogfile;
    }

	public boolean validateDate(String strDate)
	{
		/* Check if date is 'null' */
		if (strDate.trim().equals(""))
		{
			System.out.println("Date cannot be null");
		    return false;
		}
		/* Date is not 'null' */
		else
		{
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		    sdfrmt.setLenient(false);
		    try
		    {
		        sdfrmt.parse(strDate); 
		        //System.out.println(strDate+" is valid date format");
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        System.out.println(strDate+" is Invalid Date format");
		        return false;
		    }
		    /* Return true if date format is valid */
		    return true;
		}
	}
}
