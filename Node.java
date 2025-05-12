/**
 * Node represents a file or folder in the file system.
 * Each node knows its name, type (file or folder), parent, and children (if folder).
 * 
 * @author Brian Tramuel
 * @date May 11, 2025
 */
import java.util.*;

public class Node {
    String name;
    boolean isFile;
    Node parent;
    Map<String, Node> children;

    public Node(String name, boolean isFile, Node parent) {
        this.name = name;
        this.isFile = isFile;
        this.parent = parent;
        if (!isFile)
            this.children = new HashMap<>();
    }

    public boolean hasChild(String name) {
        return !isFile && children.containsKey(name);
    }

    public Node getChild(String name) {
        return children.get(name);
    }

    public void addChild(Node child) {
        if (!isFile)
            children.put(child.name, child);
    }

    public List<String> listChildren() {
        if (isFile) return List.of();
        List<String> list = new ArrayList<>(children.keySet());
        Collections.sort(list);
        return list;
    }
}
