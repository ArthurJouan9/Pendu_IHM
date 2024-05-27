import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     **/
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurLancerPartie(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas une partie en cours
     * @param actionEvent l'événement action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        if (modelePendu.getNbLettresRestantes() < 26) {
            // Si une partie est en cours, demander confirmation
            Optional<ButtonType> reponse = vuePendu.popUpPartieEnCours().showAndWait(); // on lance la fenêtre popup et on attend la réponse
            // si la réponse est oui
            if (reponse.isPresent() && reponse.get().equals(ButtonType.YES)) {
                // L'utilisateur a confirmé, recommencer une nouvelle partie
                vuePendu.lancePartie();
            }
            // Si l'utilisateur clique sur "Non" ou ferme la boîte de dialogue, on ne fait rien
        } else {
            // Pas de partie en cours, recommencer directement une nouvelle partie
            vuePendu.lancePartie();
        }
    }
}
