import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Login implements Serializable{
	private static final long serialVersionUID = 1L;
	private User user;
	private Timestamp timestamp;
	public Login(User user)
	{
		this.user=user;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public User getUser()
	{
		return user;
	}
	public Timestamp getTimestamp()
	{
		return timestamp;
	}
	public boolean validation(ArrayList<User> userList)
	{
		for (User u: userList){
			if(u.getID().equals(user.getID()))
			{
				if(u.getPassword().equals(user.getPassword()))
				{
					user.setUserType(u.getUserTypeInt());				
					break;
				}
				else{
					user.setUserType(0);
				}
			}
			else
				user.setUserType(0);
		};
		if(user.getUserType().equals("Unknown")){
			return false;
		}
		else{
			return true;
		}
	}
}
