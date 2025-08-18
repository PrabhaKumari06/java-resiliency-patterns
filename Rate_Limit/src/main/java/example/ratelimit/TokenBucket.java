package example.ratelimit;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket {
    private final long bucketCapacity;
    private final AtomicLong availableTokes = new AtomicLong(100);

    public TokenBucket(long bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    public boolean isAllowed(long request) {
        if (availableTokes.get() < request) {
            System.out.println("Too many request, can't process at this point of time ...");
            return false;
        }
        System.out.println("Request processed ..");
        availableTokes.addAndGet(-request);
        return true;

    }

    public void reFill(long tokensToAdd) {
        long newValue = availableTokes.addAndGet(tokensToAdd);
        if (newValue > bucketCapacity)
            availableTokes.set(bucketCapacity);
    }
}
