package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Search extends JPanel {

    private JTextArea textArea;
    private JTextField textField;
    private JButton searchButton;
    private ArrayList<Integer> matchingIndex;
    private int currentIndex;
    HighlightPainter defaultHighlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private int currentHighlightIndex = 0;
    private int totalHighlightCount = 0;

    public Search() {
        textArea = TextPanel.txtArea;
        textField = new JTextField(20);
        searchButton = new JButton("Search");
        matchingIndex = new ArrayList<>();

        JPanel panel = new JPanel();
//        panel.add(textArea);
        panel.add(textField);
        panel.add(searchButton);
        add(panel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                matchingIndex.clear();
                String searchTerm = textField.getText();
                String text = textArea.getText();
                int index = text.indexOf(searchTerm);
                while (index >= 0) {
                    matchingIndex.add(index);
                    index = text.indexOf(searchTerm, index + 1);
                }
                currentIndex = 0;
                if (matchingIndex.size() > 0) {
                    highlight(matchingIndex.get(currentIndex), searchTerm);
                }
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (matchingIndex.size() > 0) {
                    if (currentIndex == matchingIndex.size() - 1) {
                        currentIndex = 0;
                    } else {
                        currentIndex++;
                    }
                    String searchTerm = textField.getText();
                    highlight(matchingIndex.get(currentIndex), searchTerm);
                }
            }
        });
    }

    private void highlight(int index, String searchTerm) {
        textArea.setCaretPosition(index + searchTerm.length());
        textArea.moveCaretPosition(index);
        try {
            textArea.getHighlighter().addHighlight(index, index + searchTerm.length(), defaultHighlightPainter);
        } catch (BadLocationException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleUpArrow() {
        if (currentHighlightIndex > 0) {
            currentHighlightIndex--;
            String searchTerm = textField.getText();
            textArea.getHighlighter().removeAllHighlights();
            int index = textArea.getText().indexOf(searchTerm, currentHighlightIndex);
            highlight(index, searchTerm);
        }
    }

    private void handleDownArrow() {
        if (currentHighlightIndex < totalHighlightCount - 1) {
            currentHighlightIndex++;
            String searchTerm = textField.getText();
            textArea.getHighlighter().removeAllHighlights();
            int index = textArea.getText().indexOf(searchTerm, currentHighlightIndex);
            highlight(index, searchTerm);
        }
    }

    private void handleKeyEvents(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            handleUpArrow();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            handleDownArrow();
        }
    }
}
