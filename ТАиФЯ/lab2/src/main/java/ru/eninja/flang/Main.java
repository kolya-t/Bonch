package ru.eninja.flang;

import javax.swing.*;

/**
 * Точка запуска программы
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Frame::new);
    }
}
