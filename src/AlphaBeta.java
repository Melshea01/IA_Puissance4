public class AlphaBeta {
    private int maxProfondeur;
    private char joueur;
    private char adversaire;

    public AlphaBeta(int maxProfondeur, char joueur, char adversaire) {
        this.maxProfondeur = maxProfondeur;
        this.joueur = joueur;
        this.adversaire = adversaire;
    }

    public Action alphaBeta(Node racine) {
        racine.getFils();
        EvaluationAction evaluation = joueurMax(racine, maxProfondeur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return evaluation.getAction();
    }

    public int getMaxProfondeur() {
        return maxProfondeur;
    }

    private EvaluationAction joueurMax(Node node, int profondeur, int alpha, int beta) {
        if(profondeur != 0)node.setFils();
        if (node.estFeuille()) {
            return new EvaluationAction(eval(node), null);
        }

        int meilleurEval = Integer.MIN_VALUE;
        Action meilleureAction = null;

        for (Node fils : node.getFils()) {
            EvaluationAction evaluationAction = joueurMin(fils, profondeur - 1, alpha, beta);
            if (evaluationAction.getEval() > meilleurEval) {
                meilleurEval = evaluationAction.getEval();
                meilleureAction = fils.getAction();
            }
            if (meilleurEval >= beta) {
                return new EvaluationAction(meilleurEval, meilleureAction);
            }
            alpha = Math.max(alpha, meilleurEval);
        }
        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private EvaluationAction joueurMin(Node node, int profondeur, int alpha, int beta) {
        //Si on est sur une feuille ou que la profondeur est null, on retourne l'évaluation de la node
        if(profondeur != 0)node.setFils();
        if (node.estFeuille()) {
            return new EvaluationAction(eval(node), null);
        }

        //Pire valeur possible pour nous
        int meilleurEval = Integer.MAX_VALUE;
        Action meilleureAction = null;

        for (Node fils : node.getFils()) {
            EvaluationAction evaluationAction = joueurMax(fils, profondeur - 1, alpha, beta);
            if (evaluationAction.getEval() < meilleurEval) {
                meilleurEval = evaluationAction.getEval();
                meilleureAction = fils.getAction();
            }

            if (meilleurEval <= alpha) {
                return new EvaluationAction(meilleurEval, meilleureAction);
            }
            beta = Math.min(beta, meilleurEval);
        }

        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private int eval(Node node) {
        int scoreTotal = node.getJeu().calculScore(joueur) - node.getJeu().calculScore(adversaire);
        return scoreTotal;
    }
}
