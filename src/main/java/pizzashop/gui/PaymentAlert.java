package pizzashop.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;
import pizzashop.model.PaymentType;
import pizzashop.service.PaymentOperation;
import pizzashop.service.WaiterService;

import java.util.Optional;

public class PaymentAlert implements PaymentOperation {

    
    Logger logger = Logger.getLogger(PaymentAlert.class);
    private String dottedLines = "--------------------------";
  
    private WaiterService service;

    public PaymentAlert(WaiterService service){

        this.service=service;
    }

    @Override
    public void cardPayment() {
        logger.info(dottedLines);
        logger.info("Paying by card...");
        logger.info("Please insert your card!");
        logger.info(dottedLines);
    }
    @Override
    public void cashPayment() {
        logger.info(dottedLines);
        logger.info("Paying cash...");
        logger.info("Please show the cash...!");
        logger.info(dottedLines);
    }
    @Override
    public void cancelPayment() {
        logger.info(dottedLines);
        logger.info("Payment choice needed...");
        logger.info(dottedLines);
    }
      public void showPaymentAlert(int tableNumber, double totalAmount ) {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent() && result.get() == cardPayment) {
            cardPayment();
            service.addPayment(tableNumber, PaymentType.CARD,totalAmount);
        } else if (result.isPresent() && result.get() == cashPayment) {
            cashPayment();
            service.addPayment(tableNumber, PaymentType.CASH,totalAmount);
        } else if (result.isPresent() && result.get() == cancel) {
             cancelPayment();
        } else {
            cancelPayment();
        }
    }
}
