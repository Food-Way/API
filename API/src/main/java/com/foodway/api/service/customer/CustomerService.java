package com.foodway.api.service.customer;

import com.foodway.api.model.Comment;
import com.foodway.api.model.Customer;
import com.foodway.api.model.Establishment;
import com.foodway.api.model.Favorite;
import com.foodway.api.record.DTOs.CommentDTO;
import com.foodway.api.record.DTOs.CustomerProfileDTO;
import com.foodway.api.record.DTOs.EstablishmentDTO;
import com.foodway.api.record.RequestUserCustomer;
import com.foodway.api.record.UpdateCustomerData;
import com.foodway.api.repository.CommentRepository;
import com.foodway.api.repository.CustomerRepository;
import com.foodway.api.repository.FavoriteRepository;
import com.foodway.api.repository.RateRepository;
import com.foodway.api.service.establishment.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EstablishmentService establishmentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    public ResponseEntity<List<Customer>> getCustomers() {
        if (customerRepository.findAll().isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(customerRepository.findAll());
    }

    public ResponseEntity<Customer> getCustomer(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> ResponseEntity.status(200).body(value)).orElseGet(() -> ResponseEntity.status(404).build());
    }

    public ResponseEntity<Customer> putCustomer(UUID id, UpdateCustomerData data) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        customerOptional.get().update(Optional.ofNullable(data));
        return ResponseEntity.status(200).body(customerRepository.save(customerOptional.get()));
    }

    public ResponseEntity<Customer> saveCustomer(RequestUserCustomer userCreateDto) {
        Customer createdCustomer = new Customer(userCreateDto);
        return ResponseEntity.status(201).body(customerRepository.save(createdCustomer));
    }

    public ResponseEntity<Customer> deleteCustomer(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    public ResponseEntity<Favorite> addFavoriteEstablishment(UUID idCustomer, UUID idEstablishment) {
        UUID customerId = getCustomer(idCustomer).getBody().getIdUser();
        UUID establishmentId = establishmentService.getEstablishment(idEstablishment).getBody().getIdUser();
        Favorite favorite = new Favorite(customerId, establishmentId);
        Favorite saved = favoriteRepository.save(favorite);
        return ResponseEntity.status(201).body(saved);
    }

    public ResponseEntity<CustomerProfileDTO> getCustomerProfile(UUID idCustomer) {
        Customer customer = getCustomer(idCustomer).getBody();
        Double customerAvgRate = rateRepository.getAvgIndicatorCustomer(idCustomer);
        long customerQtdComments = commentRepository.countByIdCustomer(idCustomer);
        List<Comment> comments = commentRepository.findTop4ByIdCustomer(idCustomer).orElse(new ArrayList<>());
        List<Favorite> favorites = favoriteRepository.findTop4ByIdCustomer(idCustomer).orElse(new ArrayList<>());
        List<EstablishmentDTO> favoriteEstablishments = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();

        comments.forEach(comment -> {
            String establishmentName = establishmentService.getEstablishment(comment.getIdEstablishment()).getBody().getEstablishmentName();
            CommentDTO commentDTO = new CommentDTO(establishmentName, "Teste", comment.getComment(), 10.0);
            commentDTOS.add(commentDTO);
        });

        favorites.forEach(favorite -> {
            Establishment establishment = establishmentService.getEstablishment(favorite.getIdEstablishment()).getBody();
            EstablishmentDTO establishmentDTO = new EstablishmentDTO(establishment.getEstablishmentName(), establishment.getGeneralRate(), establishment.getCulinary(), establishment.getProfilePhoto());
            favoriteEstablishments.add(establishmentDTO);
        });

        CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customer.getName(), customer.getProfilePhoto(), customer.getBio(), 0, customerAvgRate, 0, customerQtdComments, commentDTOS, favoriteEstablishments);
        return ResponseEntity.status(200).body(customerProfileDTO);
    }
}
