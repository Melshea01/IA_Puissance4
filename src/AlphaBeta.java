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
        EvaluationAction evaluation = joueurMax(racine, maxProfondeur, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return evaluation.getAction();
    }

    public int getMaxProfondeur() {
        return maxProfondeur;
    }

    private EvaluationAction joueurMax(Node node, int profondeur, int alpha, int beta) {
        if (node.estFeuille() || profondeur == 0) {
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
            alpha = Math.max(alpha, meilleurEval);
            if (beta <= alpha) {
                break;
            }
        }

        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private EvaluationAction joueurMin(Node node, int profondeur, int alpha, int beta) {
        if (node.estFeuille() || profondeur == 0) {
            return new EvaluationAction(eval(node), null);
        }

        int meilleurEval = Integer.MAX_VALUE;
        Action meilleureAction = null;

        for (Node fils : node.getFils()) {
            EvaluationAction evaluationAction = joueurMax(fils, profondeur - 1, alpha, beta);
            if (evaluationAction.getEval() < meilleurEval) {
                meilleurEval = evaluationAction.getEval();
                meilleureAction = fils.getAction();
            }
            beta = Math.min(beta, meilleurEval);
            if (beta <= alpha) {
                break;
            }
        }

        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private int eval(Node node) {
        int scoreTotal = node.getEtat().calculScore(joueur) - node.getEtat().calculScore(adversaire);
        return scoreTotal;
    }
}
