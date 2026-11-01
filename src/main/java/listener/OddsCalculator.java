package listener;
import event.*;
import observer.Observer;
import java.util.concurrent.atomic.AtomicInteger;
public class OddsCalculator implements Observer<Event> {
    private final AtomicInteger goalCount = new AtomicInteger();
    public void onEvent(Event event) { if (event.getType() == EventType.GOAL) goalCount.incrementAndGet(); }
    public int getGoalCount() { return goalCount.get(); }
    public double getHomeWinOdds() { return 2.0 / (1 + goalCount.get()); }
}
