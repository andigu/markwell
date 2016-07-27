package gui;

import entity.Assessment;
import entity.Course;
import entity.Teacher;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

/**
 * Teacher Assignment Dashboard - allows teacher to view assignments for the course, as well as update or add assessments
 *
 * @author Joshua Xavier
 * @course ICS4U
 * @date 6/20/2016
 */
class TeacherAssignmentDashboard {
    private JFrame frame;
    private JTextField nameEditField;
    private JTextField weightEditField;
    private JCheckBox knowledgeCheckBoxEdit;
    private JCheckBox thinkingCheckBoxEdit;
    private JCheckBox applicationCheckBoxEdit;
    private JCheckBox communicationCheckBoxEdit;

    TeacherAssignmentDashboard(final Course course, final Teacher teacher) throws SQLException, ClassNotFoundException {
        initialize();

        // Initializes all variables and components
        String courseCode = course.getName();
        final Service service = AppConfig.getService();
        final JTextField assignmentNameField = new JTextField();
        final JTextField weightField = new JTextField();
        final JCheckBox knowledgeCheckBox = new JCheckBox("K/U");
        final JCheckBox applicationCheckBox = new JCheckBox("Application");
        final JCheckBox thinkingCheckBox = new JCheckBox("Thinking");
        final JCheckBox communicationCheckBox = new JCheckBox("Communication");
        final Assessment[] assessments = course.getAssessments();
        String[] assignmentNames = new String[assessments.length];

        for (int i = 0; i < assessments.length; i++) {
            assignmentNames[i] = assessments[i].getName();
        }

        final JList<String> assignmentsList = new JList<>(assignmentNames);

        nameEditField = new JTextField();
        knowledgeCheckBoxEdit = new JCheckBox("K/U");
        thinkingCheckBoxEdit = new JCheckBox("Thinking");
        applicationCheckBoxEdit = new JCheckBox("Application");
        communicationCheckBoxEdit = new JCheckBox("Communication");
        weightEditField = new JTextField();

        JButton dashboardButton = new JButton("My Dashboard");
        JLabel nameEditLabel = new JLabel("Assignment Name:");
        JLabel categoryEditLabel = new JLabel("Category:");
        final JLabel weightEditLabel = new JLabel("Weight:");
        JLabel weightLabel = new JLabel("Weight:");
        JLabel courseCodeLabel = new JLabel(courseCode);
        JLabel pathLabel = new JLabel("Dashboard > " + courseCode);
        JLabel assignmentLabel = new JLabel("Assignment Name:");
        JLabel categoryLabel = new JLabel("Category:");
        JLabel background = new JLabel();
        JButton createAssignmentButton = new JButton("Create Assignment");
        JButton saveAssignmentButton = new JButton("Save Assignment");

        assignmentLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 30));
        assignmentLabel.setBounds(37, 163, 273, 32);

        assignmentNameField.setText(null);
        assignmentNameField.setFont(new Font("Tahoma", Font.PLAIN, 27));
        assignmentNameField.setBounds(37, 196, 412, 39);
        assignmentNameField.setColumns(10);

        categoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        categoryLabel.setBounds(37, 240, 146, 39);

        knowledgeCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        knowledgeCheckBox.setBounds(83, 286, 84, 23);

        applicationCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        applicationCheckBox.setBounds(298, 312, 123, 23);

        thinkingCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        thinkingCheckBox.setBounds(298, 286, 109, 23);

        communicationCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
        communicationCheckBox.setBounds(83, 312, 171, 23);

        weightLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        weightLabel.setBounds(37, 342, 109, 39);

        weightField.setHorizontalAlignment(SwingConstants.CENTER);
        weightField.setFont(new Font("Tahoma", Font.PLAIN, 27));
        weightField.setColumns(10);
        weightField.setBounds(146, 342, 52, 39);
        weightField.addKeyListener(new KeyAdapter() { // Prohibits user from entering string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        createAssignmentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // Create a new assessment with given name and categories
                Assessment assessment = new Assessment(assignmentNameField.getText(), course, Integer.parseInt(weightField.getText()));
                if (knowledgeCheckBox.isSelected()) {
                    assessment.addCategory("knowledge");
                }
                if (applicationCheckBox.isSelected()) {
                    assessment.addCategory("application");
                }
                if (communicationCheckBox.isSelected()) {
                    assessment.addCategory("communication");
                }
                if (thinkingCheckBox.isSelected()) {
                    assessment.addCategory("thinking");
                }
                try {
                    service.addAssessment(assessment, course);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                try {
                    new TeacherAssignmentDashboard(course, teacher);
                    frame.dispose();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        createAssignmentButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
        createAssignmentButton.setBounds(37, 392, 412, 101);

        assignmentsList.setBackground(new Color(235, 234, 234));
        assignmentsList.setBorder(null);
        assignmentsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { //Add values of selected Assignment to the footer to edit
                Assessment assessment = assessments[assignmentsList.getSelectedIndex()];
                nameEditField.setText(assignmentsList.getSelectedValue());
                knowledgeCheckBoxEdit.setSelected(assessment.hasKnowledge());
                applicationCheckBoxEdit.setSelected(assessment.hasApplication());
                communicationCheckBoxEdit.setSelected(assessment.hasCommunication());
                thinkingCheckBoxEdit.setSelected(assessment.hasThinking());
                weightEditField.setText(Integer.toString(assessment.getWeight()));
            }
        });
        assignmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        assignmentsList.setFont(new Font("Tahoma", Font.PLAIN, 30));
        assignmentsList.setBounds(495, 91, 317, 342);

        dashboardButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        dashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new TeacherDashboard(teacher);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        dashboardButton.setBounds(505, 442, 299, 51);

        nameEditLabel.setFont(new Font("Myriad Pro", Font.PLAIN, 30));
        nameEditLabel.setBounds(37, 606, 273, 32);

        nameEditField.setText(assignmentsList.getSelectedValue());
        nameEditField.setFont(new Font("Tahoma", Font.PLAIN, 27));
        nameEditField.setColumns(10);
        nameEditField.setBounds(285, 600, 519, 39);

        categoryEditLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        categoryEditLabel.setBounds(37, 646, 146, 39);

        knowledgeCheckBoxEdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        knowledgeCheckBoxEdit.setBounds(196, 658, 84, 23);

        thinkingCheckBoxEdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        thinkingCheckBoxEdit.setBounds(477, 658, 109, 23);

        applicationCheckBoxEdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        applicationCheckBoxEdit.setBounds(309, 658, 123, 23);

        communicationCheckBoxEdit.setFont(new Font("Tahoma", Font.PLAIN, 20));
        communicationCheckBoxEdit.setBounds(633, 658, 171, 23);

        weightEditLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
        weightEditLabel.setBounds(37, 696, 109, 39);

        weightEditField.setHorizontalAlignment(SwingConstants.CENTER);
        weightEditField.setFont(new Font("Tahoma", Font.PLAIN, 27));
        weightEditField.setColumns(10);
        weightEditField.setBounds(146, 696, 52, 39);
        weightEditField.addKeyListener(new KeyAdapter() { // Prohibits user from entering string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        saveAssignmentButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { //Save all edits to the Assignment
                String oldName = assignmentsList.getSelectedValue();
                Assessment assessment = new Assessment(nameEditField.getText(), course, Integer.parseInt(weightEditField.getText()));
                if (applicationCheckBoxEdit.isSelected()) {
                    assessment.addCategory("application");
                }
                if (thinkingCheckBoxEdit.isSelected()) {
                    assessment.addCategory("thinking");
                }
                if (communicationCheckBoxEdit.isSelected()) {
                    assessment.addCategory("communication");
                }
                if (knowledgeCheckBoxEdit.isSelected()) {
                    assessment.addCategory("knowledge");
                }
                try {
                    service.updateAssessment(oldName, assessment, course);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                // FIXME: 6/22/2016 Should not have to re-open a frame to reload data.
                try { // Reload the frame to reload all data
                    new TeacherAssignmentDashboard(course, teacher);
                    frame.dispose();
                } catch (SQLException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        saveAssignmentButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
        saveAssignmentButton.setBounds(477, 688, 327, 47);

        courseCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        courseCodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
        courseCodeLabel.setBounds(22, 23, 437, 39);

        pathLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        pathLabel.setBounds(222, 62, 237, 20);

        background.setBounds(0, 0, 850, 850);
        background.setIcon(new ImageIcon(getClass().getResource("/image/background/teacher/assignment.png")));

        // Adds all components to the frame
        frame.getContentPane().add(assignmentLabel);
        frame.getContentPane().add(categoryLabel);
        frame.getContentPane().add(weightLabel);
        frame.getContentPane().add(pathLabel);
        frame.getContentPane().add(courseCodeLabel);
        frame.getContentPane().add(nameEditLabel);
        frame.getContentPane().add(categoryEditLabel);
        frame.getContentPane().add(weightEditLabel);
        frame.getContentPane().add(assignmentNameField);
        frame.getContentPane().add(weightField);
        frame.getContentPane().add(knowledgeCheckBox);
        frame.getContentPane().add(applicationCheckBox);
        frame.getContentPane().add(thinkingCheckBox);
        frame.getContentPane().add(communicationCheckBox);
        frame.getContentPane().add(createAssignmentButton);
        frame.getContentPane().add(saveAssignmentButton);
        frame.getContentPane().add(dashboardButton);
        frame.getContentPane().add(assignmentsList);
        frame.getContentPane().add(nameEditField);
        frame.getContentPane().add(knowledgeCheckBoxEdit);
        frame.getContentPane().add(thinkingCheckBoxEdit);
        frame.getContentPane().add(weightEditField);
        frame.getContentPane().add(applicationCheckBoxEdit);
        frame.getContentPane().add(communicationCheckBoxEdit);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Assignments");
        frame.setResizable(false);
        frame.setBounds(0, 0, 850, 850);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}
