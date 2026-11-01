package visitor;
import model.*;
public interface Visitor<R> {
    R visit(League league);
    R visit(Club club);
    R visit(Team team);
    R visit(Goalkeeper goalkeeper);
    R visit(Defender defender);
    R visit(Forward forward);
    R visit(Coach coach);
    default void end(League league) { }
    default void end(Club club) { }
    default void end(Team team) { }
}
