package visitor;
import model.*;
import java.util.ArrayList;
import java.util.List;
public class TopPlayersVisitor implements Visitor<List<Player>> {
    private final int minimum;
    private final List<Player> players = new ArrayList<>();
    public TopPlayersVisitor(int minimum) { this.minimum = minimum; }
    private List<Player> add(Player player) { if (player.getRating() >= minimum) players.add(player); return players; }
    public List<Player> visit(League league) { return players; }
    public List<Player> visit(Club club) { return players; }
    public List<Player> visit(Team team) { return players; }
    public List<Player> visit(Goalkeeper player) { return add(player); }
    public List<Player> visit(Defender player) { return add(player); }
    public List<Player> visit(Forward player) { return add(player); }
    public List<Player> visit(Coach coach) { return players; }
    public List<Player> getPlayers() { return List.copyOf(players); }
}
