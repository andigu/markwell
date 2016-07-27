package gui;

import entity.*;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Teacher Class Dashboard - allows teacher to view information about a course he/she is teaching, take attendance, and
 * enter other screens relating to the course (edit marks and assessments)
 *
 * @author Joshua Xavier
 * @course ICS4U
 * @date 6/20/2016
 */
class TeacherClassDashboard {

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JTextField studentNameField;

    TeacherClassDashboard(final Teacher teacher, final Course course) throws SQLException, ClassNotFoundException {
        initialize();

        // Initialize all variables and components
        final Service service = AppConfig.getService();
        final Student[] students = ((TeacherCourseAssociation) teacher.getCourseAssociationByName(course.getName())).getStudents();
        final String[] studentNames = new String[students.length];
        final JRadioButton absentRadioButton = new JRadioButton("Absent");
        final JRadioButton lateRadioButton = new JRadioButton("Late");
        String courseCode = course.getName();

        for (int i = 0; i < students.length; i++) {
            studentNames[i] = students[i].getName();
        }

        final JList<String> listStudents = new JList<>(studentNames);

        studentNameField = new JTextField();
        JLabel studentLabel = new JLabel("Student: ");
        JLabel courseCodeLabel = new JLabel(courseCode);
        JLabel pathLabel = new JLabel("Dashboard > " + courseCode);
        JLabel background = new JLabel();
        JButton dashboardButton = new JButton();
        JButton submitLabel = new JButton("Submit");
        JButton marksButton = new JButton();
        JButton assignmentsButton = new JButton();
        JScrollPane scrollPane = new JScrollPane();

        listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listStudents.setFont(new Font("Tahoma", Font.PLAIN, 30));

        scrollPane.setBounds(495, 91, 317, 351);
        scrollPane.setViewportView(listStudents);
        listStudents.setBackground(new Color(235, 234, 234));
        listStudents.setBorder(null);
        listStudents.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Indicate which student is selected for attendance purposes
                if (listStudents.getSelectedIndex() >= 0) {
                    studentNameField.setText(studentNames[listStudents.getSelectedIndex()]);
                }
            }
        });

        studentLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        studentLabel.setBounds(495, 470, 118, 26);

        studentNameField.setEditable(false);
        studentNameField.setBounds(575, 470, 237, 26);
        studentNameField.setColumns(10);

        buttonGroup.add(absentRadioButton);
        absentRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        absentRadioButton.setBounds(545, 503, 109, 33);

        buttonGroup.add(lateRadioButton);
        lateRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lateRadioButton.setBounds(707, 503, 109, 33);

        submitLabel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Add a late or absence to the student if the button is selected
                if (lateRadioButton.isSelected()) {
                    try {
                        service.addLate((StudentCourseAssociation) students[listStudents.getSelectedIndex()].
                                getCourseAssociationByName(course.getName()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else if (absentRadioButton.isSelected()) {
                    try {
                        service.addAbsence((StudentCourseAssociation) students[listStudents.getSelectedIndex()].
                                getCourseAssociationByName(course.getName()));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                buttonGroup.clearSelection();
            }
        });
        submitLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
        submitLabel.setBounds(575, 543, 192, 33);

        marksButton.setIcon(new ImageIcon(getClass().getResource("/image/button/marks.png")));
        marksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Show a new screen showing information about the selected mark
                try {
                    new TeacherMarkDashboard(teacher, course);
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }

        });
        marksButton.setFont(new Font("Tahoma", Font.PLAIN, 68));
        marksButton.setBounds(37, 157, 420, 151);

        assignmentsButton.setIcon(new ImageIcon(getClass().getResource("/image/button/assignments.png")));
        assignmentsButton.setFont(new Font("Tahoma", Font.PLAIN, 48));
        assignmentsButton.addActionListener(new ActionListener() { // Show a new screen showing information about the selected assessment
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherAssignmentDashboard(course, teacher);
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        assignmentsButton.setBounds(37, 339, 420, 128);

        dashboardButton.setIcon(new ImageIcon(getClass().getResource("/image/button/dashboard.png")));
        dashboardButton.setFont(new Font("Tahoma", Font.PLAIN, 48));
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Go back to the main screen (teacher dashboard)
                try {
                    new TeacherDashboard(teacher);
                    frame.dispose();
                } catch (ClassNotFoundException | SQLException ignored) {
                }
            }
        });
        dashboardButton.setBounds(37, 500, 420, 76);

        courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 60));
        courseCodeLabel.setBounds(30, 31, 427, 56);

        pathLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        pathLabel.setBounds(222, 91, 237, 20);

        background.setBounds(0, 0, 850, 630);
        background.setIcon(new ImageIcon(getClass().getResource("/image/background/teacher/class.png")));

        // Add all components to the frame
        frame.getContentPane().add(studentNameField);
        frame.getContentPane().add(studentLabel);
        frame.getContentPane().add(courseCodeLabel);
        frame.getContentPane().add(absentRadioButton);
        frame.getContentPane().add(lateRadioButton);
        frame.getContentPane().add(submitLabel);
        frame.getContentPane().add(marksButton);
        frame.getContentPane().add(assignmentsButton);
        frame.getContentPane().add(dashboardButton);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(pathLabel);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("My Class");
        frame.setResizable(false);
        frame.setBounds(0, 0, 850, 630);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}
