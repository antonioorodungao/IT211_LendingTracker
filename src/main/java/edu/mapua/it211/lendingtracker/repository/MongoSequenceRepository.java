package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.SequenceLoanId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoSequenceRepository extends MongoRepository<SequenceLoanId, String> {
    Optional<SequenceLoanId> findById(String id);
}
