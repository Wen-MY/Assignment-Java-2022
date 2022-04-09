import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.util.Arrays;

public class UserController{
    protected ArrayList <User> userList;
    Scanner input = new Scanner(System.in);
    Console console = System.console();

    public UserController(){
        userList = new ArrayList <User>();
    }
    public void menu(){
        System.out.printf("Please select the following: \n");
        System.out.printf("1. View all user\n");
        System.out.printf("2. Search user\n");
        System.out.printf("3. Add user\n");
        System.out.printf("4. Exit\n");
        int choice = input.nextInt();
        input.nextLine();

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
            menu();
        }  
        else if(choice == 2){
            searchUser();
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
        else if(choice == 3){
            addUser();
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
        else if (choice ==4){
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
        }
        else{
            System.out.printf("Invalid input\n");
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
    }
    public void printUser(User user){
        System.out.printf(
            "User ID: %8s  User Name: %15s  User Type: %8s\n",
            user.getID(), user.getName(), user.getUserType()
        );
    }
    public void searchUser(){
        System.out.printf("Insert the user ID to search: ");
        String userID = input.next();

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
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1){
                System.out.printf("Please insert the new data.\n");
                System.out.printf("User Name: ");
                String name = input.next();
                input.nextLine();

                user.setName(name);
                setUserType(user);
                setPassword(user);

                System.out.printf("User edited.\n");
            }
            else if(choice == 2){
                System.out.printf("Are you sure? (y/n)");
                char userInput = input.next().charAt(0);
                if (userInput == 'y' || userInput == 'Y'){
                    deleteUser(user);
                    System.out.printf("User deleted\n");
                }
                else{
                    System.out.printf("Failed to delete user\n");
                }
            }
        }
    }
    public void setPassword(User user){
        char[] password1, password2;
        boolean valid = false;
        
        do{
            do{
                System.out.printf("\nNew password (minimum 8 characters)\n: ");
                password1 = console.readPassword();
                if (password1.length<8){
                    System.out.printf("\nPassword must have at least 8 characters\n");
                }
            }while(password1.length<8);

            System.out.printf("\nPlease insert again\n: ");
            password2 = console.readPassword();

            if (Arrays.equals(password1, password2)){
                valid = true;
            }
            else{
                System.out.printf("\nPassword does not match\n");
                System.out.printf("Please try again\n");
            }
        }while(!valid);

        for (int i=0; i<password1.length; i++){
            password1[i] += 13;
        }
        user.setPassword(password1);
    }
    public void setUserType(User user){
        boolean valid = false;
        int choice;
        do{ 
            System.out.printf("Please choose user Type: \n");
            System.out.printf("1. Owner\n");
            System.out.printf("2. Employee\n");
            System.out.printf("3. Member\n");
            System.out.printf("4. Guest\n");
            System.out.printf(">>> ");
            choice = input.nextInt();

            CheckValid check = new CheckValid(1, 4, choice);

            if(check.checkIntValid()){
                valid = true;
            }
            else{
                System.out.printf("Invalid Input\n");
                System.out.printf("Please insert again: ");
                choice = input.nextInt();
                check.setInput(choice);
            }
        } while (!valid);
        user.setUserType(choice);
    }
    public void addUser(){
        System.out.printf("Please insert the details of the user.\n");
        System.out.printf("User ID: ");
        String id = input.next();
        System.out.printf("User Name: ");
        String name = input.next();
    
        User user = new User(name, id);
        setUserType(user);
        setPassword(user);

        userList.add(user);
        System.out.printf("User added successfully\n");
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
}