package pizzashop.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class PaymentRepositoryUnitTesting {

    private static PaymentRepository paymentRepository;

    @BeforeAll
    public static void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void test_addPayment_valid() throws Exception {
        Payment test = mock(Payment.class);
        Mockito.when(test.getTableNumber()).thenReturn(1);
        Mockito.when(test.getType()).thenReturn(PaymentType.CARD);
        Mockito.when(test.getAmount()).thenReturn(30d);

        int currentSize = paymentRepository.getAll().size();
        paymentRepository.add(test);
        assertEquals(currentSize + 1, paymentRepository.getAll().size());
    }

    @Test
    void test_addPayment_invalid() {
        Payment test = mock(Payment.class);
        Mockito.when(test.getTableNumber()).thenReturn(1);
        Mockito.when(test.getType()).thenReturn(null);
        Mockito.when(test.getAmount()).thenReturn(30d);

        assertThrows(Exception.class, () -> paymentRepository.add(test));
    }
}