public class Minimax {
    private int maxProfondeur;
    private char joueur;
    private char adversaire;

    public Minimax(int maxProfondeur, char joueur, char adversaire) {
        this.maxProfondeur = maxProfondeur;
        this.joueur = joueur;
        this.adversaire = adversaire;
    }

    public Action minimax(Node racine) {
        EvaluationAction evaluation = joueurMax(racine, maxProfondeur);
        return evaluation.getAction();
    }

    public int getMaxProfondeur() {
        return maxProfondeur;
    }

    private EvaluationAction joueurMax(Node node, int profondeur) {
        if (node.estFeuille() || profondeur == 0) {
            return new EvaluationAction(eval(node), null);
        }

        int meilleurEval = Integer.MIN_VALUE;
        Action meilleureAction = null;

        for (Node fils : node.getFils()) {
            EvaluationAction evaluationAction = joueurMin(fils, profondeur - 1);
            if (evaluationAction.getEval() > meilleurEval) {
                meilleurEval = evaluationAction.getEval();
                meilleureAction = fils.getAction();
            }
        }

        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private EvaluationAction joueurMin(Node node, int profondeur) {
        if (node.estFeuille() || profondeur == 0) {
            return new EvaluationAction(eval(node), null);
        }

        int meilleurEval = Integer.MAX_VALUE;
        Action meilleureAction = null;

        for (Node fils : node.getFils()) {
            EvaluationAction evaluationAction = joueurMax(fils, profondeur - 1);
            if (evaluationAction.getEval() < meilleurEval) {
                meilleurEval = evaluationAction.getEval();
                meilleureAction = fils.getAction();
            }
        }

        return new EvaluationAction(meilleurEval, meilleureAction);
    }

    private int eval(Node node) {
        int scoreTotal = node.getEtat().calculScore(joueur) - node.getEtat().calculScore(adversaire);
        return scoreTotal;
    }


}


