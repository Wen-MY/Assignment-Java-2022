public class User{
    private String name, id, userType;
    private char[] password;

    public User(String initName, String initID){
        name = initName;
        id = initID;
    }
    public String getName(){
        return name;
    }
    public void setName(String value){
        name = value;
    }
    public char[] getPassword(){
        return password;
    }
    public void setPassword(char[] value){
        password = value;
        
    }
    public String getID(){
        return id;
    }
    public String getUserType(){
        return userType;
    }
    public void setUserType(int choice){
        if (choice == 1){
            userType = "admin";
        }
        else if (choice == 2){
            userType = "employee";
        }
        else if (choice == 3){
            userType = "member";
        }
    }
}