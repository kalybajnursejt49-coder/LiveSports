package visitor;
import model.*;
public class AverageSalaryVisitor implements Visitor<Double> {
    private double sum;
    private int count;
    private void add(double salary) { sum += salary; count++; }
    public Double visit(League league) { return getAverage(); }
    public Double visit(Club club) { return getAverage(); }
    public Double visit(Team team) { return getAverage(); }
    public Double visit(Goalkeeper player) { add(player.getSalary()); return getAverage(); }
    public Double visit(Defender player) { add(player.getSalary()); return getAverage(); }
    public Double visit(Forward player) { add(player.getSalary()); return getAverage(); }
    public Double visit(Coach coach) { add(coach.getSalary()); return getAverage(); }
    public double getAverage() { return count == 0 ? 0 : sum / count; }
}
