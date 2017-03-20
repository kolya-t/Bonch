package ru.eninja.flang;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;


/**
 * Класс формы
 */
public class Frame extends JFrame {

    private JLabel currentStateLbl;
    private JButton ioBtn;
    private JButton aBtn;
    private JButton cBtn;

    private Switcher switcher;
    private String currentState;

    /**
     * Конструктор окна
     */
    public Frame() {
        super("");
        setTheme();
        initWindow();
        initComponents();
        setFont();
        initListeners();
        addComponentsToFrame();
        pack();
        setVisible(true);
    }

    /**
     * Установка темы окна
     */
    private void setTheme() {
        try {
            UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализация окна
     */
    private void initWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Инициализация компонентов, которые будут распологаться на окне
     */
    private void initComponents() {
        currentStateLbl = new JLabel("Текущее состояние");
        ioBtn = new JButton("I/O");
        aBtn = new JButton("A");
        cBtn = new JButton("C");
    }

    /**
     * Установка шрифта
     */
    private void setFont() {
        try {
            String fontFileName = "OpenSans-Regular.ttf";
            float fontSize = 18;

            InputStream fontStream = getClass().getClassLoader().getResourceAsStream(fontFileName);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = font.deriveFont(Font.PLAIN, fontSize);

            currentStateLbl.setFont(font);
            ioBtn.setFont(font);
            aBtn.setFont(font);
            cBtn.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализация и установка для элементов окна слушателя события (клика)
     */
    private void initListeners() {
        switcher = new Switcher(this);
        ioBtn.addActionListener(switcher);
        aBtn.addActionListener(switcher);
        cBtn.addActionListener(switcher);
    }

    /**
     * Добавление компонентов на форму
     */
    private void addComponentsToFrame() {
        JPanel lblPanel = new JPanel(new FlowLayout());
        lblPanel.add(currentStateLbl);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(ioBtn);
        btnPanel.add(aBtn);
        btnPanel.add(cBtn);

        add(lblPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
    }

    /**
     * Возвращает текущее состояние конечного автомата
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * Устанавливает текущее состояние конечного автомата
     */
    public void setCurrentState(String state) {
        this.currentState = state;
        currentStateLbl.setText("Текущее состояние: " + state);
    }
}
