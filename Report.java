import java.util.Date;
public abstract class Report {
	
	protected Date date,date2;
	public Report(Date date)
	{
		this.date =date;
		this.date2 = null;
	}
	public Report(Date date1,Date date2)
	{
		this.date=date1;
		this.date2=date2;
	}
	public abstract void CreateReport();
}
