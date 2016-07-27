package gui;

import entity.Student;
import service.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * Student login screen
 *
 * @author Dannish Siddiqui
 * @course ICS4U
 * @date 6/20/2016
 */
public class StudentLogin {
    private JFrame frame;

    public StudentLogin() {
        initialize();

        final JTextField studentNumberField = new JTextField();
        final JPasswordField passwordField = new JPasswordField();
        JButton signInButton = new JButton();
        JButton homeButton = new JButton();
        JLabel background = new JLabel();

        studentNumberField.setHorizontalAlignment(SwingConstants.CENTER);
        studentNumberField.setForeground(Color.LIGHT_GRAY);
        studentNumberField.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        studentNumberField.setBounds(21, 97, 256, 47);
        studentNumberField.setColumns(10);
        studentNumberField.addKeyListener(new KeyAdapter() { // Prohibits user from entering string
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
        passwordField.setBounds(21, 162, 266, 47);

        signInButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Request to sign in
                Service service;
                try {
                    service = AppConfig.getService();
                    Student student = service.getStudentByCredentials(studentNumberField.getText(),
                            new String(passwordField.getPassword()));
                    if (student == null) { // If no student with matching password was found, indicate invalid login and clear text fields
                        JTextPane invalidLoginPane = new JTextPane();
                        invalidLoginPane.setText("INVALID LOGIN");
                        invalidLoginPane.setBounds(129, 75, 80, 20);
                        frame.getContentPane().add(invalidLoginPane);
                        invalidLoginPane.setVisible(true);
                        studentNumberField.setText(null);
                        passwordField.setText(null);
                    } else {
                        new StudentDashboard(student);
                        frame.dispose();
                    }
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        signInButton.setIcon(new ImageIcon(getClass().getResource("/image/button/login.png")));
        signInButton.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        signInButton.setBounds(197, 220, 132, 47);

        homeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Bring user back to home
                new Start();
                frame.dispose();
            }
        });
        homeButton.setIcon(new ImageIcon(getClass().getResource("/image/button/home.png")));
        homeButton.setFont(new Font("Myriad Pro", Font.PLAIN, 16));
        homeButton.setBounds(21, 220, 132, 47);

        background.setIcon(new ImageIcon(getClass().getResource("/image/background/student/login.png")));
        background.setBounds(0, 0, 339, 290);

        frame.getContentPane().add(passwordField);
        frame.getContentPane().add(signInButton);
        frame.getContentPane().add(homeButton);
        frame.getContentPane().add(studentNumberField);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Login");
        frame.setBounds(100, 100, 347, 317);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

    }
}
