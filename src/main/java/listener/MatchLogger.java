package listener;
import event.Event;
import observer.Observer;
import java.util.concurrent.atomic.AtomicInteger;
public class MatchLogger implements Observer<Event> {
    private final AtomicInteger count = new AtomicInteger();
    public void onEvent(Event event) { count.incrementAndGet(); System.out.println("Log: " + event); }
    public int getCount() { return count.get(); }
}
