package com.rutar.jdropzonepanel;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.event.*;

// ............................................................................
/// Демонстація основних можливостей JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public class JDropZonePanelDemo extends JFrame {

// ============================================================================
/// Конструктор за замовчуванням

public JDropZonePanelDemo()
    { initComponents();
      initAppIcons(); }

// ============================================================================
/// Головний метод програми
/// @param args масив параметрів запуску програми

public static void main (String args[]) {

    // Правила оформлення проектів описані тут:
    // https://github.com/RutarAndriy/My_Coding_Rules

    EventQueue.invokeLater(() -> {
        new JDropZonePanelDemo().setVisible(true);
    });
}

// ============================================================================
/// Оновлення текстових підказок для повзунків

private void updateToolTips()
    { sld_smileWidth.setToolTipText("Ширина усмішки: %d°"
                    .formatted(sld_smileWidth.getValue()));
      sld_lineWidth.setToolTipText("Товщина ліній: %d"
                   .formatted(sld_lineWidth.getValue())); }

// ============================================================================
/// Відновлення стандартних налаштувань компонента

private void resetSettings()
    { cmp_face.setSmile(true);
      cmp_face.setSmileWidth(120);
      cmp_face.setLineWidth(JDropZonePanel.LINE_WIDTH_NORM);
      cmp_face.setBackground(null);
      cmp_face.setForeground(null);
      sld_smileWidth.setValue(cmp_face.getSmileWidth());
      sld_lineWidth.setValue(cmp_face.getLineWidth());
      updateToolTips(); }

// ============================================================================
/// Генерування випадкового кольору

private Color getRandomColor()
    { return new Color((int)(Math.random()*255),
                       (int)(Math.random()*255),
                       (int)(Math.random()*255)); }

// ============================================================================
/// Встановлення іконок для головного вікна

private void initAppIcons() {

    BufferedImage icon;
    ArrayList<Image> appIcons = new ArrayList<>();

    try {
        
    for (String resource : new String[] { "icon_16.png",
                                          "icon_32.png" }) {
        resource = "icons/" + resource;
        icon = ImageIO.read(getClass().getResourceAsStream(resource));
        appIcons.add(icon); }
    
    setIconImages(appIcons); }
    
    catch (IOException _) { } }

// ============================================================================
/// Цей метод викликається з конструктора для ініціалізації форми.
/// УВАГА: НЕ змінюйте цей код. Вміст цього методу завжди 
/// перезапишеться редактором форм

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmp_face = new JDropZonePanel();
        lbl_lineWidth = new JLabel();
        sld_lineWidth = new JSlider();
        lbl_smileWidth = new JLabel();
        sld_smileWidth = new JSlider();
        btn_smileType = new JButton();
        btn_fColor = new JButton();
        btn_bColor = new JButton();
        btn_reset = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("JDropZonePanel Demo");
        setResizable(false);

        cmp_face.addJDropZonePanelListener(new JDropZonePanelListener() {
            public void smileTypeChange(JDropZonePanelEvent evt) {
                onSmileTypeChange(evt);
            }
            public void smileWidthChange(JDropZonePanelEvent evt) {
                onSmileWidthChange(evt);
            }
            public void lineWidthChange(JDropZonePanelEvent evt) {
                onLineWidthChange(evt);
            }
        });

        lbl_lineWidth.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_lineWidth.setText("Товщина ліній");
        lbl_lineWidth.setAlignmentX(Component.LEFT_ALIGNMENT);

        sld_lineWidth.setMaximum(18);
        sld_lineWidth.setMinimum(1);
        sld_lineWidth.setValue(5);
        sld_lineWidth.setAlignmentX(0.0F);
        sld_lineWidth.addChangeListener(this::onStateChange);

        lbl_smileWidth.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_smileWidth.setText("Ширина усмішки");

        sld_smileWidth.setMaximum(175);
        sld_smileWidth.setValue(120);
        sld_smileWidth.setAlignmentX(0.0F);
        sld_smileWidth.addChangeListener(this::onStateChange);

        btn_smileType.setText("Усмішка / Гримаса");
        btn_smileType.setActionCommand("smile");
        btn_smileType.addActionListener(this::onButtonClick);

        btn_fColor.setText("Змінити колір ліній");
        btn_fColor.setActionCommand("fColor");
        btn_fColor.addActionListener(this::onButtonClick);

        btn_bColor.setText("Змінити колір фону");
        btn_bColor.setActionCommand("bColor");
        btn_bColor.addActionListener(this::onButtonClick);

        btn_reset.setText("Відновити стандартні налаштування");
        btn_reset.setActionCommand("reset");
        btn_reset.addActionListener(this::onButtonClick);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(cmp_face, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_lineWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sld_lineWidth, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_bColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_fColor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_smileType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sld_smileWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_reset, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(lbl_smileWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_lineWidth)
                        .addGap(5, 5, 5)
                        .addComponent(sld_lineWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(lbl_smileWidth)
                        .addGap(5, 5, 5)
                        .addComponent(sld_smileWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(btn_smileType)
                        .addGap(5, 5, 5)
                        .addComponent(btn_fColor)
                        .addGap(5, 5, 5)
                        .addComponent(btn_bColor)
                        .addGap(5, 5, 5)
                        .addComponent(btn_reset))
                    .addComponent(cmp_face, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        updateToolTips();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void onButtonClick(ActionEvent evt) {//GEN-FIRST:event_onButtonClick
        switch (evt.getActionCommand()) {
            // Усмішка/гримаса
            case "smile"  -> cmp_face.setSmile(!cmp_face.isSmile());
            // Колір ліній
            case "fColor" -> cmp_face.setForeground(getRandomColor());
            // Колір фону
            case "bColor" -> cmp_face.setBackground(getRandomColor());
            // Відновлення стандартних налаштувань
            case "reset"  -> resetSettings();
        }
    }//GEN-LAST:event_onButtonClick

    private void onStateChange(ChangeEvent evt) {//GEN-FIRST:event_onStateChange
        // Оновлення властивостей компонента
        if (evt.getSource() == sld_lineWidth)
            { cmp_face.setLineWidth(sld_lineWidth.getValue()); }
        else
            { cmp_face.setSmileWidth(sld_smileWidth.getValue()); }
        // Оновлення текстових підказок
        updateToolTips();
    }//GEN-LAST:event_onStateChange

    private void onSmileTypeChange(JDropZonePanelEvent evt) {//GEN-FIRST:event_onSmileChange
        IO.println("Тип усмішки змінився з %s на %s"
          .formatted(evt.getOldValue(), evt.getNewValue()));
    }//GEN-LAST:event_onSmileChange

    private void onSmileWidthChange(JDropZonePanelEvent evt) {//GEN-FIRST:event_onSmileWidthChange
        IO.println("Ширина усмішки змінилася з %s на %s"
          .formatted(evt.getOldValue(), evt.getNewValue()));
    }//GEN-LAST:event_onSmileWidthChange

    private void onLineWidthChange(JDropZonePanelEvent evt) {//GEN-FIRST:event_onLineWidthChange
        IO.println("Товщина ліній змінилася з %s на %s"
          .formatted(evt.getOldValue(), evt.getNewValue()));
    }//GEN-LAST:event_onLineWidthChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btn_bColor;
    private JButton btn_fColor;
    private JButton btn_reset;
    private JButton btn_smileType;
    private JDropZonePanel cmp_face;
    private JLabel lbl_lineWidth;
    private JLabel lbl_smileWidth;
    private JSlider sld_lineWidth;
    private JSlider sld_smileWidth;
    // End of variables declaration//GEN-END:variables

// Кінець класу JDropZonePanelBeanInfo ========================================

}
