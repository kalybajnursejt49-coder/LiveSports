package model;
public abstract class Player implements Element {
    private final String name;
    private final double salary;
    private final int rating;
    private final int goals;
    protected Player(String name, double salary, int rating, int goals) {
        this.name = name; this.salary = salary; this.rating = rating; this.goals = goals;
    }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public int getRating() { return rating; }
    public int getGoals() { return goals; }
}
