public class User{
    private String id, userType, password;

    public User(String initID){
        id = initID;
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
    }
}