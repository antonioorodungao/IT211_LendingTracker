package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sequenceloanid")
@Data
public class SequenceLoanId {
    @Id
    private String id;
    private int seq;
}
