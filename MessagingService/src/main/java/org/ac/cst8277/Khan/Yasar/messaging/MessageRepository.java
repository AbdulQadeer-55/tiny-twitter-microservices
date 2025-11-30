package main.java.org.ac.cst8277.Khan.Yasar.messaging;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;

public interface MessageRepository extends R2dbcRepository<Message, Long> {
    Flux<Message> findByUserId(Long userId);

    @Query("SELECT m.* FROM messages m JOIN subscriptions s ON m.user_id = s.producer_id WHERE s.subscriber_id = :subscriberId")
    Flux<Message> findMessagesForSubscriber(Long subscriberId);
}