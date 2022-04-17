import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UserReport extends Report {
	private HashMap <Timestamp,String> userlogfile;
	public UserReport (Date date,HashMap <Timestamp,String> userlogfile)
	{
		super(date);
		this.userlogfile=userlogfile;
	}
	public UserReport (Date date1,Date date2,HashMap <Timestamp,String> userlogfile)
	{
		super(date1,date2);
		this.userlogfile=userlogfile;
	}
	public void CreateReport()
	{
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		for(Timestamp key: userlogfile.keySet())
		{
			if(date2==null)
			{
				if((sdfrmt.format(key)).equals(sdfrmt.format(date)))
				{
					System.out.println("Event   \t:"+userlogfile.get(key)+"\nDate and Time   :"+key + '\n');
				}
				
			}
			else
			{
				if(((sdfrmt.format(key)).equals(sdfrmt.format(date))||(sdfrmt.format(key)).equals(sdfrmt.format(date2))||(key.after(date) && key.before(date2))))
				{
					System.out.println("Event   \t:"+userlogfile.get(key)+"\nDate and Time   :"+key+ '\n');
				}
			}
		}
	}
	
	
	
}
