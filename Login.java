import java.util.Arrays;
import java.util.Scanner;
import java.io.Console;

public class Login{
    private String id;
    private char[] password;
    private User user;
    private UserController manageUser;

    Console console = System.console();
    Scanner input = new Scanner(System.in);

    public Login(UserController initManageUser){
        manageUser = initManageUser;
    }

    public void login(){
        boolean valid = false;
        do{
            System.out.printf("\nUser ID: ");
            id = input.next();
            System.out.printf("Password: ");
            password = console.readPassword();
            for (int i=0; i<password.length; i++){
                password[i] += 13;
            }
            if (!Valid()){
                System.out.printf("ID or Password incorrect.\n");
                System.out.printf("Please try again.\n");
            }
            else{
                valid = true;
            }
        }while(!valid);

        System.out.printf("Login succeed\n");
        manageUser.printUser(user);
    }

    public boolean Valid(){
        user = manageUser.findUser(id);
        if (user == null){
            return false;
        }
        else{
            if (Arrays.equals(user.getPassword(), password)){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
