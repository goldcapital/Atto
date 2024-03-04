import controller.AuthController;
import db.DataBase;
import db.InitDataBase;

public class Main {

    public static void main(String[] args) {
        DataBase.initTable();

        InitDataBase.adminInit();
        InitDataBase.addCompanyCard();

        AuthController authController = new AuthController();
        authController.start();

    }
}
