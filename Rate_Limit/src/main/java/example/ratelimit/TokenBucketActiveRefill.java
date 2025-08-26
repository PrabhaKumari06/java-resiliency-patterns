package example.ratelimit;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketActiveRefill {
    private final long bucketCapacity;
    private final AtomicLong availableTokes = new AtomicLong(100);

    public TokenBucketActiveRefill(long bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    public boolean isRequestAllowed(long request) {
        if (availableTokes.get() < request) {
            System.out.println("Too many request, can't process at this point of time ...");
            return false;
        }
        System.out.println("Request processed ..");
        availableTokes.addAndGet(-request);
        return true;

    }

    public void refill(long tokensToAdd) {
        long newValue = availableTokes.addAndGet(tokensToAdd);
        if (newValue > bucketCapacity)
            availableTokes.set(bucketCapacity);
    }
}
