package gui;

import entity.Course;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Main screen for the student - displays general information about the student:
 * <ul>
 * <li>Student name</li>
 * <li>Student number</li>
 * <li>Net score</li>
 * <li>Total # of lates</li>
 * <li>Total # of absences</li>
 * <li># of clubs and volunteer hours</li>
 * </ul>
 *
 * @author Dannish Siddiqui
 * @course ICS4U
 * @date 6/20/2016
 */
class StudentDashboard {
    private JFrame frame;

    StudentDashboard(final Student student) throws SQLException, ClassNotFoundException {
        initialize();

        // Initialize all variables and components
        final String studentName = student.getName();
        final String studentNumber = student.getUsername();
        final double netScore = student.getNetScore();
        final int lates = student.getLates();
        final int absences = student.getAbsences();
        final int clubs = student.getClubs();
        final int volunteerHours = student.getVolunteerHours();
        final Course[] courses = student.getCourses();
        String[] courseNames = new String[courses.length];

        for (int i = 0; i < courses.length; i++) {
            courseNames[i] = courses[i].getName();
        }

        final JList<String> courseList = new JList<>(courseNames);

        JMenuBar menuBar = new JMenuBar();
        JMenu myAccountMenu = new JMenu("My Account");
        JMenu optionsMenu = new JMenu("Options");
        JMenu logoutMenu = new JMenu("Log Out");
        JButton logoutButton = new JButton("Log Out");
        JLabel studentNameLabel = new JLabel("Hi " + studentName);
        JLabel studentNumberLabel = new JLabel(studentNumber);
        JLabel lateLabel = new JLabel("Days Late: " + lates);
        JLabel absentLabel = new JLabel("Days Absent: " + absences);
        JLabel netScoreLabel = new JLabel("Net Score is " + netScore);
        JLabel clubsLabel = new JLabel("Hours: " + volunteerHours + " Clubs: " + clubs);
        JLabel background = new JLabel();

        courseList.setVisibleRowCount(2);
        courseList.setBackground(new Color(222, 222, 222));
        courseList.setBorder(null);
        courseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Take the user to the page of the course they want to view
                new StudentClassDashboard(student, courses[courseList.getSelectedIndex()]);
                frame.dispose();
            }
        });

        menuBar.setBounds(0, 0, 844, 21);

        logoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Log out - go back to the start screen
                new Start();
                frame.dispose();
            }
        });

        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseList.setFont(new Font("Tahoma", Font.PLAIN, 30));
        courseList.setBounds(416, 232, 379, 474);

        studentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 65));
        studentNameLabel.setBounds(10, 46, 796, 63);

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Log out - go back to the start screen
                new Start();
                frame.dispose();
            }
        });
        logoutButton.setBounds(733, 24, 89, 23);

        studentNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        studentNumberLabel.setBounds(721, 110, 89, 14);

        lateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lateLabel.setBounds(22, 420, 166, 35);

        absentLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        absentLabel.setBounds(199, 420, 166, 35);

        netScoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        netScoreLabel.setBounds(22, 218, 313, 73);

        clubsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clubsLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        clubsLabel.setBounds(39, 587, 294, 153);

        background.setBounds(0, 0, 850, 850);
        background.setIcon(new ImageIcon(getClass().getResource("/image/background/student/main.png")));

        menuBar.add(optionsMenu);
        optionsMenu.add(logoutMenu);
        optionsMenu.add(myAccountMenu);

        // Add all components to the frame
        frame.getContentPane().add(menuBar);
        frame.getContentPane().add(studentNameLabel);
        frame.getContentPane().add(studentNumberLabel);
        frame.getContentPane().add(courseList);
        frame.getContentPane().add(clubsLabel);
        frame.getContentPane().add(lateLabel);
        frame.getContentPane().add(netScoreLabel);
        frame.getContentPane().add(logoutButton);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Student Dashboard");
        frame.setResizable(false);
        frame.setBounds(0, 0, 850, 850);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

    }
}
