package pizzashop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import pizzashop.controller.MainGUIController;
import pizzashop.gui.KitchenGUI;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.ServiceException;
import pizzashop.service.WaiterService;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        MenuRepository repoMenu = new MenuRepository();
        PaymentRepository payRepo = new PaymentRepository();
        WaiterService service = new WaiterService(repoMenu, payRepo);
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Main.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainFXML.fxml"));
        Parent box = loader.load();
        MainGUIController ctrl = loader.getController();
        ctrl.setService(service);
        primaryStage.setTitle("PizeriaX");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(false);
        primaryStage.setOnCloseRequest(event -> {
            Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to exit the Main window?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.YES) {
                    try {
                        logger.info("Incasari cash: " + service.getTotalAmount(PaymentType.CASH, payRepo.getAll()));
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                    try {
                        logger.info("Incasari card: " + service.getTotalAmount(PaymentType.CARD, payRepo.getAll()));
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    }
                    primaryStage.close();
                }
                // consume event
                else if (result.get() == ButtonType.NO) {
                    event.consume();
                } else {
                    event.consume();
                }
            }
        });
        primaryStage.setScene(new Scene(box));
        primaryStage.show();
        KitchenGUI kitchenGUI = new KitchenGUI();
        kitchenGUI.initialize();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
