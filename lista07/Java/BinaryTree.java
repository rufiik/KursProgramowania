/* Plik: BinaryTree.java
* Autor: Rafał Wochna 279752
*/
import java.io.Serializable;
/**
 * Drzewo binarne.
 * @param <T> typ wartości w drzewie
 */
class TreeNode<T extends Comparable<T>> implements Serializable {
    T value; // Wartość węzła.
    TreeNode<T> left; /* Lewe dziecko węzła.*/ 
    TreeNode<T> right; /* Prawe dziecko węzła.*/ 

    TreeNode(T value) {
        this.value = value; /* Ustaw wartość węzła. */
        this.left = null; /* Ustaw lewe dziecko na null. */
        this.right = null; /* Ustaw prawe dziecko na null. */
    } 
}
/**
 * Drzewo binarne.
 * @param <T> typ wartości w drzewie
 * @see TreeNode
 * @see Serializable
 * @see Comparable
 */
public class BinaryTree<T extends Comparable<T>> implements Serializable {
    private TreeNode<T> root; /* Korzeń drzewa. */
/**
 * Wstawia wartość do drzewa.
 * @param value - wartość do wstawienia
 */
    public void insert(T value) {
        root = insertRec(root, value);
    }
/**
 * Wstawia wartość do drzewa przy użyciu rekurencji.
 * @param root - korzeń drzewa
 * @param value - wartość do wstawienia
 * @return korzeń drzewa
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
 * Usuwa wartość z drzewa.
 * @param value - wartość do usunięcia
 */
    public void delete(T value) {
        root = deleteRec(root, value);
    }
/**
 * Usuwa wartość z drzewa przy użyciu rekurencji.
 * @param root - korzeń drzewa
 * @param value - wartość do usunięcia
 * @return korzeń drzewa
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
 * Znajduje najmniejszą wartość w drzewie.
 * @param root - korzeń drzewa
 * @return najmniejsza wartość w drzewie
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
 * Wyszukuje wartość w drzewie.
 * @param value - wartość do wyszukania
 * @return true, jeśli wartość zostanie znaleziona, false w przeciwnym razie
 */
    public boolean search(T value) {
        return searchRec(root, value) != null;
    }
/**
 * Wyszukuje wartość w drzewie przy użyciu rekurencji.
 * @param root - korzeń drzewa
 * @param value - wartość do wyszukania
 * @return węzeł zawierający wartość lub null, jeśli wartość nie zostanie znaleziona
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
 * Rysuje drzewo.
 * @return drzewo jako ciąg znaków
 */
    public String draw() {
        StringBuilder result = new StringBuilder();
        drawRec(root, 0, result);
        return result.toString();
    }
    /**
     * Rysuje drzewo przy użyciu rekurencji.
     * @param node - bieżący węzeł
     * @param space - dodatkowa ilość spacji
     * @param result - wynikowy ciąg znaków
     */
    private void drawRec(TreeNode<T> node, int depth, StringBuilder result) {
        if (node == null) return;
        drawRec(node.left, depth + 1, result);
        for (int i = 0; i < depth*4; i++)
            result.append("  "); 
        result.append(node.value);
        result.append("\n \n");
        drawRec(node.right, depth + 1, result);
    }
}