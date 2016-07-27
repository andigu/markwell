package gui;

import entity.*;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * Teacher Mark Dashboard - screen that allows teacher's to update/add marks for her students under different assignments
 *
 * @author Joshua Xavier
 * @course ICS4U
 * @date 6/20/2016
 */
class TeacherMarkDashboard {
    private JFrame frame;
    private JTextField assignmentNameField;
    private JTextField weightField;
    private JTextField studentNameField;
    private JTextField applicationMarkField;
    private JTextField communicationMarkField;
    private JTextField thinkingMarkField;
    private JTextField knowledgeMarkField;

    TeacherMarkDashboard(final Teacher teacher, final Course course) throws SQLException, ClassNotFoundException {
        initialize();

        final Service service = AppConfig.getService();
        final Assessment[] assessments = course.getAssessments();
        final Student[] students = ((TeacherCourseAssociation) teacher.getCourseAssociationByName(course.getName())).getStudents();
        final String[] studentNames = new String[students.length];
        String[] assignmentNames = new String[assessments.length];

        for (int i = 0; i < assessments.length; i++) {
            assignmentNames[i] = assessments[i].getName();
        }
        for (int i = 0; i < students.length; i++) {
            studentNames[i] = students[i].getName();
        }

        final JList<String> studentList = new JList<>(studentNames);
        final JList<String> assignmentList = new JList<>(assignmentNames);

        assignmentNameField = new JTextField();
        studentNameField = new JTextField(studentList.getSelectedValue());
        weightField = new JTextField();
        applicationMarkField = new JTextField();
        communicationMarkField = new JTextField();
        thinkingMarkField = new JTextField();
        knowledgeMarkField = new JTextField();

        JScrollPane studentPane = new JScrollPane();
        JScrollPane assignmentPane = new JScrollPane();
        JLabel background = new JLabel();
        JLabel weightLabel = new JLabel("Weight:");
        final JLabel studentLabel = new JLabel("Student:");
        JLabel assignmentNameLabel = new JLabel("Assignment Name:");
        JLabel applicationLabel = new JLabel("Application:");
        JLabel communicationLabel = new JLabel("Comm:");
        JLabel knowledgeLabel = new JLabel("K/U:");
        JLabel thinkingLabel = new JLabel("Thinking:");
        JButton dashboardButton = new JButton("Dashboard");
        JButton saveMarkButton = new JButton("Save Mark");

        studentPane.setBounds(458, 91, 354, 397);
        studentPane.setViewportView(studentList);

        studentList.setSelectedIndex(0);
        studentList.setBackground(new Color(235, 234, 234));
        studentList.setBorder(null);
        studentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Show information about the selected assessment student pair
                studentNameField.setText(studentList.getSelectedValue());
                Assessment assessment = assessments[assignmentList.getSelectedIndex()];
                if (studentList.getSelectedIndex() != -1) {
                    Student student = students[studentList.getSelectedIndex()];
                    AssessmentMark mark = ((StudentCourseAssociation) student.getCourseAssociationByName(course.getName())).
                            getMarkByAssessment(assessment);
                    assignmentNameField.setText(assignmentList.getSelectedValue());
                    weightField.setText(Integer.toString(assessment.getWeight()));
                    communicationMarkField.setText(null);
                    applicationMarkField.setText(null);
                    knowledgeMarkField.setText(null);
                    thinkingMarkField.setText(null);
                    if (mark == null) {
                        communicationMarkField.setEditable(assessment.hasCommunication());
                        applicationMarkField.setEditable(assessment.hasApplication());
                        knowledgeMarkField.setEditable(assessment.hasKnowledge());
                        thinkingMarkField.setEditable(assessment.hasThinking());
                    } else {
                        if (mark.hasCategory("communication")) {
                            communicationMarkField.setEditable(true);
                            communicationMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Communication)));
                        }
                        if (mark.hasCategory("application")) {
                            applicationMarkField.setEditable(true);
                            applicationMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Application)));
                        }
                        if (mark.hasCategory("knowledge")) {
                            knowledgeMarkField.setEditable(true);
                            knowledgeMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Knowledge)));
                        }
                        if (mark.hasCategory("thinking")) {
                            thinkingMarkField.setEditable(true);
                            thinkingMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Thinking)));
                        }
                    }
                }
            }
        });
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Tahoma", Font.PLAIN, 30));

        assignmentPane.setBounds(37, 91, 354, 397);
        assignmentPane.setViewportView(assignmentList);

        assignmentList.setSelectedIndex(0);
        assignmentList.setBackground(new Color(235, 234, 234));
        assignmentList.setBorder(null);
        assignmentList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Show information about the selected assignment student pair
                Assessment assessment = assessments[assignmentList.getSelectedIndex()];
                Student student = students[studentList.getSelectedIndex()];
                AssessmentMark mark = ((StudentCourseAssociation) student.getCourseAssociationByName(course.getName())).
                        getMarkByAssessment(assessment);
                assignmentNameField.setText(assignmentList.getSelectedValue());
                weightField.setText(Integer.toString(assessment.getWeight()));
                communicationMarkField.setText(null);
                applicationMarkField.setText(null);
                knowledgeMarkField.setText(null);
                thinkingMarkField.setText(null);
                if (mark == null) { // If the student doesn't have a mark under the selected assessment, create a new one
                    communicationMarkField.setEditable(assessment.hasCommunication());
                    applicationMarkField.setEditable(assessment.hasApplication());
                    knowledgeMarkField.setEditable(assessment.hasKnowledge());
                    thinkingMarkField.setEditable(assessment.hasThinking());
                } else {
                    if (mark.hasCategory("communication")) {
                        communicationMarkField.setEditable(true);
                        communicationMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Communication)));
                    }
                    if (mark.hasCategory("application")) {
                        applicationMarkField.setEditable(true);
                        applicationMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Application)));
                    }
                    if (mark.hasCategory("knowledge")) {
                        knowledgeMarkField.setEditable(true);
                        knowledgeMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Knowledge)));
                    }
                    if (mark.hasCategory("thinking")) {
                        thinkingMarkField.setEditable(true);
                        thinkingMarkField.setText(Integer.toString(mark.getMark(AssessmentCategory.Thinking)));
                    }
                }
            }
        });
        assignmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentList.setFont(new Font("Tahoma", Font.PLAIN, 30));
        assignmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentList.setFont(new Font("Tahoma", Font.PLAIN, 30));

        assignmentNameLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
        assignmentNameLabel.setBounds(37, 602, 159, 29);

        assignmentNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        assignmentNameField.setEditable(false);
        assignmentNameField.setBounds(206, 602, 460, 29);
        assignmentNameField.setColumns(10);

        weightLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
        weightLabel.setBounds(689, 600, 60, 29);

        weightField.setHorizontalAlignment(SwingConstants.CENTER);
        weightField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        weightField.setEditable(false);
        weightField.setColumns(10);
        weightField.setBounds(752, 600, 60, 29);

        studentLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 17));
        studentLabel.setBounds(540, 642, 61, 29);

        studentNameField.setHorizontalAlignment(SwingConstants.CENTER);
        studentNameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        studentNameField.setEditable(false);
        studentNameField.setColumns(10);
        studentNameField.setBounds(611, 642, 201, 29);

        dashboardButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Bring user back to the main screen (dashboard)
                try {
                    new TeacherDashboard(teacher);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        dashboardButton.setBounds(597, 682, 215, 58);

        saveMarkButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        saveMarkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Saves the updated mark to the database and object model
                Assessment assessment = assessments[assignmentList.getSelectedIndex()];
                Student student = students[studentList.getSelectedIndex()];
                StudentCourseAssociation studentCourseAssociation = (StudentCourseAssociation)
                        student.getCourseAssociationByName(course.getName());
                AssessmentMark mark = studentCourseAssociation.getMarkByAssessment(assessment);
                if (mark == null) { // If the student doesn't have a mark for that assessment, create a new one
                    mark = new AssessmentMark(assessment);
                }
                if (assessment.hasKnowledge()) {
                    mark.setMark("knowledge", Integer.parseInt(knowledgeMarkField.getText()));
                }
                if (assessment.hasCommunication()) {
                    mark.setMark("communication", Integer.parseInt(communicationMarkField.getText()));
                }
                if (assessment.hasApplication()) {
                    mark.setMark("application", Integer.parseInt(applicationMarkField.getText()));
                }
                if (assessment.hasThinking()) {
                    mark.setMark("thinking", Integer.parseInt(thinkingMarkField.getText()));
                }
                try {
                    service.updateMark(studentCourseAssociation, mark);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        saveMarkButton.setBounds(401, 682, 186, 58);

        applicationMarkField.setEditable(false);
        applicationMarkField.setColumns(10);
        applicationMarkField.setBounds(495, 648, 34, 23);
        applicationMarkField.addKeyListener(new KeyAdapter() { // Prohibits user from entering a string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        communicationMarkField.setEditable(false);
        communicationMarkField.setToolTipText("Insert the Total marks in Category");
        communicationMarkField.setColumns(10);
        communicationMarkField.setBounds(339, 648, 34, 23);
        communicationMarkField.addKeyListener(new KeyAdapter() { // Prohibits user from entering a string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        thinkingMarkField.setEditable(false);
        thinkingMarkField.setToolTipText("Insert the Total marks in Category");
        thinkingMarkField.setColumns(10);
        thinkingMarkField.setBounds(229, 645, 34, 23);
        thinkingMarkField.addKeyListener(new KeyAdapter() { // Prohibits user from entering a string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        knowledgeMarkField.setEditable(false);
        knowledgeMarkField.setToolTipText("Insert the Total marks in Category");
        knowledgeMarkField.setHorizontalAlignment(SwingConstants.CENTER);
        knowledgeMarkField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        knowledgeMarkField.setColumns(10);
        knowledgeMarkField.setBounds(90, 648, 34, 23);
        knowledgeMarkField.addKeyListener(new KeyAdapter() { // Prohibits user from entering string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        applicationLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        applicationLabel.setBounds(383, 648, 109, 23);

        communicationLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        communicationLabel.setBounds(272, 648, 70, 23);

        thinkingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        thinkingLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        thinkingLabel.setBounds(134, 648, 83, 23);

        knowledgeLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        knowledgeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        knowledgeLabel.setBounds(37, 647, 43, 23);

        background.setBounds(0, 0, 850, 850);
        background.setIcon(new ImageIcon(getClass().getResource("/image/background/teacher/marks.png")));

        // Adds all components to frame
        frame.getContentPane().add(dashboardButton);
        frame.getContentPane().add(studentNameField);
        frame.getContentPane().add(weightField);
        frame.getContentPane().add(applicationMarkField);
        frame.getContentPane().add(assignmentNameField);
        frame.getContentPane().add(communicationMarkField);
        frame.getContentPane().add(thinkingMarkField);
        frame.getContentPane().add(knowledgeMarkField);
        frame.getContentPane().add(studentLabel);
        frame.getContentPane().add(assignmentNameLabel);
        frame.getContentPane().add(weightLabel);
        frame.getContentPane().add(studentPane);
        frame.getContentPane().add(saveMarkButton);
        frame.getContentPane().add(applicationLabel);
        frame.getContentPane().add(communicationLabel);
        frame.getContentPane().add(thinkingLabel);
        frame.getContentPane().add(knowledgeLabel);
        frame.getContentPane().add(assignmentPane);
        frame.getContentPane().add(background);
        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Marks");
        frame.setResizable(false);
        frame.setBounds(0, 0, 850, 823);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}
