package model;
import visitor.Visitor;
import java.util.List;
public final class League implements Element {
    private final String name;
    private final List<Club> clubs;
    public League(String name, List<Club> clubs) { this.name = name; this.clubs = List.copyOf(clubs); }
    public String getName() { return name; }
    public List<Club> getClubs() { return clubs; }
    public <R> R accept(Visitor<R> visitor) {
        R result = visitor.visit(this);
        for (Club club : clubs) club.accept(visitor);
        visitor.end(this);
        return result;
    }
}
