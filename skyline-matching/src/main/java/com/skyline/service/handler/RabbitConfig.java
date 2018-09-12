package com.skyline.service.handler;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	
	public static final String queue = "coin-trade";
	
	public static String exchange = "ec-coin-trade";
	
	public static String binding = "coin.trade.#";
	
    @Bean
    public Queue helloQueue() {
        return new Queue(queue);
    }
    
    @Bean  
    public TopicExchange defaultExchange() {  
        return new TopicExchange(exchange);  
    }  
    
    @Bean  
    public Binding binding() {  
        return BindingBuilder.bind(helloQueue()).to(defaultExchange()).with(binding);  
    } 
}
