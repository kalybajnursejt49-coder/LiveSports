package event;
public final class MatchEvent implements Event {
    private final EventType type;
    private final String match;
    private final String team;
    private final String player;
    public MatchEvent(EventType type, String match, String team, String player) {
        this.type = type; this.match = match; this.team = team; this.player = player;
    }
    public EventType getType() { return type; }
    public String getMatch() { return match; }
    public String getTeam() { return team; }
    public String getPlayer() { return player; }
    public String toString() { return type + " " + match + " " + team + " " + player; }
}
