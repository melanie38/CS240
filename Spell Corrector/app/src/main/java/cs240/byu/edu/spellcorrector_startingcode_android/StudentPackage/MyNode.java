package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;


/**
 * Created by melanie on 04/05/2016.
 */
public class MyNode implements ITrie.INode {
    private MyNode[] nodes;
    private int count;
    private String name = new String();

    public MyNode() {
        nodes = new MyNode[26];
        count = 0;
    }
    public MyNode letterFound(int index) {
        if (nodes[index] != null) {
            return nodes[index];
        }
        else {
            return null;
        }
    }
    public void addNode(int index, MyNode ptr) {
        nodes[index] = ptr;
    }
    public void updateCount() { count++; }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    // from the ITrie.INode interface
    public int getValue(){
        return count;
    }

}
