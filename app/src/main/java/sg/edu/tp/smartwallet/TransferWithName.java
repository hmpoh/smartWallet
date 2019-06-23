package sg.edu.tp.smartwallet;

class TransferWithName {
    public String amount;
    public String dateTime;
    public String name;

    public TransferWithName(String amount, String dateTime, String name) {
        this.amount = amount;
        this.dateTime = dateTime;
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
