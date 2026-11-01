package app;
import event.*;
import listener.*;
import model.*;
import observer.*;
import service.SquadReportService;
import visitor.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int threads = args.length == 0 ? 3 : Integer.parseInt(args[0]);
        if (threads < 3) throw new IllegalArgumentException("At least 3 producer threads are required");
        Team lions = new Team("Lions", List.of(new Goalkeeper("Alex", 1000, 82, 0), new Defender("Ben", 1200, 78, 1), new Forward("Chris", 2000, 91, 8), new Coach("Dana", 1500)));
        Team tigers = new Team("Tigers", List.of(new Goalkeeper("Eli", 900, 75, 0), new Defender("Finn", 1100, 80, 2), new Forward("Gina", 1800, 87, 6), new Coach("Hana", 1400)));
        League league = new League("University League", List.of(new Club("North Club", List.of(lions)), new Club("South Club", List.of(tigers))));
        FootballMatchFeed match = new FootballMatchFeed();
        TrainingMatchFeed training = new TrainingMatchFeed();
        Scoreboard scoreboard = new Scoreboard();
        OddsCalculator odds = new OddsCalculator();
        FantasyPoints fantasy = new FantasyPoints();
        PushNotifier push = new PushNotifier();
        MatchLogger logger = new MatchLogger();
        Leaderboard leaderboard = new Leaderboard();
        SquadReportService reports = new SquadReportService(league, 80);
        match.subscribe(EventType.GOAL, scoreboard);
        match.subscribe(EventType.GOAL, odds);
        match.subscribe(EventType.GOAL, fantasy);
        match.subscribe(EventType.RED_CARD, fantasy);
        match.subscribe(EventType.RED_CARD, reports);
        match.subscribe(EventType.GOAL, push);
        match.subscribe(EventType.RED_CARD, logger);
        match.subscribe(EventType.MATCH_END, logger);
        training.subscribe(EventType.YELLOW_CARD, fantasy);
        training.subscribe(EventType.YELLOW_CARD, logger);
        fantasy.subscribe(EventType.PLAYER_POINTS, leaderboard);
        reports.subscribe(EventType.SQUAD_REPORT, logger);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        try {
            executor.submit(() -> match.publish(new MatchEvent(EventType.GOAL, "Final", "Lions", "Chris")));
            executor.submit(() -> training.publish(new MatchEvent(EventType.YELLOW_CARD, "Training", "Tigers", "Gina")));
            executor.submit(() -> match.publish(new MatchEvent(EventType.RED_CARD, "Final", "Lions", "Ben")));
            executor.submit(() -> match.publish(new MatchEvent(EventType.MATCH_END, "Final", "Lions", "")));
        } finally {
            executor.shutdown();
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) throw new IllegalStateException("Producers did not finish");
        }
        SalaryVisitor salary = new SalaryVisitor();
        RatingStatsVisitor rating = new RatingStatsVisitor();
        TopPlayersVisitor top = new TopPlayersVisitor(85);
        JsonExportVisitor json = new JsonExportVisitor();
        league.accept(salary); league.accept(rating); league.accept(top); league.accept(json);
        System.out.println("Lions score: " + scoreboard.getScore("Lions"));
        System.out.println("Chris fantasy points: " + leaderboard.getPoints("Chris"));
        System.out.println("Total salary: " + salary.getTotal());
        System.out.println("Average rating: " + rating.getAverage());
        System.out.println("Top players: " + top.getPlayers().size());
        System.out.println("JSON: " + json.getJson());
    }
}
