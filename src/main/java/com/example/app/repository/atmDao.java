package com.example.app.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.example.app.models.Atm;


public interface atmDao extends ReactiveMongoRepository<Atm, String> {


	
}
