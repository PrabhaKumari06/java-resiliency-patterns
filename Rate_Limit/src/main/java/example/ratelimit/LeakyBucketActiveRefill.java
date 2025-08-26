package example.ratelimit;

import java.util.concurrent.atomic.AtomicLong;

public class LeakyBucketActiveRefill {
    private final long bucketCapacity;
    private final long leakRate;
    private final AtomicLong availableRequestInBucket = new AtomicLong(0);

    public LeakyBucketActiveRefill(long bucketCapacity, long leakRate) {
        this.bucketCapacity = bucketCapacity;
        this.leakRate = leakRate;
    }

    public boolean isAllowed(long request) {
        if (bucketCapacity > availableRequestInBucket.get()) {
            // serve the request
            availableRequestInBucket.addAndGet(request);
            System.out.println("request processed ....");
            return true;
        }
        // stop request
        System.out.println("can't process the request at this point of time...");
        return false;
    }

    public void leak() {
        if (availableRequestInBucket.get() < leakRate)
            availableRequestInBucket.set(0);
        else
            availableRequestInBucket.addAndGet(-leakRate);
    }
}
