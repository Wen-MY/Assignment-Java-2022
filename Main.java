package Concept_View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
public class Main {
	
	static ArrayList <User> user = new ArrayList<>();
	static ArrayList <Product> product = new ArrayList<>();
	static ArrayList <Sales> sales = new ArrayList<>();
	static HashMap <Login,String> userlogfile = new HashMap<>();
	
	public static void main(String [] args)
	{
		user.add(new User(1,"Admin","Admin"));
		Scanner scanner = new Scanner(System.in);
		Login login;
		do{
			System.out.print("Username :");
			String username= scanner.nextLine();
			System.out.print("Password :");
			String password= scanner.nextLine();
			login = new Login(new User(username,password));
			if (login.getUser().getId().compareTo("Admin")==0)
				break;
			else if(!login.validation())
				System.out.println("Username or Password Wrong");	
		}while (!login.validation());
		userlogfile.put(login,"Login\n");
		int menuopt;
		int submenuopt;
		do{
			Scanner s = new Scanner(System.in);
			adminmenu();
			menuopt = s.nextInt();
			if(menuopt!=0)
			{
			do{
				adminsubmenu(menuopt);
				submenuopt = s.nextInt();
				switch(menuopt)
				{
				case 1:
					userEvent(submenuopt,login);
					break;
				case 2:
					salesEvent(submenuopt,login);
					break;
				case 3:
					productEvent(submenuopt,login);
					break;
				case 4:
					reportEvent(submenuopt,login);
					break;
				case 5:
					stockEvent(submenuopt,login);
					break;
				case 6:
					UserSetting(submenuopt,login);
					break;
				default:
					System.out.println("Invalid Option");
				}
				
			}while(submenuopt!=0);
			}
			else
			{
				filingEvent();
				System.out.println("Exiting...All Event Saved.");
				menuopt=0;
			}
			
		}while(menuopt!=0);
	}
	
	public static void adminmenu()
	{
		System.out.println("1.User Management");
		System.out.println("2.Sales Management");
		System.out.println("3.Product Management");
		System.out.println("4.Report Management");
		System.out.println("5.Stock Management");
		System.out.println("6.Account Setting");
		System.out.println("0.Exit");
	}
	public static void adminsubmenu(int opt)
	{
		switch(opt)
		{
		case 1://User
			System.out.println("1.create user\n2.list users\n3.delete user\n0.Exit to main menu");
			break;
		case 2://Sales
			System.out.println("1.create sales\n2.list sales\n3.delete sales\n0.Exit to main menu");
			break;
		case 3://Product
			System.out.println("1.create product\n2.list products\n3.delete product\n4.edit product\n0.Exit to main menu");
			break;
		case 4://Report
			System.out.println("1.Sales Report\n2.Stock Report\n3.User Report\n0.Exit to main menu");
			break;
		case 5://haven't touch yet product need add more one attribute to do
			System.out.println("1.update stock\n2.Low Stock Item\n0.Exit to main menu");
			break;
		case 6://Setting
			System.out.println("1.change Username\n2.change Password\n0.Exit to main menu");
			break;
		default:
			System.out.println("Invalid Option");
		}
	}
	public static void usermenu()
	{
		System.out.println("1.Create sales");
		System.out.println("2.Update stock");
		System.out.println("3.Account Setting");
		System.out.println("4.Exit");
	}

	public static void userEvent(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		case 0:
			System.out.println("\n");
			break;
		case 1:
			System.out.println("User ID :");
			String user_id;
			user_id = input.nextLine();
			System.out.println("User Password :");
			String user_password = input.nextLine();
			System.out.println("User Type :");
			//input.nextLine();
			int user_type =Integer.parseInt(input.nextLine());;
			User u = new User(user_type,user_id,user_password);
			user.add(u);
			userlogfile.put(login, "New User Add :" + u.getId());
			break;
		case 2:
			System.out.println("Type\tid\tpassword\n------------------------");
			ObjectFile o = new ObjectFile();
			for(int i=0;i<o.Reader(3).size();i++)
			{
				System.out.println(o.Reader(3).get(i).toString());
			}
			input.nextLine();
			break;
		case 3:
			System.out.println("User ID: ");
			String temp_id=input.nextLine();
			int checker=0;
			for(int i=0;i<user.size();i++)
			{
				if(user.get(i).getId().equals(temp_id))
				{
					userlogfile.put(login, "User Deleted :" + user.get(i).getId());
					user.remove(i);
				}
				else
					checker++;
			}
			if (checker==user.size())
				System.out.println("No Matched ID found");
			input.nextLine();
			break;
		default:
			System.out.println("Invalid Option");
			input.nextLine();
		}
	}
	public static void productEvent(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		case 0:
			System.out.println("\n");
			break;
		case 1: //create product
			System.out.println("Product ID:");
			String ID = input.nextLine();
			System.out.println("Product Name:");
			String Name =input.nextLine();
			System.out.println("Product Unit Price:");
			double price=input.nextDouble();
			System.out.println("Product Category:");
			String type=input.nextLine();
			product.add(new Product(ID,Name,price,type));
			userlogfile.put(login, "New Product Add :" + ID);
			break;
		case 2: //read product
			ObjectFile o = new ObjectFile();
			for(int i=0;i<o.Reader(1).size();i++)
			{
				System.out.println(o.Reader(1).get(i).toString());
			}
			input.nextLine();
			break;
		case 3://delete product
			System.out.print("Product ID :");
			String temp_id = input.nextLine();
			int checker=0;
			for(int i=0;i<product.size();i++)
			{
				if(product.get(i).getId().equals(temp_id))
				{
					userlogfile.put(login, "Product deleted :" + product.get(i).getId());
					product.remove(i);
				}
				else
					checker++;
			}
			if (checker==product.size())
				System.out.println("No Matched ID found");
			input.nextLine();
			break;
		case 4://edit product
			System.out.print("Product ID :");
			String edit_id = input.nextLine();
			int chkexist=0;
			int matchedid=0;
			for(int i=0;i<product.size();i++)
			{
				if(!(product.get(i).getId().equals(edit_id)))
					chkexist++;
				else
					matchedid=i;
			}
			if (chkexist==product.size())
				System.out.println("No Matched ID found");
			else
			{
				System.out.println("Edit\n1.Product Name\n2.Price\n3.Product Type\n");
				int productedit=input.nextInt();
				switch(productedit)
				{
				case 1:
					System.out.println("New Name:");
					String old_id=product.get(matchedid).getName();
					product.get(matchedid).setName(input.nextLine()); 
					userlogfile.put(login, "Product Name Edited :" + old_id+" to " +product.get(matchedid).getName());
					break;
				case 2:
					System.out.println("New Price:");
					double old_price = product.get(matchedid).getUprice();
					product.get(matchedid).setUprice(input.nextInt());
					userlogfile.put(login, "Product Unit Price Edited :" + old_price+" to " +product.get(matchedid).getUprice());
					break;
				case 3:
					System.out.println("New Type:");
					String old_type=product.get(matchedid).getType();
					product.get(matchedid).setType(input.nextLine());
					userlogfile.put(login, "Product Type Edited :" + old_type+" to " +product.get(matchedid).getType());
					break;
				default:
					System.out.println("Invalid Option");
					input.nextLine();
				}
			}
			input.nextLine();
			break;
		default:
			System.out.println("Invalid Option");
			input.nextLine();
			
		}
	}
	public static void salesEvent(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		case 0:
			System.out.println("\n");
			break;
		case 1://create sales
			int new_sales_id = (sales.get(sales.size()-1).getSalesID())+1;
			HashMap <Product ,Integer> product_hmap = new HashMap<>();
			double total=0;
			String p_id_string;
			Product p=null;
			do 
			{
			System.out.println("Product ID(Enter 0 to stop adding product):");
			p_id_string=input.nextLine();
			p_id_string=p_id_string.toUpperCase();
			for(int i=0;i<product.size();i++)			{
				if(p_id_string.matches(product.get(i).getId()))
				{
					p = product.get(i);
				}
			}
			if(p == null)
				System.out.print("Product id not exist\n");
			else{
				if(!p_id_string.matches("0"))
				{
					System.out.println("Quantity:");
					int Quantity = input.nextInt();
					product_hmap.put(p,Quantity);
					total+=(p.getUprice()*Quantity);
				}
			}
			}while(!p_id_string.matches("0"));
			Sales s = new Sales(new_sales_id,product_hmap,total);
			sales.add(s);
			userlogfile.put(login, "New Sales Created :" + s.getSalesID());
			break;
		case 2:
			ObjectFile o = new ObjectFile();
			for(int i=0;i<o.Reader(2).size();i++)
			{
				System.out.println(o.Reader(2).get(i).toString());
			}
			input.nextLine();
			break;
		case 3:
			System.out.print("Sales ID :");
			int temp_sales_id = input.nextInt();
			int checker=0;
			for(int i=0;i<sales.size();i++)
			{
				if(sales.get(i).getSalesID()==(temp_sales_id))
				{
					userlogfile.put(login, "Sales Deleted :" + sales.get(i).getSalesID());
					sales.remove(i);
				}
				else
					checker++;
			}
			if (checker==product.size())
				System.out.println("No Matched ID found");
			input.nextLine();
			break;
		default:
			System.out.println("Invalid Option");
			input.nextLine();
		}
	}
	public static void reportEvent(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		System.out.println("1.Report By Date\n2.Report By Date Interval");
		int filter;
		String strdate=null;
		String strdate2=null;
		do{
		filter = input.nextInt();
		if(filter==1)
		{	
			do{
			System.out.println("Date (Format must in DD/MM/YYYY):");
			strdate = input.nextLine();
			}while(validateDate(strdate));
			
		}
		else if(filter==2)
		{
			do{
				System.out.println("From Date (Format must in DD/MM/YYYY):");
				strdate = input.nextLine();
				}while(validateDate(strdate));
			do{
				System.out.println("To Date (Format must in DD/MM/YYYY):");
				strdate2 = input.nextLine();
				}while(validateDate(strdate2));
		}
		else
			System.out.println("Invalid Option");
		}while(filter!=1||filter!=2);
		try{
		Date date=new SimpleDateFormat("dd/MM/yyyy").parse(strdate);
		Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(strdate2);
		switch(opt)
		{
		case 1://sales report
			if(date2==null)
			{
				SalesR sales_rep = new SalesR(date);
			}
			else
			{
				SalesR sales_rep = new SalesR(date,date2);
			}
			input.nextLine();
			break;
		case 2://stock report
			System.out.println("Invalid Option");
			break;
		case 3://user report
			if(date!=null)
			{
				UserR user_rep = new UserR(date);
			}
			else
			{
				UserR user_rep = new UserR(date2);
			}
			input.nextLine();
			break;
		default:
			System.out.println("Invalid Option");
			input.nextLine();
		}
		}catch(ParseException pe)
		{
			System.out.println(pe.getMessage());
		}
	}
	public static void stockEvent(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		
		}
	}
	public static void UserSetting(int opt,Login login)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		case 0:
			System.out.println("\n");
			break;
		case 1:
			System.out.print("New Username:");
			String new_username = input.nextLine();
			String old_username=login.getUser().getId();
			login.getUser().setId(new_username);
			userlogfile.put(login, "Account name changed: "+old_username+ " to " +login.getUser().getId());
			break;
		case 2:
			System.out.print("New Password:");
			String new_password = input.nextLine();
			String old_password=login.getUser().getPassword();
			login.getUser().setId(new_password);
			userlogfile.put(login, "Account name changed: "+old_password+ " to " +login.getUser().getPassword());
			break;
		default:
			System.out.println("Invalid Option");
			input.nextLine();
		}
	}
	public static void filingEvent()
	{
		ObjectFile o = new ObjectFile();
		o.S_Writer(sales);
		o.P_Writer(product);
		o.U_Writer(user);
		o.U_Writer(userlogfile);
	}
	public static boolean validateDate(String strDate)
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
		        Date javaDate = sdfrmt.parse(strDate); 
		        System.out.println(strDate+" is valid date format");
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

