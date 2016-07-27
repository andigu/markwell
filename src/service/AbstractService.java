package service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import entity.CourseAssociation;
import entity.Student;
import entity.StudentCourseAssociation;
import entity.Teacher;
import entity.TeacherCourseAssociation;

/**
 * Service that implements all read-only methods (this is because for testing, there must be a way to isolate the
 * database from the testing yet still test the service.
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
abstract class AbstractService implements Service {
    Population<Student> studentSearchTree;
    Population<Teacher> teacherSearchTree;

    /**
     * Gets the student that has matching credentials by searching the BST of students for a student of matching
     * username, then checks if the password matches.
     *
     * @param username The username of the student to be found.
     * @param password The password of the student to be found.
     * @return Student with matching credentials, or null if no student is found.
     */
    public Student getStudentByCredentials(String username, String password) {
        Student student = studentSearchTree.find(username); // Finds node in BST of students that has matching username
        if (student != null && student.passwordMatches(password)) { // Checks if the password matches
            return student;
        } else {
            return null;
        }
    }

    /**
     * Gets the teacher that has matching credentials by searching the BST of teachers for a student of matching
     * username, then checks if the password matches.
     *
     * @param username The username of the teacher to be found.
     * @param password The password of the teacher to be found.
     * @return Student with matching credentials, or null if no teacher is found with matching credentials.
     */
    public Teacher getTeacherByCredentials(String username, String password) {
        Teacher teacher = teacherSearchTree.find(username); // Finds node in BST of students that has matching username
        if (teacher != null && teacher.passwordMatches(password)) { // Checks if the password matches
            return teacher;
        } else {
            return null;
        }
    }

    /**
     * Sorts a given array of students by name.
     *
     * @param students  The array of students to be sorted.
     * @param ascending Whether or not the sorted list is ascending or descending in order.
     * @return Sorted list of students, by the order specified (ascending or descending).
     */
    public Student[] sortByName(Student[] students, boolean ascending) {
        return sort(students, StudentComparators.getSortByName(ascending));
    }

    /**
     * Sorts a given array of students using a comparator using a merge sort.
     *
     * @param students   The array of students to be sorted.
     * @param comparator The comparator that tells the program what method of comparison to use when sorting.
     * @return Sorted list of students, by the order specified (ascending or descending).
     */
    private Student[] sort(Student[] students, Comparator<Student> comparator) {
        if (students.length <= 1) {
            return students;
        } else {
            int middleIndex = students.length / 2; // Find middle index
            // Merge the two sorted halves of the list to get a sorted full list
            return merge(sort(slice(students, 0, middleIndex), comparator),
                    sort(slice(students, middleIndex, students.length), comparator), comparator);
        }
    }

    /**
     * Slices a given array of students.
     *
     * @param students   The array of students to be sliced.
     * @param startIndex The first index of the range (inclusive).
     * @param endIndex   The last index of the range (non-inclusive).
     * @return Sliced array of students by the two indices given.
     */
    private Student[] slice(Student[] students, int startIndex, int endIndex) {
        return Arrays.copyOfRange(students, startIndex, endIndex);
    }

    /**
     * Merges two arrays of students, putting them in the correct order as specified by the comparator. Helper function
     * for the merge sort.
     *
     * @param arrayA The first half of the array of students to be sorted.
     * @param arrayB The second half ot eh array of students to be sorted.
     * @param comparator     The comparator that tells the program what method of comparison to use when sorting.
     * @return Sorted list of students, by the order specified (ascending or descending).
     */
    private Student[] merge(Student[] arrayA, Student[] arrayB, Comparator<Student> comparator) {
        Student[] merged = new Student[arrayA.length + arrayB.length];
        int aIndex = 0; // Keep track of the item in arrayA
        int bIndex = 0; // Keep track of the item in arrayB
        int mergedIndex = 0; // Keep track of the index in the merged array to add to
        while (aIndex < arrayA.length && bIndex < arrayB.length) { // While both indices are within the size of the arrays
            if (comparator.compare(arrayA[aIndex], arrayB[bIndex]) < 0) { // Choose between adding an item from arrayA and arrayB
                merged[mergedIndex] = arrayA[aIndex];
                aIndex++;
            } else {
                merged[mergedIndex] = arrayB[bIndex];
                bIndex++;
            }
            mergedIndex++;
        }
        while (aIndex < arrayA.length) { // While there are still items left in arrayA, add the items to the merged array
            merged[mergedIndex] = arrayA[aIndex];
            mergedIndex++;
            aIndex++;
        }
        while (bIndex < arrayB.length) { // While there are still items left in arrayB, add the items to the merged array
            merged[mergedIndex] = arrayB[bIndex];
            mergedIndex++;
            bIndex++;
        }
        return merged;
    }

    /**
     * Gets all students being taught by a given teacher.
     *
     * @param teacher The teacher whose students are being searched for.
     * @return An array of all students being taught by the given teacher.
     */
    public Student[] getStudentsByTeacher(Teacher teacher) {
        Set<Student> students = new HashSet<>();
        // Checks every course association the teacher has and gets all the students enrolled
        for (CourseAssociation courseAssociation : teacher.getCourseAssociations()) {
            for (StudentCourseAssociation studentCourseAssociation : ((TeacherCourseAssociation) courseAssociation).getStudentCourseAssociations()) {
                students.add(studentCourseAssociation.getStudent());
            }
        }
        return students.toArray(new Student[0]);
    }

}
