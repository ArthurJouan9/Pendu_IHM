import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches
 * le choix ici est d'un faire un hérité d'un TilePane
 */
public class Clavier extends TilePane {
    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     * @param touches une chaine de caractères qui contient les lettres à mettre sur les touches
     * @param actionTouches le contrôleur des touches
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        this.clavier = new ArrayList<>();
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(20));
        this.setTileAlignment(Pos.CENTER);

        for (char c : touches.toCharArray()) {
            Button button = new Button(String.valueOf(c));
            button.setOnAction(actionTouches);
            button.setShape(new Circle(20));
            button.setMinSize(40, 40);
            button.setMaxSize(40, 40);
            // Ajouter une marge intérieure pour déplacer le contenu du bouton vers le haut
            button.setPadding(new Insets(5, 0, 0, 0)); // Marge de 5 pixels en haut
            clavier.add(button);
            this.getChildren().add(button);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     * @param touchesDesactivees une chaine de caractères contenant la liste des touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees) {
        for (Button button : clavier) {
            if (touchesDesactivees.contains(button.getText())) {
                button.setDisable(true);
            } else {
                button.setDisable(false);
            }
        }
    }
}
