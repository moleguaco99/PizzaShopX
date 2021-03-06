package pizzashop.repository;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static String filename = "data/payments.txt";
    private List<Payment> paymentList;
    Logger logger = Logger.getLogger(PaymentRepository.class);

    public PaymentRepository(){
        this.paymentList = new ArrayList<>();
        readPayments();
        BasicConfigurator.configure();
    }

    private void readPayments(){
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        try(BufferedReader br = new BufferedReader(new FileReader(file)) ) {

            String line = null;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }

        } catch (IOException e) {
            logger.trace(e.getMessage(),e);
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        int tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment) throws Exception{
        if(payment.getType() == null)
            throw new Exception("null type of payment");
        paymentList.add(new Payment(payment.getTableNumber(), payment.getType(), payment.getAmount()));
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (Payment p:paymentList) {
                logger.info(p.toString());
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.info("I/O exception");
        }
    }

}
