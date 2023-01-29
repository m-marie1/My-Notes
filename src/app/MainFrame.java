package app;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
//import javax.swing.JTextArea;

// MainFrame class is the main window (JFrame) of the app where all other classes come together
public class MainFrame extends JFrame {

    static JFrame frame;

    private Toolbar toolbar;
    private FormatPanel format;
    private TextPanel textPanel;

    private static JSplitPane splitPane;
    static JPanel sidePanel;
    private static boolean isSidePanelVisible;
    private JPanel scrollPane;
    
    String dateAndName;
    String text;
    String nameWithoutExtension;

    public MainFrame() {

        frame = this;
//        timeout = TextPanel.tOut;

//        format = new FormatPanel();
        textPanel = new TextPanel();
        format = new FormatPanel();
        toolbar = new Toolbar();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyKeyEventDispatcher());

//        scrollPane = new SideWindow();
//        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, frame.getContentPane(), scrollPane);
//        splitPane.setDividerLocation(200);
//        JButton button = new JButton("Open Side Window");
// Use GridBagLayout to design the layout
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.01;

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        add(toolbar, gc);

        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;

        add(format, gc);

        gc.weightx = 1;
        gc.weighty = 20;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTH;

        add(textPanel, gc);

//      Add image as the app icon
        ImageIcon img = new ImageIcon("C:\\Users\\me\\Desktop\\icon.png");
        setIconImage(img.getImage());

        setTitle("My Notes");
//        setSize(600, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent evt) {
//                timeout.stop();
                TextPanel.tOut.stop();
            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setMinimumSize(new Dimension(1, 200));

        sidePanel = new SideWindow();
        // Create the side panel
//        sidePanel = new JPanel();
//        sidePanel.setPreferredSize(new Dimension(200, 200));

        // Create a scroll pane for the side panel
//        JScrollPane scrollPane = new JScrollPane(sidePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Create a split pane to hold the main panel and the side panel
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, frame.getContentPane(), sidePanel);
        splitPane.setResizeWeight(0.0);
//        splitPane.setDividerLocation(400);
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(5);
        splitPane.setRightComponent(null); // <-- add this line to make side window invisible on startup
        isSidePanelVisible = false;

        File folder = new File("C:\\Users\\me\\Documents\\My Notes\\");
        File[] listOfFiles = folder.listFiles();

//        if (SideWindow.sidePanel.getComponentCount() == 0) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {

                    JPanel filePanel = new JPanel();
                    filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
//                        filePanel.add(Box.createHorizontalStrut(50));

                    String name = file.getName();
                    String dir = file.getAbsolutePath();
                    int index = name.indexOf(".");
                    nameWithoutExtension = name.substring(0, index);
                    
                    try {
                        text = new BufferedReader(new FileReader(dir), 32768).lines().collect(Collectors.joining(System.lineSeparator()));
                        dateAndName = nameWithoutExtension.substring(0,19) + (text.length() > 20 ? text.substring(0, 20) : text);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    

                      
                    JLabel fileLabel = new JLabel(dateAndName + "...");
                    JButton fileButton = new JButton("Open");
                    SideWindow.sidePanel.add(Box.createVerticalStrut(10));
                    
                    fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    fileButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

                    filePanel.add(fileLabel);
                    filePanel.add(fileButton);

                    SideWindow.sidePanel.add(filePanel);
                    SideWindow.sidePanel.revalidate();

                    fileButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            frame.setTitle(file.getName());
                            Toolbar.fileName = frame.getTitle();
                            Toolbar.fileAddress = file.getParent()+"\\";
//                            BufferedReader br;
                            try {
                                BufferedReader br = new BufferedReader(new FileReader(dir), 32768);
                                String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
                                TextPanel.txtArea.setText("");
                                TextPanel.txtArea.append(txt);
                                TextPanel.txtArea.setCaretPosition(0);
//                                Toolbar.fileName = TextPanel.txtArea.getText().length()> 20 ? TextPanel.txtArea.getText().substring(0, 20) : TextPanel.txtArea.getText();
//                                frame.setTitle(Toolbar.fileName);
                            } catch (FileNotFoundException ex) {
                                System.out.println("I'm broken, Fix me quickly or I'll keep throwing this exception on you!" + ex);
//                                Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

//                        JLabel label = new JLabel(file.getName());
//                        panel.add(label);
                }
            }
//        }

        // Create a button
//        JButton button = new JButton("Open/Close Side Window");
        // Add an action listener to the button
        FormatPanel.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                File folder = new File("C:\\Users\\me\\Documents\\My Notes\\");
//                File[] listOfFiles = folder.listFiles();
//
//                if (SideWindow.sidePanel.getComponentCount() == 0) {
//                    for (File file : listOfFiles) {
//                        if (file.isFile() && file.getName().endsWith(".txt")) {
//                            
//
//                            JPanel filePanel = new JPanel();
//                            filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
////                        filePanel.add(Box.createHorizontalStrut(50));
//
//                            String name = file.getName();
//                            String dir = file.getAbsolutePath();
//                            int index = name.indexOf(".");
//                            String nameWithoutExtension = name.substring(0, index);
//
////                      
//                            JLabel fileLabel = new JLabel(nameWithoutExtension);
//                            JButton fileButton = new JButton("Open");
//                            SideWindow.sidePanel.add(Box.createVerticalStrut(10));
//
//                            filePanel.add(fileLabel);
//                            fileLabel.add(Box.createHorizontalGlue());
//                            filePanel.add(fileButton);
//
//                            SideWindow.sidePanel.add(filePanel);
//                            SideWindow.sidePanel.revalidate();
//
//                            fileButton.addActionListener(new ActionListener() {
//                                @Override
//                                public void actionPerformed(ActionEvent e) {
////                            BufferedReader br;
//                                    try {
//                                        BufferedReader br = new BufferedReader(new FileReader(dir), 32768);
//                                        String txt = br.lines().collect(Collectors.joining(System.lineSeparator()));
//                                        TextPanel.txtArea.setText("");
//                                        TextPanel.txtArea.append(txt);
//                                        TextPanel.txtArea.setCaretPosition(0);
//                                    } catch (FileNotFoundException ex) {
//                                        Logger.getLogger(SideWindow.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//
//                                }
//                            });
//
////                        JLabel label = new JLabel(file.getName());
////                        panel.add(label);
//                        }
//                    }
//                }
                if (isSidePanelVisible) {
                    splitPane.setRightComponent(null);
                    isSidePanelVisible = false;
                } else {
                    splitPane.setRightComponent(sidePanel);
                    splitPane.setResizeWeight(0.0);

                    splitPane.setDividerLocation(60 * frame.getWidth() / 100);
                    splitPane.setContinuousLayout(true);
                    isSidePanelVisible = true;
                }
                frame.validate();
            }
        });

        // Add the button to the main frame
//        frame.add(button);
        // Update the main frame's content pane
        frame.setContentPane(splitPane);
        // Display the main frame
        frame.pack();
        setSize(1000, 650);
        setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
