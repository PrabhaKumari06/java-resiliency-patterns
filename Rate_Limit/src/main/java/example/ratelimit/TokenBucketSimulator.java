package example.ratelimit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenBucketSimulator {
    public static void main(String[] args) {

        TokenBucket tokenBucket = new TokenBucket(50);
        ExecutorService requests = Executors.newFixedThreadPool(2);
        requests.submit(() -> {
            while (true) {
                tokenBucket.isAllowed(1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ScheduledExecutorService fillToken = Executors.newScheduledThreadPool(1);
        fillToken.scheduleAtFixedRate(() -> {
            tokenBucket.reFill(30);
            System.out.println("refilled ..");
        }, 0, 5, TimeUnit.SECONDS);

    }

}

