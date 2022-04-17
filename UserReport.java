import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UserReport extends Report {
	private HashMap <Login,String> userlogfile;
	public UserReport (Date date,HashMap <Login,String> userlogfile)
	{
		super(date);
		this.userlogfile=userlogfile;
	}
	public UserReport (Date date1,Date date2,HashMap <Login,String> userlogfile)
	{
		super(date1,date2);
		this.userlogfile=userlogfile;
	}
	public void CreateReport()
	{
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		for(Login key: userlogfile.keySet())
		{
			if(date2==null)
			{
				if((sdfrmt.format(key.getTimestamp())).equals(sdfrmt.format(date)))
				{
					System.out.println("User ID \t:" +key.getUser().getID()+"\nEvent   \t:"+userlogfile.get(key)+"\nDate and Time   :"+key.getTimestamp() + '\n');
				}
				
			}
			else
			{
				if(((sdfrmt.format(key.getTimestamp())).equals(sdfrmt.format(date))||(sdfrmt.format(key.getTimestamp())).equals(sdfrmt.format(date2))||(key.getTimestamp().after(date) && key.getTimestamp().before(date2))))
				{
					System.out.println("User ID \t:" +key.getUser().getID()+"\nEvent   \t:"+userlogfile.get(key)+"\nDate and Time   :"+key.getTimestamp()+ '\n');
				}
			}
		}
	}
	
	
	
}
