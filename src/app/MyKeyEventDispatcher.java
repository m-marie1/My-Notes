//package app;
//
//import static app.SideWindow.sidePanel;
//import java.awt.Component;
//import java.awt.KeyEventDispatcher;
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.stream.Collectors;
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//
//// This class creates a new note of copied text on the clipboard and saves it automaticaly when clicking "ctrl alt c"
//
//class MyKeyEventDispatcher implements KeyEventDispatcher {
//
//    JFrame frame;
//    String fileName;
//
//    public MyKeyEventDispatcher() {
//        frame = MainFrame.frame;
//
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent e) {
//        if (e.getID() == KeyEvent.KEY_PRESSED) {
//            if (e.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK) && e.getKeyCode() == KeyEvent.VK_C) {
//                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//                String clipboardText = "";
//
//                try {
//                    clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
//                    System.out.println(clipboardText);
//                } catch (UnsupportedFlavorException | IOException ex) {
//                    ex.printStackTrace();
//                }
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//                String currentDate = dateFormat.format(new Date());
//
//                fileName = currentDate + (clipboardText.length() > 20 ? clipboardText.substring(0, 20) : clipboardText);
//                JPanel filePanel = new JPanel();
//                filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
//
//                JLabel fileLabel = new JLabel(fileName + "...");
//                JButton fileButton = new JButton("Open");
//                sidePanel.add(Box.createVerticalStrut(10));
//
//                fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//                fileButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
//
//                filePanel.add(fileLabel);
//                filePanel.add(fileButton);
//
//                File directory = new File("C:\\Users\\me\\Documents\\My Notes");
//
//                String invalidChars = "\\/:*?\"<>|";
//                for (int i = 0; i < invalidChars.length(); i++) {
//                    String b = Character.toString(invalidChars.charAt(i));
//                    fileName = fileName.replaceAll("\\".equals(b) || "*".equals(b) || "?".equals(b) || "|".equals(b) ? "\\" + Character.toString(invalidChars.charAt(i)) : Character.toString(invalidChars.charAt(i)), "");
//
//                }
//                String fileNameCopy = fileName;
//
//                TextPanel.txtArea.setText("");
//                TextPanel.txtArea.append(clipboardText);
//                frame.setTitle(fileNameCopy);
//
//                File file = new File(directory, fileNameCopy + ".txt");
//                try {
//                    FileOutputStream fos = new FileOutputStream(file);
//                    fos.write(clipboardText.getBytes());
//                    fos.flush();
//                    fos.close();
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                sidePanel.add(filePanel);
//                sidePanel.revalidate();
//
//                fileButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        frame.setTitle(fileNameCopy+".txt");
//                        Toolbar.fileName = frame.getTitle();
//                        Toolbar.fileAddress = "C:\\Users\\me\\Documents\\My Notes\\";
//                        try {
//                            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\me\\Documents\\My Notes\\" + fileNameCopy + ".txt"), 32768);
//                            String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
//                            TextPanel.txtArea.setText("");
//                            TextPanel.txtArea.append(txt);
//                        } catch (FileNotFoundException ex) {
//                            Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//
//                    }
//                });
//            }
//        }
//        return false;
//
//    }
//}
