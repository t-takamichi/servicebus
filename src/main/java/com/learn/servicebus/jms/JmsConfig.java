package com.learn.servicebus.jms;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.jms.servicebus.connection-string}")
    private String senderConnectionString;

    @Value("${spring.jms.servicebus.idle-timeout}")
    private int senderIdleTimeout;

    @Value("${spring.jms.listener.connection-string}")
    private String listenerConnectionString;

    @Value("${spring.jms.listener.idle-timeout}")
    private int listenerIdleTimeout;


    private static final String AMQP_URI_FORMAT = "amqps://%s?amqp.idleTimeout=%d";

    @Bean
    @Primary
    public ConnectionFactory senderConnectionFactory() {
        AzureServiceBusKey azureServiceBusKey = AzureConnectionStringResolver.getServiceBusKey(senderConnectionString);
        String host = azureServiceBusKey.getHost();
        String sasKeyName = azureServiceBusKey.getSharedAccessKeyName();
        String sasKey = azureServiceBusKey.getSharedAccessKey();

        String remoteUri = String.format(AMQP_URI_FORMAT, host, senderIdleTimeout);
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory();
        jmsConnectionFactory.setRemoteURI(remoteUri);
        jmsConnectionFactory.setUsername(sasKeyName);
        jmsConnectionFactory.setPassword(sasKey);
        return new CachingConnectionFactory(jmsConnectionFactory);
    }

    @Primary
    @Bean
    public JmsListenerContainerFactory<?> listenerQeueFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory queueFactory = new DefaultJmsListenerContainerFactory();
        queueFactory.setConnectionFactory(listenerConnectionFactory());
        return queueFactory;
    }

    @Bean
    public ConnectionFactory listenerConnectionFactory() {
        AzureServiceBusKey azureServiceBusKey = AzureConnectionStringResolver.getServiceBusKey(listenerConnectionString);
        String host = azureServiceBusKey.getHost();
        String sasKeyName = azureServiceBusKey.getSharedAccessKeyName();
        String sasKey = azureServiceBusKey.getSharedAccessKey();

        String remoteUri = String.format(AMQP_URI_FORMAT, host, listenerIdleTimeout);
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory();
        jmsConnectionFactory.setRemoteURI(remoteUri);
        jmsConnectionFactory.setUsername(sasKeyName);
        jmsConnectionFactory.setPassword(sasKey);
        return new CachingConnectionFactory(jmsConnectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(senderConnectionFactory());
        jmsTemplate.setDefaultDestinationName("test-queue-jms");
        return jmsTemplate;
    }
}
