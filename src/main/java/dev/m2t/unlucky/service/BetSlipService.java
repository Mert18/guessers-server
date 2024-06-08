package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateBetSlipRequest;
import dev.m2t.unlucky.repository.BetSlipPagingRepository;
import dev.m2t.unlucky.repository.BetSlipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return null;
    }

    public BaseResponse getPublicBetSlips(Pageable pageable) {
        return new BaseResponse("Bet slips retrieved successfully", true, false, betSlipPagingRepository.findAllByIsPublic(true, pageable));
    }
}
