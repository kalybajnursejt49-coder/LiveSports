package listener;
import event.*;
import observer.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
public class FantasyPoints extends EventSource implements Observer<Event> {
    private final ConcurrentHashMap<String, AtomicInteger> points = new ConcurrentHashMap<>();
    public void onEvent(Event event) {
        if (!(event instanceof MatchEvent)) return;
        MatchEvent match = (MatchEvent) event;
        int change = 0;
        if (match.getType() == EventType.GOAL) change = 5;
        if (match.getType() == EventType.YELLOW_CARD) change = -1;
        if (match.getType() == EventType.RED_CARD) change = -3;
        if (change != 0) {
            int total = points.computeIfAbsent(match.getPlayer(), key -> new AtomicInteger()).addAndGet(change);
            publish(new PlayerPointsEvent(match.getPlayer(), total));
        }
    }
    public int getPoints(String player) { AtomicInteger value = points.get(player); return value == null ? 0 : value.get(); }
}
