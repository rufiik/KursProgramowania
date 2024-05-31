/* File: BinaryTree.java
* @author : Rafał Wochna 279752
*/

import java.io.Serializable;
/**
 * A binary tree.
 * @param <T> the type of the values in the tree
 */
class TreeNode<T extends Comparable<T>> implements Serializable {
    T value; // The value of the node.
    TreeNode<T> left; /* The left child of the node.*/ 
    TreeNode<T> right; /* The right child of the node.*/ 

    TreeNode(T value) {
        this.value = value; /* Set the value of the node.*/
        this.left = null;
        this.right = null;
    }
}
/**
 * A binary tree.
 * @param <T> the type of the values in the tree
 * @see TreeNode
 * @see Serializable
 * @see Comparable
 */
public class BinaryTree<T extends Comparable<T>> implements Serializable {
    private TreeNode<T> root; /* The root of the tree.*/
/**
 * Inserts a value into the tree.
 * @param value - the value to insert
 */
    public void insert(T value) {
        root = insertRec(root, value);
    }
/**
 * Inserts a value into the tree using recursion.
 * @param root - the root of the tree
 * @param value - the value to insert
 * @return the root of the tree
 */
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
 * Deletes a value from the tree.
 * @param value - the value to delete
 */
    public void delete(T value) {
        root = deleteRec(root, value);
    }
/**
 * Deletes a value from the tree using recursion.
 * @param root - the root of the tree
 * @param value - the value to delete
 * @return the root of the tree
 */
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
/**
 * Finds the minimum value in the tree.
 * @param root - the root of the tree
 * @return the minimum value in the tree
 */
    private T minValue(TreeNode<T> root) {
        T minVal = root.value;
        while (root.left != null) {
            minVal = root.left.value;
            root = root.left;
        }
        return minVal;
    }
/**
 * Searches for a value in the tree.
 * @param value - the value to search for
 * @return true if the value is found, false otherwise
 */
    public boolean search(T value) {
        return searchRec(root, value) != null;
    }
/**
 * Searches for a value in the tree using recursion.
 * @param root - the root of the tree
 * @param value - the value to search for
 * @return the node containing the value, or null if the value is not found
 */
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
 * Draws the tree.
 * @return the tree as a string
 */
    public String draw() {
        StringBuilder result = new StringBuilder();
        drawRec(root, 0, result);
        return result.toString();
    }
    /**
     * Draws the tree using recursion.
     * @param node - the current node
     * @param space - the space to add
     * @param result - the result string
     */
    private void drawRec(TreeNode<T> node, int space, StringBuilder result) {
        if (node == null) return;
        space += 10;
        drawRec(node.right, space, result);
        for (int i = 10; i < space; i++)
            result.append(" ");
            result.append(node.value);
            result.append("\n");
            drawRec(node.left, space, result);
    }
}
