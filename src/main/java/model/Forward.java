package model;
import visitor.Visitor;
public final class Forward extends Player {
    public Forward(String name, double salary, int rating, int goals) { super(name, salary, rating, goals); }
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}
