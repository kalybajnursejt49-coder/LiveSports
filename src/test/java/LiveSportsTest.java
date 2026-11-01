import event.*;
import listener.*;
import model.*;
import observer.*;
import service.SquadReportService;
import visitor.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
public class LiveSportsTest {
    private Team team() { return new Team("Lions", List.of(new Goalkeeper("A", 100, 70, 0), new Defender("B", 200, 80, 1), new Forward("C", 300, 90, 5), new Coach("D", 400))); }
    private League league() { return new League("L", List.of(new Club("Club", List.of(team())))); }
    private MatchEvent goal() { return new MatchEvent(EventType.GOAL, "M", "Lions", "C"); }
    @Test void subscribe() { FootballMatchFeed feed = new FootballMatchFeed(); AtomicInteger count = new AtomicInteger(); feed.subscribe(EventType.GOAL, e -> count.incrementAndGet()); feed.publish(goal()); assertEquals(1, count.get()); }
    @Test void unsubscribe() { FootballMatchFeed feed = new FootballMatchFeed(); AtomicInteger count = new AtomicInteger(); Observer<Event> observer = e -> count.incrementAndGet(); feed.subscribe(EventType.GOAL, observer); feed.unsubscribe(EventType.GOAL, observer); feed.publish(goal()); assertEquals(0, count.get()); }
    @Test void unsubscribeInsideHandler() { FootballMatchFeed feed = new FootballMatchFeed(); AtomicInteger count = new AtomicInteger(); Observer<Event> observer = new Observer<Event>() { public void onEvent(Event e) { count.incrementAndGet(); feed.unsubscribe(EventType.GOAL, this); } }; feed.subscribe(EventType.GOAL, observer); feed.publish(goal()); feed.publish(goal()); assertEquals(1, count.get()); }
    @Test void eventFiltering() { FootballMatchFeed feed = new FootballMatchFeed(); AtomicInteger count = new AtomicInteger(); feed.subscribe(EventType.RED_CARD, e -> count.incrementAndGet()); feed.publish(goal()); assertEquals(0, count.get()); }
    @Test void chain() { FootballMatchFeed feed = new FootballMatchFeed(); FantasyPoints fantasy = new FantasyPoints(); Leaderboard board = new Leaderboard(); feed.subscribe(EventType.GOAL, fantasy); fantasy.subscribe(EventType.PLAYER_POINTS, board); feed.publish(goal()); assertEquals(5, board.getPoints("C")); }
    @Test void secondFeed() { TrainingMatchFeed feed = new TrainingMatchFeed(); AtomicInteger count = new AtomicInteger(); feed.subscribe(EventType.YELLOW_CARD, e -> count.incrementAndGet()); feed.publish(new MatchEvent(EventType.YELLOW_CARD, "T", "Lions", "B")); assertEquals(1, count.get()); }
    @Test void scoreboard() { Scoreboard board = new Scoreboard(); board.onEvent(goal()); board.onEvent(goal()); assertEquals(2, board.getScore("Lions")); }
    @Test void odds() { OddsCalculator odds = new OddsCalculator(); odds.onEvent(goal()); assertEquals(1.0, odds.getHomeWinOdds()); }
    @Test void salary() { SalaryVisitor visitor = new SalaryVisitor(); league().accept(visitor); assertEquals(1000.0, visitor.getTotal()); }
    @Test void ratingCount() { RatingStatsVisitor visitor = new RatingStatsVisitor(); league().accept(visitor); assertEquals(3, visitor.getCount()); }
    @Test void ratingSum() { RatingStatsVisitor visitor = new RatingStatsVisitor(); league().accept(visitor); assertEquals(240, visitor.getSum()); }
    @Test void ratingAverage() { RatingStatsVisitor visitor = new RatingStatsVisitor(); league().accept(visitor); assertEquals(80.0, visitor.getAverage()); }
    @Test void bestPlayer() { RatingStatsVisitor visitor = new RatingStatsVisitor(); league().accept(visitor); assertEquals("C", visitor.getBest().getName()); }
    @Test void topPlayers() { TopPlayersVisitor visitor = new TopPlayersVisitor(80); league().accept(visitor); assertEquals(List.of("B", "C"), visitor.getPlayers().stream().map(Player::getName).toList()); }
    @Test void json() { JsonExportVisitor visitor = new JsonExportVisitor(); league().accept(visitor); assertEquals("{\"type\":\"league\",\"name\":\"L\",\"children\":[{\"type\":\"club\",\"name\":\"Club\",\"children\":[{\"type\":\"team\",\"name\":\"Lions\",\"children\":[{\"type\":\"goalkeeper\",\"name\":\"A\",\"salary\":100.0,\"rating\":70,\"goals\":0},{\"type\":\"defender\",\"name\":\"B\",\"salary\":200.0,\"rating\":80,\"goals\":1},{\"type\":\"forward\",\"name\":\"C\",\"salary\":300.0,\"rating\":90,\"goals\":5},{\"type\":\"coach\",\"name\":\"D\",\"salary\":400.0}]}]}]}", visitor.getJson()); }
    @Test void redCardReport() { FootballMatchFeed feed = new FootballMatchFeed(); SquadReportService reports = new SquadReportService(league(), 80); AtomicInteger count = new AtomicInteger(); reports.subscribe(EventType.SQUAD_REPORT, e -> { SquadReportEvent report = (SquadReportEvent) e; assertEquals(1000.0, report.getSalary()); assertEquals(List.of("B", "C"), report.getTopPlayers()); count.incrementAndGet(); }); feed.subscribe(EventType.RED_CARD, reports); feed.publish(new MatchEvent(EventType.RED_CARD, "M", "Lions", "B")); assertEquals(1, count.get()); }
    @Test void stress() throws Exception { FootballMatchFeed feed = new FootballMatchFeed(); AtomicInteger count = new AtomicInteger(); feed.subscribe(EventType.GOAL, e -> count.incrementAndGet()); ExecutorService executor = Executors.newFixedThreadPool(4); for (int i = 0; i < 4; i++) executor.submit(() -> { for (int j = 0; j < 1000; j++) feed.publish(goal()); }); executor.shutdown(); assertTrue(executor.awaitTermination(30, TimeUnit.SECONDS)); assertEquals(4000, count.get()); }
}
