package pl.pzprojects.table;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import pl.pzprojects.dto.PayoutsEthermineDataDto;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class EtherumTableModel {

    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty paidOn;
    private final SimpleDoubleProperty amountInDolars;

    public EtherumTableModel(Double amount, String paidOn, Double amountInDolars) {
        this.amount = new SimpleDoubleProperty(amount);
        this.paidOn = new SimpleStringProperty(paidOn);
        this.amountInDolars = new SimpleDoubleProperty(amountInDolars);
    }

    public static EtherumTableModel of(PayoutsEthermineDataDto dto, Double ethPrice){
        String date = getDate(dto.getPaidOn());

        return new EtherumTableModel(dto.getAmount() / 1000000000000000000L, date, ethPrice * dto.getAmount()/1000000000000000000L);
    }

    private static String getDate(Long paidOnTimeStamp){
        String date;
        if(paidOnTimeStamp > 0){
            Locale locale = new Locale("pl", "PL");
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
            date = dateFormat.format(new Date(paidOnTimeStamp*1000));
        }
        else
            date = "Unpaid Balance";
        return date;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getPaidOn() {
        return paidOn.get();
    }

    public SimpleStringProperty paidOnProperty() {
        return paidOn;
    }

    public void setPaidOn(String paidOn) {
        this.paidOn.set(paidOn);
    }

    public double getAmountInDolars() {
        return amountInDolars.get();
    }

    public SimpleDoubleProperty amountInDolarsProperty() {
        return amountInDolars;
    }
}
