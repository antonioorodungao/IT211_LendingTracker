package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.Utils;
import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.exceptions.BorrowerNotFoundException;
import edu.mapua.it211.lendingtracker.model.Borrower;
import edu.mapua.it211.lendingtracker.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    public List<Borrower> listDebtors(){
        return borrowerRepository.findAll();
    }

    public Borrower save(Borrower borrower){
        borrower.setBorrowerId(mongoSequenceGenerator.generateSequence("sequenceborrowerid"));
        borrower.setStatus(Utils.BorrowerStatus.OPEN.name());
        return borrowerRepository.save(borrower);
    }

    public void deleteAll(){
        borrowerRepository.deleteAll();
    }

    public Borrower get(Long id) throws BorrowerNotFoundException {
           Optional<Borrower> result = borrowerRepository.findById(id);
           if(result.isPresent()){
               return result.get();
           }else{
               throw new BorrowerNotFoundException(String.format("Debtor with id %s is not found.", id));
           }

    }

    public void closeBorrower(Long id) {
        borrowerRepository.setDebtorStatusClosed(id, Utils.BorrowerStatus.CLOSED.name());
    }
}
