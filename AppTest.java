public class AppTest {
    public static void main(String[] args){
        UserController manageUser = new UserController();
        manageUser.menu();
        Login login = new Login(manageUser);
        login.login();
        ProductController manageProduct = new ProductController();
        manageProduct.menu();
    }
}