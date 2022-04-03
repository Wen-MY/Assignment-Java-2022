package Concept_View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
public class Main {
	
	static ArrayList <User> user = new ArrayList<>();
	static ArrayList <Product> product = new ArrayList<>();
	static ArrayList <Sales> sales = new ArrayList<>();
	static HashMap <Login,String> userlogfile = new HashMap<>();
	
	public static void main(String [] args)
	{
		user.add(new User(1,"Admin","Admin")); //default user
		Scanner scanner = new Scanner(System.in);
		Login login;
		do{
			System.out.print("Username :");
			String username= scanner.nextLine();
			System.out.print("Password :");
			String password= scanner.nextLine();
			login = new Login(new User(username,password));
			if(!login.validation(user))
				System.out.println("Username or Password Wrong");
		}while (!login.validation(user));
		userlogfile.put(login,"Login\n");
		int menuopt;
		int submenuopt;
		do{
			Scanner s = new Scanner(System.in);
			adminmenu();
			menuopt = s.nextInt();
			if(menuopt==0)
				break;
			do{
				adminsubmenu(menuopt);
				submenuopt = s.nextInt();
				if(submenuopt==0)
					break;
				switch(menuopt)
				{
				case 1:
					userEvent(submenuopt);
					break;
				case 2:
					salesEvent(submenuopt);
					break;
				case 3:
					productEvent(submenuopt);
					break;
				case 4:
					reportEvent(submenuopt);
					break;
				case 5:
					stockEvent(submenuopt);
					break;
				case 6:
					UserSetting(submenuopt);
					break;
				default:
					System.out.println("Invalid Option");
				}
				
			}while(submenuopt!=0);
			
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
		case 1:
			System.out.println("1.create user\n2.list users\n3.delete user\n0.Exit to main menu");
			break;
		case 2:
			System.out.println("1.create sales\n2.list sales\n3.delete sales\n0.Exit to main menu");
			break;
		case 3:
			System.out.println("1.create product\n2.list products\n3.delete product\n4.edit product\n0.Exit to main menu");
			break;
		case 4:
			System.out.println("1.Sales Report\n2.Stock Report\n3.User Report\n0.Exit to main menu");
			break;
		case 5://haven't touch yet product need add more one attribute to do
			System.out.println("1.update stock\n2.Low Stock Item\n0.Exit to main menu");
			break;
		case 6:
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

	public static void userEvent(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
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
			break;
		case 2:
			System.out.println("Type\tid\tpassword\n------------------------");
			for(int i=0;i<user.size();i++)
			{
				System.out.println(user.get(i).toString());
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
					user.remove(i);
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
	public static void productEvent(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
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
			break;
		case 2: //read product
			for(int i=0;i<product.size();i++)
			{
				System.out.println(product.get(i).toString());
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
					product.remove(i);
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
					product.get(matchedid).setName(input.nextLine());
					break;
				case 2:
					System.out.println("New Price:");
					product.get(matchedid).setUprice(input.nextInt());
					break;
				case 3:
					System.out.println("New Type:");
					product.get(matchedid).setType(input.nextLine());
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
	public static void salesEvent(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
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
			break;
		case 2:
			for(int i=0;i<sales.size();i++)
			{
				System.out.println(sales.get(i).toString());
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
					sales.remove(i);
				else
					checker++;
			}
			if (checker==product.size())
				System.out.println("No Matched ID found");
			input.nextLine();
			break;
		case 4:
		}
	}
	public static void reportEvent(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
			
		}
	}
	public static void stockEvent(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		
		}
	}
	public static void UserSetting(int opt)
	{
		Scanner input = new Scanner(System.in);
		switch(opt)
		{
		
		}
	}
}
