package gui;

import entity.Teacher;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Teacher login - screen for teacher to log in
 *
 * @author Dannish Siddiqui
 * @course ICS4U
 * @date 6/20/2016
 */
class TeacherLogin {
    private JFrame frame;

    TeacherLogin() {
        initialize();

        final JTextField teacherNumberField = new JTextField();
        final JPasswordField passwordField = new JPasswordField();
        final JButton signInButton = new JButton();
        JButton homeButton = new JButton();
        JLabel background = new JLabel();

        teacherNumberField.setHorizontalAlignment(SwingConstants.CENTER);
        teacherNumberField.setForeground(Color.LIGHT_GRAY);
        teacherNumberField.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        teacherNumberField.setBounds(21, 97, 256, 47);
        teacherNumberField.setColumns(10);
        teacherNumberField.addKeyListener(new KeyAdapter() { // Prohibits user from entering string
            public void keyTyped(KeyEvent e) {
                char keyEntered = e.getKeyChar();
                if (!(Character.isDigit(keyEntered)
                        || keyEntered == KeyEvent.VK_BACK_SPACE
                        || keyEntered == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setToolTipText("Password");
        passwordField.setBounds(21, 162, 256, 47);

        signInButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Login requested
                try {
                    Service service = AppConfig.getService();
                    Teacher teacher = service.getTeacherByCredentials(teacherNumberField.getText(),
                            new String(passwordField.getPassword()));
                    if (teacher == null) { // If no teacher with matching password was found, indicate to the user and clear all text fields
                        JTextPane invalidLoginPane = new JTextPane();
                        invalidLoginPane.setText("INVALID");
                        invalidLoginPane.setBounds(129, 75, 80, 20);
                        frame.getContentPane().add(invalidLoginPane);
                        teacherNumberField.setText(null);
                        passwordField.setText(null);
                    } else {
                        new TeacherDashboard(teacher);
                        frame.dispose();
                    }
                } catch (ClassNotFoundException | SQLException ignored) {
                    ignored.printStackTrace();
                }
            }
        });
        signInButton.setIcon(new ImageIcon(getClass().getResource("/image/button/login.png")));
        signInButton.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        signInButton.setBounds(197, 220, 132, 47);

        homeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Brings user back to the start screen
                new Start();
                frame.dispose();
            }
        });
        homeButton.setIcon(new ImageIcon(getClass().getResource("/image/button/home.png")));
        homeButton.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        homeButton.setBounds(21, 220, 132, 47);

        background.setIcon(new ImageIcon(getClass().getResource("/image/background/teacher/login.png")));
        background.setBounds(0, 0, 339, 290);

        // Adds all components to start screen
        frame.getContentPane().add(teacherNumberField);
        frame.getContentPane().add(homeButton);
        frame.getContentPane().add(signInButton);
        frame.getContentPane().add(passwordField);
        frame.getContentPane().add(background);
        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Log in");
        frame.setBounds(100, 100, 339, 315);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}