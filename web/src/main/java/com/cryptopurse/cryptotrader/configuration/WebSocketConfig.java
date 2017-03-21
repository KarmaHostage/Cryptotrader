package com.cryptopurse.cryptotrader.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);

    @Value("${com.cryptopurse.cryptotrader.messaging.ws.stomp-endpoint}")
    private String stompEndpoint;
    @Value("${com.cryptopurse.cryptotrader.messaging.ws.app-endpoint}")
    private String appEndpoint;
    @Value("${com.cryptopurse.cryptotrader.messaging.ws.heartbeatMillis}")
    private int heartbeatTime;
    @Value("${com.cryptopurse.cryptotrader.messaging.ws.disconnectDelayMillis}")
    private int disconnectDelay;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ThreadPoolTaskScheduler messageBrokerTaskScheduler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        ThreadPoolTaskScheduler pingScheduler = new ThreadPoolTaskScheduler();
        pingScheduler.initialize();
        config.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{heartbeatTime, 0L}).setTaskScheduler(pingScheduler);
        config.setApplicationDestinationPrefixes(appEndpoint);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        messageBrokerTaskScheduler.setErrorHandler(t -> LOGGER.info("Error in messageBrokerTask", t));
        registry.addEndpoint(stompEndpoint.substring(stompEndpoint.lastIndexOf('/')))
                .setAllowedOrigins("*")
                .withSockJS()
                .setTaskScheduler(messageBrokerTaskScheduler)
                .setHeartbeatTime(heartbeatTime)
                .setDisconnectDelay(disconnectDelay);
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return true;
    }

}