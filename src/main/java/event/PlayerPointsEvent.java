package event;
public final class PlayerPointsEvent implements Event {
    private final String player;
    private final int points;
    public PlayerPointsEvent(String player, int points) { this.player = player; this.points = points; }
    public EventType getType() { return EventType.PLAYER_POINTS; }
    public String getPlayer() { return player; }
    public int getPoints() { return points; }
    public String toString() { return "PLAYER_POINTS " + player + "=" + points; }
}
