package com.textquo.dreamcode.server.services;

import com.textquo.dreamcode.server.services.util.ShardedCounter;

public class ShardedCounterService {

    private ShardedCounter counter;

    public ShardedCounterService() {
    }

    public void incrementCounter(String counterName) {
        counter = new ShardedCounter(counterName);
        counter.increment();
    }

    public void increaseShards(String counterName, int count) {
        counter = new ShardedCounter(counterName);
        counter.addShards(count);
    }

    public Long getCount(String counterName) {
        counter = new ShardedCounter(counterName);
        return counter.getCount();
    }

}