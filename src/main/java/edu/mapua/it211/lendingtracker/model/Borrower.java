package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document("borrower")
@Data
public class Borrower {

    @DBRef
    private List<Loan> loans;

    @Id
    private long borrowerId;
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

    public Borrower(){

    }
    public Borrower(String firstName, String lastName, String email, String mobileNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status="OPEN";
    }

}
