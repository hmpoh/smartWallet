package sg.edu.tp.smartwallet;

public class Transfer {

    public String fromMobileNumber;
    public String toMobileNumber;
    public String toGroupAccountNumber;
    public String amount;
    public String notes;
    public String date;
    public String time;


    public Transfer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Transfer(String personMobileNumber, String textViewAmount, String textViewNotes,String currentUserMobileNumber, String currentDate, String currentTime) {
        this.fromMobileNumber = currentUserMobileNumber ;
        this.toMobileNumber = personMobileNumber;
        this.date = currentDate;
        this.time = currentTime;
        this.amount = textViewAmount;
        this.notes = textViewNotes;
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
