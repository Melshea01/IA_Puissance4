import UCT.Puissance4UCT;

import java.util.ArrayList;
import java.util.List;

public class NodeUCT {
    private List<ActionUCT> actionsFils;
    private ActionUCT actionParent;
    private Puissance4 jeu;

//  valeur de récompense cumulée du nœud
    private double Q;
//  nombre de simulations effectuées
    private int N ;

    public NodeUCT(Puissance4 puissance4, ActionUCT actionParent) {
        this.actionsFils = new ArrayList<>();
        this.actionParent = actionParent;
        this.jeu = puissance4;
        this.Q = 0.00;
        this.N = 1;
        for (int col = 0; col < 7; col++) {
            if (jeu.validerCoup(col)) {
                this.actionsFils.add(new ActionUCT(col, this));
            }
        }
        if(this.actionParent !=null)this.actionParent.addNodeFils(this);
    }

    public ActionUCT getAction() {
        return actionParent;
    }


    public Puissance4 getJeu(){
        return this.jeu;
    }


    public boolean isTerminal() {
        return this.jeu.isTerminated();
    }

    public boolean isFullyExpanded() {
        for(ActionUCT actionFils : actionsFils){
            if(actionFils.getNodeFils()==null)return false;
        }
        return true;
    }

    public List<ActionUCT> getUntriedActions(){
        List<ActionUCT> untriedActions = new ArrayList<>();
        for (ActionUCT actionFils : actionsFils){
            if(actionFils.getNodeFils()==null)untriedActions.add(actionFils);
        }
        return untriedActions;
    }

    public void incrementN(){
        this.N = N++;
    }

    public void incrementQ(double delta){
        this.Q = Q + delta;
    }

    public NodeUCT getParent() {
        return this.getParent();
    }

    public double getQ() {
        return Q;
    }

    public int getN() {
        return N;
    }

    public List<ActionUCT> getAllActions(){
        return this.actionsFils;
    }

    public List<NodeUCT> getNodeFils(){
        List<NodeUCT> nodeFils = new ArrayList<>();

        for(ActionUCT action : actionsFils){
            nodeFils.add(action.getNodeParent());
        }

        return  nodeFils;
    }
}
