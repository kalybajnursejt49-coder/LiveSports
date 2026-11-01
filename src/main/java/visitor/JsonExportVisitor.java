package visitor;
import model.*;
import java.util.ArrayList;
import java.util.List;
public class JsonExportVisitor implements Visitor<String> {
    private final StringBuilder json = new StringBuilder();
    private final List<Boolean> first = new ArrayList<>();
    private void comma() {
        if (!first.isEmpty()) {
            int last = first.size() - 1;
            if (first.get(last)) first.set(last, false); else json.append(',');
        }
    }
    private String quote(String value) {
        StringBuilder result = new StringBuilder("\"");
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '"' || c == '\\') result.append('\\');
            if (c == '\n') result.append("\\n");
            else if (c == '\r') result.append("\\r");
            else if (c == '\t') result.append("\\t");
            else if (c < 32) result.append(String.format("\\u%04x", (int) c));
            else result.append(c);
        }
        return result.append('"').toString();
    }
    private void start(String type, String name) {
        comma(); json.append("{\"type\":").append(quote(type)).append(",\"name\":").append(quote(name)).append(",\"children\":[");
        first.add(true);
    }
    private void endContainer() { json.append("]}"); first.remove(first.size() - 1); }
    private String player(String type, Player player) {
        comma(); json.append("{\"type\":").append(quote(type)).append(",\"name\":").append(quote(player.getName())).append(",\"salary\":").append(player.getSalary()).append(",\"rating\":").append(player.getRating()).append(",\"goals\":").append(player.getGoals()).append('}'); return json.toString();
    }
    public String visit(League league) { start("league", league.getName()); return json.toString(); }
    public String visit(Club club) { start("club", club.getName()); return json.toString(); }
    public String visit(Team team) { start("team", team.getName()); return json.toString(); }
    public String visit(Goalkeeper player) { return player("goalkeeper", player); }
    public String visit(Defender player) { return player("defender", player); }
    public String visit(Forward player) { return player("forward", player); }
    public String visit(Coach coach) { comma(); json.append("{\"type\":\"coach\",\"name\":").append(quote(coach.getName())).append(",\"salary\":").append(coach.getSalary()).append('}'); return json.toString(); }
    public String visit(Physiotherapist staff) { comma(); json.append("{\"type\":\"physiotherapist\",\"name\":").append(quote(staff.getName())).append(",\"salary\":").append(staff.getSalary()).append('}'); return json.toString(); }
    public void end(League league) { endContainer(); }
    public void end(Club club) { endContainer(); }
    public void end(Team team) { endContainer(); }
    public String getJson() { return json.toString(); }
}
