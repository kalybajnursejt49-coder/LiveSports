package event;
import java.util.List;
public final class SquadReportEvent implements Event {
    private final String team;
    private final double salary;
    private final List<String> topPlayers;
    public SquadReportEvent(String team, double salary, List<String> topPlayers) {
        this.team = team; this.salary = salary; this.topPlayers = List.copyOf(topPlayers);
    }
    public EventType getType() { return EventType.SQUAD_REPORT; }
    public String getTeam() { return team; }
    public double getSalary() { return salary; }
    public List<String> getTopPlayers() { return topPlayers; }
    public String toString() { return "SQUAD_REPORT " + team + " salary=" + salary + " top=" + topPlayers; }
}
