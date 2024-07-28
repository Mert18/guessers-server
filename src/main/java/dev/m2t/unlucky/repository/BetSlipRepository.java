package dev.m2t.unlucky.repository;

import dev.m2t.unlucky.model.enums.SlipStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetSlipRepository extends MongoRepository<BetSlip, String>{
    List<BetSlip> findByRoomIdAndStatus(String roomId, SlipStatusEnum status);
}
