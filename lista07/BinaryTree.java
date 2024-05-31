// File: BinaryTree.java

import java.util.ArrayList;
import java.util.List;

/**
 * A generic binary tree node.
 *
 * @param <T> the type of value stored in the node
 */
class TreeNode<T extends Comparable<T>> {
    T value;
    TreeNode<T> left;
    TreeNode<T> right;

    TreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
/**
 * A generic binary tree implementation.
 *
 * @param <T> the type of value stored in the tree
 */
public class BinaryTree<T extends Comparable<T>> {
    private TreeNode<T> root;
    /**
     * Inserts a value into the binary tree.
     *
     * @param value the value to insert
     */
    public void insert(T value) {
        root = insertRec(root, value);
    }
    private TreeNode<T> insertRec(TreeNode<T> root, T value) {
        if (root == null) {
            root = new TreeNode<>(value);
            return root;
        }
        if (value.compareTo(root.value) < 0) {
            root.left = insertRec(root.left, value);
        } else if (value.compareTo(root.value) > 0) {
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    /**
     * Deletes a value from the binary tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        root = deleteRec(root, value);
    }

    private TreeNode<T> deleteRec(TreeNode<T> root, T value) {
        if (root == null) return root;

        if (value.compareTo(root.value) < 0) {
            root.left = deleteRec(root.left, value);
        } else if (value.compareTo(root.value) > 0) {
            root.right = deleteRec(root.right, value);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            root.value = minValue(root.right);
            root.right = deleteRec(root.right, root.value);
        }

        return root;
    }

    private T minValue(TreeNode<T> root) {
        T minVal = root.value;
        while (root.left != null) {
            minVal = root.left.value;
            root = root.left;
        }
        return minVal;
    }

    /**
     * Searches for a value in the binary tree.
     *
     * @param value the value to search for
     * @return true if the value is found, false otherwise
     */
    public boolean search(T value) {
        return searchRec(root, value) != null;
    }

    private TreeNode<T> searchRec(TreeNode<T> root, T value) {
        if (root == null || root.value.equals(value)) {
            return root;
        }
        if (value.compareTo(root.value) < 0) {
            return searchRec(root.left, value);
        }
        return searchRec(root.right, value);
    }

    /**
     * Draws the binary tree to display its hierarchy.
     */
    public void draw() {
        drawRec(root, 0);
    }
    
    private void drawRec(TreeNode<T> node, int space) {
        if (node == null) return;
    
        space += 10;
    
        drawRec(node.right, space);
    
        System.out.print("\n");
        for (int i = 10; i < space; i++)
            System.out.print(" ");
        System.out.println(node.value);
    
        drawRec(node.left, space);
    }

    public static void main(String[] args) {
        BinaryTree<Integer> intTree = new BinaryTree<>();
        intTree.insert(50);
        intTree.insert(50);
        intTree.insert(30);
        intTree.insert(70);
        intTree.insert(20);
        intTree.insert(40);
        intTree.insert(60);
        intTree.insert(80);
        intTree.draw();

        BinaryTree<String> stringTree = new BinaryTree<>();
        stringTree.insert("M");
        stringTree.insert("B");
        stringTree.insert("Q");
        stringTree.insert("A");
        stringTree.insert("C");
        stringTree.insert("O");
        stringTree.insert("R");
        stringTree.draw();
        BinaryTree<Double> doubleTree = new BinaryTree<>();
        doubleTree.insert(50.0);
        doubleTree.insert(30.0);
        doubleTree.insert(70.0);
        doubleTree.insert(20.0);
        doubleTree.insert(40.0);
        doubleTree.insert(60.0);
        doubleTree.insert(80.0);
        doubleTree.draw();
    }
}
