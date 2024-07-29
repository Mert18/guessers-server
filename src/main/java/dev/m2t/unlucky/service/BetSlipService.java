package dev.m2t.unlucky.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

@Service
public class BetSlipService {
    private static final Logger logger = LoggerFactory.getLogger(BetSlipService.class);
//    private final BetSlipRepository betSlipRepository;
//    private final BetSlipPagingRepository betSlipPagingRepository;
//    private final UserRepository userRepository;
//    private final RoomRepository roomRepository;
//
//    public BetSlipService(BetSlipRepository betSlipRepository, BetSlipPagingRepository betSlipPagingRepository, UserRepository userRepository, RoomRepository roomRepository) {
//        this.betSlipRepository = betSlipRepository;
//        this.betSlipPagingRepository = betSlipPagingRepository;
//        this.userRepository = userRepository;
//        this.roomRepository = roomRepository;
//    }
//
//    public BaseResponse createBetSlip(CreateBetSlipRequest createBetSlipRequest, String username) {
//        User user = userRepository.findByUsername(username);
//        if(user == null) {
//            return new BaseResponse("User not found.", false, false, null);
//        }else if(user.getBalance() < createBetSlipRequest.getStakes()) {
//            return new BaseResponse("Insufficient balance.", false, true, null);
//        }else if(!user.getRooms().contains(createBetSlipRequest.getRoomId())) {
//            return new BaseResponse("You are not a member of this room.", false, false, null);
//        }
//        BetSlip betSlip = new BetSlip();
//        betSlip.setBets(createBetSlipRequest.getBets());
//        betSlip.setDate(LocalDateTime.now());
//        betSlip.setStatus(SlipStatusEnum.IN_PROGRESS);
//        betSlip.setStakes(createBetSlipRequest.getStakes());
//        betSlip.setTotalOdds(createBetSlipRequest.getTotalOdds());
//        betSlip.setUsername(username);
//        betSlip.setRoomId(createBetSlipRequest.getRoomId());
//
//        BetSlip savedBetSlip = betSlipRepository.save(betSlip);
//
//        user.setBalance(user.getBalance() - createBetSlipRequest.getStakes());
//        userRepository.save(user);
//        logger.info("Bet slip with id {} created successfully.", savedBetSlip.getId());
//        return new BaseResponse("Bet placed successfully.", true, true, savedBetSlip);
//    }
//
//    public BaseResponse listSelfBetSlips(Pageable pageable, String username) {
//        return new BaseResponse("Bet slips retrieved successfully", true, false, betSlipPagingRepository.findAllByUsername(username, pageable));
//    }
//
//    public BaseResponse listRoomBetSlips(String roomId, String username, Pageable pageable) {
//        User user = userRepository.findByUsername(username);
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if(room == null) {
//            return new BaseResponse("Room not found.", false, false, null);
//        }else if(user == null) {
//            return new BaseResponse("User not found.", false, false, null);
//        }else if(!room.getUsers().contains(username)) {
//            return new BaseResponse("You are not a member of this room.", false, false, null);
//        }else {
//            Page<BetSlip> betSlips = betSlipPagingRepository.findAllByRoomIdAndDateAfter(roomId, LocalDateTime.now().minusDays(1), pageable);
//            return new BaseResponse("Bet slips retrieved successfully", true, false, betSlips);
//        }
//    }
}
