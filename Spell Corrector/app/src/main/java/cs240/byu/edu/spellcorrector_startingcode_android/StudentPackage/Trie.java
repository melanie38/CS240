package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;


import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by melanie on 04/05/2016.
 */
public class Trie implements ITrie {
    MyNode root = new MyNode();
    TreeSet<String> myTree = new TreeSet<>();
    int nodeCount = 1;

    public void add(String word) {
        // convert the string to lower string letters
        word = word.toLowerCase();
        char letter;
        int letterIndex;

        // let the recursion start!
        MyNode ptr = root;
        for (int index = 0; index < word.length(); index ++) {
            // look up char in the word
            letter = word.charAt(index);
            letterIndex = letter - 'a';
            if (ptr.letterFound(letterIndex) == null) {
                // create a new node for the letter
                ptr.addNode(letterIndex, new MyNode());
                nodeCount++;
            }
            ptr = ptr.letterFound(letterIndex);
        }
        ptr.updateCount();
        myTree.add(word);
    }

    public ITrie.INode find(String word) {
        word = word.toLowerCase();
        MyNode ptr = root;
        char letter;
        int letterIndex;

        for (int index = 0; index < word.length(); index ++) {
            letter = word.charAt(index);
            letterIndex = letter - 'a';
            if (ptr.letterFound(letterIndex) == null) {
                return null;
            }
            else {
                ptr = ptr.letterFound(letterIndex);
            }
        }
        if (ptr.getValue() == 0) {
            return null;
        }
        return ptr;
    }

    // number of unique words in the trie
    public int getWordCount() {
        return myTree.size();
    }

    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {
        StringBuilder wordsInTrie = new StringBuilder();
        Iterator i = myTree.iterator();
        while (i.hasNext()) {
            wordsInTrie.append(i.next() + "\n");
        }
        return wordsInTrie.toString();
    }

    @Override
    public int hashCode() {
        int hashCode = (nodeCount / 3) * myTree.size();
        return hashCode;
    }

    @Override
    public boolean equals(Object input) {
        if (input == null) {
            return false;
        }
        else {
            if (!(input instanceof Trie)) {
                return false;
            }
            Trie t = (Trie) input;
            int hash = hashCode();
            if (hash != t.hashCode()) {
                return false;
            }
            if (myTree.size() != t.getWordCount()) {
                return false;
            }
            if (nodeCount != t.getNodeCount()) {
                return false;
            }
            // check if the words that are in my Trie are found in the other trie and compare the frequences
            Iterator i = myTree.iterator();
            while (i.hasNext()) {
                String word = i.next().toString();
                if (t.find(word) == null) {
                    return false;
                }
                else if (find(word).getValue() != t.find(word).getValue()) {
                    return false;
                }
            }
            return true;
        }
    }
}
