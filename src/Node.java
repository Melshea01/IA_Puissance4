import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Node> fils;
    private Action action;
    private Puissance4 jeu;

    public Node(Puissance4 puissance4) {
        fils = new ArrayList<>();
        this.jeu = puissance4;
    }

    public List<Node> getFils() {

            return this.fils;
    }
    public void setFils(){
        for (int col = 0; col < 7; col++) {
            if (jeu.validerCoup(col)) {
                Puissance4 newJeu = jeu.clone();
                newJeu.placerJeton(col);
                newJeu.switchJoueur();
                Node fils = new Node(newJeu);
                fils.setAction(new Action(col));
                addFils(fils);
            }
        }
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

    public Puissance4 getJeu(){
        return this.jeu;
    }


    public boolean estFeuille() {
        return fils.isEmpty();
    }



}
