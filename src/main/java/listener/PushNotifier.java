package listener;
import event.Event;
import observer.Observer;
import java.util.concurrent.atomic.AtomicInteger;
public class PushNotifier implements Observer<Event> {
    private final AtomicInteger sent = new AtomicInteger();
    public void onEvent(Event event) { sent.incrementAndGet(); System.out.println("Push: " + event); }
    public int getSent() { return sent.get(); }
}
