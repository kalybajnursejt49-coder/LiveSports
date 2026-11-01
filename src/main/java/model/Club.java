package model;
import visitor.Visitor;
import java.util.List;
public final class Club implements Element {
    private final String name;
    private final List<Team> teams;
    public Club(String name, List<Team> teams) { this.name = name; this.teams = List.copyOf(teams); }
    public String getName() { return name; }
    public List<Team> getTeams() { return teams; }
    public <R> R accept(Visitor<R> visitor) {
        R result = visitor.visit(this);
        for (Team team : teams) team.accept(visitor);
        visitor.end(this);
        return result;
    }
}
