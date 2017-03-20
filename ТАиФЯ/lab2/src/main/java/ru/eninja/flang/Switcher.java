package ru.eninja.flang;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Класс конечного автомата
 */
public class Switcher implements ActionListener {

    /* Форма */
    private Frame frame;

    /* Состояния */
    private static final String OFF = "Выкл";
    private static final String INC = "Вкл";
    private static final String A   = "A";
    private static final String C   = "C";

    /* Переходы */
    private static final String IO  = "I/O";
    //                          A   = "A";
    //                          C   = "C";


    public Switcher(Frame frame) {
        this.frame = frame;
        frame.setCurrentState(OFF);
    }

    /**
     * Метод, вызываемый при клике на кнопку
     */
    @Override
    public void actionPerformed(ActionEvent e) {
         if (!(e.getSource() instanceof JButton)) {
             return;
         }
        String btnText = ((JButton) e.getSource()).getText();

         /* Переходы между состояниями */
        switch (frame.getCurrentState()) {
            case OFF:
                switch (btnText) {
                    case IO:
                        frame.setCurrentState(INC);
                        break;
                }
                break;
            case INC:
                switch (btnText) {
                    case IO:
                        frame.setCurrentState(OFF);
                        break;
                    case A:
                        frame.setCurrentState(A);
                        break;
                    case C:
                        frame.setCurrentState(C);
                        break;
                }
                break;
            case A:
                switch (btnText) {
                    case A:
                        frame.setCurrentState(INC);
                        break;
                    case C:
                        frame.setCurrentState(C);
                        break;
                    case IO:
                        frame.setCurrentState(OFF);
                        break;
                }
                break;
            case C:
                switch (btnText) {
                    case IO:
                        frame.setCurrentState(OFF);
                        break;
                    case C:
                        frame.setCurrentState(INC);
                        break;
                    case A:
                        frame.setCurrentState(A);
                        break;
                }
                break;
        }
    }
}
