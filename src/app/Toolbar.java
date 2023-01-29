/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

public class Toolbar extends JMenuBar implements ActionListener, DocumentListener {

    private JMenuBar menuBar;
    private JMenu file;
    private JMenu edit;
    private JMenu view;
    private JMenu format;
    private JMenu theme;

    static JMenuItem saveNote;
    JPanel sidePanel;

    private JMenuItem newFile;
    private JMenuItem newWindow;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem saveAs;
    private JMenuItem exit;

    private JMenuItem undo;
    private JMenuItem redo;

    private JMenuItem onTop;
    private JMenu zoom;
    private JMenuItem zIn;
    private JMenuItem zOut;
    private JMenuItem zRestore;

    private JMenu fontFamily;
    private JMenuItem wordWrap;

    private JMenuItem calibri;
    private JMenuItem courier;
    private JMenuItem garamond;
    private JMenuItem georgia;
    private JMenuItem impact;
    private JMenuItem times;

    private JMenuItem light;
    private JMenuItem dark;
    private JMenuItem black;
    private JMenuItem terminal;

    JFrame frame;
    BufferedReader br;
    static String fileName;
    static String fileAddress;
    JTextArea textArea;
    UndoManager um;
    JComboBox family;

    Font fCalibri;
    Font fCourier;
    Font fGaramond;
    Font fGeorgia;
    Font fImpact;
    Font fTimes;

    public Toolbar() {

        sidePanel = MainFrame.sidePanel;

        textArea = TextPanel.txtArea;
        um = TextPanel.um;
        family = FormatPanel.family;

        frame = MainFrame.frame;
        fileName = "My Notes";
        menuBar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("View");
        format = new JMenu("Format");
        theme = new JMenu("Theme");

//        saveNote = new JMenuItem("Save");
//        saveNote.addActionListener(this);
//        add(saveNote);
        newFile = new JMenuItem("New");
        newWindow = new JMenuItem("New Window");
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        saveAs = new JMenuItem("Save as");
        exit = new JMenuItem("Exit");

        newFile.addActionListener(this);
        newWindow.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        saveAs.addActionListener(this);
        exit.addActionListener(this);

        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");

        undo.addActionListener(this);
        redo.addActionListener(this);

        onTop = new JMenuItem("Always On Top: OFF");
        zoom = new JMenu("Zoom");

        zIn = new JMenuItem("Zoom In");
        zOut = new JMenuItem("Zoom Out");
        zRestore = new JMenuItem("Restore Default Zoom");

        onTop.addActionListener(this);
        zIn.addActionListener(this);
        zOut.addActionListener(this);
        zRestore.addActionListener(this);

        fontFamily = new JMenu("Font");
        wordWrap = new JMenuItem("Word Wrap: ON");
        wordWrap.addActionListener(this);

        calibri = new JMenuItem("Calibri");
        courier = new JMenuItem("Courier New");
        garamond = new JMenuItem("Garamond");
        georgia = new JMenuItem("Georgia");
        impact = new JMenuItem("Impact");
        times = new JMenuItem("Times New Roman");

        calibri.addActionListener(this);
        courier.addActionListener(this);
        garamond.addActionListener(this);
        georgia.addActionListener(this);
        impact.addActionListener(this);
        times.addActionListener(this);

        light = new JMenuItem("Light");
        dark = new JMenuItem("Dark");
        black = new JMenuItem("BLack");
        terminal = new JMenuItem("Terminal");

        light.addActionListener(this);
        dark.addActionListener(this);
        black.addActionListener(this);
        terminal.addActionListener(this);

        fontFamily.add(calibri);
        fontFamily.add(courier);
        fontFamily.add(garamond);
        fontFamily.add(georgia);
        fontFamily.add(impact);
        fontFamily.add(times);

        file.add(newFile);
        file.add(newWindow);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAs);
        file.add(exit);
        file.add(exit);

        edit.add(undo);
        edit.add(redo);

        view.add(onTop);
        zoom.add(zIn);
        zoom.add(zOut);
        zoom.add(zRestore);
        view.add(zoom);

        format.add(fontFamily);
        format.add(wordWrap);

        theme.add(light);
        theme.add(dark);
        theme.add(black);
        theme.add(terminal);

//        Add key shortcuts to different menu items using accelerators
        KeyStroke keyStrokeToNew
                = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToWindow
                = KeyStroke.getKeyStroke("control shift N");

        KeyStroke keyStrokeToOpen
                = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToSave
                = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToSaveAs
                = KeyStroke.getKeyStroke("control shift S");

        KeyStroke keyStrokeToExit
                = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToUndo
                = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToRedo
                = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToBeOnTop
                = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToZoomIn
                = KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToZoomOut
                = KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToDefaultZoom
                = KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK);

        KeyStroke keyStrokeToWrap
                = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);

        newFile.setAccelerator(keyStrokeToNew);
        newWindow.setAccelerator(keyStrokeToWindow);
        openFile.setAccelerator(keyStrokeToOpen);
        saveFile.setAccelerator(keyStrokeToSave);
        saveAs.setAccelerator(keyStrokeToSaveAs);
        exit.setAccelerator(keyStrokeToExit);
        undo.setAccelerator(keyStrokeToUndo);
        redo.setAccelerator(keyStrokeToRedo);
        onTop.setAccelerator(keyStrokeToBeOnTop);
        zIn.setAccelerator(keyStrokeToZoomIn);
        zOut.setAccelerator(keyStrokeToZoomOut);
        zRestore.setAccelerator(keyStrokeToDefaultZoom);
        wordWrap.setAccelerator(keyStrokeToWrap);

//        fCalibri = new Font("Calibri", Font.PLAIN, textArea.getFont().getSize());
//        fCourier = new Font("Courier New", Font.PLAIN, textArea.getFont().getSize());
//        fGaramond = new Font("Garamond", Font.PLAIN, textArea.getFont().getSize());
//        fGeorgia = new Font("Georgia", Font.PLAIN, textArea.getFont().getSize());
//        fImpact = new Font("Impact", Font.PLAIN, textArea.getFont().getSize());
//        fTimes = new Font("Times New Roman", Font.PLAIN, textArea.getFont().getSize());
        add(file);
        add(edit);
        add(view);
        add(format);
        add(theme);

        saveNote = new JMenuItem("Save Note");
        saveNote.addActionListener(this);
        Color originalColor = saveNote.getBackground();
        saveNote.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveNote.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveNote.setBackground(originalColor);
            }
        });

        add(saveNote);

//        setMinimumSize(new Dimension(1, this.getHeight()));
        textArea.getDocument().addDocumentListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

//        if (e.getSource() == saveNote) {
//            saveAs();
//            JButton fileButton = new JButton("File " + fileName);
//            fileButton.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    open();
//                }
//            });
//            sidePanel.add(fileButton);
//            sidePanel.validate();
//
//        }
// Edit menu items
        if (e.getSource()
                == undo) {
            um.undo();

        }

        if (e.getSource()
                == redo) {
            um.redo();

        }

// View menu items
//        Make the window stay on top of other apps
        if (e.getSource()
                == onTop) {
            if ("Always On Top: OFF".equals(onTop.getText())) {
                onTop.setText("Always On Top: ON");
                frame.setAlwaysOnTop(true);

            } else if ("Always On Top: ON".equals(onTop.getText())) {
                onTop.setText("Always On Top: OFF");
                frame.setAlwaysOnTop(false);

            }

        }
//      Zoom In and out and restore the default by changing font size

        if (e.getSource()
                == zIn) {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, textArea.getFont().getSize() + 4));

        }

        if (e.getSource()
                == zOut) {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, textArea.getFont().getSize() - 4));

        }

        if (e.getSource()
                == zRestore) {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, 30));

        }
// Format menu items

//      Wrap text so you can see the content in 1 page without horizontal scrolling
        if (e.getSource()
                == wordWrap) {
            if ("Word Wrap: OFF".equals(wordWrap.getText())) {
                wordWrap.setText("Word Wrap: ON");
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

            } else if ("Word Wrap: ON".equals(wordWrap.getText())) {
                wordWrap.setText("Word Wrap: OFF");
                textArea.setLineWrap(false);
                textArea.setWrapStyleWord(false);
            }

        }

//        Commonly used fonts to use instead of the full fonts list in format panel
        if (e.getSource()
                == calibri) {
            fCalibri = new Font("Calibri", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fCalibri);
            family.setSelectedItem("Calibri");
        }

        if (e.getSource()
                == courier) {
            fCourier = new Font("Courier New", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fCourier);
            family.setSelectedItem("Courier");
        }

        if (e.getSource()
                == garamond) {
            fGaramond = new Font("Garamond", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fGaramond);
            family.setSelectedItem("Garamond");
        }

        if (e.getSource()
                == georgia) {
            fGeorgia = new Font("Georgia", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fGeorgia);
            family.setSelectedItem("Georgia");
        }

        if (e.getSource()
                == impact) {
            fImpact = new Font("Impact", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fImpact);
            family.setSelectedItem("Impact");
        }

        if (e.getSource()
                == times) {
            fTimes = new Font("Times New Roman", Font.PLAIN, textArea.getFont().getSize());
            textArea.setFont(fTimes);
            family.setSelectedItem("Times New Roman");

        }
//      Differnt Themes--Change foreground color(text), background color, and mouse cursor color

        if (e.getSource()
                == light) {
            textArea.setBackground(Color.white);
            textArea.setForeground(Color.black);
            textArea.setCaretColor(Color.black);
        }

        if (e.getSource()
                == dark) {
            textArea.setBackground(new Color(61, 61, 61));
            textArea.setForeground(Color.white);
            textArea.setCaretColor(Color.white);
        }

        if (e.getSource()
                == black) {
            textArea.setBackground(Color.black);
            textArea.setForeground(Color.white);
            textArea.setCaretColor(Color.white);
        }

        if (e.getSource()
                == terminal) {
            textArea.setBackground(Color.black);
            textArea.setForeground(Color.green);
            textArea.setCaretColor(Color.green);
        }

// File Menu Items
        if (e.getSource()
                == newFile) {
            fileName = "New File";
            frame.setTitle("New File");
            textArea.setText("");
            fileAddress = null;

        }

        if (e.getSource()
                == newWindow) {
            new MainFrame();
        }

        if (e.getSource()
                == openFile) {

            FileDialog fd = new FileDialog(frame, "Open File", FileDialog.LOAD);
            fd.setVisible(true);

            if (fd.getFile() != null) {

                fileName = fd.getFile();
                fileAddress = fd.getDirectory();
                frame.setTitle(fileName);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName), 32768);

                    String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
                    textArea.setText("");
                    textArea.append(txt);
                } catch (Exception ex) {

                    System.out.println("FILE DIDN'T OPEN!");

                }
            }

        }

        if (e.getSource()
                == saveFile) {

            if ("My Notes".equals(fileName) || "New File".equals(fileName)) {
                saveAs();
            } else {

                save();

            }

        }

        if (e.getSource()
                == saveAs) {

            saveAs();

        }

        if (e.getSource()
                == exit) {
            frame.dispose();

        }

    }

    public void open() {
        FileDialog fd = new FileDialog(frame, "Open File", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null) {

            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            frame.setTitle(fileName);

            try {
                BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName), 32768);

                String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
                textArea.setText("");
                textArea.append(txt);
                textArea.setCaretPosition(0);
            } catch (Exception ex) {

                System.out.println("FILE DIDN'T OPEN!");

            }
        }

    }

    public void save() {
        try {

            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(textArea.getText());
            frame.setTitle(fileName);
            fw.close();

        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG, TRY AGAIN!");
        }

    }

    public void saveAs() {

        FileDialog fd = new FileDialog(frame, "Save File", FileDialog.SAVE);
        fd.setFile("*.txt");
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            frame.setTitle(fileName);
        }

        try {

            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(textArea.getText());
            fw.close();

        } catch (Exception e) {

            System.out.println("SOMETHING WENT WRONG, TRY AGAIN!");
        }

    }

//    Document listener methods for the text area to let the user know if there's unsaved text by adding a star next to window title (File name when opened)
    @Override
    public void insertUpdate(DocumentEvent e) {

        savedState();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        savedState();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        savedState();
    }

    public void savedState() {
        try {

            if ("My Notes".equals(fileName)) {
                frame.setTitle("*" + fileName);
            } else if ("New File".equals(fileName)) {
                frame.setTitle("*" + fileName);
            } else if (new BufferedReader(new FileReader(fileAddress + fileName), 32768).lines().collect(Collectors.joining(System.lineSeparator())).equals(textArea.getText())) {
                frame.setTitle(fileName);

            } else {
                frame.setTitle("*" + fileName);
            }

            if ("".equals(textArea.getText())) {

                frame.setTitle(fileName);

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
