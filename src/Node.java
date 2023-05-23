import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> fils;
    private Action action;
    private Puissance4 etat;

    public Node(Puissance4 puissance4, int profondeur) {
        fils = new ArrayList<>();
        this.etat = puissance4;

        if(profondeur>0)
        for (int col = 0; col < 7; col++) {
            if (etat.validerCoup(col)) {
                Puissance4 newEtat = etat.clone();
                newEtat.placerJeton(col);
                newEtat.switchJoueur();
                Node fils = new Node(newEtat, profondeur-1);
                fils.setAction(new Action(col));
                addFils(fils);
            }
        }
    }

    public List<Node> getFils() {
        return fils;
    }


    public void addFils(Node fils) {
        this.fils.add(fils);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Puissance4 getEtat(){
        return this.etat;
    }


    public boolean estFeuille() {
        return fils.isEmpty();
    }



}
