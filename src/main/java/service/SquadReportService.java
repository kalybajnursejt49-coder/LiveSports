package service;
import event.*;
import model.*;
import observer.*;
import visitor.*;
import java.util.ArrayList;
import java.util.List;
public class SquadReportService extends EventSource implements Observer<Event> {
    private final League league;
    private final int minimumRating;
    public SquadReportService(League league, int minimumRating) { this.league = league; this.minimumRating = minimumRating; }
    public void onEvent(Event event) {
        if (event.getType() != EventType.RED_CARD) return;
        MatchEvent card = (MatchEvent) event;
        for (Club club : league.getClubs()) {
            for (Team team : club.getTeams()) {
                if (!team.getName().equals(card.getTeam())) continue;
                for (Element member : team.getMembers()) {
                    if (member instanceof Player && ((Player) member).getName().equals(card.getPlayer())) {
                        SalaryVisitor salary = new SalaryVisitor();
                        TopPlayersVisitor top = new TopPlayersVisitor(minimumRating);
                        team.accept(salary);
                        team.accept(top);
                        List<String> names = new ArrayList<>();
                        for (Player player : top.getPlayers()) names.add(player.getName());
                        publish(new SquadReportEvent(team.getName(), salary.getTotal(), names));
                        return;
                    }
                }
            }
        }
    }
}
