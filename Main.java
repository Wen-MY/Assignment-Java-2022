
import java.util.*;
import java.sql.Timestamp;
public class Main {
    
    static ArrayList <User> userList = new ArrayList<>();
    static ArrayList <Product> productList = new ArrayList<>();
    static ArrayList <Receipt> salesList = new ArrayList<>();
    static HashMap <Timestamp,String> userlogfile = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    
    public static void main(String [] args){        
        system_init();
        Login login;
        
        if(userList.isEmpty()){
            User admin = new User (1,"Admin","Admin");
            userList.add(admin);
        }
        while (true){
            System.out.print("Username :");
            String username= input.nextLine();
            System.out.print("Password :");
            String password= input.nextLine();
            login = new Login(new User(username,password));
            if(!login.validation(userList)){
                System.out.println("Username or Password Wrong");
            }	
            else{
                break;
            }
            userlogfile.put(new Timestamp(System.currentTimeMillis()),"Login|"+login.getUser().getID());
            
            String choice;
            if(login.getUser().getUserType().equals("admin")){
                while (true){
                    adminmenu();
                    choice = input.nextLine();
                    if(choice.equals("1")){
                        UserController manageUser = new UserController(userList, login);
                        manageUser.menu();
                        if(manageUser.getUserlogfile()!= null){
                            manageUser.getUserlogfile().forEach(
                                (key,value) -> userlogfile.put(key,value)
                            );
                        }
                    }
                    else if(choice.equals("2")){
                        SalesMenuController manageSales = new SalesMenuController(productList,salesList, login);
                        manageSales.showSalesMenu();
                        if (manageSales.getUserlogfile() != null)
                        {
                            manageSales.getUserlogfile().forEach(
                                (key,value) -> userlogfile.put(key,value)
                            );
                        }
                    }
                    else if(choice.equals("3")){
                        ProductController manageProduct = new ProductController(productList, login);
                        manageProduct.menu();
                        if(manageProduct.getUserlogfile()!= null){
                            manageProduct.getUserlogfile().forEach(
                                (key,value) -> userlogfile.put(key,value)
                            );
                        }
                    }
                    else if(choice.equals("4")){
                        ReportEvent e =new ReportEvent(salesList, userlogfile);
                        e.Event();
                    }
                    else if(choice.equals("5")){
                        stockManagement(login);
                    }
                    else if(choice.equals("6")){
                        UserSetting(login);
                    }
                    else if (choice.equals("0")){
                        System.out.printf("Exiting...\n");
                        fillingEvent();
                        System.out.printf("Data Saved\n");
                        System.out.printf("Press Enter to continue...");
                        input.nextLine();
                        break;
                    } 
                    else{
                        System.out.printf("\nInvalid Input\n");
                    }
                }
            }
            else if(login.getUser().getUserType().equals("staff")){
                while (true){
                    usermenu();
                    choice = input.nextLine();
                    if (choice.equals("1")){
                        SalesMenuController manageSales = new SalesMenuController(productList,salesList, login);
                        manageSales.addSales();
                        if (manageSales.getUserlogfile() != null){
                            manageSales.getUserlogfile().forEach(
                                (key,value) -> userlogfile.put(key,value)
                            );
                        }
                    }
                    else if (choice.equals("2")){
                        stockManagement(login);
                    }
                    else if (choice.equals("3")){
                        UserSetting(login);
                    }
                    else if (choice.equals("0")){
                        System.out.printf("Exiting...\n");
                        fillingEvent();
                        System.out.printf("Data Saved\n");
                        System.out.printf("Press Enter to continue...");
                        input.nextLine();
                        break;
                    }
                }
            }
            else{
                SalesMenuController manageSales = new SalesMenuController(productList,salesList, login);
                manageSales.addSales();
                if (manageSales.getUserlogfile() != null)
                {
                    manageSales.getUserlogfile().forEach(
                        (key,value) -> userlogfile.put(key,value)
                    );
                }
            }
        }
    }
    
    public static void adminmenu(){
        System.out.printf("1.User Management\n");
        System.out.printf("2.Sales Management\n");
        System.out.printf("3.Product Management\n");
        System.out.printf("4.Report Management\n");
        System.out.printf("5.Update stock\n");
        System.out.printf("6.Account Setting\n");
        System.out.printf("0.Exit\n");
    }
    public static void usermenu(){
        System.out.printf("1.Create sales\n");
        System.out.printf("2.Update stock\n");
        System.out.printf("3.Account Setting\n");
        System.out.printf("0.Exit\n");
    }
    
    public static void UserSetting(Login login){	
        while(true){
            System.out.printf("1. Edit Username\n");
            System.out.printf("2. Edit Password\n");
            System.out.printf("0. Back to Menu\n");
            String choice = input.nextLine();

            if(choice.equals("1")){
                String initName = login.getUser().getID();
                System.out.printf("New Username: ");
                String username = input.nextLine();
                login.getUser().setID(username);
                System.out.printf("Username changed successfully\n");
                System.out.printf("Press enter to continue...");
                input.nextLine();

                userlogfile.put(
                    new Timestamp(System.currentTimeMillis()), 
                    "Account name changed: "+initName+ " to " +login.getUser().getID()
                );
                break;
            }
            else if(choice.equals("2")){
                String initPassword = login.getUser().getPassword();
                String password1, password2;
                while (true){
                    while (true){
                        System.out.printf("\nNew password (minimum 8 characters)\n: ");
                        password1 = input.nextLine();
                        if (password1.length()<8){
                            System.out.printf("\nPassword must have at least 8 characters\n");
                        }
                        else{
                            break;
                        }
                    }
                    System.out.printf("\nPlease insert again\n: ");
                    password2 = input.nextLine();

                    if (password1.equals(password2)){
                        break;
                    }
                    else{
                        System.out.printf("\nPassword does not match\n");
                        System.out.printf("Please try again\n");
                    }
                }
                login.getUser().setPassword(password1);
                System.out.printf("Password changed successfully\n");
                System.out.printf("Press enter to continue...");
                input.nextLine();
                
                userlogfile.put(
                    new Timestamp(System.currentTimeMillis()), 
                    String.format(
                        "Account Password changed: '%s' to '%s'%s",
                        initPassword, password1 ,+'|'+login.getUser().getID()
                    )
                );
                break;
            }
            else if(choice.equals("0")){
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
    public static void stockManagement(Login login){
        ProductController manageProduct = new ProductController(productList, login);
        Product product;
        while (true){
            System.out.printf("Insert the restock product ID: ");
            String id = input.nextLine();
            product = manageProduct.findProduct(id);
            if (product == null){
                System.out.printf("Product not found\n");
                System.out.printf("Press enter to continue...");
                input.nextLine();
            }
            else{
                System.out.printf("Product found\n");
                manageProduct.printProduct(product);
                break;
            }
        }
        int number;
        while (true){
            System.out.printf("Enter the restock number: ");
            try{
                number = Integer.parseInt(input.nextLine());
                if (number <= 0){
                    System.out.printf("\nPositive Integer Required\n\n");
                }
                else{
                    break;
                }
            } catch (NumberFormatException ex){
                System.out.printf("\nInteger Input Required\n\n");
            }
        }
        product.updateStock(number);
        System.out.printf("Restock Successfully\n");
        manageProduct.printProduct(product);
        System.out.printf("Press Enter to continue...");
        input.nextLine();
    }
    public static void fillingEvent(){
        ObjectFile o = new ObjectFile();
        o.S_Writer(salesList);
        o.P_Writer(productList);
        o.U_Writer(userList);
        o.U_Writer(userlogfile);
    }
    public static void system_init(){
        ObjectFile o = new ObjectFile();
        o.Reader(1).forEach((p)->productList.add((Product)p));
        o.Reader(2).forEach((p)->salesList.add((Receipt)p));
        o.Reader(3).forEach((p)->userList.add((User)p));
        o.Map_Reader().forEach(
            (key,value) -> userlogfile.put(key,value)
        );
    }
}
