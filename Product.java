import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
    private String id, name, productType;
    private double price;
    private int stock;

    public Product(String initID, String initName, double initPrice, String initType){
        name = initName;
        id = initID;
        price = initPrice;
        productType = initType;
    }
    
    public String getID(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String value){
        name = value;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double value){
        price = value;
    }
    
    public String getProductType(){
        return productType;
    }
    
    public void setProductType(String value){
        productType = value;
    }
    
    public int getStock()
    {
    	return this.stock;
    }
    
    public void updateStock(int num)
    {
    	stock += num;
    }
}