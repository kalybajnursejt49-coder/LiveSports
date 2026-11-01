package visitor;
import model.*;
public class RatingStatsVisitor implements Visitor<Integer> {
    private int count;
    private int sum;
    private Player best;
    private void add(Player player) {
        count++; sum += player.getRating();
        if (best == null || player.getRating() > best.getRating()) best = player;
    }
    public Integer visit(League league) { return count; }
    public Integer visit(Club club) { return count; }
    public Integer visit(Team team) { return count; }
    public Integer visit(Goalkeeper player) { add(player); return count; }
    public Integer visit(Defender player) { add(player); return count; }
    public Integer visit(Forward player) { add(player); return count; }
    public Integer visit(Coach coach) { return count; }
    public Integer visit(Physiotherapist staff) { return count; }
    public int getCount() { return count; }
    public int getSum() { return sum; }
    public double getAverage() { return count == 0 ? 0 : (double) sum / count; }
    public Player getBest() { return best; }
}
