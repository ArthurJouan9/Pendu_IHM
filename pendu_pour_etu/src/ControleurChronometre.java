import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurChronometre implements EventHandler<ActionEvent> {
    private long tempsPrec;
    private long tempsEcoule;
    private Chronometre chrono;

    public ControleurChronometre(Chronometre chrono) {
        this.chrono = chrono;
        this.tempsPrec = System.currentTimeMillis();
        this.tempsEcoule = 0;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - tempsPrec;
        tempsPrec = currentTime;
        tempsEcoule += delta;
        chrono.setTime(tempsEcoule);
    }

    public void reset() {
        tempsEcoule = 0;
        tempsPrec = System.currentTimeMillis();
    }
}
