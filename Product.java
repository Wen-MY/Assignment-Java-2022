public class Product{
    private String id, name, productType;
    private double price;

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
}