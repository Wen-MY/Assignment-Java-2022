import java.sql.Timestamp;
import java.util.*;

public class ProductController{
    private Login login;
    private HashMap <Timestamp,String> userlogfile;
    private ArrayList <Product> productList;
    Scanner input = new Scanner(System.in);

    public ProductController(ArrayList <Product> initList, Login initLogin){
        productList = initList;
        login = initLogin;
        userlogfile = new HashMap<Timestamp,String>();
    }
    public void menu(){
        while (true){
            System.out.printf("Please select the following: \n");
            System.out.printf("1. View all product\n");
            System.out.printf("2. Search product\n");
            System.out.printf("3. Add product\n");
            System.out.printf("0. Exit\n");
            String choice = input.nextLine();

            if (choice.equals("1")){
                int i = getNumOfProduct();
                if (i == 0){
                    System.out.printf("No product found.\n");
                }
                else{
                    System.out.printf("There has %d product.\n", i);
                    for (int j=0; j<i; j++){
                        Product product = productList.get(j);
                        printProduct(product);
                    }
                }
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }  
            else if(choice.equals("2")){
                searchProduct();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if(choice.equals("3")){
                addProduct();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if(choice.equals("0")){
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
                break;
            }
            else{
                System.out.printf("Invalid input\n");
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
        }
    }
    public void printProduct(Product product){
        System.out.printf(
            "Product ID\t: %20s\nProduct Name\t: %20s \nProduct Type\t: %20s\nPrice\t\t: %20.2f\nStock\t\t: %20d\n\n",
            product.getID(), product.getName(), product.getProductType(), product.getPrice(), product.getStock()
        );
    }
    public void searchProduct(){
        System.out.printf("Insert the product ID to search: ");
        String productID = input.nextLine();

        Product product = findProduct(productID);
        if (product == null){
            System.out.printf("Product not found.\n");
        }
        else{
            System.out.printf("product found\n");
            while (true){
                printProduct(product);
                System.out.printf("\nDo you wish to: \n");
                System.out.printf("1. Edit the product\n");
                System.out.printf("2. Delete the product\n");
                System.out.printf("0. Back to menu\n>>>");
                String choice = input.nextLine();

                if (choice.equals("1")){
                    String initName = product.getName();
                    double initPrice = product.getPrice();
                    String initType = product.getProductType();
                    
                    System.out.printf("Please insert the new data.\n");
                    System.out.printf("Product Name: ");
                    String name = input.nextLine();
                    System.out.printf("Product Price: ");
                    double price;
                    while(true){
                        try{
                            price = Double.parseDouble(input.nextLine());
                            break;
                        } catch (NumberFormatException ex){
                            System.out.printf("Integer Input Required\n");
                        }
                    }
                    System.out.printf("Product Type: ");
                    String type = input.nextLine();

                    product.setName(name);
                    product.setPrice(price);
                    product.setProductType(type);

                    System.out.printf("Product edited.\n");

                    userlogfile.put(
                        new Timestamp(System.currentTimeMillis()), 
                        String.format(
                            "Edited Product Name: '%s' to '%s'\nPrice: '%.2f' to '%.2f'\nType: '%s' to '%s'|%s",
                            initName, product.getName(), initPrice, product.getPrice(), 
                            initType, product.getProductType(),login.getUser().getID()
                        )
                    );
                    break;
                }
                else if(choice.equals("2")){
                    System.out.printf("Are you sure? (y/n)");
                    char userInput = input.next().charAt(0);
                    if (userInput == 'y' || userInput == 'Y'){
                        deleteProduct(product);
                        System.out.printf("Product deleted\n");
                        userlogfile.put(
                            new Timestamp(System.currentTimeMillis()), 
                            "Deleted Product: "+product.getName() +'|'+login.getUser().getID()
                        );
                        break;
                    }
                    else{
                        System.out.printf("Failed to delete product\n");
                    }
                }
                else if(choice.equals("3")){
                    int restock;
                    while(true){
                        System.out.printf("Please enter the restock number: ");
                        try{
                            restock = Integer.parseInt(input.nextLine());
                            if (restock <= 0){
                                System.out.printf("Positive Integer Required\n");
                            }
                            else{
                                break;
                            }
                        } catch(NumberFormatException ex){
                            System.out.printf("Integer Input Required\n");
                        }
                    }
                    product.updateStock(restock);
                }
                else if(choice.equals("0")){
                    System.out.printf("Press enter to continue...");
                    input.nextLine();   
                    break;
                }
                else{
                    System.out.printf("Invalid Input\n");
                    System.out.printf("Press enter to continue...");
                    input.nextLine();
                }
            }
        }
    }
    public void addProduct(){
        int index;
        if (productList.size() == 0){
            index = 1;
        }
        else{
            index = Integer.parseInt(productList.get(productList.size()-1).getID()) + 1;
        }
        String id = String.format("%08d", index);
        System.out.printf("Please insert the details of the product.\n");
        System.out.printf("Product Name: ");
        String name = input.nextLine();
        System.out.printf("Product Price: ");
        double price;
        while(true){
            try{
                price = Double.parseDouble(input.nextLine());
                break;
            } catch (NumberFormatException ex){
                System.out.printf("Double Input Required\n");
            }
        }
        System.out.printf("Product Stock: ");
        int stock;
        while(true){
            try{
                stock = Integer.parseInt(input.nextLine());
                break;
            } catch (NumberFormatException ex){
                System.out.printf("Integer Input Required\n");
            }
        }
        System.out.printf("Product Type: ");
        String type = input.nextLine();
    
        Product product = new Product(id, name, price, type, stock);

        productList.add(product);
        System.out.printf("Product added successfully\n");
        userlogfile.put(new Timestamp(System.currentTimeMillis()), "Added Product: "+product.getName()+'|'+login.getUser().getID());
    }
    public int getNumOfProduct(){
        return productList.size();
    }
    public Product findProduct(String id){
        for (int i=0; i<getNumOfProduct(); i++){
            if (productList.get(i).getID().equals(id)){
                return productList.get(i);
            }
        }
        return null;
    }
    public void deleteProduct(Product product){
        productList.remove(product);
    }   
    public HashMap <Timestamp,String> getUserlogfile(){
        return userlogfile;
    }
}
