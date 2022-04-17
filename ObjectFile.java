import java.io.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.ArrayList;

public class ObjectFile{
	
	public void S_Writer(ArrayList<Receipt> s)
	{
		try {
		FileOutputStream f = new FileOutputStream(new File("SalesRecord.txt"));
		ObjectOutputStream o = new ObjectOutputStream(f);
		
		o.writeObject(s);
		o.close();
		}catch (FileNotFoundException e)
		{
			System.out.println("File not Found");
		}catch (IOException e) 
		{
			System.out.println("Error initializing stream in S_W");
		}
	}
	public void P_Writer(ArrayList<Product> p)
	{
		try {
			FileOutputStream f = new FileOutputStream(new File("ProductRecord.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(p);
			o.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found");
			}catch (IOException e) 
			{
				System.out.println("Error initializing stream in P_W");
			}
	}
	public void U_Writer(ArrayList<User> u)
	{
		try {
			FileOutputStream f = new FileOutputStream(new File("UserRecord.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(u);
			o.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found");
			}catch (IOException e) 
			{
				System.out.println("Error initializing stream in U_W");
			}
	}
	public void U_Writer(HashMap <Timestamp,String> log)
	{
		try {
		FileOutputStream f = new FileOutputStream(new File("UserLogFile.txt"));
		ObjectOutputStream o = new ObjectOutputStream(f);
		
		o.writeObject(log);
		o.close();
		}catch (FileNotFoundException e)
		{
			System.out.println("File not Found");
		}catch (IOException e) 
		{
			System.out.println("Error initializing stream in U_Wlog");
		}
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public ArrayList<Object> Reader(int type)
	{
		String path=null;
		switch(type)
		{
		case 1:
			path="ProductRecord.txt";
			break;
		case 2:
			path="SalesRecord.txt";
			break;
		case 3:
			path="UserRecord.txt";
			break;
		default:
		}
		ArrayList<Object> temp = new ArrayList<>();
		try {
			FileInputStream f = new FileInputStream(new File(path));
			ObjectInputStream i = new ObjectInputStream(f);
			temp=(ArrayList<Object>)i.readObject();
			i.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found(_Ignore when first time launch_)");
			}catch (IOException e) 
			{
				e.printStackTrace();
			}catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			finally {
				return temp;
			}
	}
	@SuppressWarnings({ "finally", "unchecked" })
	public HashMap<Timestamp,String> Map_Reader()
	{
		HashMap<Timestamp,String> temp = new HashMap<>();
		try {
			FileInputStream f = new FileInputStream(new File("UserLogFile.txt"));
			ObjectInputStream i = new ObjectInputStream(f);
			
			temp=(HashMap<Timestamp,String>)i.readObject();
			i.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found(_Ignore when first time launch_)");
			}catch (IOException e) 
			{
				System.out.println("Error initializing stream in L_R");
			}catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			finally {
				return temp;
			}
	}
}
