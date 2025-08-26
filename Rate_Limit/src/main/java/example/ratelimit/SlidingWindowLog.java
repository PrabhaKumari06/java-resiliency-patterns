package example.ratelimit;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLog {
    private final int windowSize;
    private final Queue<Long> logData;
    private int windowMovePerSec;

    public SlidingWindowLog(int windowSize) {
        this.windowSize = windowSize;
        this.logData = new LinkedList<>();
        windowMovePerSec = 1000;
    }

    public boolean isRequestAllowed() {
        long curTime = System.currentTimeMillis();
        while (!logData.isEmpty() && curTime - windowMovePerSec > logData.peek()) {
            logData.poll();
        }

        if (logData.size() < windowSize) {
            logData.offer(System.currentTimeMillis());
            return true;
        }
        return false;

    }
}
