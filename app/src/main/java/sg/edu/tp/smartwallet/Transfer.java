package sg.edu.tp.smartwallet;

public class Transfer {

    public String fromMobileNumber;
    public String toMobileNumber;
    public String amount;
    public String notes;
    public String date;
    public String time;


    public Transfer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Transfer(String textViewMobileNumber, String textViewAmount, String textViewNotes,String currentUserMobileNumber, String currentDate, String currentTime) {
        this.fromMobileNumber = currentUserMobileNumber ;
        this.toMobileNumber = textViewMobileNumber;
        this.date = currentDate;
        this.time = currentTime;
        this.amount = textViewAmount;
        this.notes = textViewNotes;
    }
}
