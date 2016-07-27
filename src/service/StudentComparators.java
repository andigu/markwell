package service;

import entity.Student;

import java.util.Comparator;

/**
 * Stores ways to compare students in the form of comparators (e.g. compare by name, net score, average, etc.)
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */

class StudentComparators {
    // Comparator that compares students by name
    static Comparator<Student> getSortByName(final boolean ascending) {
        return new Comparator<Student>() {
            @Override
            public int compare(Student studentA, Student studentB) {
                if (ascending) {
                    return studentA.getName().compareTo(studentB.getName());
                } else {
                    return studentB.getName().compareTo(studentA.getName());
                }
            }
        };
    }
}
