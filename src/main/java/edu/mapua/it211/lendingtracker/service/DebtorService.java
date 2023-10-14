package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.exceptions.DebtorNotFoundException;
import edu.mapua.it211.lendingtracker.model.Borrower;
import edu.mapua.it211.lendingtracker.repository.DebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtorService {

    @Autowired
    private DebtorRepository debtorRepository;

    public List<Borrower> listDebtors(){
        return debtorRepository.findAll();
    }

    public void save(Borrower borrower){
        borrower.setBorrowerId(borrower.generateID());
        debtorRepository.save(borrower);
    }

    public void deleteAll(){
        debtorRepository.deleteAll();
    }

    public Borrower get(String id) throws DebtorNotFoundException {
           Optional<Borrower> result = debtorRepository.findById(id);
           if(result.isPresent()){
               return result.get();
           }else{
               throw new DebtorNotFoundException(String.format("Debtor with id %s is not found.", id));
           }

    }

    public void deleteDebtor(String id) {
        debtorRepository.setDebtorStatusClosed(id);
    }
}
