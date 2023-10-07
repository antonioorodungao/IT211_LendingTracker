package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.exceptions.DebtorNotFoundException;
import edu.mapua.it211.lendingtracker.model.Debtor;
import edu.mapua.it211.lendingtracker.repository.DebtorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebtorService {

    @Autowired
    private DebtorRepository debtorRepository;

    public List<Debtor> listDebtors(){
        return debtorRepository.findAll();
    }

    public void save(Debtor debtor){
        debtor.setBorrowerId(debtor.generateID());
        debtorRepository.save(debtor);
    }

    public void deleteAll(){
        debtorRepository.deleteAll();
    }

    public Debtor get(String id) throws DebtorNotFoundException {
           Optional<Debtor> result = debtorRepository.findById(id);
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
