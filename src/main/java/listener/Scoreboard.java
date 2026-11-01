package listener;
import event.*;
import observer.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
public class Scoreboard implements Observer<Event> {
    private final ConcurrentHashMap<String, AtomicInteger> scores = new ConcurrentHashMap<>();
    public void onEvent(Event event) {
        if (event.getType() == EventType.GOAL) {
            MatchEvent goal = (MatchEvent) event;
            scores.computeIfAbsent(goal.getTeam(), key -> new AtomicInteger()).incrementAndGet();
        }
    }
    public int getScore(String team) { AtomicInteger value = scores.get(team); return value == null ? 0 : value.get(); }
}
