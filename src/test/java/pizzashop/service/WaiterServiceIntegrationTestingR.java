package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WaiterServiceIntegrationTestingR {

    private static WaiterService waiterService;

    @BeforeAll
    public static void setUp(){
        PaymentRepository paymentRepository = new PaymentRepository();
        MenuRepository menuRepository = mock(MenuRepository.class);
        waiterService = new WaiterService(menuRepository, paymentRepository);
    }

    @Test
    void test_addPayment_valid() {
        Payment test = mock(Payment.class);
        Mockito.when(test.getTableNumber()).thenReturn(1);
        Mockito.when(test.getType()).thenReturn(PaymentType.CARD);
        Mockito.when(test.getAmount()).thenReturn(30d);

        try {
            int currentSize = waiterService.getPayments().size();
            waiterService.addPayment(test.getTableNumber(), test.getType(), test.getAmount());
            assertEquals(currentSize + 1, waiterService.getPayments().size());
        }
        catch (Exception ex){
            assert(false);
        }

    }

    @Test
    void test_addPayment_invalid() {
        Payment test = mock(Payment.class);
        Mockito.when(test.getTableNumber()).thenReturn(-1);
        Mockito.when(test.getType()).thenReturn(PaymentType.CARD);
        Mockito.when(test.getAmount()).thenReturn(-20d);

        assertThrows(Exception.class, ()-> waiterService.addPayment(test.getTableNumber(), test.getType(), test.getAmount()));
    }

}