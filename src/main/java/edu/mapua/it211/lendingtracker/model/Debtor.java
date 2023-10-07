package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document("debtors")
@Data
public class Debtor {

    @Id
    private String borrowerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String photoUrl;
    private String status;
    private LocalDateTime registrationDate;
    {
        this.registrationDate = LocalDateTime.now();
    }

    public Debtor(){

    }
    public Debtor(String firstName, String lastName, String email, String mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status="OPEN";
        this.borrowerId = generateID();
    }

    public String generateID(){
        return registrationDate.getYear() + firstName.substring(0, 3) + lastName.substring(0,3);
    }
}
