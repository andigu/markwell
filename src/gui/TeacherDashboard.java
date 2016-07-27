package gui;

import entity.Course;
import entity.Student;
import entity.Teacher;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Teacher Dashboard - main screen for teachers - displays information about all of their students, and all of the
 * courses that he/she teaches
 *
 * @author Dannish Siddiqui
 * @course ICS4U
 * @date 6/20/2016
 */
class TeacherDashboard {

    private JFrame frame;
    private JTextField volunteerHoursField;
    private JTextField clubsField;
    private JLabel studentName;
    private int numHours;
    private int numClubs;

    /**
     * Launch the application.
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    TeacherDashboard(final Teacher teacher) throws ClassNotFoundException, SQLException {
        initialize();

        // Initialize all variables and components
        final Service service = AppConfig.getService();
        final Course[] courses = teacher.getCourses();
        final Student[] students = service.sortByName(service.getStudentsByTeacher(teacher), true);
        String[] studentNames = new String[students.length];
        String[] courseNames = new String[courses.length];
        String teacherName = teacher.getName();

        for (int i = 0; i < courses.length; i++) {
            courseNames[i] = courses[i].getName();
        }
        for (int i = 0; i < students.length; i++) {
            studentNames[i] = students[i].getName();
        }

        final JList<String> listStudents = new JList<>(studentNames);
        final JList<String> listCourses = new JList<>(courseNames);

        studentName = new JLabel(listStudents.getSelectedValue());
        volunteerHoursField = new JTextField();
        clubsField = new JTextField();

        JScrollPane studentsPane = new JScrollPane();
        JScrollPane coursesPane = new JScrollPane();
        JLabel teacherNameLabel = new JLabel("Hi " + teacherName);
        JLabel volunteerHoursLabel = new JLabel("Volunteer Hours");
        JLabel addHourLabel = new JLabel();
        JLabel subtractHourLabel = new JLabel();
        JLabel clubsLabel = new JLabel("Clubs");
        JLabel addClubLabel = new JLabel();
        JLabel subtractClubLabel = new JLabel();
        JLabel background = new JLabel();
        JButton saveChangesButton = new JButton("Save Changes");
        JButton logoutButton = new JButton("Log Out");


        listStudents.setSelectedIndex(0);
        listStudents.setBackground(new Color(222, 222, 222));
        listStudents.setBorder(null);
        listStudents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Displays the information about the selected student
                studentName.setText(listStudents.getSelectedValue());
                numHours = students[listStudents.getSelectedIndex()].getVolunteerHours();
                numClubs = students[listStudents.getSelectedIndex()].getClubs();
                clubsField.setText(Integer.toString(numClubs));
                volunteerHoursField.setText(Integer.toString(numHours));
            }
        });
        listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listStudents.setFont(new Font("Tahoma", Font.PLAIN, 28));

        listCourses.setBackground(new Color(222, 222, 222));
        listCourses.setBorder(null);
        listCourses.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Bring user to new screen with details about the selected course
                Course course = courses[listCourses.getSelectedIndex()];
                try {
                    new TeacherClassDashboard(teacher, course);
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        listCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCourses.setFont(new Font("Tahoma", Font.PLAIN, 30));

        studentsPane.setBounds(456, 227, 346, 298);
        studentsPane.setViewportView(listStudents);

        coursesPane.setBounds(49, 227, 354, 474);
        coursesPane.setViewportView(listCourses);

        teacherNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 83));
        teacherNameLabel.setBounds(31, 29, 758, 90);

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Logout - brings user back to start
                new Start();
                frame.dispose();
            }
        });
        logoutButton.setBounds(739, 29, 78, 23);

        studentName.setHorizontalAlignment(SwingConstants.CENTER);
        studentName.setFont(new Font("Tahoma", Font.PLAIN, 39));
        studentName.setBounds(451, 536, 366, 40);

        volunteerHoursLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        volunteerHoursLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volunteerHoursLabel.setBounds(459, 587, 196, 40);

        volunteerHoursField.setHorizontalAlignment(SwingConstants.CENTER);
        volunteerHoursField.setFont(new Font("Tahoma", Font.PLAIN, 45));
        volunteerHoursField.setEditable(false);
        volunteerHoursField.setText(Integer.toString(students[listStudents.getSelectedIndex()].getVolunteerHours()));
        volunteerHoursField.setBounds(498, 622, 86, 76);
        volunteerHoursField.setColumns(10);

        addHourLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Adds one volunteer hour to the student
                numHours += 1;
                volunteerHoursField.setText(Integer.toString(numHours));
            }
        });
        addHourLabel.setIcon(new ImageIcon(getClass().getResource("/image/button/plus.png")));
        addHourLabel.setBounds(594, 622, 36, 36);

        subtractHourLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Takes away one hour from the student unless the student has no hours
                if (numHours > 0) {
                    numHours -= 1;
                }
                volunteerHoursField.setText(Integer.toString(numHours));
            }
        });
        subtractHourLabel.setIcon(new ImageIcon(getClass().getResource("/image/button/minus.png")));
        subtractHourLabel.setBounds(594, 662, 36, 36);


        clubsLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        clubsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clubsLabel.setBounds(645, 587, 157, 40);

        clubsField.setHorizontalAlignment(SwingConstants.CENTER);
        clubsField.setFont(new Font("Tahoma", Font.PLAIN, 40));
        clubsField.setText(Integer.toString(students[listStudents.getSelectedIndex()].getClubs()));
        clubsField.setEditable(false);
        clubsField.setColumns(10);
        clubsField.setBounds(673, 622, 86, 76);

        addClubLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Adds a club membership to the student
                numClubs += 1;
                clubsField.setText(Integer.toString(numClubs));
            }
        });
        addClubLabel.setIcon(new ImageIcon(getClass().getResource("/image/button/plus.png")));
        addClubLabel.setBounds(766, 622, 36, 36);

        subtractClubLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Subtracts a club membership to the student unless they have none
                if (numClubs > 0) {
                    numClubs -= 1;
                }
                clubsField.setText(Integer.toString(numClubs));
            }
        });
        subtractClubLabel.setIcon(new ImageIcon(getClass().getResource("/image/button/minus.png")));
        subtractClubLabel.setBounds(766, 662, 36, 36);

        saveChangesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Save changes to the volunteer hours/clubs for the student
                try {
                    service.setVolunteerHours(students[listStudents.getSelectedIndex()], numHours);
                    service.setClubs(students[listStudents.getSelectedIndex()], numClubs);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        saveChangesButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        saveChangesButton.setBounds(495, 705, 264, 35);

        background.setIcon(new ImageIcon(getClass().getResource("/image/background/teacher/main.png")));
        background.setBounds(0, 0, 850, 850);

        // Adds all components to the frame
        frame.getContentPane().add(clubsField);
        frame.getContentPane().add(volunteerHoursField);
        frame.getContentPane().add(studentName);
        frame.getContentPane().add(teacherNameLabel);
        frame.getContentPane().add(coursesPane);
        frame.getContentPane().add(studentsPane);
        frame.getContentPane().add(clubsLabel);
        frame.getContentPane().add(addClubLabel);
        frame.getContentPane().add(subtractClubLabel);
        frame.getContentPane().add(volunteerHoursLabel);
        frame.getContentPane().add(addHourLabel);
        frame.getContentPane().add(subtractHourLabel);
        frame.getContentPane().add(saveChangesButton);
        frame.getContentPane().add(logoutButton);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Teacher Dashboard");
        frame.setResizable(false);
        frame.setBounds(0, 0, 850, 850);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}
