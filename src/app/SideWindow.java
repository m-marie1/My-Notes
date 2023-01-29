package app;

//import static app.Toolbar.fileName;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class SideWindow extends JPanel {

    static JPanel sidePanel;
    JFrame frame;
    String fileNameC;
//    String fileName;

    public SideWindow() {
        frame = MainFrame.frame;

        sidePanel = new JPanel();
        setLayout(new BorderLayout());
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        KeyStroke keyStrokeToClipboard
                = KeyStroke.getKeyStroke("control shift C");

        Toolbar.saveNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("*My Notes".equals(frame.getTitle()) || "*New File".equals(frame.getTitle())) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                    String currentDate = dateFormat.format(new Date());
                    String text = TextPanel.txtArea.getText();
//                    fileName = ;

                    String fileName = currentDate + (text.length() > 20 ? text.substring(0, 20) : text);
//                    String invalidChars = "\\/:*?\"<>|";
//                    for (int i = 0; i < invalidChars.length(); i++) {
//                            String b = Character.toString(invalidChars.charAt(i));
//                            String fileName = currentDate + (text.length() > 20 ? text.substring(0, 20) : text).replaceAll("\\".equals(b) || "*".equals(b) || "?".equals(b) || "|".equals(b) ? "\\" + Character.toString(invalidChars.charAt(i)) : Character.toString(invalidChars.charAt(i)), "");
//                            fileNameC = fileName + ".txt";
//
//                        }
//                    // The variable passed to JButton dynamically can't be reassigned or it will only work with the last assignment
//                    String fileNameCopy = fileNameC;
//                    frame.setTitle(fileName);

                    if (!"".equals(TextPanel.txtArea.getText())) {
                        JPanel filePanel = new JPanel();
                        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));

                        JLabel fileLabel = new JLabel(fileName + "...");
                        JButton fileButton = new JButton("Open");
                        sidePanel.add(Box.createVerticalStrut(10));

                        fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        fileButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

                        filePanel.add(fileLabel);
                        filePanel.add(fileButton);

                        File directory = new File("C:\\Users\\me\\Documents\\My Notes");
                        
                        String invalidChars = "\\/:*?\"<>|";
                    for (int i = 0; i < invalidChars.length(); i++) {
                            String b = Character.toString(invalidChars.charAt(i));
                            fileName = currentDate + (text.length() > 20 ? text.substring(0, 20) : text).replaceAll("\\".equals(b) || "*".equals(b) || "?".equals(b) || "|".equals(b) ? "\\" + Character.toString(invalidChars.charAt(i)) : Character.toString(invalidChars.charAt(i)), "");
                            fileNameC = fileName + ".txt";

                        }
                    // The variable passed to JButton dynamically can't be reassigned or it will only work with the last assignment
                    String fileNameCopy = fileNameC;

//                        String invalidChars = "\\/:*?\"<>|";
//                        for (int i = 0; i < invalidChars.length(); i++) {
//                            String b = Character.toString(invalidChars.charAt(i));
//                            fileName = fileName.replaceAll("\\".equals(b) || "*".equals(b) || "?".equals(b) || "|".equals(b) ? "\\" + Character.toString(invalidChars.charAt(i)) : Character.toString(invalidChars.charAt(i)), "");
//
//                        }
//                        fileNameCopy = fileName + ".txt";
                        frame.setTitle(fileNameCopy);

                        File file = new File(directory, fileNameCopy);
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(TextPanel.txtArea.getText().getBytes());
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        sidePanel.add(filePanel);
                        sidePanel.revalidate();

                        fileButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setTitle(fileNameCopy); 
                                Toolbar.fileName = frame.getTitle();
                                Toolbar.fileAddress = "C:\\Users\\me\\Documents\\My Notes\\";
                                try {
                                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\me\\Documents\\My Notes\\" + fileNameCopy), 32768);
                                String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
                                TextPanel.txtArea.setText("");
                                TextPanel.txtArea.append(txt);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            }
                        });
                    }
                } else {

                    frame.setTitle(Toolbar.fileName);
//                    Toolbar.fileName = fileNameCopy + ".txt";
//                    Toolbar.fileAddress = "C:\\Users\\me\\Documents\\My Notes\\";
                    String date = frame.getTitle().substring(0, 19);
                    File dir = new File("C:\\Users\\me\\Documents\\My Notes");
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().startsWith(date)) {
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                fos.write(TextPanel.txtArea.getText().getBytes());
                                fos.flush();
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

//                    JPanel mainPanel = new JPanel();
                    // Add JPanels with JButtons and JLabels to mainPanel

                    for (Component c : sidePanel.getComponents()) {
                        if (c instanceof JPanel) {
                            JPanel subPanel = (JPanel) c;
                            for (Component subC : subPanel.getComponents()) {
                                if (subC instanceof JLabel) {
                                    JLabel label = (JLabel) subC;
                                    // Do something with the label, such as getting its text
                                    if(label.getText().substring(0, 19).equals(frame.getTitle().substring(0, 19))){
                                        String text = TextPanel.txtArea.getText().length() > 20 ? TextPanel.txtArea.getText().substring(0, 20) : TextPanel.txtArea.getText();
                                        label.setText(frame.getTitle().substring(0, 19) + text);
                                        
                                        
                                    }
                                }
                            }
                        }
                    }

                }
            }

        });
        JScrollPane scroll = new JScrollPane(sidePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(scroll, BorderLayout.CENTER);
    }

}
