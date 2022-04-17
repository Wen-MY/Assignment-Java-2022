
import java.util.*;
import java.io.*;
public class Main {
	
	static ArrayList <User> userList = new ArrayList<>();
	static ArrayList <Product> productList = new ArrayList<>();
	static ArrayList <Receipt> salesList = new ArrayList<>();
	static HashMap <Login,String> userlogfile = new HashMap<>();
	
	@SuppressWarnings("resource")
	public static void main(String [] args)
	{
		Scanner s = new Scanner(System.in);
		
		system_init();
		Login login;
		if(userList.isEmpty())
		{
			User admin = new User (1,"Admin","Admin");
			userList.add(admin);
			//login = new Login(admin); for testing purpose
		}
		
		do{
			System.out.print("Username :");
			String username= s.nextLine();
			System.out.print("Password :");
			String password= s.nextLine();
			login = new Login(new User(username,password));
			if(!login.validation(userList))
				System.out.println("Username or Password Wrong");	
		}while (!login.validation(userList));
		userlogfile.put(login,"Login\t\t\t");
		
		
		int menuopt;
		if(login.getUser().getUserTypeInt() == 1)
		{
		try {
			do{
				adminmenu();
				
					menuopt = Integer.parseInt(s.nextLine());
					if(menuopt!=0)
					{
						switch(menuopt)
						{
						case 1:
							UserController manageUser = new UserController(userList, login);
							manageUser.menu();
							if(manageUser.getUserlogfile()!= null)
							{
								manageUser.getUserlogfile().forEach(
										(key,value) -> userlogfile.put(key,value)
										);
							}
							break;
						case 2://sales event
							SalesMenuController manageSales = new SalesMenuController(productList,salesList, login);
							manageSales.showSalesMenu();
							if (manageSales.getUserlogfile() != null)
							{
								manageSales.getUserlogfile().forEach(
										(key,value) -> userlogfile.put(key,value)
										);
							}
							break;
						case 3:
							ProductController manageProduct = new ProductController(productList, login);
							manageProduct.menu();
							if(manageProduct.getUserlogfile()!= null)
							{
								manageProduct.getUserlogfile().forEach(
										(key,value) -> userlogfile.put(key,value)
										);
							}
							break;
						case 4:
							ReportEvent e =new ReportEvent(salesList,userlogfile);
							e.Event();
							break;
						case 5:
							System.out.println("Invalid Option");
							break;
						case 6:
							UserSetting(login);
							break;
						default:
							System.out.println("Invalid Option");
						}
					}
					else
					{
						filingEvent();
						System.out.println("Exiting...All Event Saved.");
						menuopt=0;
					}
				
			}while(menuopt!=0);
		}catch(NumberFormatException nfe)
		{
			nfe.getMessage();
			System.out.println("Invalid Option");
		}
		}
		else
		{
			usermenu();
			try {
			do {
				
			menuopt = Integer.parseInt(s.nextLine());
			switch(menuopt)
			{
			case 0:
				filingEvent();
				System.out.println("Exiting...All Event Saved.");
			case 1:
				SalesMenuController manageSales = new SalesMenuController(productList,salesList, login);
				manageSales.addSales();
				break;
			case 2:
				
				break;
			case 3:
				UserSetting(login);
				break;
			default:
				System.out.println("Invalid Option");
			}
			}while(menuopt!=0);
		}catch(NumberFormatException nfe)
		{
			nfe.getMessage();
			System.out.println("Invalid Option");
		}
		}
	}
	
	public static void adminmenu()
	{
		System.out.println("1.User Management");
		System.out.println("2.Sales Management");
		System.out.println("3.Product Management");
		System.out.println("4.Report Management");
		System.out.println("5.Update stock");
		System.out.println("6.Account Setting");
		System.out.println("0.Exit");
	}
	public static void usermenu()
	{
		System.out.println("1.Create sales");
		System.out.println("2.Update stock");
		System.out.println("3.Account Setting");
		System.out.println("0.Exit");
	}
	
	public static void UserSetting(Login login)
	{
		@SuppressWarnings("resource")
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
		o.S_Writer(salesList);
		o.P_Writer(productList);
		o.U_Writer(userList);
		o.U_Writer(userlogfile);
	}
	public static void system_init()
	{
		ObjectFile o = new ObjectFile();
		o.Reader(1).forEach((p)->productList.add((Product)p));
		o.Reader(2).forEach((p)->salesList.add((Receipt)p));
		o.Reader(3).forEach((p)->userList.add((User)p));
		o.Map_Reader().forEach((key,value) ->{
			userlogfile.put(key,value);
			
		});
		
	}
}
