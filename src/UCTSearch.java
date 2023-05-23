import java.util.List;
import java.util.Random;

public class UCTSearch {
    private static final double Cp = Math.sqrt(2); // Exploration constant
    private static final int DEFAULT_SIMULATION_COUNT = 1000; // Default number of simulations

    private Random random;

    public UCTSearch() {
        this.random = new Random();
    }

    public Action uctSearch(Puissance4 initialJeu, int simulationCount) {
        Node root = new Node(initialJeu,);
        long endTime = System.currentTimeMillis() + simulationCount;

        while (System.currentTimeMillis() < endTime) {
            Node selectedNode = treePolicy(root);
            double delta = defaultPolicy(selectedNode.getJeu());
            backup(selectedNode, delta);
        }

        return bestChild(root, 0).getAction();
    }

    private Node treePolicy(Node node) {
        while (!node.isTerminal()) {
            if (!node.isFullyExpanded()) {
                return expand(node);
            } else {
                node = bestChild(node, Cp);
            }
        }
        return node;
    }

    private Node expand(Node node) {
        List<Action> untriedActions = node.getUntriedActions();
        Action action = untriedActions.get(random.nextInt(untriedActions.size()));

        Puissance4 newJeu = node.getJeu().clone();
        newJeu.placerJeton(action.getColumn());
        Node newChild = new Node(newJeu, action, node);
        node.addFils(newChild);

        return newChild;
    }

    private Node bestChild(Node node, double c) {
        double bestValue = -Double.MAX_VALUE;
        Node bestChild = null;

        for (Node child : node.getFils()) {
            double value = child.getQ() / child.getN() + c * Math.sqrt(2*Math.log(node.getN()) / child.getN());
            if (value > bestValue) {
                bestValue = value;
                bestChild = child;
            }
        }

        return bestChild;
    }

    private double defaultPolicy(Puissance4 jeu) {
        //Ajouter un atribu terminal dans jeu nan ???
        while (!jeu.isTerminal()) {
            List<Action> actions = jeu.getAvailableActions();
            Action randomAction = actions.get(random.nextInt(actions.size()));
            jeu.placerJeton(randomAction.getColumn());
        }
        return jeu.getReward();
    }

    private void backup(Node node, double delta) {
        while (node != null) {
            node.incrementN();
            node.incrementQ(delta);
            node = node.getParent();
        }
    }
}

/*
class Node {
    private State state;
    private Action action;
    private Node parent;
    private List<Node> children;
    private int n;
    private double q;

    // Implémentez les getters et setters, ainsi que les autres méthodes nécessaires

    public boolean isTerminal() {
        // Implémentez la logique pour vérifier si le nœud est terminal
        // Retournez true si c'est un état terminal, false sinon
    }

    public boolean isFullyExpanded() {
        // Implémentez la logique pour vérifier si le nœud est entièrement développé (tous les enfants ont été explorés)
        // Retournez true si le nœud est entièrement développé, false sinon
    }

    public List<Action> getUntriedActions() {
        // Implémentez la logique pour obtenir les actions non testées à partir de l'état
        // Retournez la liste des actions non testées
    }

    public void addChild(Node child) {
        // Implémentez la logique pour ajouter un enfant au nœud
    }

    public List<Node> getChildren() {
        // Implémentez la logique pour obtenir la liste des enfants du nœud
        // Retournez la liste des enfants
    }

    public void incrementN() {
        // Implémentez la logique pour incrémenter le compteur N du nœud
    }

    public void incrementQ(double delta) {
        // Implémentez la logique pour incrémenter le compteur Q du nœud avec le delta donné
    }
}
*/
