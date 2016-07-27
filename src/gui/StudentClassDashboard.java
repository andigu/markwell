package gui;

import entity.AssessmentCategory;
import entity.Course;
import entity.Student;
import entity.StudentCourseAssociation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Student Class Dashboard - displays information about the selected course that the student is enrolled in:
 * <ul>
 * <li>Course name</li>
 * <li># of lates</li>
 * <li># of absences</li>
 * <li>Teacher name</li>
 * <li>Teacher email</li>
 * <li>Average in the course</li>
 * </ul>.
 *
 * @author Joshua Xavier
 * @course ICS4U
 * @date 6/20/2016
 */
class StudentClassDashboard {
    private JFrame frame;

    StudentClassDashboard(final Student student, final Course course) {
        initialize();

        // Initialize all variables and components
        StudentCourseAssociation courseAssociation = (StudentCourseAssociation) student.getCourseAssociationByName
                (course.getName());
        final String courseCode = course.getName();
        final int lates = courseAssociation.getLates();
        final int absences = courseAssociation.getAbsences();
        final String teacherName = courseAssociation.getTeacher().getName();
        final String teacherEmail = courseAssociation.getTeacher().getEmail();
        final JTextPane averagePane = new JTextPane();

        JMenuBar menuBar = new JMenuBar();
        JMenu logOutMenu = new JMenu("Log Out");
        JMenu backMenu = new JMenu("Back");
        JMenu optionMenu = new JMenu("Options");
        JSeparator separator = new JSeparator();
        JButton knowledgeButton = new JButton("KNOWLEDGE");
        JButton applicationButton = new JButton("APPLICATION");
        JButton communicationButton = new JButton("COMMUNICATION");
        JButton thinkingButton = new JButton("THINKING");
        JLabel courseCodeLabel = new JLabel(courseCode + " - Average: " + student.getCourseAssociationByName(course.
                getName()).getAverage());
        JLabel attendanceLabel = new JLabel("Attendance");
        JLabel teacherInformationLabel = new JLabel("Teacher Information");
        JLabel background = new JLabel();
        JTextField latesField = new JTextField();
        JTextField teacherInformationField = new JTextField();

        averagePane.setEditable(false);
        averagePane.setFont(new Font("Tahoma", Font.PLAIN, 18));
        averagePane.setBounds(40, 219, 357, 50);

        menuBar.setBounds(0, 0, 434, 26);
        menuBar.add(optionMenu);

        backMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Go back to the student dashboard
                try {
                    new StudentDashboard(student);
                    frame.dispose();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        logOutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Log out - bring the user back to the start screen
                new Start();
                frame.dispose();
            }
        });

        separator.setBounds(226, 280, 0, 227);

        knowledgeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) { // User wants to view their average in the knowledge category
                int numberMark = ((StudentCourseAssociation) student.getCourseAssociationByName
                        (course.getName())).getCategoryAverage(AssessmentCategory.Knowledge);
                averagePane.setText(numberMark + "%");
            }
        });
        knowledgeButton.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
        knowledgeButton.setBounds(30, 135, 186, 37);

        applicationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // User wants to view their average in the application category
                averagePane.setText(Integer.toString(((StudentCourseAssociation) student.getCourseAssociationByName
                        (course.getName())).getCategoryAverage(AssessmentCategory.Application)) + "%");
            }
        });
        applicationButton.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
        applicationButton.setBounds(30, 174, 186, 34);

        communicationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // User wants to view their average in the communication category
                averagePane.setText(Integer.toString(((StudentCourseAssociation) student.getCourseAssociationByName
                        (course.getName())).getCategoryAverage(AssessmentCategory.Communication)) + "%");
            }
        });
        communicationButton.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
        communicationButton.setBounds(225, 174, 185, 33);

        thinkingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // User wants to view their average in the thinking category
                averagePane.setText(Integer.toString(((StudentCourseAssociation) student.getCourseAssociationByName
                        (course.getName())).getCategoryAverage(AssessmentCategory.Thinking)) + "%");
            }
        });
        thinkingButton.setFont(new Font("Myriad Web Pro", Font.PLAIN, 14));
        thinkingButton.setBounds(226, 135, 183, 37);

        courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseCodeLabel.setBounds(30, 33, 379, 91);

        attendanceLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        attendanceLabel.setBounds(50, 289, 166, 43);

        latesField.setText("Days Late: " + lates + "\n Days Absent: " + absences);
        latesField.setEditable(false);
        latesField.setBounds(60, 333, 325, 91);
        latesField.setColumns(10);

        teacherInformationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        teacherInformationLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        teacherInformationLabel.setBounds(50, 423, 166, 43);

        teacherInformationField.setEditable(false);
        teacherInformationField.setText("Name: " + teacherName + "\n Email: " + teacherEmail);
        teacherInformationField.setColumns(10);
        teacherInformationField.setBounds(50, 463, 325, 91);

        background.setBounds(0, 0, 450, 850);
        background.setIcon(new ImageIcon(getClass().getResource("/image/background/student/class.png")));

        optionMenu.add(logOutMenu);
        optionMenu.add(backMenu);

        // Add all components to the frame
        frame.getContentPane().add(menuBar);
        frame.getContentPane().add(separator);
        frame.getContentPane().add(averagePane);
        frame.getContentPane().add(teacherInformationField);
        frame.getContentPane().add(teacherInformationLabel);
        frame.getContentPane().add(latesField);
        frame.getContentPane().add(attendanceLabel);
        frame.getContentPane().add(courseCodeLabel);
        frame.getContentPane().add(knowledgeButton);
        frame.getContentPane().add(thinkingButton);
        frame.getContentPane().add(applicationButton);
        frame.getContentPane().add(communicationButton);
        frame.getContentPane().add(background);
        frame.getContentPane().add(menuBar);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("My Class");
        frame.setBounds(100, 100, 440, 593);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

    }
}



