package example.ratelimit;

public class LeakyBucketLazyRefill {
    private final int bucketSize;
    private int leakRate;
    private long lastTimeLeak;
    private int availableRequestInBucket;

    public LeakyBucketLazyRefill(int bucketSize) {
        this.bucketSize = bucketSize;
        availableRequestInBucket = 0;
        leakRate = 50; // per mint leak 50 request
        lastTimeLeak = System.currentTimeMillis();
    }

    public boolean isRequestAllowed(int request) {
        leak();
        availableRequestInBucket += request;
        if (availableRequestInBucket > bucketSize) {
            System.out.println("can't process request now ");
            return false;
        }
        return true;
    }

    public void leak() {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTimeLeak > 60_000) {
            availableRequestInBucket = Math.max(0, availableRequestInBucket - leakRate);
            lastTimeLeak = curTime;
        }
    }
}
