// File: BinaryTree.cpp

#include <iostream>
#include <memory>
#include <vector>
#include <string>
#include <algorithm>

/**
 * A generic binary tree node.
 *
 * @tparam T the type of value stored in the node
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
 * A generic binary tree implementation.
 *
 * @tparam T the type of value stored in the tree
 */
template <typename T>
class BinaryTree {
private:
    std::shared_ptr<TreeNode<T>> root;

    std::shared_ptr<TreeNode<T>> insertRec(std::shared_ptr<TreeNode<T>> node, T value) {
        if (!node) return std::make_shared<TreeNode<T>>(value);
        if (value < node->value) node->left = insertRec(node->left, value);
        else if (value > node->value) node->right = insertRec(node->right, value);
        return node;
    }

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

    T minValue(std::shared_ptr<TreeNode<T>> node) {
        T minVal = node->value;
        while (node->left) {
            minVal = node->left->value;
            node = node->left;
        }
        return minVal;
    }

    bool searchRec(std::shared_ptr<TreeNode<T>> node, T value) {
        if (!node) return false;
        if (node->value == value) return true;
        if (value < node->value) return searchRec(node->left, value);
        return searchRec(node->right, value);
    }

    void drawRec(std::shared_ptr<TreeNode<T>> node, int space) {
        if (!node) return;

        space += 10;
        drawRec(node->right, space);

        std::cout << std::endl;
        for (int i = 10; i < space; i++) std::cout << " ";
        std::cout << node->value << "\n";

        drawRec(node->left, space);
    }

public:
    void insert(T value) {
        root = insertRec(root, value);
    }

    void deleteNode(T value) {
        root = deleteRec(root, value);
    }

    bool search(T value) {
        return searchRec(root, value);
    }

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
