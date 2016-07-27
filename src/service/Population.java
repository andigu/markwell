package service;

import entity.Person;

/**
 * A Binary Search Tree that holds nodes that are children of the Person object. More efficient search than a linear
 * algorithm in most cases.
 * <p>
 * Bugs: empty BST will raise errors with search
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
class Population<PersonType extends Person> {
    private PersonType root;
    private Population<PersonType> left;
    private Population<PersonType> right;

    private Population(PersonType person) {
        root = person;
        left = null;
        right = null;
    }

    Population(PersonType[] people) {
        for (PersonType person : people) {
            insert(person);
        }
    }

    /**
     * Recursively inserts a node into the tree.
     */
    private void insert(PersonType node) {
        if (root == null) {
            root = node;
        } else {
            int compareResult = compare(node, root);
            if (compareResult < 0) { // If the node to be inserted is less than the root node
                if (left != null) { // If there is a left child, continue in the recursive insertion
                    left.insert(node);
                } else {  // If there is no left child, set the left child to be the new node
                    left = new Population<>(node);
                }
            } else if (compareResult > 0) { // If the node to be inserted is greater than the root node
                if (right != null) { // If there is a right child, continue in recursive insertion
                    right.insert(node);
                } else { // If there is no right child, set the right child to be the new node
                    right = new Population<>(node);
                }
            }
        }
    }

    /**
     * Recursively finds a node with the given username.
     *
     * @param username The username of the student to be found.
     * @return The person with the given username.
     */
    PersonType find(String username) {
        PersonType result = null;
        if (username.equals(root.getUsername())) { // Base case: node found
            result = root;
        } else {
            int compareResult = compare(username, root.getUsername());
            // Go to the left if the username is less lexicographically than the root node's username and a left child exists
            if (compareResult < 0 && left != null) {
                result = left.find(username);
            }
            // Go to the right if the username is greater lexicographically than the root node's username and a right child exists
            else if (compareResult > 0 && right != null) {
                result = right.find(username);
            }
        }
        return result;
    }

    /**
     * Helper function that compares two people.
     *
     * @param a The first person to be compared.
     * @param b The second person to be compared.
     * @return A negative number of a's username is less lexicographically than b's, zero if they're the same and a
     * positive number if a's username is greater lexicographically than b's.
     */
    private int compare(PersonType a, PersonType b) {
        return a.compareTo(b);
    }

    private int compare(String a, String b) {
        return a.compareTo(b);
    }
}
