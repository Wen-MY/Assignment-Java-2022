package Concept_View;

public class User {

	private int type;
	private String id;
	private String password;
	////
	public User(String id,String password)
	{
		this.id=id;
		this.password=password;
	} //for login purpose
	public User(int type,String id,String password)
	{
		this.type=type;
		this.id=id;
		this.password=password;
	}
	////
	public int getType()
	{
		return type;
	}
	public String getTypeString()
	{
		switch(type)
		{
		case 1:
			return "Admin";
		case 2:
			return "Staff";
		case 3:
			return "Customer";
		default:
			return null;
		}
	}
	public String getId()
	{
		return id;
	}
	public String getPassword()
	{
		return password;
	}
	////
	public void setId(String id)
	{
		this.id=id;
	}
	public void setType(int type) 
	{
		this.type=type;
	}		
	public void setPassword(String password)
	{
		this.password = password;
	}
	@Override 
	public String toString()
	{
		return getTypeString() +'\t'+ id +'\t' +password;
	}
	
}

	

