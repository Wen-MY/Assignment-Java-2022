import java.util.*;

public class ProductController{
    private Login login;
    private HashMap <Login, String> userlogfile;
    private ArrayList <Product> productList;
    Scanner input = new Scanner(System.in);

    public ProductController(ArrayList <Product> initList, Login initLogin){
        productList = initList;
        login = initLogin;
    }
    public void menu(){
        while (true){
            System.out.printf("Please select the following: \n");
            System.out.printf("1. View all product\n");
            System.out.printf("2. Search product\n");
            System.out.printf("3. Add product\n");
            System.out.printf("4. Exit\n");
            int choice;
            while(true){
                try{
                    choice = Integer.parseInt(input.nextLine());
                    break;
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
            }

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
            }  
            else if(choice == 2){
                searchProduct();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if(choice == 3){
                addProduct();
                System.out.printf("\nPress Enter to continue...");
                input.nextLine();
            }
            else if (choice ==4){
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
            "Product ID: %8s  Product Name: %15s Product Type: %8s\n",
            product.getID(), product.getName(), product.getProductType()
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
            printProduct(product);
            System.out.printf("\nDo you wish to: \n");
            System.out.printf("1. Edit the product\n");
            System.out.printf("2. Delete the product\n");
            System.out.printf("3. Back to menu\n>>>");
            int choice;
            while(true){
                try{
                    choice = Integer.parseInt(input.nextLine());
                    break;
                } catch (NumberFormatException ex){
                    System.out.printf("Integer Input Required\n");
                }
            }

            if (choice == 1){
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
                    login, 
                    String.format(
                        "Edited Product Name: '%s' to '%s'\nPrice: '%.2f' to '%.2f'\nType: '%s' to '%s'",
                        initName, product.getName(), initPrice, product.getPrice(), initType, product.getProductType()
                    )
                );
            }
            else if(choice == 2){
                System.out.printf("Are you sure? (y/n)");
                char userInput = input.next().charAt(0);
                if (userInput == 'y' || userInput == 'Y'){
                    deleteProduct(product);
                    System.out.printf("Product deleted\n");
                    userlogfile.put(login, "Deleted Product: "+product.getName());
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
        String id = input.nextLine();
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
    
        Product product = new Product(id, name, price, type);

        productList.add(product);
        System.out.printf("Product added successfully\n");
        userlogfile.put(login, "Added Product: "+product.getName());
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
    public HashMap <Login, String> getUserlogfile(){
        return userlogfile;
    }
}