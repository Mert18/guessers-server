package dev.m2t.guessers.service;

import dev.m2t.guessers.model.LiveActivityMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class LiveActivityService {

    private static final Logger logger = LoggerFactory.getLogger(LiveActivityService.class);
    private static final String TOPIC = "/topic/live-activity";

    private final SimpMessagingTemplate messagingTemplate;
    private final RandomGenerator rng = RandomGenerator.getDefault();

    private static final List<String> SENTENCES = List.of(
            "speedy_hawk guesses his friend will arrive more than 20 minutes late to the meetup",
            "lucky_seven guesses the new game update will drop before the weekend",
            "nightowl99 guesses his team will win the next ranked match",
            "blazing_ace guesses the road trip will take at least an hour longer than planned",
            "thunder_wolf guesses his friend will finish the series in under three days",
            "iron_fist22 guesses the office will run out of coffee before noon",
            "nova_rider guesses it will rain on the day of the outdoor event",
            "silent_bob77 guesses his friend will reach Diamond rank by end of the season",
            "cosmic_ray guesses the new restaurant will have a 40-minute wait time",
            "pixel_storm guesses the flight will be delayed by more than an hour",
            "dark_horse44 guesses his friend will not finish the book before the movie comes out",
            "swift_arrow guesses the match will go into overtime",
            "blue_falcon guesses his team will win the office fantasy league this week",
            "venom_rush guesses the group trip destination will change at least once more",
            "sky_limit guesses his friend will swap mains before the next ranked season",
            "echo_nine guesses the project deadline will be pushed back again",
            "ghost_rider7 guesses it will snow before the end of the month",
            "star_chaser guesses his friend will hit level 100 before him",
            "razor_edge guesses the new season premiere will break the streaming record",
            "bold_eagle guesses his friend will still be on the same quest as last week",
            "silver_fox guesses the startup will announce a new funding round this quarter",
            "turbo_flash guesses his friend will switch to a new main character after the next patch",
            "deep_space guesses the concert will sell out before he gets a ticket",
            "neon_tiger guesses his friend will reach a new personal best at the gym this month",
            "cold_steel guesses the sequel will score lower than the original on review sites",
            "iron_hawk guesses his duo will win more than 5 matches tonight",
            "rogue_wave guesses the team will finish the escape room with time to spare",
            "alpha_dog99 guesses his friend will not last a week on the new diet",
            "wild_card guesses the player transfer will be announced before Friday",
            "solar_wind guesses it will be the hottest day of the year this weekend",
            "quick_draw guesses his friend will get promoted to the next rank this month",
            "blue_thunder guesses the match will end in a draw",
            "night_fury guesses the new phone model will be announced at the next event",
            "red_storm guesses his friend will reach 1000 hours in the game before summer",
            "free_spirit guesses the team will win their next three home games",
            "gold_rush77 guesses the package will not arrive within the estimated delivery window",
            "laser_beam guesses his friend will choose the harder difficulty on the first run",
            "magnet_pro guesses the hackathon winner will be a solo participant",
            "sharp_eye guesses his friend will finish the campaign mode in one sitting",
            "sky_walker guesses the app will reach one million users by end of year",
            "jade_dragon guesses his friend will not make it to the final round of the tournament",
            "black_mamba guesses the road will be closed on the way back",
            "cyber_punk guesses his team will drop out of the top 3 by next week",
            "blaze_runner guesses the host will run out of parking spots before the event starts",
            "titan_force guesses his friend will place top 10 in the online tournament",
            "hyper_speed guesses the game will have server issues on launch day",
            "zenith_pro guesses his friend will pick the same starter as last time",
            "iron_will guesses the study group will go off topic within the first 15 minutes",
            "pixel_hero guesses the speedrun record will be broken this month",
            "astro_boy guesses the underdog team will advance past the group stage",
            "lone_wolf guesses his friend will not finish the side quests before the main story",
            "power_surge guesses the event will sell more tickets than last year",
            "frost_bite guesses his friend will not sleep before 3 AM during the game jam",
            "heat_wave guesses the new map will become the most played within a week",
            "arctic_fox guesses his friend will forget the starting lineup he predicted",
            "super_nova guesses the early access game will leave early access within 6 months",
            "dark_knight guesses the new character reveal will happen at the next major event",
            "bolt_action guesses his friend will reach the final boss before him",
            "mind_blast guesses the tournament bracket will have at least two major upsets",
            "sun_blazer guesses the voice actor announcement will leak before the official reveal",
            "shadow_run guesses his friend will pick the red option every time there is a choice",
            "phantom_ace guesses the server maintenance will last longer than the announced window",
            "hyper_lion guesses the local sports team will win their next away game",
            "storm_rider guesses his friend will not complete the daily challenge streak",
            "ultra_pro guesses the movie will get a sequel greenlit within 3 months of release",
            "mega_force guesses the new meta will be solved within the first two weeks of the patch",
            "alpha_rise guesses his friend will switch from PC to controller mid-session",
            "nova_blast guesses the sold-out item will be restocked before next week",
            "steel_jaw guesses the community vote will end with an unexpected winner",
            "volt_surge guesses his friend will still be in character creation after 30 minutes",
            "sky_punch guesses the world record attempt will succeed on the first try",
            "iron_cross guesses the weather will change before the outdoor match ends",
            "thunder_cat guesses his friend will spend more time on the tutorial than the actual game",
            "blaze_fox guesses the limited-time event item will not be obtained before the deadline",
            "ace_high guesses the underdog player will reach the semifinals",
            "top_gun guesses his friend will still not have a guild by next week",
            "steel_rain guesses the new studio album will be released before the announced date",
            "chrome_wolf guesses the beta test will reveal a game-breaking bug within the first day",
            "dark_flame guesses his friend will reach master rank before the season ends",
            "night_hawk guesses the expansion will add more content than any previous one",
            "fire_bolt guesses the speedrunner will beat his own record at the next event",
            "turbo_jet guesses the film will surpass the book's rating on review sites",
            "power_play guesses his friend will regret not pre-ordering when stock runs out",
            "quick_step guesses the patch notes will nerf the top-played character",
            "blaze_storm guesses the charity stream will exceed last year's donation total",
            "ice_king guesses his friend will not finish assembling the furniture without help",
            "wild_fire guesses the DLC announcement will come sooner than the community expects",
            "sky_net guesses the match will be decided in the last 5 minutes",
            "thunder_dome guesses his friend will reach the credit roll before finishing all side content",
            "sonic_boom guesses the fan theory will turn out to be correct",
            "flash_point guesses the open beta will attract more players than the closed one",
            "warp_speed guesses his friend will abandon the hardcore run before the final boss",
            "hyper_drive guesses the roster change will improve the team's ranking within two weeks",
            "zero_gravity guesses the community event prize will be claimed in under an hour",
            "light_speed guesses his friend will not read a single tooltip in the entire game",
            "quantum_ace guesses the next update will add cross-platform play",
            "proton_rush guesses the hall-of-fame inductee will be the least expected nominee",
            "neutron_star guesses his friend will complete the challenge on the very last attempt",
            "event_horizon guesses the final patch of the year will be the most controversial one"
    );

    public LiveActivityService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 10000)
    public void broadcastLiveActivity() {
        String sentence = SENTENCES.get(rng.nextInt(SENTENCES.size()));
        LiveActivityMessage message = new LiveActivityMessage(sentence);
        logger.debug("Broadcasting live activity: {}", sentence);
        messagingTemplate.convertAndSend(TOPIC, message);
    }
}
