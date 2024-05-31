/* Plik: BinaryTree.cpp*/ 
/*Autor: Rafal Wochna 279752*/
#include <iostream>
#include <memory>
#include <vector>
#include <string>
#include <algorithm>

/**
 * Węzeł generycznego drzewa binarnego.
 * @tparam T typ przechowywanej wartości w węźle
 */
template <typename T>
class TreeNode {
public:
    T value;
    std::shared_ptr<TreeNode<T>> left;
    std::shared_ptr<TreeNode<T>> right;

    TreeNode(T val) : value(val), left(nullptr), right(nullptr) {}
};

/**
 * Generyczna implementacja drzewa binarnego.
 *
 * @tparam T typ przechowywanej wartości w drzewie
 */
template <typename T>
class BinaryTree {
private:
    std::shared_ptr<TreeNode<T>> root;

    /**
     * Rekurencyjne wstawianie wartości do drzewa.
     *
     * @param node wskaźnik na aktualny węzeł
     * @param value wartość do wstawienia
     * @return wskaźnik na zaktualizowany węzeł
     */
    std::shared_ptr<TreeNode<T>> insertRec(std::shared_ptr<TreeNode<T>> node, T value) {
        if (!node) return std::make_shared<TreeNode<T>>(value);
        if (value < node->value) node->left = insertRec(node->left, value);
        else if (value > node->value) node->right = insertRec(node->right, value);
        return node;
    }

    /**
     * Rekurencyjne usuwanie wartości z drzewa.
     *
     * @param node wskaźnik na aktualny węzeł
     * @param value wartość do usunięcia
     * @return wskaźnik na zaktualizowany węzeł
     */
    std::shared_ptr<TreeNode<T>> deleteRec(std::shared_ptr<TreeNode<T>> node, T value) {
        if (!node) return node;

        if (value < node->value) node->left = deleteRec(node->left, value);
        else if (value > node->value) node->right = deleteRec(node->right, value);
        else {
            if (!node->left) return node->right;
            if (!node->right) return node->left;

            node->value = minValue(node->right);
            node->right = deleteRec(node->right, node->value);
        }
        return node;
    }

    /**
     * Znajdowanie minimalnej wartości w drzewie.
     *
     * @param node wskaźnik na aktualny węzeł
     * @return minimalna wartość w drzewie
     */
    T minValue(std::shared_ptr<TreeNode<T>> node) {
        T minVal = node->value;
        while (node->left) {
            minVal = node->left->value;
            node = node->left;
        }
        return minVal;
    }

    /**
     * Rekurencyjne wyszukiwanie wartości w drzewie.
     *
     * @param node wskaźnik na aktualny węzeł
     * @param value wartość do wyszukania
     * @return true, jeśli wartość została znaleziona; false w przeciwnym razie
     */
    bool searchRec(std::shared_ptr<TreeNode<T>> node, T value) {
        if (!node) return false;
        if (node->value == value) return true;
        if (value < node->value) return searchRec(node->left, value);
        return searchRec(node->right, value);
    }

    /**
     * Rekurencyjne rysowanie drzewa.
     *
     * @param node wskaźnik na aktualny węzeł
     * @param space odstęp w rysowaniu
     */
    void drawRec(std::shared_ptr<TreeNode<T>> node, int space) {
        if (!node) return;

        space += 10;
        drawRec(node->left, space);

        std::cout << std::endl;
        for (int i = 10; i < space; i++) std::cout << " ";
        std::cout << node->value << "\n";

        drawRec(node->right, space);
    }

public:
    /**
     * Wstawianie wartości do drzewa.
     *
     * @param value wartość do wstawienia
     */
    void insert(T value) {
        root = insertRec(root, value);
    }

    /**
     * Usuwanie wartości z drzewa.
     *
     * @param value wartość do usunięcia
     */
    void deleteNode(T value) {
        root = deleteRec(root, value);
    }

    /**
     * Wyszukiwanie wartości w drzewie.
     *
     * @param value wartość do wyszukania
     * @return true, jeśli wartość została znaleziona; false w przeciwnym razie
     */
    bool search(T value) {
        return searchRec(root, value);
    }

    /**
     * Rysowanie drzewa.
     */
    void draw() {
        drawRec(root, 0);
    }
};

int main() {
    BinaryTree<int> intTree;
    intTree.insert(50);
    intTree.insert(30);
    intTree.insert(70);
    intTree.insert(20);
    intTree.insert(40);
    intTree.insert(60);
    intTree.insert(80);
    intTree.draw();
    std::cout << "Drzewo po zmianach\n";
    intTree.deleteNode(20);
    intTree.deleteNode(30);
    intTree.draw();
    intTree.search(30) ? std::cout << "30 istnieje\n" : std::cout << "30 nie istnieje\n";
    intTree.search(40) ? std::cout << "40 istnieje\n" : std::cout << "40 nie istnieje\n";
    std::cout << "\n";

    BinaryTree<std::string> stringTree;
    stringTree.insert("M");
    stringTree.insert("B");
    stringTree.insert("Q");
    stringTree.insert("A");
    stringTree.insert("C");
    stringTree.insert("O");
    stringTree.insert("R");
    stringTree.draw();
    std::cout << "Drzewo po zmianach\n";
    stringTree.deleteNode("A");
    stringTree.deleteNode("R");
    stringTree.draw();
    stringTree.search("R") ? std::cout << "R istnieje\n" : std::cout << "R nie istnieje\n";
    stringTree.search("C") ? std::cout << "C istnieje\n" : std::cout << "C nie istnieje\n";
    std::cout << "\n";

    BinaryTree<double> doubleTree;
    doubleTree.insert(50.5);
    doubleTree.insert(30.3);
    doubleTree.insert(70.2);
    doubleTree.insert(20.1);
    doubleTree.insert(40.5);
    doubleTree.insert(60.4);
    doubleTree.insert(80.6);
    doubleTree.draw();
    std::cout << "Drzewo po zmianach\n";
    doubleTree.deleteNode(20.1);
    doubleTree.deleteNode(60.4);
    doubleTree.draw();
    doubleTree.search(60.4) ? std::cout << "60.4 istnieje\n" : std::cout << "60.4 nie istnieje\n";
    doubleTree.search(40.5) ? std::cout << "40.5 istnieje\n" : std::cout << "40.5 nie istnieje\n";
    std::cout << "\n";

    return 0;
}
