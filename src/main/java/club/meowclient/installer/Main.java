package club.meowclient.installer;

/*
    Author: ClientSiders
    Created: 4/1/2022
*/

import club.meowclient.installer.util.FileUtility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

public class Main {
    public static Main INSTANCE = new Main();

    public String URL = "https://cdn.glitch.global/00fef02b-ad55-4fea-b75a-0db8e0263d1e/gato.zip?v=1704214059731";
    public String FILE_NAME = "gato.zip";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> gui());
    }

    private static void gui() {
        JFrame frame = new JFrame("Meow Client Installation Wizard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        Border titledBorder = BorderFactory.createTitledBorder("Installation Wizard");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Meow Client");
        title.setFont(new Font("Arial", Font.BOLD, 40));

        JLabel copyright = new JLabel("\u00A9 2021-2024 Meow Client, All Rights Reserved");
        copyright.setFont(new Font("Arial", Font.PLAIN, 10));

        JButton installButton = new JButton("Install");
        installButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, installButton.getPreferredSize().height));

        installButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String appDataDirectory = System.getenv("APPDATA");
                String destinationDirectory = Paths.get(appDataDirectory, ".minecraft", "versions").toString();
                try {
                    FileUtility.downloadAndExtractZip(INSTANCE.URL, INSTANCE.FILE_NAME, destinationDirectory);
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Installation failed!");
                    return;
                }

                simulateInstallation(3);

                JOptionPane.showMessageDialog(frame, "Installation complete! \nRun the Minecraft Launcher and create a new profile");

//                simulateInstallation(1);

//                WebUtility.openWebPage("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            }
        });

        panel.add(title);

        panel.add(copyright);

        panel.add(Box.createVerticalStrut(53));
        panel.add(installButton);

        panel.setBorder(titledBorder);

        frame.getContentPane().add(panel);

        frame.setVisible(true);
        frame.setResizable(false);
    }

    private static void simulateInstallation(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
