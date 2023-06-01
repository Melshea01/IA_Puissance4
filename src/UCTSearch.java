import UCT.Puissance4UCT;

import java.util.List;
import java.util.Random;

public class UCTSearch {
    private static final double c = Math.sqrt(2); // Exploration constant
    private static final int DEFAULT_SIMULATION_COUNT = 10000; // Default number of simulations

    private Random random;
    private char joueur;

    public UCTSearch(char joueur) {
        this.joueur = joueur;
        this.random = new Random();
    }

    public ActionUCT uctSearch(Puissance4 initialJeu) {
        NodeUCT racine = new NodeUCT(initialJeu,null);

        int counter = 0;
        while (counter < DEFAULT_SIMULATION_COUNT) {
            NodeUCT selectedNode = treePolicy(racine);
            double delta = defaultPolicy(selectedNode);
            backup(selectedNode, delta);
            counter+=1;
        }

        return bestChild(racine, 0).getAction();
    }

    private NodeUCT treePolicy(NodeUCT node) {
        while (!node.isTerminal()) {
            if (!node.isFullyExpanded()) {
                return expand(node);
            } else {
                node = bestChild(node, c);
            }
        }
        return node;
    }

    private NodeUCT expand(NodeUCT node) {
        List<ActionUCT> untriedActions = node.getUntriedActions();
        ActionUCT action = untriedActions.get(random.nextInt(untriedActions.size()));

        Puissance4 newJeu = node.getJeu().clone();
        newJeu.placerJeton(action.getColumn());
        newJeu.switchJoueur();
        NodeUCT newChild = new NodeUCT(newJeu, action);

        return newChild;
    }

    private NodeUCT bestChild(NodeUCT node, double c) {
        double bestValue = Double.NEGATIVE_INFINITY;
        NodeUCT bestChild = null;

        for (NodeUCT child : node.getNodeFils()) {
            double value = child.getQ() / child.getN() + c * Math.sqrt(2*Math.log(node.getN()) / child.getN());
            if (value > bestValue) {
                bestValue = value;
                bestChild = child;
            }
        }

        return bestChild;
    }

    private double defaultPolicy(NodeUCT node) {
        Puissance4 jeu = node.getJeu();
        while (!node.isTerminal()) {
            List<ActionUCT> actions = node.getAllActions();
            ActionUCT randomAction = actions.get(random.nextInt(actions.size()));
            jeu = node.getJeu().clone();
            int ligne = jeu.placerJeton(randomAction.getColumn());
            jeu.verifierGagnant(ligne, randomAction.getColumn());
            jeu.grilleRemplie();
            jeu.switchJoueur();
            node = new NodeUCT(jeu, randomAction);
        }
        return jeu.calculScore(joueur);
    }

    private void backup(NodeUCT node, double delta) {
        while (node != null) {
            node.incrementN();
            node.incrementQ(delta);
            node = node.getParent();
        }
    }
}
