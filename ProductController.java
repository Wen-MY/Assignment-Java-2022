import java.util.ArrayList;
import java.util.Scanner;

public class ProductController{
    private ArrayList <Product> productList;
    Scanner input = new Scanner(System.in);

    public ProductController(){
        productList = new ArrayList <Product>();
    }
    public void menu(){
        System.out.printf("Please select the following: \n");
        System.out.printf("1. View all product\n");
        System.out.printf("2. Search product\n");
        System.out.printf("3. Add product\n");
        System.out.printf("4. Exit\n");
        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1){
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
            menu();
        }  
        else if(choice == 2){
            searchProduct();
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
        else if(choice == 3){
            addProduct();
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
        else if (choice ==4){
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
        }
        else{
            System.out.printf("Invalid input\n");
            System.out.printf("\nPress Enter to continue...");
            input.nextLine();
            menu();
        }
    }
    public void printProduct(Product product){
        System.out.printf(
            "Product ID: %8s  Product Name: %15s Product Type: %8s\n",
            product.getID(), product.getName(), product.getProductType()
        );
    }
    public void searchProduct(){
        System.out.printf("Insert the product ID to search: ");
        String productID = input.next();

        Product product = findProduct(productID);
        if (product == null){
            System.out.printf("Product not found.\n");
        }
        else{
            System.out.printf("product found\n");
            printProduct(product);
            System.out.printf("\nDo you wish to: \n");
            System.out.printf("1. Edit the product\n");
            System.out.printf("2. Delete the product\n");
            System.out.printf("3. Back to menu\n>>>");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1){
                System.out.printf("Please insert the new data.\n");
                System.out.printf("Product Name: ");
                String name = input.next();
                System.out.printf("Product Price: ");
                double price = input.nextDouble();
                System.out.printf("Product Type: ");
                String type = input.next();
                input.nextLine();

                product.setName(name);
                product.setPrice(price);
                product.setProductType(type);

                System.out.printf("Product edited.\n");
            }
            else if(choice == 2){
                System.out.printf("Are you sure? (y/n)");
                char userInput = input.next().charAt(0);
                if (userInput == 'y' || userInput == 'Y'){
                    deleteProduct(product);
                    System.out.printf("Product deleted\n");
                }
                else{
                    System.out.printf("Failed to delete product\n");
                }
            }
        }
    }
    public void addProduct(){
        System.out.printf("Please insert the details of the product.\n");
        System.out.printf("Product ID: ");
        String id = input.next();
        System.out.printf("Product Name: ");
        String name = input.next();
        System.out.printf("Product Price: ");
        double price = input.nextDouble();
        System.out.printf("Product Type: ");
        String type = input.next();
    
        Product product = new Product(id, name, price, type);

        productList.add(product);
        System.out.printf("Product added successfully\n");
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
}