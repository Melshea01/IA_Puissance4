import java.util.Scanner;

public class Puissance4 {
    private char[][] grille;
    private int lignes;
    private int colonnes;
    private char joueurActuel;

    public Puissance4(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new char[lignes][colonnes];
        this.joueurActuel = 'X';
        initialiserGrille();
    }

    private void initialiserGrille() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = '-';
            }
        }
    }

    public void  switchJoueur(){
        joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
    }



    public void jouer(boolean XisIA, boolean OisIA) {
        boolean jeuTermine = false;
        Scanner scanner = new Scanner(System.in);
        char playerSymbol;

        /*
        // Demander au joueur de choisir un pion
        System.out.println("Choisissez un pion entre 'X' et 'O':");
        while (true) {
            String input = scanner.nextLine();
            if (input.length() == 1) {
                char symbol = input.charAt(0);
                if (symbol == 'X' || symbol == 'O') {
                    playerSymbol = symbol;
                    break;
                }
            }
            System.out.println("Veuillez choisir entre 'X' et 'O'.");
        }

        // Utiliser le symbole choisi par le joueur
        this.joueurActuel = playerSymbol;
        System.out.println("Vous avez choisi le pion " + playerSymbol);

         */

        //Initialisation Minimax
        Minimax minimaxX = new Minimax(5,'X','O');
        Minimax minimaxO= new Minimax(2,'O','X');

        while (!jeuTermine) {
            afficherGrille();

            // Demander au joueur actuel de choisir une colonne
            int colonne = 0;
            if((joueurActuel=='X' && !XisIA) || (joueurActuel=='O' && !OisIA)){
                System.out.print("Joueur " + joueurActuel + ", choisissez une colonne (1-" + colonnes + "): ");
                colonne = scanner.nextInt() - 1;
            }
            else {
                Minimax minimax = minimaxX;
                if(joueurActuel=='O'){
                    minimax = minimaxO;
                }
                Node node =new Node(this, minimax.getMaxProfondeur());
                colonne = minimax.minimax(node).getColumn();

            }


            if (validerCoup(colonne)) {
                // Jouer le coup et vérifier si le joueur a gagné
                int ligne = placerJeton(colonne);
                if (verifierGagnant(ligne, colonne)) {
                    afficherGrille();
                    System.out.println("Le joueur " + joueurActuel + " a gagné !");
                    jeuTermine = true;
                } else if (grilleRemplie()) {
                    // Vérifier si la grille est remplie (match nul)
                    afficherGrille();
                    System.out.println("Match nul !");
                    jeuTermine = true;
                } else {
                    // Passer au joueur suivant
                    char joueurAdverse = (joueurActuel == 'X') ? 'O' : 'X';
                    int score = calculScore(joueurActuel) - calculScore(joueurAdverse);
                    System.out.println("score ligne = " + score);
                    joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Coup invalide, veuillez réessayer !");
            }
        }

        scanner.close();
    }

    public Puissance4 clone() {
        Puissance4 clone = new Puissance4(lignes, colonnes);
        clone.joueurActuel = this.joueurActuel;

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                clone.grille[i][j] = this.grille[i][j];
            }
        }

        return clone;
    }

    private void afficherGrille() {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
        for (int j = 0; j < colonnes; j++) {
            System.out.print((j + 1) + " ");
        }
        System.out.println();
    }

    public boolean validerCoup(int colonne) {
        return colonne >= 0 && colonne < colonnes && grille[0][colonne] == '-';
    }

    public int placerJeton(int colonne) {
        int ligne = -1;
        for (int i = lignes - 1; i >= 0; i--) {
            if (grille[i][colonne] == '-') {
                grille[i][colonne] = joueurActuel;
                ligne = i;
                break;
            }
        }
        return ligne;
    }



    public int calculScore(char joueur){
        int score = 0;


        //Vérification des lignes
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes - 3; j++) {
                int countJoueur = 0;
                int countVides = 0;
                for (int k = 0; k < 4; k++) {
                    if (grille[i][j + k] == joueur) {
                        countJoueur++;
                    } else if (grille[i][j + k] == '-') {
                        countVides++;
                    }
                }
                score = evaluationScore(countJoueur,countVides,score);
            }
        }


        // Vérification des alignements verticaux
        for (int j = 0; j < colonnes; j++) {
            for (int i = 0; i < lignes - 3; i++) {
                int countJoueur = 0;
                int countVides = 0;
                for (int k = 0; k < 4; k++) {
                    if (grille[i + k][j] == joueur) {
                        countJoueur++;
                    } else if (grille[i + k][j] == '-') {
                        countVides++;
                    }
                }
                score = evaluationScore(countJoueur,countVides,score);
            }
        }


        // Diagonale descendante
        for (int row = 0; row < lignes - 3; row++) {
            for (int col = 0; col < colonnes - 3; col++) {
                int countJoueur = 0;
                int countVides = 0;

                for (int i = 0; i < 4; i++) {
                    if (grille[row + i][col + i] == joueur) {
                        countJoueur++;
                    } else if (grille[row + i][col + i] =='-') {
                        countVides++;
                    } else {
                        countJoueur = 0;
                        countVides = 0;
                    }
                }
                score = evaluationScore(countJoueur,countVides,score);
            }
        }

        // Diagonale ascendante
        for (int row = 0; row < lignes - 3; row++) {
            for (int col = 0; col < colonnes - 3; col++) {
                int countJoueur = 0;
                int countVides = 0;

                for (int i = 0; i < 4; i++) {
                    if (grille[row + 3 - i][col + i] == joueur) {
                        countJoueur++;
                    } else if (grille[row + 3 - i][col + i] =='-') {
                        countVides++;
                    } else {
                        countJoueur = 0;
                        countVides = 0;
                    }
                }
                score = evaluationScore(countJoueur,countVides,score);
            }
        }

        return score;
    }

    public int evaluationScore(int countJoueur, int countVides, int score){
        if (countJoueur == 4) {
            score += 1000;
        } else if (countJoueur == 3 && countVides == 1) {
            score += 50;
        } else if (countJoueur == 2 && countVides == 2) {
            score += 5;
        } else if (countJoueur == 1 && countVides == 3) {
            score += 1;
        }
        return score;
    }

    private boolean verifierGagnant(int ligne, int colonne) {
        char jeton = joueurActuel;

        // Vérifier les lignes horizontales
        int count = 0;
        for (int j = 0; j < colonnes; j++) {
            if (grille[ligne][j] == jeton) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Vérifier les lignes verticales
        count = 0;
        for (int i = 0; i < lignes; i++) {
            if (grille[i][colonne] == jeton) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Vérifier les diagonales
        count = 0;
        int i = ligne;
        int j = colonne;
        while (i >= 0 && j < colonnes && grille[i][j] == jeton) {
            count++;
            i--;
            j++;
        }
        i = ligne + 1;
        j = colonne - 1;
        while (i < lignes && j >= 0 && grille[i][j] == jeton) {
            count++;
            i++;
            j--;
        }
        if (count >= 4) {
            return true;
        }

        // Vérifier les diagonales
        count = 0;
        i = ligne;
        j = colonne;
        while (i >= 0 && j >= 0 && grille[i][j] == jeton) {
            count++;
            i--;
            j--;
        }
        i = ligne + 1;
        j = colonne + 1;
        while (i < lignes && j < colonnes && grille[i][j] == jeton) {
            count++;
            i++;
            j++;
        }
        if (count >= 4) {
            return true;
        }

        return false;
    }

    private boolean grilleRemplie() {
        for (int j = 0; j < colonnes; j++) {
            if (grille[0][j] == '-') {
                return false;
            }
        }
        return true;
    }

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
        switch (choice) {
            case 1:
                // Mode Joueur contre Joueur
                System.out.println("Mode Joueur contre Joueur sélectionné.");
                jeu.jouer(false,false);
                break;
            case 2:
                // Mode Joueur contre Ordinateur
                System.out.println("Mode Joueur contre IA sélectionné.");
                //TODO A modifier quand le mode joueur contre Ordinateur
                jeu.jouer(false, true);
                break;
            case 3:
                // Mode Ordinateur contre Ordinateur
                System.out.println("Mode IA contre IA sélectionné.");
                //TODO A modifier quand le mode Ordinateur vs Ordinateur
                jeu.jouer(true, true);
                break;
            case 4:
                // Quitter le jeu
                System.out.println("Au revoir !");
                break;
        }

    }


}