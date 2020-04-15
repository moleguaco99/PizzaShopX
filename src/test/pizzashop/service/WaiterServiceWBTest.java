package pizzashop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WaiterServiceWBTest {
    private static WaiterService waiterService;

    @BeforeAll
    public static void setUp(){
        MenuRepository menuRepository = new MenuRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        waiterService = new WaiterService(menuRepository, paymentRepository);
    }

    @AfterAll
    public static void tearDown(){
        waiterService = null;
    }

    @DisplayName("WBT_valid - paymentType and payment list are valid")
    @ParameterizedTest
    @EnumSource(value = PaymentType.class, names = "CASH", mode = EnumSource.Mode.MATCH_ANY)
    public void test_F02_parameters_valid(PaymentType type){

        List<Payment> l = waiterService.getPayments();

        try{
            double total = waiterService.getTotalAmount(type,l);
            assertTrue(total > 0);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("WBT_valid - paymentType and payment list are valid")
    @ParameterizedTest
    @EnumSource(value = PaymentType.class, names = "CASH", mode = EnumSource.Mode.MATCH_ANY)
    public void test_F02_parameters_valid_02(PaymentType type){

        List<Payment> l = new ArrayList<>();
        l.add(new Payment(6,PaymentType.CASH,43.00));
        l.add(new Payment(1,PaymentType.CARD,12.00));
        l.add(new Payment(5,PaymentType.CASH,42.00));
        l.add(new Payment(1,PaymentType.CARD,74.00));

        try{
            double total = waiterService.getTotalAmount(type,l);
            assertTrue(total == 85.00);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("WBT_invalid - paymentType is invalid")
    public void test_F02_paymentType_invalid(){

        List<Payment> l = waiterService.getPayments();
        assertThrows(ServiceException.class,()->waiterService.getTotalAmount(null,l));

    }

    @DisplayName("WBT_invalid - paymentList is invalid")
    @ParameterizedTest
    @EnumSource(value = PaymentType.class, names = "CARD", mode = EnumSource.Mode.MATCH_ANY)
    public void test_F02_paymentList_invalid_null(PaymentType type) {
            List<Payment> l = new ArrayList<>();
        try {
            double total = waiterService.getTotalAmount(type, l);
            assertTrue(total == 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @DisplayName("WBT_invalid - payment list is invalid")
    @ParameterizedTest
    @EnumSource(value = PaymentType.class, names = "CARD", mode = EnumSource.Mode.MATCH_ANY)
    public void test_F02_paymentList_invalid(PaymentType type) {

        try {
            double total = waiterService.getTotalAmount(type, null);
            assertTrue(total == 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }



}