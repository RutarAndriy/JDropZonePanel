package com.rutar.jdropzonepanel;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.beans.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

import static com.rutar.jdropzonepanel.JDropZonePanel.*;

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

    SwingUtilities.invokeLater(() ->
      { var window = new JDropZonePanelDemo();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        SwingUtilities.invokeLater(() ->
          { window.setMinimumSize(window.getSize()); }); }); }

// ============================================================================
/// Генерування випадкового кольору

private Color chooseColor (Color oldColor)
  { var color = JColorChooser.showDialog(this, "Виберіть колір", oldColor);
    return color == null ? oldColor : color; }

// ============================================================================
/// Зміна кольору існуючої рамки

private Border updateBorderColor (Border border)
  { if (border instanceof LineBorder lb)
      { var color = chooseColor(lb.getLineColor());
        var width = lb.getThickness();
        return new LineBorder(color, width); }
    else if (border instanceof StrokeBorder sb)
      { var color = chooseColor((Color)sb.getPaint());
        var stroke = sb.getStroke();
        return new StrokeBorder(stroke, color); }
    else { return null; } }

// ============================================================================
/// Зміна товщини існуючої рамки

private Border updateBorderWidth (Border border, int width)
  { if (border instanceof LineBorder lb)
      { var color = lb.getLineColor();
        return new LineBorder(color, width); }
    else if (border instanceof StrokeBorder sb)
      { var color = (Color)sb.getPaint();
        var stroke = createBasicStroke(width, sb.getStroke().getDashArray());
        return new StrokeBorder(stroke, color); }
    else { return null; } }

// ============================================================================
/// Оновлення інформаційних написів

private void updateLabels (JSlider slider)
  { // Крок ліній візерунку
    if (slider == sld_linesStep)
        { updateLabelValue(lbl_linesStep, sld_linesStep.getValue()); }
    // Ширина ліній візерунку
    else if (slider == sld_linesWidth)
        { updateLabelValue(lbl_linesWidth, sld_linesWidth.getValue()); }
    // Відступ ліній візерунку
    else if (slider == sld_linesIndent)
        { updateLabelValue(lbl_linesIndent, sld_linesIndent.getValue()); }
    // Товщина рамок
    else if (slider == sld_bordersWidth)
        { updateLabelValue(lbl_bordersWidth, sld_bordersWidth.getValue()); }
    // Ширина відступу допоміжних компонентів
    else
        { updateLabelValue(lbl_extraGap, sld_extraGap.getValue()); } }

// ============================================================================
/// Оновлення конкретного інформаційного напису

private void updateLabelValue (JLabel label, int value)
  { var text = label.getText();
    text = text.substring(0, text.indexOf(": ") + 2) + value + "px";
    label.setText(text); }

// ============================================================================
/// Оновлення даних допоміжних елементів

private void updateExtraData (DocumentEvent e)
  { JTextField source = (JTextField) e.getDocument().getProperty("owner");
    if (source == fld_labelText)
      { dzp_main.setExtraLabelText(source.getText()); }
    else
      { dzp_main.setExtraButtonText(source.getText()); } }

// ============================================================================
/// Генерування випадкового штрихування

private BasicStroke getRandomBasicStroke (int width) {
  
    Random random = new Random();
    
    float[] pattern = new float[random.nextInt(2, 9)];
    if (width == -1) { width = sld_linesWidth.getValue(); }
    
    for (int z = 0; z < pattern.length; z++)
      { pattern[z] = random.nextInt(0, 32); }
    
    return createBasicStroke(width, pattern);
}

// ============================================================================
/// Генерування випадкової рамки

private Border getRandomBorder (Border oldBorder) {

    Color oldColor = null;
    var random = new Random();

    if (oldBorder instanceof LineBorder lb)
      { oldColor = lb.getLineColor(); }
    else if (oldBorder instanceof StrokeBorder sb)
      { oldColor = (Color)sb.getPaint(); }

    var width = sld_bordersWidth.getValue();

    var strokeBorder = new StrokeBorder(getRandomBasicStroke(width), oldColor);
    var lineBorder = new LineBorder(oldColor, width, false);

    return random.nextInt(7) == 0 ? lineBorder : strokeBorder;
}

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

    dzp_main = new JDropZonePanel();
    pnl_control = new JPanel();
    lbl_linesStep = new JLabel();
    sld_linesStep = new JSlider();
    lbl_linesWidth = new JLabel();
    sld_linesWidth = new JSlider();
    lbl_linesIndent = new JLabel();
    sld_linesIndent = new JSlider();
    pnl_linesVisibility = new JPanel();
    btn_fVisible = new JToggleButton();
    btn_sVisible = new JToggleButton();
    pnl_linesColors = new JPanel();
    btn_fColor = new JButton();
    btn_sColor = new JButton();
    pnl_linesStrokes = new JPanel();
    btn_fStroke = new JButton();
    btn_sStroke = new JButton();
    btn_antialias = new JToggleButton();
    sp_linesStrokes = new JSeparator();
    lbl_bordersWidth = new JLabel();
    sld_bordersWidth = new JSlider();
    pnl_bordersColors = new JPanel();
    btn_aBorderColor = new JButton();
    btn_pBorderColor = new JButton();
    pnl_bordersStrokes = new JPanel();
    btn_aBorderStroke = new JButton();
    btn_pBorderStroke = new JButton();
    sp_bordersStrokes = new JSeparator();
    lbl_extraGap = new JLabel();
    sld_extraGap = new JSlider();
    cb_extraType = new JComboBox<>();
    fld_labelText = new JTextField();
    fld_buttonText = new JTextField();
    sp_buttonText = new JSeparator();
    btn_autoEditText = new JToggleButton();
    btn_activeDaD = new JToggleButton();
    btn_simulate = new JToggleButton();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("JDropZonePanel Demo");

    dzp_main.addJDroppablePanelListener(new JDropZonePanelListener() {
      public void enableDaDChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void dragSimulateChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void enableAntialiasChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void firstLinesTypeDrawChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void secondtLinesTypeDrawChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void firstLinesTypeColorChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void secondLinesTypeColorChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void firstLinesTypeStrokeChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void secondLinesTypeStrokeChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void activeBorderChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void passiveBorderChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void linesStepChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void linesIndentChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void extraTypeChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void extraLabelTextChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void extraButtonTextChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void extraAutoEditTextChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
      public void extraIndentChange(JDropZonePanelEvent evt) {
        onPropertyChange(evt);
      }
    });

    lbl_linesStep.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_linesStep.setText("Крок ліній візерунку: 0px");

    sld_linesStep.setMaximum(99);
    sld_linesStep.setMinimum(3);
    sld_linesStep.setMinorTickSpacing(1);
    sld_linesStep.setSnapToTicks(true);
    sld_linesStep.setValue(dzp_main.getLinesStep());
    updateLabels(sld_linesStep);
    sld_linesStep.addChangeListener(this::onSliderChange);

    lbl_linesWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_linesWidth.setText("Товщина ліній візерунку: 0px");

    sld_linesWidth.setMajorTickSpacing(5);
    sld_linesWidth.setMaximum(30);
    sld_linesWidth.setMinimum(1);
    sld_linesWidth.setMinorTickSpacing(1);
    sld_linesWidth.setSnapToTicks(true);
    BasicStroke stroke = dzp_main.getFirstLinesTypeStroke();
    sld_linesWidth.setValue((int)stroke.getLineWidth());
    updateLabels(sld_linesWidth);
    sld_linesWidth.addChangeListener(this::onSliderChange);

    lbl_linesIndent.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_linesIndent.setText("Відступ ліній візерунку: 0px");

    sld_linesIndent.setMajorTickSpacing(1);
    sld_linesIndent.setMaximum(30);
    sld_linesIndent.setValue(dzp_main.getLinesIndent());
    updateLabels(sld_linesIndent);
    sld_linesIndent.addChangeListener(this::onSliderChange);

    pnl_linesVisibility.setLayout(new GridLayout(1, 0, 3, 0));

    btn_fVisible.setText("Лінії I типу ∅");
    btn_fVisible.setActionCommand("fLineVisible");
    btn_fVisible.addActionListener(this::onButtonPress);
    pnl_linesVisibility.add(btn_fVisible);

    btn_sVisible.setText("Лінії II типу ∅");
    btn_sVisible.setActionCommand("sLineVisible");
    btn_sVisible.addActionListener(this::onButtonPress);
    pnl_linesVisibility.add(btn_sVisible);

    pnl_linesColors.setLayout(new GridLayout(1, 0, 3, 0));

    btn_fColor.setText("Колір ліній I типу");
    btn_fColor.setActionCommand("fLineColor");
    btn_fColor.addActionListener(this::onButtonPress);
    pnl_linesColors.add(btn_fColor);

    btn_sColor.setText("Колір ліній II типу");
    btn_sColor.setActionCommand("sLineColor");
    btn_sColor.addActionListener(this::onButtonPress);
    pnl_linesColors.add(btn_sColor);

    pnl_linesStrokes.setLayout(new GridLayout(1, 0, 3, 0));

    btn_fStroke.setText("Штрихування ліній I типу");
    btn_fStroke.setActionCommand("fLineStroke");
    btn_fStroke.addActionListener(this::onButtonPress);
    pnl_linesStrokes.add(btn_fStroke);

    btn_sStroke.setText("Штрихування ліній II типу");
    btn_sStroke.setActionCommand("sLineStroke");
    btn_sStroke.addActionListener(this::onButtonPress);
    pnl_linesStrokes.add(btn_sStroke);

    btn_antialias.setText("Згладити лінії візерунку");
    btn_antialias.setActionCommand("antialias");
    btn_antialias.addActionListener(this::onButtonPress);

    lbl_bordersWidth.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_bordersWidth.setText("Товщина рамок: 0px");

    sld_bordersWidth.setMaximum(9);
    sld_bordersWidth.setMinimum(1);
    sld_bordersWidth.setMinorTickSpacing(1);
    sld_bordersWidth.setSnapToTicks(true);
    sld_bordersWidth.setValue(((LineBorder)dzp_main.getActiveBorder()).getThickness());
    updateLabels(sld_bordersWidth);
    sld_bordersWidth.addChangeListener(this::onSliderChange);

    pnl_bordersColors.setLayout(new GridLayout(1, 0, 3, 0));

    btn_aBorderColor.setText("Колір активної рамки");
    btn_aBorderColor.setActionCommand("aBorderColor");
    btn_aBorderColor.addActionListener(this::onButtonPress);
    pnl_bordersColors.add(btn_aBorderColor);

    btn_pBorderColor.setText("Колір неактивної рамки");
    btn_pBorderColor.setActionCommand("pBorderColor");
    btn_pBorderColor.addActionListener(this::onButtonPress);
    pnl_bordersColors.add(btn_pBorderColor);

    pnl_bordersStrokes.setLayout(new GridLayout(1, 0, 3, 0));

    btn_aBorderStroke.setText("Штрихування активної рамки");
    btn_aBorderStroke.setActionCommand("aBorderStroke");
    btn_aBorderStroke.addActionListener(this::onButtonPress);
    pnl_bordersStrokes.add(btn_aBorderStroke);

    btn_pBorderStroke.setText("Штрихування неактивної рамки");
    btn_pBorderStroke.setActionCommand("pBorderStroke");
    btn_pBorderStroke.addActionListener(this::onButtonPress);
    pnl_bordersStrokes.add(btn_pBorderStroke);

    lbl_extraGap.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_extraGap.setText("Відступ допоміжних компонентів: 0px");

    sld_extraGap.setMajorTickSpacing(5);
    sld_extraGap.setMaximum(30);
    sld_extraGap.setMinorTickSpacing(1);
    sld_extraGap.setSnapToTicks(true);
    sld_extraGap.setValue(dzp_main.getExtraIndent());
    updateLabels(sld_extraGap);
    sld_extraGap.addChangeListener(this::onSliderChange);

    cb_extraType.setModel(new DefaultComboBoxModel<>(new String[] { "Відображати інформаційну підказку і кнопку вибору файлів", "Відображати лише інформаційну підказку", "Відображати лише кнопку вибору файлів", "Не відображати інформаційну підказку та кнопку вибору файлів" }));
    cb_extraType.setActionCommand("cb_components_type");
    ((JLabel)cb_extraType.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
    cb_extraType.addActionListener(this::onExtraTypeSelect);

    fld_labelText.setHorizontalAlignment(JTextField.CENTER);
    fld_labelText.setText(dzp_main.getExtraLabelText());
    fld_labelText.setActionCommand("fld_label_text");
    Document docOne = fld_labelText.getDocument();
    docOne.putProperty("owner", fld_labelText);
    docOne.addDocumentListener(documentListener);

    fld_buttonText.setHorizontalAlignment(JTextField.CENTER);
    fld_buttonText.setText(dzp_main.getExtraButtonText());
    fld_buttonText.setActionCommand("fld_button_text");
    Document docTwo = fld_buttonText.getDocument();
    docTwo.putProperty("owner", fld_buttonText);
    docTwo.addDocumentListener(documentListener);

    btn_autoEditText.setText("Автоматичне редагування тексту кнопки вибору файлів увімкнене");
    btn_autoEditText.setActionCommand("autoEditText");
    btn_autoEditText.addActionListener(this::onButtonPress);

    btn_activeDaD.setText("Перетягування доступне");
    btn_activeDaD.setActionCommand("activeDaD");
    btn_activeDaD.addActionListener(this::onButtonPress);

    btn_simulate.setText("Імітувати перетягування");
    btn_simulate.setActionCommand("simulate");
    btn_simulate.addActionListener(this::onButtonPress);

    GroupLayout pnl_controlLayout = new GroupLayout(pnl_control);
    pnl_control.setLayout(pnl_controlLayout);
    pnl_controlLayout.setHorizontalGroup(pnl_controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(sld_linesStep, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(lbl_linesWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sld_linesWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(pnl_linesVisibility, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(pnl_linesColors, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(pnl_linesStrokes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sp_linesStrokes)
      .addComponent(lbl_bordersWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sld_bordersWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(pnl_bordersColors, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(pnl_bordersStrokes, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sp_bordersStrokes)
      .addComponent(lbl_extraGap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sld_extraGap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(cb_extraType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(btn_simulate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(fld_labelText)
      .addComponent(lbl_linesStep, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(fld_buttonText)
      .addComponent(btn_autoEditText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sp_buttonText)
      .addComponent(btn_activeDaD, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(lbl_linesIndent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(sld_linesIndent, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(btn_antialias, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    pnl_controlLayout.setVerticalGroup(pnl_controlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(pnl_controlLayout.createSequentialGroup()
        .addComponent(lbl_linesStep, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(sld_linesStep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(lbl_linesWidth)
        .addGap(3, 3, 3)
        .addComponent(sld_linesWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(lbl_linesIndent)
        .addGap(3, 3, 3)
        .addComponent(sld_linesIndent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(pnl_linesVisibility, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(pnl_linesColors, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(pnl_linesStrokes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(btn_antialias)
        .addGap(3, 3, 3)
        .addComponent(sp_linesStrokes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lbl_bordersWidth)
        .addGap(3, 3, 3)
        .addComponent(sld_bordersWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(pnl_bordersColors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(pnl_bordersStrokes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(sp_bordersStrokes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(lbl_extraGap)
        .addGap(3, 3, 3)
        .addComponent(sld_extraGap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(cb_extraType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(fld_labelText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(fld_buttonText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(sp_buttonText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(3, 3, 3)
        .addComponent(btn_autoEditText)
        .addGap(3, 3, 3)
        .addComponent(btn_activeDaD)
        .addGap(3, 3, 3)
        .addComponent(btn_simulate))
    );

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addComponent(dzp_main, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        .addGap(5, 5, 5)
        .addComponent(pnl_control, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(5, 5, 5))
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(5, 5, 5)
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addComponent(pnl_control, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(dzp_main, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGap(5, 5, 5))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void onSliderChange(ChangeEvent evt) {//GEN-FIRST:event_onSliderChange
    var source = evt.getSource();
    // Крок промальовування ліній
    if (source == sld_linesStep)
        { dzp_main.setLinesStep(sld_linesStep.getValue()); }
    // Товщина ліній візерунку
    else if (source == sld_linesWidth)
        { var width = sld_linesWidth.getValue();
          var fPattern = dzp_main.getFirstLinesTypeStroke().getDashArray();
          var sPattern = dzp_main.getSecondLinesTypeStroke().getDashArray();
          var fLinesStroke = createBasicStroke(width, fPattern);
          var sLinesStroke = createBasicStroke(width, sPattern);
          dzp_main.setFirstLinesTypeStroke(fLinesStroke);
          dzp_main.setSecondLinesTypeStroke(sLinesStroke); }
    // Відступи ліній по краях компонента
    else if (source == sld_linesIndent)
        { dzp_main.setLinesIndent(sld_linesIndent.getValue()); }
    // Товщина рамок
    else if (source == sld_bordersWidth)
        { var width = sld_bordersWidth.getValue();
          var ab = updateBorderWidth(dzp_main.getActiveBorder(),  width);
          var pb = updateBorderWidth(dzp_main.getPassiveBorder(), width);
          dzp_main.setActiveBorder(ab);
          dzp_main.setPassiveBorder(pb); }
    // Товщина рамок
    else if (source == sld_extraGap)
        { dzp_main.setExtraIndent(sld_extraGap.getValue()); }
    // Оновлення інформаційних міток
    updateLabels((JSlider) evt.getSource());
  }//GEN-LAST:event_onSliderChange

  private void onButtonPress(ActionEvent evt) {//GEN-FIRST:event_onButtonPress
    switch (evt.getActionCommand()) {
      // Видимість ліній першого типу
      case "fLineVisible" ->
        dzp_main.setFirstLinesTypeDraw(!btn_fVisible.isSelected());
      // Видимість ліній другого типу
      case "sLineVisible" ->
        dzp_main.setSecondLinesTypeDraw(!btn_sVisible.isSelected());  
      // Колір ліній першого типу
      case "fLineColor" -> 
        { var color = dzp_main.getFirstLinesTypeColor();
          dzp_main.setFirstLinesTypeColor(chooseColor(color)); } 
      // Колір ліній другого типу
      case "sLineColor" -> 
        { var color = dzp_main.getSecondLinesTypeColor();
          dzp_main.setSecondLinesTypeColor(chooseColor(color)); }
      // Штрихування ліній першого типу
      case "fLineStroke" -> 
        { dzp_main.setFirstLinesTypeStroke(getRandomBasicStroke(-1)); }
      // Штрихування ліній другого типу
      case "sLineStroke" -> 
        { dzp_main.setSecondLinesTypeStroke(getRandomBasicStroke(-1)); }
      // Колір активної рамки
      case "aBorderColor" ->
        { var border = dzp_main.getActiveBorder();
          dzp_main.setActiveBorder(updateBorderColor(border)); }
       // Колір неактивної рамки
      case "pBorderColor" ->
        { var border = dzp_main.getPassiveBorder();
          dzp_main.setPassiveBorder(updateBorderColor(border)); }
      // Штрихування активної рамки
      case "aBorderStroke" ->
        { var border = dzp_main.getActiveBorder();
          dzp_main.setActiveBorder(getRandomBorder(border)); }
      // Штрихування неактивної рамки
      case "pBorderStroke" ->
        { var border = dzp_main.getPassiveBorder();
          dzp_main.setPassiveBorder(getRandomBorder(border)); }
      // Згладжування ліній візерунку
      case "antialias" ->
        { var selected = btn_antialias.isSelected();
          var text = selected ? "Лінії візерунку згладжуються" :
                                "Згладити лінії візерунку";
          dzp_main.setAntialias(selected);
          btn_antialias.setText(text); }
      // Автоматичне редагування тексту кнопки вибору файлів
      case "autoEditText" ->
        { var selected = !btn_autoEditText.isSelected();
          var text = "Автоматичне редагування тексту кнопки вибору "
                   + "файлів " + (selected ? "увімкнене" : "вимкнене");
          dzp_main.setExtraAutoEditText(selected);
          btn_autoEditText.setText(text); }
      // Активація/деактивація перетягування
      case "activeDaD" ->
        { var selected = !btn_activeDaD.isSelected();
          dzp_main.setDaDEnable(selected);
          btn_activeDaD.setText(selected ? "Перетягування доступне" :
                                           "Перетягування недоступне"); }
      // Імітація drag-події
      case "simulate" ->
        { var selected = btn_simulate.isSelected();
          dzp_main.setDragSimulate(selected);
          btn_simulate.setText(selected ? "Перетягування імітується" :
                                          "Імітувати перетягування"); } }
  }//GEN-LAST:event_onButtonPress

  private void onExtraTypeSelect(ActionEvent evt) {//GEN-FIRST:event_onExtraTypeSelect
    switch (cb_extraType.getSelectedIndex()) {
      case 0 -> { fld_labelText.setEnabled(true);
                  fld_buttonText.setEnabled(true);
                  dzp_main.setExtraType(EXTRA_TYPE_TEXT_AND_BUTTON); }
      case 1 -> { fld_labelText.setEnabled(true);
                  fld_buttonText.setEnabled(false);
                  dzp_main.setExtraType(EXTRA_TYPE_TEXT_ONLY); }
      case 2 -> { fld_labelText.setEnabled(false);
                  fld_buttonText.setEnabled(true);
                  dzp_main.setExtraType(EXTRA_TYPE_BUTTON_ONLY); }
      case 3 -> { fld_labelText.setEnabled(false);
                  fld_buttonText.setEnabled(false);
                  dzp_main.setExtraType(EXTRA_TYPE_NONE); }
    }
  }//GEN-LAST:event_onExtraTypeSelect

  private void onPropertyChange(JDropZonePanelEvent evt) {//GEN-FIRST:event_onPropertyChange
    IO.println(evt);
  }//GEN-LAST:event_onPropertyChange

// ============================================================================
/// Прослуховувач змін текстових полів введення
 
private final DocumentListener documentListener = new DocumentListener() {

    @Override
    public void insertUpdate (DocumentEvent e) { updateExtraData(e); }
    @Override
    public void removeUpdate (DocumentEvent e) { updateExtraData(e); }
    
    @Override
    public void changedUpdate (DocumentEvent e) {} };

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JButton btn_aBorderColor;
  private JButton btn_aBorderStroke;
  private JToggleButton btn_activeDaD;
  private JToggleButton btn_antialias;
  private JToggleButton btn_autoEditText;
  private JButton btn_fColor;
  private JButton btn_fStroke;
  private JToggleButton btn_fVisible;
  private JButton btn_pBorderColor;
  private JButton btn_pBorderStroke;
  private JButton btn_sColor;
  private JButton btn_sStroke;
  private JToggleButton btn_sVisible;
  private JToggleButton btn_simulate;
  private JComboBox<String> cb_extraType;
  private JDropZonePanel dzp_main;
  private JTextField fld_buttonText;
  private JTextField fld_labelText;
  private JLabel lbl_bordersWidth;
  private JLabel lbl_extraGap;
  private JLabel lbl_linesIndent;
  private JLabel lbl_linesStep;
  private JLabel lbl_linesWidth;
  private JPanel pnl_bordersColors;
  private JPanel pnl_bordersStrokes;
  private JPanel pnl_control;
  private JPanel pnl_linesColors;
  private JPanel pnl_linesStrokes;
  private JPanel pnl_linesVisibility;
  private JSlider sld_bordersWidth;
  private JSlider sld_extraGap;
  private JSlider sld_linesIndent;
  private JSlider sld_linesStep;
  private JSlider sld_linesWidth;
  private JSeparator sp_bordersStrokes;
  private JSeparator sp_buttonText;
  private JSeparator sp_linesStrokes;
  // End of variables declaration//GEN-END:variables

// Кінець класу JDropZonePanelDemo ============================================

}
