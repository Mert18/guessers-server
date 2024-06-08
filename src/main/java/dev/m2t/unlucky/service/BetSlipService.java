package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.model.BetSlip;
import dev.m2t.unlucky.model.enums.SlipStatusEnum;
import dev.m2t.unlucky.repository.BetSlipPagingRepository;
import dev.m2t.unlucky.repository.BetSlipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BetSlipService {
    private static final Logger logger = LoggerFactory.getLogger(BetSlipService.class);
    private final BetSlipRepository betSlipRepository;
    private final BetSlipPagingRepository betSlipPagingRepository;

    public BetSlipService(BetSlipRepository betSlipRepository, BetSlipPagingRepository betSlipPagingRepository) {
        this.betSlipRepository = betSlipRepository;
        this.betSlipPagingRepository = betSlipPagingRepository;
    }

    public BaseResponse createBetSlip(CreateBetSlipRequest createBetSlipRequest, String username) {
        BetSlip betSlip = new BetSlip();
        betSlip.setBets(createBetSlipRequest.getBets());
        betSlip.setDate(LocalDateTime.now());
        betSlip.setStatus(SlipStatusEnum.IN_PROGRESS);
        betSlip.setTotalOdds(createBetSlipRequest.getTotalOdds());
        betSlip.setUsername(username);
        betSlip.setWon(false);
        betSlip.setPublic(true);

        BetSlip savedBetSlip = betSlipRepository.save(betSlip);
        logger.info("Bet slip with id {} created successfully.", savedBetSlip.getId());
        return new BaseResponse("Bet placed successfully.", true, true, savedBetSlip);
    }

    public BaseResponse getPublicBetSlips(Pageable pageable) {
        return new BaseResponse("Bet slips retrieved successfully", true, false, betSlipPagingRepository.findAllByIsPublic(true, pageable));
    }

    public BaseResponse getUserBetSlips(Pageable pageable, String username) {
        return new BaseResponse("Bet slips retrieved successfully", true, false, betSlipPagingRepository.findAllByUsername(username, pageable));
    }
}
