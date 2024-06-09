package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.dto.request.ListRoomBetSlipsRequest;
import dev.m2t.unlucky.model.BetSlip;
import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.model.enums.SlipStatusEnum;
import dev.m2t.unlucky.repository.BetSlipPagingRepository;
import dev.m2t.unlucky.repository.BetSlipRepository;
import dev.m2t.unlucky.repository.EventRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BetSlipService {
    private static final Logger logger = LoggerFactory.getLogger(BetSlipService.class);
    private final BetSlipRepository betSlipRepository;
    private final BetSlipPagingRepository betSlipPagingRepository;
    private final UserRepository userRepository;

    public BetSlipService(BetSlipRepository betSlipRepository, BetSlipPagingRepository betSlipPagingRepository, UserRepository userRepository) {
        this.betSlipRepository = betSlipRepository;
        this.betSlipPagingRepository = betSlipPagingRepository;
        this.userRepository = userRepository;
    }

    public BaseResponse createBetSlip(CreateBetSlipRequest createBetSlipRequest, String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return new BaseResponse("User not found.", false, false, null);
        }else if(user.getBalance() < createBetSlipRequest.getStakes()) {
            return new BaseResponse("Insufficient balance.", false, false, null);
        }else if(!user.getRooms().contains(createBetSlipRequest.getRoomId())) {
            return new BaseResponse("You are not a member of this room.", false, false, null);
        }
        BetSlip betSlip = new BetSlip();
        betSlip.setBets(createBetSlipRequest.getBets());
        betSlip.setDate(LocalDateTime.now());
        betSlip.setStatus(SlipStatusEnum.IN_PROGRESS);
        betSlip.setTotalOdds(createBetSlipRequest.getTotalOdds());
        betSlip.setUsername(username);
        betSlip.setWon(false);
        betSlip.setRoomId(createBetSlipRequest.getRoomId());

        BetSlip savedBetSlip = betSlipRepository.save(betSlip);

        user.setBalance(user.getBalance() - createBetSlipRequest.getStakes());
        userRepository.save(user);
        logger.info("Bet slip with id {} created successfully.", savedBetSlip.getId());
        return new BaseResponse("Bet placed successfully.", true, true, savedBetSlip);
    }

    public BaseResponse listUserBetSlips(Pageable pageable, String username) {
        return new BaseResponse("Bet slips retrieved successfully", true, false, betSlipPagingRepository.findAllByUsername(username, pageable));
    }
}
