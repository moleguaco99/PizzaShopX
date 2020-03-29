package pizzashop.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;

class WaiterServiceTest {

    private static WaiterService waiterService;

    @BeforeAll
    public static void init(){
        MenuRepository menuRepository = new MenuRepository();
        PaymentRepository paymentRepository = new PaymentRepository();
        waiterService = new WaiterService(menuRepository, paymentRepository);
    }

    @AfterAll
    public static void clear(){
        waiterService = null;
    }

    @DisplayName("ECP_valid01 - amount")
    @ParameterizedTest
    @ValueSource(doubles = {10, 15, 20})
    public void test_F01_ECP_valid01(double amount){
        int numberOfPayments = waiterService.getPayments().size();
        try{
            waiterService.addPayment(2, PaymentType.CARD, amount);
            assertTrue(waiterService.getPayments().size() == numberOfPayments + 1);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("ECP_valid02 - table")
    @ParameterizedTest
    @ValueSource(ints = {3, 6, 8})
    public void test_F01_ECP_valid02(int table){
        int numberOfPayments = waiterService.getPayments().size();
        try{
            waiterService.addPayment(table, PaymentType.CARD, 10d);
            assertTrue(waiterService.getPayments().size() == numberOfPayments + 1);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("ECP_invalid01")
    @ParameterizedTest
    @ValueSource(doubles = {-5, -6, -8})
    public void test_F01_ECP_invalid01(double amount){
        int numberOfPayments = waiterService.getPayments().size();
        assertThrows(Exception.class, ()->waiterService.addPayment(2, PaymentType.CARD, amount));
        assertTrue(numberOfPayments == waiterService.getPayments().size());
    }

    @DisplayName("ECP_invalid01")
    @ParameterizedTest
    @ValueSource(ints = {9, 10, 11})
    public void test_F01_ECP_invalid02(int table){
        int numberOfPayments = waiterService.getPayments().size();
        assertThrows(Exception.class, ()->waiterService.addPayment(table, PaymentType.CARD, 5d));
        assertTrue(numberOfPayments == waiterService.getPayments().size());
    }

    @Test
    @DisplayName("BVA_valid01")
    public void test_F01_BVA_valid01(){
        int numberOfPayments = waiterService.getPayments().size();
        try{
            waiterService.addPayment(2, PaymentType.CARD, 0d);
            assertTrue(waiterService.getPayments().size() == numberOfPayments + 1);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }


    @DisplayName("BVA_valid02")
    @ParameterizedTest
    @ValueSource(ints = {1, 8})
    public void test_F01_BVA_valid02(int table){
        int numberOfPayments = waiterService.getPayments().size();
        try{
            waiterService.addPayment(table, PaymentType.CARD, 10d);
            assertTrue(waiterService.getPayments().size() == numberOfPayments + 1);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("BVA_invalid01")
    public void test_F01_BVA_invalid01(){
        int numberOfPayments = waiterService.getPayments().size();
        assertThrows(Exception.class, ()->waiterService.addPayment(3, PaymentType.CARD, -0.1d));
        assertTrue(numberOfPayments == waiterService.getPayments().size());
    }

    @DisplayName("BVA_invalid02")
    @ParameterizedTest
    @ValueSource(ints = {0, 9})
    public void test_F01_BVA_invalid02(int table){
        int numberOfPayments = waiterService.getPayments().size();
        assertThrows(Exception.class, ()->waiterService.addPayment(table, PaymentType.CARD, 0d));
        assertTrue(numberOfPayments == waiterService.getPayments().size());
    }

}