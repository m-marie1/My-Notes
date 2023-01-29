/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class TextPanel extends JPanel {

    static JTextArea txtArea;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    UndoManager uManager;
    static UndoManager um;
    JSpinner size;
    Timer timeout;
    static Timer tOut;

    public TextPanel() {

        textArea = new JTextArea();
        txtArea = textArea;
        uManager = new UndoManager();
        um = uManager;

        timeout = new Timer(200, evt -> {
            size = FormatPanel.size;
        });
        timeout.setRepeats(false);
        timeout.start();
        tOut = timeout;

//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                size = FormatPanel.size;
//            }
//        }, 200);
//        Use UndoManager to enable undoing redoing
        textArea.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                uManager.addEdit(e.getEdit());
            }
        });
        textArea.setFont(new Font("Arial", Font.PLAIN, 30));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        setLayout(new BorderLayout());

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

//        Zoom in and out using the mouse wheel while holding CTRL
        scrollPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {

                if (evt.isControlDown()) {
                    textArea.setFont(new Font(textArea.getFont().getFontName(), textArea.getFont().getStyle(),
                            evt.getUnitsToScroll() > 0 ? textArea.getFont().getSize() - 2
                            : textArea.getFont().getSize() + 2));
                    size.setValue(textArea.getFont().getSize());
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);

    }
//    timer.cancel();

}
