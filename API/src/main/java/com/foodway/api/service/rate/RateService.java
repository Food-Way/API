package com.foodway.api.service.rate;

import com.foodway.api.handler.exceptions.CustomerNotFoundException;
import com.foodway.api.handler.exceptions.EstablishmentNotFoundException;
import com.foodway.api.handler.exceptions.RateNotFoundException;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Rate;
import com.foodway.api.record.RequestRate;
import com.foodway.api.record.RequestRateAddOrUpdate;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.EstablishmentRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RateService {

    @Autowired
    RateRepository rateRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;

    public ResponseEntity<List<Rate>> getAll() {
        List<Rate> rates = rateRepository.findAll();
        if(rates.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "List of Rate is empty");
        return ResponseEntity.status(200).body(rates);
    }

    public ResponseEntity<Rate> get(Long id) {
        if(!rateRepository.existsById(id)) throw new RateNotFoundException("Rate not found!");
        return ResponseEntity.status(200).body(rateRepository.findById(id).get());
    }

    public ResponseEntity<List<Rate>> addOrUpdate(RequestRateAddOrUpdate data) {
        if(!customerRepository.existsById(data.idCustomer())){
            throw new CustomerNotFoundException("Customer not found!");
        }
        if(!establishmentRepository.existsById(data.idEstablishment())){
            throw new EstablishmentNotFoundException("Establishment not found!");
        }
        final Customer customer = customerRepository.findById(data.idCustomer()).get();
        final Establishment establishment = establishmentRepository.findById(data.idEstablishment()).get();

        // rates that comed
        List<Rate> rates = new ArrayList<>();
        for(RequestRateAddOrUpdate.DescriptionRate rate : data.rates()){
            rates.add(new Rate(data.idCustomer(), data.idEstablishment(), rate.ratePoint(), rate.name()));
        }

        for(Rate newRate : rates){
            customer.addRate(newRate);
            establishment.addRate(newRate);
            newRate.setIdCustomer(newRate.getIdCustomer());
            newRate.setIdEstablishment(newRate.getIdEstablishment());
            rateRepository.save(newRate);
        }

        return ResponseEntity.status(201).body(rates);
    }

    public ResponseEntity<Void>delete(Long id) {
        if(!rateRepository.existsById(id)) throw new RateNotFoundException("Rate not found!");
        rateRepository.deleteById(id);
        return ResponseEntity.status(200).build();
    }


}
