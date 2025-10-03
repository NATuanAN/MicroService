package product_service.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMqConfig {
    private static final String queue = "product_queue";

    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonbMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonbMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    Queue queue() {
        return new Queue(queue, true);
    }

    @Bean
    Queue queue2() {
        return new Queue("user_queue", true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("user_exchange");
    }

    @Bean
    Binding binding(TopicExchange topicExchange, Queue queue2) {
        return BindingBuilder.bind(queue2).to(topicExchange).with("test_user");
    }
}
