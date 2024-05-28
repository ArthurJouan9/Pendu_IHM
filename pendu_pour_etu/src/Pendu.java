import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData ;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;


/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */    
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Label motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Label leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button Settings;
    /**
     * le bouton Accueil / Maison
     */    
    private Button Accueil;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */ 
    private Button LancerGame;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono ...)
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        this.chrono = new Chronometre();
        niveaux = List.of("Facile", "Medium", "Difficile", "Expert");
        // A terminer d'implementer
    }

    /**
     * @return  le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene(){
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        panelCentral = new BorderPane();
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre(){

        BorderPane banniere = new BorderPane();
        banniere.setBackground(new Background(new BackgroundFill(Color.valueOf("#EAEEFF"), CornerRadii.EMPTY, Insets.EMPTY)));
        banniere.setPadding(new Insets(20));
        HBox buttonBox = new HBox(10); 
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        
        Label Titre = new Label("Jeu Du Pendu");
        Titre.setFont(Font.font("Arial",FontWeight.BOLD, 32) ) ;



        Accueil = new Button();
        Settings = new Button();
        Button Info = new Button();

        ImageView accueilImageView = new ImageView(new Image("file:img/home.png"));
        accueilImageView.setFitWidth(32); 
        accueilImageView.setFitHeight(32); 
        Accueil.setGraphic(accueilImageView);
        Accueil.setOnAction(new RetourAccueil(this.modelePendu, this));


        ImageView settingsImageView = new ImageView(new Image("file:img/parametres.png"));
        settingsImageView.setFitWidth(32); 
        settingsImageView.setFitHeight(32); 
        Settings.setGraphic(settingsImageView);

        ImageView infoImageView = new ImageView(new Image("file:img/info.png"));
        infoImageView.setFitWidth(32); 
        infoImageView.setFitHeight(32); 
        Info.setGraphic(infoImageView);

        banniere.setLeft(Titre);
        buttonBox.getChildren().addAll(Accueil, Settings, Info);
        banniere.setRight(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER_RIGHT);


        return banniere;


    }

    /**
     * @return le panel du chronomètre
     */
    private TitledPane leChrono() {
        TitledPane chronoPane = new TitledPane("Chronomètre", this.chrono);
        chronoPane.setCollapsible(false);
        return chronoPane;
    }

    // /**
     // * @return la fenêtre de jeu avec le mot crypté, l'image, la barre
     // *         de progression et le clavier
     // */
    // private Pane fenetreJeu(){
        // A implementer
        // Pane res = new Pane();
        // return res;
    // }

    // /**
     // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de jeu
     // */
    // private Pane fenetreAccueil(){
        // A implementer    
        // Pane res = new Pane();
        // return res;
    // }

    /**
     * charge les images à afficher en fonction des erreurs
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire){
        for (int i=0; i<this.modelePendu.getNbErreursMax()+1; i++){
            File file = new File(repertoire+"/pendu"+i+".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil(){
        panelCentral.getChildren().clear();
        Accueil.setDisable(true);
        Settings.setDisable(false);


        VBox accueil = new VBox(20);
        accueil.setPadding(new Insets(20));
        LancerGame = new Button("Lancer une partie");
        LancerGame.setOnAction(new ControleurLancerPartie(this.modelePendu, this));

        VBox vbox = new VBox(10); 
        vbox.setPadding(new Insets(10));

        ToggleGroup groupNiveau = new ToggleGroup();

        for (String niveau : niveaux) {
            RadioButton radioButton = new RadioButton(niveau);
            radioButton.setToggleGroup(groupNiveau); 
            radioButton.setOnAction(new ControleurNiveau(this.modelePendu));
            vbox.getChildren().add(radioButton); 
        }

        TitledPane titledPane = new TitledPane("Niveau de difficulté", vbox);
        titledPane.setCollapsible(false);

        accueil.getChildren().addAll(LancerGame, titledPane);
        panelCentral.setCenter(accueil);
    }
    
    public void modeJeu(){

        panelCentral.getChildren().clear();
        Accueil.setDisable(false);
        Settings.setDisable(true);



        BorderPane root = new BorderPane();
        VBox Vboxgauche = new VBox(20);
        VBox Vboxdroite = new VBox(20);
        Vboxgauche.setAlignment(Pos.BASELINE_CENTER);
        Vboxgauche.setPadding(new Insets(20));
        Vboxdroite.setPadding(new Insets(20));

        root.setLeft(Vboxgauche);
        root.setRight(Vboxdroite);

        this.motCrypte = new Label(this.modelePendu.getMotCrypte());
        this.motCrypte.setFont(new Font(28));
        Vboxgauche.getChildren().add(this.motCrypte);

        this.dessin = new ImageView(this.lesImages.get(0));
        Vboxgauche.getChildren().add(this.dessin);

        this.pg = new ProgressBar();
        this.pg.setProgress(0);
        Vboxgauche.getChildren().add(this.pg);

        this.clavier = new Clavier("abcdefghijklmnopqrstuvwxyz-", new ControleurLettres(this.modelePendu, this));
        Vboxgauche.getChildren().add(this.clavier);

        leNiveau = new Label("Niveau " + niveaux.get(this.modelePendu.getNiveau()));
        leNiveau.setFont(new Font(28));
        Vboxdroite.getChildren().add(leNiveau);

        TitledPane chronoPane = this.leChrono();
        Vboxdroite.getChildren().add(chronoPane);

        Button nouveau = new Button("Nouveau mot");
        nouveau.setOnAction(new ControleurLancerPartie(modelePendu, this));
        Vboxdroite.getChildren().add(nouveau);

        panelCentral.setCenter(root);
        Vboxgauche.setAlignment(Pos.TOP_CENTER);
    }
    
    public void modeParametres(){
        // A implémenter
    }

    /** lance une partie */
    public void lancePartie(){
        this.modeJeu();
        chrono.resetTime();
        chrono.start();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        motCrypte.setText(modelePendu.getMotCrypte());
        int erreurs = ( modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants());
        dessin.setImage(this.lesImages.get(erreurs));
        pg.setProgress((double) erreurs / modelePendu.getNbErreursMax());
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * @return le chronomètre du jeu
     */ 
 

    public Alert popUpPartieEnCours(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"La partie est en cours!\n Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }
        
    public Alert popUpReglesDuJeu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Règles du jeu");
        alert.setHeaderText(null);
        alert.setContentText("Les règles du jeu du pendu...");
        return alert;
    }

    public Alert popUpMessageGagne() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Gagné !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations, vous avez gagné !");
        chrono.stop();
        return alert;
    }

    public Alert popUpMessagePerdu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Perdu !");
        alert.setHeaderText(null);
        alert.setContentText("Dommage, vous avez perdu. Essayez encore !");
        chrono.stop();
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }    
}
