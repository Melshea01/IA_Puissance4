public class EvaluationAction {
    private int eval;
    private Action action;

    public EvaluationAction(int eval, Action action) {
        this.eval = eval;
        this.action = action;
    }

    public int getEval() {
        return eval;
    }

    public Action getAction() {
        return action;
    }
}
