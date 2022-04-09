import java.util.*;
import java.io.Console;
import java.text.ParseException;
public class Main {
	
	static ArrayList <User> userList = new ArrayList<>();
	static ArrayList <Product> productList = new ArrayList<>();
	static ArrayList <Sales> sales = new ArrayList<>();
	static HashMap <Login,String> userlogfile = new HashMap<>();
	
	public static void main(String [] args)
	{
		User admin = new User ("Admin");
		String password = "Admin";
		admin.setPassword(password);
		admin.setUserType(1);
		userList.add(admin);

		Login login = new Login(userList);
		login.menu();
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
					userEvent(login);
					break;
				case 2:
					salesEvent(submenuopt,login);
					break;
				case 3:
					productEvent(login);
					break;
				case 4:
					reportEvent(submenuopt,login);
					break;
				case 5:
					stockEvent(submenuopt,login);
					break;
				case 6:
					UserSetting(login);
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
			//menu in the controller
			break;
		case 2://Sales
			System.out.println("1.create sales\n2.list sales\n3.delete sales\n0.Exit to main menu");
			break;
		case 3://Product
			//menu in the controller
			break;
		case 4://Report
			System.out.println("1.Sales Report\n2.Stock Report\n3.User Report\n0.Exit to main menu");
			break;
		case 5://haven't touch yet product need add more one attribute to do
			System.out.println("1.update stock\n2.Low Stock Item\n0.Exit to main menu");
			break;
		case 6://Setting
			//move to userSetting();
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

	public static void userEvent(Login login)
	{
		UserController manageUser = new UserController(userList, login);
		manageUser.menu();
		userlogfile.putAll(manageUser.getUserlogfile());
	}
	public static void productEvent(Login login)
	{
		ProductController manageProduct = new ProductController(productList, login);
		manageProduct.menu();
		userlogfile.putAll(manageProduct.getUserlogfile());
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
		while(true){
			System.out.printf("1. Edit Username\n");
			System.out.printf("2. Edit Password\n");
			System.out.printf("3. Back to Menu\n");
			int choice;
			while(true){
                try{
                    choice = Integer.parseInt(input.nextLine());
                    break;
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
            }

			if(choice == 1){
				String initName = login.getUser().getID();
				System.out.printf("New Username: ");
				String username = input.nextLine();
				login.getUser().setID(username);
				System.out.printf("Username changed successfully\n");
				System.out.printf("Press enter to continue...");
				input.nextLine();

				userlogfile.put(login, "Account name changed: "+initName+ " to " +login.getUser().getID());
				break;
			}
			else if(choice == 2){
				String initPassword = login.getUser().getPassword();
				Console console = System.console();
				String password1, password2;
				boolean valid = false;
				do{
					do{
						System.out.printf("\nNew password (minimum 8 characters)\n: ");
						password1 = new String(console.readPassword());
						if (password1.length()<8){
							System.out.printf("\nPassword must have at least 8 characters\n");
						}
					}while(password1.length()<8);

					System.out.printf("\nPlease insert again\n: ");
					password2 = new String(console.readPassword());

					if (password1.equals(password2)){
						valid = true;
					}
					else{
						System.out.printf("\nPassword does not match\n");
						System.out.printf("Please try again\n");
					}
				}while(!valid);

				System.out.printf("Username changed successfully\n");
				System.out.printf("Press enter to continue...");
				input.nextLine();

				login.getUser().setPassword(password1);
				userlogfile.put(
					login, 
					String.format(
						"Account Password changed: '%s' to '%s'",
						initPassword, password1
					)
				);
				break;
			}
			else if(choice == 3){
				System.out.printf("Press enter to continue...");
				input.nextLine();
				break;
			}
			else{
				System.out.printf("Invalid Input\n");
				System.out.printf("Press enter to continue...");
				input.nextLine();
			}
		}
	}
	public static void filingEvent()
	{
		ObjectFile o = new ObjectFile();
		o.S_Writer(sales);
		o.P_Writer(productList);
		o.U_Writer(userList);
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

