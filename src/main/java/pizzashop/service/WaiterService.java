package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class WaiterService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public WaiterService(MenuRepository menuRepo, PaymentRepository payRepo) {
        this.menuRepo = menuRepo;
        this.payRepo = payRepo;
    }

    public List<MenuDataModel> getMenuData() {
        return menuRepo.getMenu();
    }

    public List<Payment> getPayments() {
        return payRepo.getAll();
    }

    public void addPayment(int table, PaymentType type, double amount) throws Exception{
        if(table < 1 || table > 8)
            throw new Exception("Wrong number of tables");
        if(amount < 0)
            throw new Exception("Negative amount!");
        Payment payment = new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type, List<Payment> l) throws ServiceException {
        double total = 0.0f;
        if(type == null)
            throw new ServiceException("Payment type is null");
        if (l == null || l.isEmpty())
            return total;
        for (Payment p : l) {
            if (p.getType().equals(type))
                total += p.getAmount();
        }
        return total;
    }

}
