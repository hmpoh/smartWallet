package sg.edu.tp.smartwallet;

public class User {

    public String name;
    public Long mobileNumber;

    public User(String name, Long mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
