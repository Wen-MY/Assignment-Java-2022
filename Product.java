import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
    private String id, name, productType;
    private double price;
    private int stock;

    public Product(String initID, String initName, double initPrice, String initType, int initStock){
        name = initName;
        id = initID;
        price = initPrice;
        productType = initType;
        stock = initStock;
    }
    public String getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public String getProductType(){
        return productType;
    }
    public int getStock(){
    	return stock;
    }
    public void setName(String value){
        name = value;
    }
    public void setPrice(double value){
        price = value;
    }
    public void setProductType(String value){
        productType = value;
    }
    public void updateStock(int num){
    	stock += num;
    }
}
