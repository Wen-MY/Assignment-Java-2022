import java.util.*;
import java.sql.Timestamp;

public class UserController{
    private ArrayList <User> userList;
    private Login login;
    private HashMap <Timestamp,String> userlogfile = new HashMap<Timestamp,String>();
    Scanner input = new Scanner(System.in);

    public UserController(ArrayList <User> initList, Login initLogin){
        userList = initList;
        login = initLogin;
    }
    public void menu(){
        while(true){
            System.out.printf("\nPlease select the following: \n");
            System.out.printf("1. View all user\n");
            System.out.printf("2. Search user\n");
            System.out.printf("3. Add user\n");
            System.out.printf("4. Exit\n");
            int choice;
            while(true){
                try{
                    choice = Integer.parseInt(input.nextLine());
                    break;
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
            }

            if (choice == 1){
                if (userList.size() == 0){
                    System.out.printf("No user found.\n");
                }
                else{
                    System.out.printf("There has %d user.\n", userList.size());
                    for (int i=0; i<userList.size(); i++){
                        User user = userList.get(i);
                        printUser(user);
                    }
                }
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }  
            else if(choice == 2){
                searchUser();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if(choice == 3){
                addUser();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if (choice ==4){
                break;
            }
            else{
                System.out.printf("Invalid input\n");
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
        }
    }
    public void printUser(User user){
        System.out.printf(
            "User ID: %15s User Type: %8s\n",
            user.getID(), user.getUserType()
        );
    }
    public void searchUser(){
        System.out.printf("Insert the user ID to search: ");
        String userID = input.nextLine();

        User user = findUser(userID);
        if (user == null){
            System.out.printf("User not found.\n");
        }
        else{
            System.out.printf("User found\n");
            printUser(user);
            System.out.printf("\nDo you wish to: \n");
            System.out.printf("1. Edit the user\n");
            System.out.printf("2. Delete the user\n");
            System.out.printf("3. Back to menu\n>>>");
            int choice;
            while(true){
                try{
                    choice = Integer.parseInt(input.nextLine());
                    break;
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
            }

            if (choice == 1){
                System.out.printf("Please insert the new data.\n");
                System.out.printf("User ID: ");
                String id = input.nextLine();
                String initID = user.getID();
                String initType = user.getUserType();

                user.setID(id);
                setUserType(user);
                setPassword(user);
                System.out.printf("User edited.\n");

                userlogfile.put(
                    new Timestamp(System.currentTimeMillis()), 
                    String.format(
                        "Edit User ID: '%s' to '%s'\nUser Type: '%s' to '%s'|%s", 
                        initID, user.getID(), initType, user.getUserType(),login.getUser().getID()
                    )
                );
            }
            else if(choice == 2){
                System.out.printf("Are you sure? (y/n)");
                char userInput = input.next().charAt(0);
                if (userInput == 'y' || userInput == 'Y'){
                    deleteUser(user);
                    System.out.printf("User deleted\n");
                    userlogfile.put(new Timestamp(System.currentTimeMillis()), "Deleted User: "+user.getID()+'|'+login.getUser().getID());
                }
                else{
                    System.out.printf("Failed to delete user\n");
                }
            }
        }
    }
    @SuppressWarnings("resource")
	public void setPassword(User user){
        String password1, password2;
        boolean valid = false;
        
        do{
            do{
                System.out.printf("\nNew password (minimum 8 characters)\n: ");
                password1 = new Scanner(System.in).nextLine();
                if (password1.length()<8){
                    System.out.printf("\nPassword must have at least 8 characters\n");
                }
            }while(password1.length()<8);

            System.out.printf("\nPlease insert again\n: ");
            password2 = new Scanner(System.in).nextLine();

            if (password1.equals(password2)){
                valid = true;
            }
            else{
                System.out.printf("\nPassword does not match\n");
                System.out.printf("Please try again\n");
            }
        }while(!valid);

        user.setPassword(password1);
    }
    public void setUserType(User user){
        int choice=0;
        do{ 
            System.out.printf("Please choose user Type: \n");
            System.out.printf("1. Admin\n");
            System.out.printf("2. Staff\n");
            System.out.printf("3. Cashier\n");
            System.out.printf(">>> ");
                try{
                    choice = Integer.parseInt(input.nextLine());
                    if(choice<1 || choice>3)
                        System.out.printf("Invalid Input\n\n");
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
    
        	} while (choice<1 || choice>3);
        user.setUserType(choice);
        
    }
    public void addUser(){
        System.out.printf("Please insert the details of the user.\n");
        System.out.printf("User ID: ");
        String id = input.nextLine();
    
        User user = new User(id);
        setUserType(user);
        setPassword(user);

        userList.add(user);
        System.out.printf("User added successfully\n");
        userlogfile.put(new Timestamp(System.currentTimeMillis()), "Added User: "+user.getID()+'|'+login.getUser().getID());
    }
    public User findUser(String id){
        for (int i=0; i<userList.size(); i++){
            if (userList.get(i).getID().equals(id)){
                return userList.get(i);
            }
        }
        return null;
    }
    public void deleteUser(User user){
        userList.remove(user);
    }   
    public HashMap <Timestamp,String> getUserlogfile(){
        return userlogfile;
    }
}
