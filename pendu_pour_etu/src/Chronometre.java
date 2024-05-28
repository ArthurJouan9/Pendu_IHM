import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Chronometre extends Text {
    private Timeline timeline;
    private KeyFrame keyFrame;
    private ControleurChronometre actionTemps;

    public Chronometre() {
        super("0 s"); // Initializing the label to "0 s"
        setFont(new Font(20));
        setTextAlignment(TextAlignment.CENTER);
        actionTemps = new ControleurChronometre(this);
        keyFrame = new KeyFrame(Duration.seconds(1), actionTemps);
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void setTime(long tempsMillisec) {
        long seconds = tempsMillisec / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        if (minutes > 0) {
            setText(minutes + " min " + seconds + " s");
        } else {
            setText(seconds + " s");
        }
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void resetTime() {
        stop();
        actionTemps.reset();
        setText("0 s");
    }
}
