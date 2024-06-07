package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.BetSlip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetSlipRepository extends MongoRepository<BetSlip, String>{
}
