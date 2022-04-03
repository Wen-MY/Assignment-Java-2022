package Concept_View;
import java.io.*;
import java.util.ArrayList;

public class ObjectFile{
	
	public void S_Writer(ArrayList<Sales> s)
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
			System.out.println("Error initializing stream");
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
				System.out.println("Error initializing stream");
			}
	}
	public void U_Writer(ArrayList<User> u)
	{
		try {
			FileOutputStream f = new FileOutputStream(new File("ProductRecord.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			o.writeObject(u);
			o.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found");
			}catch (IOException e) 
			{
				System.out.println("Error initializing stream");
			}
	}
	
	@SuppressWarnings("finally")
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
		default:
			path=null;
			break;
		}
		ArrayList<Object> temp = new ArrayList<>();
		try {
			FileInputStream f = new FileInputStream(new File(path));
			ObjectInputStream i = new ObjectInputStream(f);
			
			temp=(ArrayList<Object>)i.readObject();
			i.close();
			}catch (FileNotFoundException e)
			{
				System.out.println("File not Found");
			}catch (IOException e) 
			{
				System.out.println("Error initializing stream");
			}catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			finally {
				return temp;
			}
	}
}
