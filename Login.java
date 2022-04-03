package Concept_View;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Login {

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
	public boolean validation(ArrayList<User> ALU)
	{
		boolean valid=false;
		for(int i=0;i<ALU.size();i++)
		{
			if(ALU.get(i).getId().equals(user.getId()))
			{
				if(ALU.get(i).getPassword().equals(user.getPassword()))
				{
					user.setType(ALU.get(i).getType());
					valid=true;
					break;
				}
			}
		}
		return valid;
	}
}
