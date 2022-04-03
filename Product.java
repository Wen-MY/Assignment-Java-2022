package Concept_View;

import java.io.Serializable;
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String ID;
	private String Name;
	private double Uprice;
	private String type;
	
	//
	public String getId()
	{
		return ID;
	}
	public String getName()
	{
		return Name;
	}
	public double getUprice()
	{
		return Uprice;
	}
	public String getType()
	{
		return type;
	}
	//
	
	//
	public void setName(String Name)
	{
		this.Name=Name;
	}
	public void setUprice(double Uprice)
	{
		this.Uprice=Uprice;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	//
	//
	public Product(String ID,String Name,double Uprice,String type)
	{
		this.ID=ID;
		this.Name = Name;
		this.Uprice=Uprice;
		this.type=type;
	}
	public Product(String Name,double Uprice,String type)
	{
		this.Name = Name;
		this.Uprice=Uprice;
		this.type=type;
	}
	//
	@Override
    public String toString() {
        return "ID:" + this.ID+ "\tName:" +this.Name+"\tUnit Price:"+this.Uprice +'\n';
    }
	
}
