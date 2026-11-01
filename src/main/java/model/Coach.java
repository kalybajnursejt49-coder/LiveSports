package model;
import visitor.Visitor;
public final class Coach implements Element {
    private final String name;
    private final double salary;
    public Coach(String name, double salary) { this.name = name; this.salary = salary; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public <R> R accept(Visitor<R> visitor) { return visitor.visit(this); }
}
