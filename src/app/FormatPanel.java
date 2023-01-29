
package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FormatPanel extends JPanel implements ActionListener {

    private JLabel font;
    private JSpinner fontSize;
    static JSpinner size;
    private JComboBox fontFamily;
    private JButton color;
    static JButton button;
    static JComboBox family;
    JTextArea textArea;

    public FormatPanel() {
        textArea = TextPanel.txtArea;

        font = new JLabel("Font Settings: ");

        fontSize = new JSpinner();

        fontSize.setValue(30);
        size = fontSize;

//        Change listener to detect change in font size in the JSpinner and set the font to that value
        fontSize.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSize.getValue()));
            }

        });

//        Get fonts to use in the JComboBox
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontFamily = new JComboBox(fonts);
        fontFamily.setSelectedItem("Arial");
        fontFamily.addActionListener(this);
        family = fontFamily;

        color = new JButton("Color");
        color.addActionListener(this);

        button = new JButton("Saved Notes");
        button.setBackground(Color.YELLOW);
        
//        setLayout(new BorderLayout());
//        JPanel westPanel = new JPanel();
//        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.X_AXIS));
//        westPanel.add(font);
//        westPanel.add(fontSize);
//        westPanel.add(fontFamily);
//        westPanel.add(color);
//        westPanel.add(button);
//
//        add(westPanel, BorderLayout.WEST);
//        add(button, BorderLayout.EAST);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.insets = new Insets(2,5,2,5);
        
        
        
        add(font, gc);
        add(fontSize, gc);
        add(fontFamily, gc);
        add(color, gc);
        
        gc.weightx = 1;
        gc.weighty = 1;

        gc.gridx = 10;
        gc.gridy = 0;       
        gc.fill = GridBagConstraints.EAST;
        gc.anchor = GridBagConstraints.EAST;
//        gc.insets = new Insets(1,400,1,1);
        add(button, gc);
//        setMinimumSize(new Dimension(1, this.getHeight()));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fontFamily) {

            textArea.setFont(new Font((String) fontFamily.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));

        }

//        Use JColorChooser to change text color
        if (e.getSource() == color) {
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null, "Change font color", Color.black);

            textArea.setForeground(color);
        }

//        if (e.getSource() == button) {
//
//            new SideWindow();
//
//        }
    }

}

