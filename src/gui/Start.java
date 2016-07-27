package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Start screen - the first screen that the user is brought to. User must indicate if he/she is a teacher or student.
 *
 * @author Joshua Xavier
 * @course ICS4U
 * @date 6/20/2016
 */
public class Start {
    private JFrame frame;

    public static void main(String[] args) {
        new Start();
    }

    public Start() {
        initialize();
        JButton studentButton = new JButton("STUDENT");
        JButton teacherButton = new JButton("TEACHER");
        JLabel background = new JLabel();

        teacherButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // User selected the teacher option
                new TeacherLogin(); // Open the teacher login page
                frame.dispose();
            }
        });
        teacherButton.setForeground(Color.DARK_GRAY);
        teacherButton.setFont(new Font("Myriad Pro", Font.PLAIN, 20));
        teacherButton.setBounds(276, 150, 199, 59);

        studentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // User selected the student option
                new StudentLogin(); // Open the student login page
                frame.dispose();
            }
        });
        studentButton.setForeground(Color.DARK_GRAY);
        studentButton.setFont(new Font("Myriad Pro", Font.PLAIN, 20));
        studentButton.setBounds(276, 220, 199, 59);

        background.setIcon(new ImageIcon(getClass().getResource("/image/background/startBackground.png")));
        background.setBounds(0, 0, 550, 375);

        // Add all components to the frame
        frame.getContentPane().add(teacherButton);
        frame.getContentPane().add(studentButton);
        frame.getContentPane().add(background);

        frame.repaint();
    }

    /**
     * Creates the frame and initializes a few default values (size, default close operation, etc.)
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Start");
        frame.setResizable(false);
        frame.setBounds(100, 100, 525, 375);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
    }
}
