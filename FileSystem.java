/**
 * FileSystem class handles command input and file system operations.
 * Simulates basic UNIX-style commands using a tree structure.
 * 
 * Commands supported: mkdir, touch, cd, ls, pwd, tree, exit
 * 
 * Author: Brian Tramuel
 * Date: May 11, 2025
 */

import java.util.*;

public class FileSystem {
    private final Node root;
    private Node current;

    public FileSystem() {
        root = new Node("/", false, null);
        current = root;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(getPath() + " $ ");
            String[] input = sc.nextLine().trim().split(" ");
            String cmd = input[0];

            switch (cmd) {
                case "mkdir" -> {
                    if (input.length < 2) System.out.println("Usage: mkdir folderName");
                    else mkdir(input[1]);
                }
                case "touch" -> {
                    if (input.length < 2) System.out.println("Usage: touch fileName");
                    else touch(input[1]);
                }
                case "ls" -> ls();
                case "cd" -> {
                    if (input.length < 2) System.out.println("Usage: cd folderName or cd ..");
                    else cd(input[1]);
                }
                case "pwd" -> System.out.println(getPath());
                case "tree" -> printTree(root, 0);
                case "exit" -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private void mkdir(String name) {
        if (current.hasChild(name)) {
            System.out.println("Directory already exists.");
        } else {
            Node folder = new Node(name, false, current);
            current.addChild(folder);
        }
    }

    private void touch(String name) {
        if (current.hasChild(name)) {
            System.out.println("File already exists.");
        } else {
            Node file = new Node(name, true, current);
            current.addChild(file);
        }
    }

    private void ls() {
        for (String name : current.listChildren()) {
            Node n = current.getChild(name);
            System.out.println((n.isFile ? "[File] " : "[Dir] ") + name);
        }
    }

    private void cd(String name) {
        if (name.equals("..")) {
            if (current.parent != null)
                current = current.parent;
        } else if (current.hasChild(name)) {
            Node next = current.getChild(name);
            if (next.isFile) {
                System.out.println("Not a directory.");
            } else {
                current = next;
            }
        } else {
            System.out.println("Directory not found.");
        }
    }

    private String getPath() {
        List<String> parts = new ArrayList<>();
        Node temp = current;
        while (temp != null && temp.parent != null) {
            parts.add(temp.name);
            temp = temp.parent;
        }
        Collections.reverse(parts);
        return "/" + String.join("/", parts);
    }

    private void printTree(Node node, int level) {
        for (int i = 0; i < level; i++) System.out.print("  ");
        System.out.println((node.isFile ? "[F] " : "[D] ") + node.name);
        if (!node.isFile) {
            for (String key : new TreeSet<>(node.children.keySet())) {
                printTree(node.children.get(key), level + 1);
            }
        }
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.start();
    }
}
