package visitor;
import model.*;
public class SalaryVisitor implements Visitor<Double> {
    private double total;
    public Double visit(League league) { return total; }
    public Double visit(Club club) { return total; }
    public Double visit(Team team) { return total; }
    public Double visit(Goalkeeper player) { total += player.getSalary(); return total; }
    public Double visit(Defender player) { total += player.getSalary(); return total; }
    public Double visit(Forward player) { total += player.getSalary(); return total; }
    public Double visit(Coach coach) { total += coach.getSalary(); return total; }
    public double getTotal() { return total; }
}
