package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WaiterServiceIntegrationTestingRE {

    private static WaiterService waiterService;

    @BeforeAll
    public static void setUp(){
        PaymentRepository paymentRepository = new PaymentRepository();
        MenuRepository menuRepository = mock(MenuRepository.class);
        waiterService = new WaiterService(menuRepository, paymentRepository);
    }

    @Test
    void test_addPayment_valid() {
        int currentSize = waiterService.getPayments().size();
        try{
            waiterService.addPayment(5, PaymentType.CARD, 10d);
            assertEquals(waiterService.getPayments().size(), currentSize + 1);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    void test_addPayment_invalid(){
        int currentSize = waiterService.getPayments().size();
        assertThrows(Exception.class, ()->waiterService.addPayment(3, PaymentType.CARD, -0.1d));
        assertEquals(currentSize, waiterService.getPayments().size());
    }
}