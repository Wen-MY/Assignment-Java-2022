import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    private String id, userType, password;

    public User(String initID){
        id = initID;
    }
    public User(int choice,String initID ,String initpassword) // for default user or hard coded
    {
    	setUserType(choice);
    	 id = initID;
    	 password = initpassword;
    }
    public User(String initID ,String initpassword) //for login purpose
    {
    	 id = initID;
    	 password = initpassword;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String value){
        password = value;
    }
    public String getID(){
        return id;
    }
    public void setID(String value){
        id = value;
    }
    public String getUserType(){
        return userType;
    }
    public int getUserTypeInt()
    {
    	if(userType.equals("admin"))
    		return 1;
    	else if(userType.equals("staff"))
    		return 2;
    	else if(userType.equals("cashier"))
    		return 3;
    	else
    		return 0;
    }
    public void setUserType(int choice){
        if (choice == 1){
            userType = "admin";
        }
        else if (choice == 2){
            userType = "staff";
        }
        else if (choice == 3){
            userType = "cashier";
        }
        else
        	userType="Unknown";
    }
}