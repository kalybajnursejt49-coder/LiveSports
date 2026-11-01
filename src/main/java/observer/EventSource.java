package observer;
import event.Event;
import event.EventType;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
public class EventSource implements Subject<Event> {
    private final ConcurrentHashMap<EventType, CopyOnWriteArrayList<Observer<Event>>> listeners = new ConcurrentHashMap<>();
    public void subscribe(EventType type, Observer<Event> observer) {
        listeners.computeIfAbsent(type, key -> new CopyOnWriteArrayList<>()).addIfAbsent(observer);
    }
    public void unsubscribe(EventType type, Observer<Event> observer) {
        CopyOnWriteArrayList<Observer<Event>> list = listeners.get(type);
        if (list != null) list.remove(observer);
    }
    public void publish(Event event) {
        CopyOnWriteArrayList<Observer<Event>> list = listeners.get(event.getType());
        if (list != null) for (Observer<Event> observer : list) observer.onEvent(event);
    }
}
