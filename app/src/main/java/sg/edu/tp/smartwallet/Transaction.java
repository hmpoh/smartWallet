package sg.edu.tp.smartwallet;

public class Transaction {
    public String fromMobileNumber;
    public String toMobileNumber;
    public String amount;
    public String notes;
    public String date;
    public String time;

    public Transaction() {
    }

    public Transaction(String personMobileNumber, String amountValue, String notes,String account_number, String currentDate, String currentTime) {
        this.fromMobileNumber = account_number ;
        this.toMobileNumber = personMobileNumber;
        this.date = currentDate;
        this.time = currentTime;
        this.amount = amountValue;
        this.notes = notes;

    }

    public String getFromMobileNumber() {
        return fromMobileNumber;
    }

    public void setFromMobileNumber(String fromMobileNumber) {
        this.fromMobileNumber = fromMobileNumber;
    }

    public String getToMobileNumber() {
        return toMobileNumber;
    }

    public void setToMobileNumber(String toMobileNumber) {
        this.toMobileNumber = toMobileNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getDateTime(){return date + " " + time;}

}
