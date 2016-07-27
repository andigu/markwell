package service;

import entity.Student;
import utility.TestUtil;

/**
 * Tests the service's sorting
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestService {
    /**
     * Test the merge sort by student name
     */
    private void testSortByName() {
        Student[] students = new Student[]{new Student("Sean"), new Student("James"), new Student("Henry"),
                new Student("Noah"), new Student("Brady"), new Student("Xavier")};

        String[] expectedAscending = new String[]{"Brady", "Henry", "James", "Noah", "Sean", "Xavier"};
        Service testService = new InMemoryService(null, null);

        Student[] sortNameAscended = testService.sortByName(students, true);
        for (int i = 0; i < sortNameAscended.length; i++) { // Check that each value of ascending sort is correct
            TestUtil.assertEquals(expectedAscending[i], sortNameAscended[i].getName());
        }
        Student[] sortNameDescended = testService.sortByName(students, false);
        for (int i = 0; i < sortNameAscended.length; i++) { // Check that each value of descending sort is correct
            TestUtil.assertEquals(expectedAscending[sortNameAscended.length - i - 1], sortNameDescended[i].getName());
        }
    }

    public static void main(String[] args) {
        TestService test = new TestService();
        test.testSortByName();
    }
}
