import java.util.ArrayList;
import java.util.List;

public class ActionUCT {

    private NodeUCT nodeParent;
    private List<NodeUCT> nodeFils;
    private int column;

    public ActionUCT(int column, NodeUCT nodeParent) {
        this.column = column;
        this.nodeParent = nodeParent;
        this.nodeFils = new ArrayList<>();
    }

    public int getColumn() {
        return column;
    }

    public NodeUCT getNodeParent(){
        return  nodeParent;
    }

    public void addNodeFils(NodeUCT fils){
        this.nodeFils.add(fils);
    }

    public List<NodeUCT> getNodeFils(){
        return this.nodeFils;
    }

}
