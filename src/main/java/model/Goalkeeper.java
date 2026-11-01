package model;
import visitor.Visitor;
public final class Goalkeeper extends Player {
    public Goalkeeper(String name, double salary, int rating, int goals) { super(name, salary, rating, goals); }
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}
