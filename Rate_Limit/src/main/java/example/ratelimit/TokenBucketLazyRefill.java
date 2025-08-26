package example.ratelimit;

public class TokenBucketLazyRefill {
    private final int tokeBucketSize;
    private int availableTokenInBucket;
    private long lastRefillTime;
    private final int refillSize;

    public TokenBucketLazyRefill(int tokeBucketSize, int refillSize) {
        this.tokeBucketSize = tokeBucketSize;
        this.refillSize = refillSize; // how many token need to add per mint.
        availableTokenInBucket = tokeBucketSize; // start with full bucket
        lastRefillTime = System.currentTimeMillis();
    }

    public boolean isRequestAllowed(int request) {
        refill();
        if (availableTokenInBucket < request) {
            System.out.println("can't process the request..");
            return false;
        }
        availableTokenInBucket -= request;
        return true;
    }

    private void refill() {
        long curTime = System.currentTimeMillis();
        if (curTime - lastRefillTime < 60_000) {
            availableTokenInBucket = Math.min(tokeBucketSize, availableTokenInBucket + refillSize);
            lastRefillTime = curTime;
        }
    }
}
