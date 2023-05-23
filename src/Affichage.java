import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Affichage {

    public static void main(String[] args) {
        Puissance4 jeu = new Puissance4(6, 7);
        Scanner scanner = new Scanner(System.in);

        // Afficher le menu
        System.out.println("=== MENU ===");
        System.out.println("1. Mode Joueur contre Joueur");
        System.out.println("2. Mode Joueur contre Ordinateur");
        System.out.println("3. Mode Ordinateur contre Ordinateur");
        System.out.println("4. Quitter");

        // Demander à l'utilisateur de choisir une option
        int choice = 1;
        while (true) {
            System.out.print("Choisissez le mode de jeu : ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) {
                    break;
                }
            }
            System.out.println("Veuillez entrer un choix valide (1-4).");
            scanner.nextLine(); // Vider la ligne restante dans le scanner
        }

        // Traiter le choix de l'utilisateur
        HashMap<Character, Integer> typeJoueur = new HashMap<Character, Integer>();
        switch (choice) {
            case 1:
                // Mode Joueur contre Joueur
                System.out.println("Mode Joueur contre Joueur sélectionné.");
                typeJoueur.put('X',1);
                typeJoueur.put('O',1);
                break;
            case 2:
                // Mode Joueur contre Ordinateur
                System.out.println("Mode Joueur contre IA sélectionné.");
                typeJoueur.put('X',1);
                typeJoueur.put('O',2);
                break;
            case 3:
                // Mode Ordinateur contre Ordinateur
                System.out.println("Mode IA contre IA sélectionné.");
                typeJoueur.put('X',2);
                typeJoueur.put('O',3);

                break;
            case 4:
                // Quitter le jeu
                System.out.println("Au revoir !");
                break;
        }
        jouer(jeu, typeJoueur);

    }

    public static void jouer(Puissance4 jeu ,HashMap<Character, Integer> typeJoueur) {
        boolean jeuTermine = false;
        Scanner scanner = new Scanner(System.in);

        while (!jeuTermine) {
            jeu.afficherGrille();
            int colonne = 0;
            Node node;
            // Demander au joueur actuel de choisir une colonne
            switch (typeJoueur.get(jeu.getJoueurActuel())){
                case 1:
                    System.out.print("Joueur " + jeu.getJoueurActuel() + ", choisissez une colonne (1-" + jeu.getJoueurActuel() + "): ");
                    colonne = scanner.nextInt() - 1;
                    break;
                case 2 :
                    Minimax minimax = new Minimax(5,jeu.getJoueurActuel(),jeu.getAdversaireActuel());
                    node = new Node(jeu, minimax.getMaxProfondeur());
                    colonne = minimax.minimax(node).getColumn();
                    break;
                case 3 :
                    AlphaBeta alphaBeta = new AlphaBeta(7,jeu.getJoueurActuel(),jeu.getAdversaireActuel());
                    node = new Node(jeu, alphaBeta.getMaxProfondeur());
                    colonne = alphaBeta.alphaBeta(node).getColumn();
                    break;

            }




            if (jeu.validerCoup(colonne)) {
                // Jouer le coup et vérifier si le joueur a gagné
                int ligne = jeu.placerJeton(colonne);
                if (jeu.verifierGagnant(ligne, colonne)) {
                    jeu.afficherGrille();
                    System.out.println("Le joueur " + jeu.getJoueurActuel() + " a gagné !");
                    jeuTermine = true;
                } else if (jeu.grilleRemplie()) {
                    // Vérifier si la grille est remplie (match nul)
                    jeu.afficherGrille();
                    System.out.println("Match nul !");
                    jeuTermine = true;
                } else {
                    // Passer au joueur suivant
                    char joueurAdverse = (jeu.getJoueurActuel() == 'X') ? 'O' : 'X';
                    int score = jeu.calculScore(jeu.getJoueurActuel()) - jeu.calculScore(joueurAdverse);
                    System.out.println("score ligne = " + score);
                    jeu.switchJoueur();
                }
            } else {
                System.out.println("Coup invalide, veuillez réessayer !");
            }
        }

        scanner.close();
    }
}
