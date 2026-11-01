package model;
import visitor.Visitor;
public final class Defender extends Player {
    public Defender(String name, double salary, int rating, int goals) { super(name, salary, rating, goals); }
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}
