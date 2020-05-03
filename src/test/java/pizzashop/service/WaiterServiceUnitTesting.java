package pizzashop.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WaiterServiceUnitTesting {

    private static PaymentRepository paymentRepository;
    private static WaiterService waiterService;

    @BeforeAll
    public static void setUp() {
        paymentRepository = mock(PaymentRepository.class);
        MenuRepository menuRepository = mock(MenuRepository.class);
        waiterService = new WaiterService(menuRepository, paymentRepository);
    }

    @Test
    void test_addPayment_valid() throws Exception {
        Payment test = mock(Payment.class);
        Mockito.when(test.getTableNumber()).thenReturn(1);
        Mockito.when(test.getType()).thenReturn(PaymentType.CARD);
        Mockito.when(test.getAmount()).thenReturn(30d);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            Object[] arguments = invocation.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                Payment payment = (Payment) arguments[0];
                System.out.println(payment);
            }
            return null;
        }).when(paymentRepository).add(test);

        waiterService.addPayment(test.getTableNumber(), test.getType(), test.getAmount());
    }

    @Test
    void test_addPayment_invalid() {
        try {
            Payment test = mock(Payment.class);
            Mockito.when(test.getTableNumber()).thenReturn(1);
            Mockito.when(test.getType()).thenReturn(null);
            Mockito.when(test.getAmount()).thenReturn(30d);

            Mockito.doThrow(Exception.class).when(paymentRepository).add(test);
            waiterService.addPayment(test.getTableNumber(), test.getType(), test.getAmount());
        }
        catch (Exception ignored) {
            assert(false);
        }
    }

}