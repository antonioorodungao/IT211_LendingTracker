package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoSequenceRepository extends MongoRepository<Sequence, String> {
    Optional<Sequence> findById(String id);
}
