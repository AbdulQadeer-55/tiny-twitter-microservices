package main.java.org.ac.cst8277.Khan.Yasar.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    private final WebClient webClient = WebClient.create("http://localhost:8081");

    private Long validateTokenAndGetUserId(String token) {
        try {
            UserDto user = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users/validate").queryParam("token", token).build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block(); 
            return user != null ? user.getId() : null;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping
    public Flux<Message> getAllMessages(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || validateTokenAndGetUserId(token) == null) return Flux.empty();
        return messageRepository.findAll();
    }

    @GetMapping("/producer/{id}")
    public Flux<Message> getProducerMessages(@RequestHeader(value = "Authorization", required = false) String token, @PathVariable Long id) {
        if (token == null || validateTokenAndGetUserId(token) == null) return Flux.empty();
        return messageRepository.findByUserId(id);
    }

    @GetMapping("/subscriber")
    public Flux<Message> getSubscriberTimeline(@RequestHeader(value = "Authorization", required = false) String token) {
        Long subscriberId = validateTokenAndGetUserId(token);
        if (subscriberId == null) return Flux.empty();
        return messageRepository.findMessagesForSubscriber(subscriberId);
    }
}