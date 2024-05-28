import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * Modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * Vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu) {
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String lettre = clickedButton.getText();
        
        // Désactiver le bouton cliqué
        clickedButton.setDisable(true);

        // Essayer la lettre dans le modèle
        int correcte = modelePendu.essaiLettre(lettre.charAt(0));

        // Mettre à jour l'affichage du mot crypté et du dessin du pendu
        vuePendu.majAffichage();

        // Vérifier si la partie est terminée
        if (modelePendu.gagne()) {
            vuePendu.chrono.stop(); // Arrêter le chronomètre
            vuePendu.popUpMessageGagne().showAndWait();
            vuePendu.modeAccueil();
        } else if (modelePendu.perdu()) {
            vuePendu.chrono.stop(); // Arrêter le chronomètre
            vuePendu.popUpMessagePerdu().showAndWait();
            vuePendu.modeAccueil();
        }
    }
}
