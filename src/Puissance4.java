public class Puissance4 {
    private char[][] grille;
    private int lignes;
    private int colonnes;
    private char joueurActuel;
    private boolean terminated;

    public Puissance4(int lignes, int colonnes) {
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.grille = new char[lignes][colonnes];
        this.joueurActuel = 'X';
        this.terminated = false;
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


    public char getJoueurActuel() {
        return joueurActuel;
    }

    public char getAdversaireActuel() {
        return (joueurActuel == 'X') ? 'O' : 'X';
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

    public boolean verifierGagnant(int ligne, int colonne) {
        char jeton = joueurActuel;

        // Vérifier les lignes horizontales
        int count = 0;
        for (int j = 0; j < colonnes; j++) {
            if (grille[ligne][j] == jeton) {
                count++;
                if (count == 4) {
                    this.terminated = true;
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
                    this.terminated = true;
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
            this.terminated = true;
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
            this.terminated = true;
            return true;
        }

        return false;
    }

    public boolean grilleRemplie() {
        for (int j = 0; j < colonnes; j++) {
            if (grille[0][j] == '-') {
                return false;
            }
        }
        this.terminated = true;
        return true;
    }

    public void afficherGrille() {
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

    /*
     * Si il y a un gagnant ou une partie nulle ou un gagnant, on renvoie terminated = true
     * */
    public boolean isTerminated(){
        return terminated;
    }


}