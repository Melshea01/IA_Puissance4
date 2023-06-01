import java.util.ArrayList;
import java.util.List;

public class ActionUCT {

    private NodeUCT nodeParent;
    private NodeUCT node;
    private int column;

    public ActionUCT(int column, NodeUCT nodeParent) {
        this.column = column;
        this.nodeParent = nodeParent;
        this.node = null;
    }

    public int getColumn() {
        return column;
    }

    public NodeUCT getNodeParent(){
        return  nodeParent;
    }

    public void addNodeFils(NodeUCT fils){
        this.node =fils;
    }

    public NodeUCT getNodeFils(){
        return this.node;
    }

}
