import com.compay.controller.AuthController;
import com.compay.db.DataBase;
import com.compay.db.InitDataBase;

public class Main {
    public static void main(String[] args) {
        DataBase.initTable();

        InitDataBase.adminInit();
        InitDataBase.addCompanyCard();

        AuthController authController = new AuthController();
        authController.start();

    }
}
