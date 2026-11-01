package listener;
import event.*;
import observer.Observer;
import java.util.concurrent.ConcurrentHashMap;
public class Leaderboard implements Observer<Event> {
    private final ConcurrentHashMap<String, Integer> points = new ConcurrentHashMap<>();
    public void onEvent(Event event) {
        PlayerPointsEvent update = (PlayerPointsEvent) event;
        points.put(update.getPlayer(), update.getPoints());
    }
    public int getPoints(String player) { return points.getOrDefault(player, 0); }
}
