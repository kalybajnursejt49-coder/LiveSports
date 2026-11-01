package model;
import visitor.Visitor;
import java.util.List;
public final class Team implements Element {
    private final String name;
    private final List<Element> members;
    public Team(String name, List<Element> members) { this.name = name; this.members = List.copyOf(members); }
    public String getName() { return name; }
    public List<Element> getMembers() { return members; }
    public <R> R accept(Visitor<R> visitor) {
        R result = visitor.visit(this);
        for (Element member : members) member.accept(visitor);
        visitor.end(this);
        return result;
    }
}
