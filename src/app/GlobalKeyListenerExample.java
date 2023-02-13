package app;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import static app.SideWindow.sidePanel;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

public class GlobalKeyListenerExample implements NativeKeyListener {

    JFrame frame;
    String fileName;
//    String output;

    public GlobalKeyListenerExample() {
        frame = MainFrame.frame;

    }

    public void nativeKeyPressed(NativeKeyEvent e) {

//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        String clipboardText = "";
//
//        try {
//            clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
////            System.out.println(clipboardText);
//        } catch (UnsupportedFlavorException | IOException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println("Key Pressed: " + e.getKeyCode());
        if ((e.getModifiers() & NativeKeyEvent.CTRL_L_MASK) != 0
                && (e.getModifiers() & NativeKeyEvent.ALT_L_MASK) != 0
                && e.getKeyCode() == NativeKeyEvent.VC_C) {

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String clipboardText = "";

            try {
                clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
//                System.out.println(clipboardText);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String currentDate = dateFormat.format(new Date());

            fileName = currentDate + (clipboardText.length() > 20 ? clipboardText.substring(0, 20) : clipboardText);
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
                fileName = fileName.replaceAll("\\".equals(b) || "*".equals(b) || "?".equals(b) || "|".equals(b) ? "\\" + Character.toString(invalidChars.charAt(i)) : Character.toString(invalidChars.charAt(i)), "");
             

            }
            fileName = fileName.replaceAll("\r|\n", ""); // Remove line breaks
            
            String fileNameCopy = fileName;
            System.out.println(fileName);

            TextPanel.txtArea.setText("");
            TextPanel.txtArea.append(clipboardText);
            frame.setTitle(fileNameCopy + ".txt");

            Toolbar.fileName = frame.getTitle();
            Toolbar.fileAddress = "C:\\Users\\me\\Documents\\My Notes\\";

//            try {
//
//                FileWriter fw = new FileWriter(directory + "\\" + fileNameCopy + ".txt");
//                fw.write(TextPanel.txtArea.getText());
////                frame.setTitle(Toolbar.fileName);
//                fw.close();
//
//            } catch (Exception ex) {
//                System.out.println("You've missed Java up, Try again" + e);
//            }
            File file = new File(directory, fileNameCopy + ".txt");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(clipboardText.getBytes());
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
                    frame.setTitle(fileNameCopy + ".txt");
                    Toolbar.fileName = frame.getTitle();
                    Toolbar.fileAddress = "C:\\Users\\me\\Documents\\My Notes\\";
                    try {
                        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\me\\Documents\\My Notes\\" + fileNameCopy + ".txt"), 32768);
                        String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
                        TextPanel.txtArea.setText("");
                        TextPanel.txtArea.append(txt);
                        TextPanel.txtArea.setCaretPosition(0);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

        }

        if ((e.getModifiers() & NativeKeyEvent.CTRL_L_MASK) != 0
                && (e.getModifiers() & NativeKeyEvent.ALT_L_MASK) != 0
                && e.getKeyCode() == NativeKeyEvent.VC_V) {

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String clipboardText = "";

            try {
                clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
//                System.out.println(clipboardText);
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }

            StringBuilder emptyLines = new StringBuilder();
            for (int i = 0; i < 2; i++) {
                emptyLines.append(System.lineSeparator());
            }

            String textWithEmptyLines = emptyLines.toString() + clipboardText;

            TextPanel.txtArea.append(textWithEmptyLines);
            TextPanel.txtArea.setCaretPosition(TextPanel.txtArea.getText().length());

//            File file = new File(Toolbar.fileAddress + Toolbar.fileName);
//                        try {
//                            FileOutputStream fos = new FileOutputStream(file);
//                            fos.write(TextPanel.txtArea.getText().getBytes());
//                            fos.flush();
//                            fos.close();
//                        } catch (FileNotFoundException ex) {
//                            Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (IOException ex) {
//                            Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                        }
            try {

                FileWriter fw = new FileWriter(Toolbar.fileAddress + Toolbar.fileName);
                fw.write(TextPanel.txtArea.getText());
                frame.setTitle(Toolbar.fileName);
                fw.close();

            } catch (Exception ex) {
                System.out.println("You've missed Java up, Try again" + e);
            }

        }

//		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
//            		try {
//                		GlobalScreen.unregisterNativeHook();
//            		} catch (NativeHookException nativeHookException) {
//                		nativeHookException.printStackTrace();
//            		}
//        	}
    }

//	public void nativeKeyReleased(NativeKeyEvent e) {
//		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
//	}
//
//	public void nativeKeyTyped(NativeKeyEvent e) {
//		System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
//	}
//
//	public static void main(String[] args) {
//		try {
//			GlobalScreen.registerNativeHook();
//		}
//		catch (NativeHookException ex) {
//			System.err.println("There was a problem registering the native hook.");
//			System.err.println(ex.getMessage());
//
//			System.exit(1);
//		}
//
//		GlobalScreen.addNativeKeyListener(new GlobalKeyListenerExample());
//	}
}
