package example.ratelimit;

public class FixedSlidingWindow {
    private final int windowSize;
    private long lastTimeWindowMoved;
    private final long moveWindowPerMin;
    private int availableRequestInWindow;

    public FixedSlidingWindow(int windowSize) {
        this.windowSize = windowSize;
        lastTimeWindowMoved = System.currentTimeMillis();
        availableRequestInWindow = 0;
        moveWindowPerMin = 60_000;
    }

    public boolean isRequestAllowed(int request) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTimeWindowMoved > moveWindowPerMin) {
            availableRequestInWindow = 0;
            lastTimeWindowMoved = curTime;
        }
        availableRequestInWindow += request;
        if (availableRequestInWindow > windowSize) {
            System.out.println("can't process the request");
            return false;
        }
        return true;
    }
}
