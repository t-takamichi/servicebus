package com.learn.servicebus.jms;

public class AzureServiceBusKey {

    private final String host;

    private final String sharedAccessKeyName;

    private final String sharedAccessKey;

    AzureServiceBusKey(String host, String sharedAccessKeyName, String sharedAccessKey) {
        this.host = host;
        this.sharedAccessKeyName = sharedAccessKeyName;
        this.sharedAccessKey = sharedAccessKey;
    }

    public String getHost() {
        return host;
    }

    public String getSharedAccessKeyName() {
        return sharedAccessKeyName;
    }

    public String getSharedAccessKey() {
        return sharedAccessKey;
    }

}
