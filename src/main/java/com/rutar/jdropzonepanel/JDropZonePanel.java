package com.rutar.jdropzonepanel;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.border.*;

import static java.awt.BasicStroke.*;
import static java.awt.RenderingHints.*;
import static javax.swing.JFileChooser.*;

// ............................................................................
/// Реалізація панелі, яка підтримує функцію перетягування даних
/// @author Rutar_Andriy
/// 10.04.2026

public class JDropZonePanel extends JPanel {

private int linesStep = 50;                               // крок скісних ліній
private int linesIndent = 5;        // відступи скісних ліній по краях елемента

private boolean firstLineDraw = true;         // малювання першої скісної лінії
private Color   firstLineColor = new Color(0x6666ff);            // колір лінії
private BasicStroke firstLineStroke = createBasicStroke(4, 10, 10, 1, 10);

private boolean secondLineDraw = true;        // малювання другої скісної лінії
private Color   secondLineColor = new Color(0x6666ff);           // колір лінії
private BasicStroke secondLineStroke = createBasicStroke(4, 10, 10, 1, 10);

// Рамка, коли режим перетягування активний
private Border activeBorder = new LineBorder(new Color(0x6666ff), 3);
// Рамка, коли режим перетягування неактивний
private Border passiveBorder = new LineBorder(Color.GRAY, 3);

private boolean dragActive = false;        // якщо true - перетягування активне
private boolean dragSimulate = false;  // якщо true - перетягування симулюється
private boolean useAntialias = false; // якщо true - згладжування ліній активне
private boolean autoEditText = true;     // якщо true - автозміна тексту кнопки

private DropTarget dropTarget;               // об'єкт для оброблення DaD-подій
private ArrayList <JDropZonePanelListener> listeners = null;    // просл. подій
private ArrayList <DropTargetListener> DaDListeners = null; // просл. DaD-подій

private int extraIndent = 3;                    // відступ допоміжних елементів
private int extraType = EXTRA_TYPE_TEXT_AND_BUTTON; // тип допоміжних елементів

// Текст інформаційної мітки
private String extraLabelText  = "Перетягніть будь-які файли на цю панель";
// Текст кнопки вибору файлів
private String extraButtonText = "Натисніть цю кнопку для вибору файлів";

// Діалогове вікно вибору файлів
private final JFileChooser fileChooser = new JFileChooser();

// ............................................................................

/// Допоміжні елементи: текст і кнопка
public static final int EXTRA_TYPE_TEXT_AND_BUTTON = 0;
/// Допоміжні елементи: лише текст
public static final int EXTRA_TYPE_TEXT_ONLY = 1;
/// Допоміжні елементи: лише кнопка
public static final int EXTRA_TYPE_BUTTON_ONLY = 2;
/// Допоміжні елементи відсутні
public static final int EXTRA_TYPE_NONE = 3;

// ============================================================================
/// Конструктор за замовчуванням

public JDropZonePanel()
  { initComponents();
    fileChooser.setMultiSelectionEnabled(true); }

// ============================================================================
/// Промальовування компонента

@Override
protected void paintComponent (Graphics g) {

    super.paintComponent(g);
        
    if (dragActive == false && dragSimulate == false) { return; }

    // ........................................................................

    int W = getWidth()-1;
    int H = getHeight()-1;
    int Q = W > H ? W : H;
    
    Graphics2D g2 = (Graphics2D) g.create();
    
    g2.setClip(linesIndent, linesIndent, W-linesIndent*2+1, H-linesIndent*2+1);
    g2.setRenderingHint(KEY_ANTIALIASING, useAntialias ? VALUE_ANTIALIAS_ON :
                                                         VALUE_ANTIALIAS_OFF);
    // ........................................................................

    if (firstLineDraw)
        { g2.setColor(getFirstLinesTypeColor());
          g2.setStroke(getFirstLinesTypeStroke());
          for (int z = 0; z*linesStep < Q * 2; z++)
              { g2.drawLine(0, z*linesStep, z*linesStep, 0); } }
    
    if (secondLineDraw)
        { g2.setColor(getSecondLinesTypeColor());
          g2.setStroke(getSecondLinesTypeStroke());
          for (int z = 1; z*linesStep < Q; z++)
              { g2.drawLine(0, z*linesStep, Q-z*linesStep, Q); }
          for (int z = 0; z*linesStep > -Q; z--)
              { g2.drawLine(0, z*linesStep, Q-z*linesStep, Q); } }

    // ........................................................................
        
    g2.dispose();
}

// ============================================================================
/// Повернення активності функції DragAndDrop
/// @return якщо true - функція DragAndDrop активна

public boolean isDaDEnable() { return dropTarget.isActive(); }

// ============================================================================
/// Задання активності функції DragAndDrop
/// @param active якщо true - активувати функцію DragAndDrop

public void setDaDEnable (boolean active)
  { var oldValue = isDaDEnable();
    dropTarget.setActive(active);
    fireAll("enableDaD", oldValue, active); }

// ============================================================================
/// Повернення симулювання перетягування
/// @return якщо true - перетягування симулюється

public boolean isDragSimulate() { return dragSimulate; }

// ============================================================================
/// Задання симулювання перетягування
/// @param dragSimulate якщо true - симулювати перетягування

public void setDragSimulate (boolean dragSimulate)
  { var oldValue = this.dragSimulate;
    this.dragSimulate = dragSimulate;
    setBorder(dragSimulate ? activeBorder :
                             dragActive ? activeBorder : passiveBorder);
    fireAll("dragSimulate", oldValue, dragSimulate); }

// ============================================================================
/// Повернення згладжування скісних ліній
/// @return якщо true - скісні лінії згладжуються

public boolean isAntialias() { return useAntialias; }

// ============================================================================
/// Задання згладжування скісних ліній
/// @param useAntialias якщо true - скісні лінії будуть згладжуватися

public void setAntialias (boolean useAntialias)
  { var oldValue = this.useAntialias;
    this.useAntialias = useAntialias;
    repaint();
    fireAll("enableAntialias", oldValue, useAntialias); }

// ============================================================================
/// Повернення відображення ліній першого типу
/// @return якщо true - лінії першого типу відображаються

public boolean isFirstLinesTypeDraw() { return firstLineDraw; }

// ============================================================================
/// Задання відображення ліній першого типу
/// @param draw якщо true - відображати лінії першого типу

public void setFirstLinesTypeDraw (boolean draw)
  { var oldValue = this.firstLineDraw;
    this.firstLineDraw = draw;
    repaint();
    fireAll("firstLinesTypeDraw", oldValue, draw); }

// ============================================================================
/// Повернення відображення ліній другого типу
/// @return якщо true - лінії другого типу відображаються

public boolean isSecondLinesTypeDraw() { return secondLineDraw; }

// ============================================================================
/// Задання відображення ліній другого типу
/// @param draw якщо true - відображати лінії другого типу

public void setSecondLinesTypeDraw (boolean draw)
  { var oldValue = this.secondLineDraw;
    this.secondLineDraw = draw;
    repaint();
    fireAll("secondLinesTypeDraw", oldValue, draw); }

// ============================================================================
/// Повернення кольору ліній першого типу
/// @return колір ліній першого типу

public Color getFirstLinesTypeColor() { return firstLineColor;  }

// ============================================================================
/// Задання кольору ліній першого типу
/// @param color колір ліній першого типу

public void setFirstLinesTypeColor (Color color)
  { var oldValue = this.firstLineColor;
    this.firstLineColor = color;
    repaint();
    fireAll("firstLinesTypeColor", oldValue, color); }

// ============================================================================
/// Повернення кольору ліній другого типу
/// @return колір ліній другого типу

public Color getSecondLinesTypeColor() { return secondLineColor; }

// ============================================================================
/// Задання кольору ліній другого типу
/// @param color колір ліній другого типу

public void setSecondLinesTypeColor (Color color)
  { var oldValue = this.secondLineColor;
    this.secondLineColor = color;
    repaint();
    fireAll("secondLinesTypeColor", oldValue, color); }

// ============================================================================
/// Повернення штрихування ліній першого типу
/// @return штрихування ліній першого типу

public BasicStroke getFirstLinesTypeStroke() { return firstLineStroke;  }

// ============================================================================
/// Задання штрихування ліній першого типу
/// @param stroke штрихування ліній першого типу

public void setFirstLinesTypeStroke (BasicStroke stroke)
  { var oldValue = this.firstLineStroke;
    this.firstLineStroke = stroke;
    repaint();
    fireAll("firstLinesTypeStroke", oldValue, stroke); }

// ============================================================================
/// Повернення штрихування ліній другого типу
/// @return штрихування ліній другого типу

public BasicStroke getSecondLinesTypeStroke() { return secondLineStroke; }

// ============================================================================
/// Задання штрихування ліній другого типу
/// @param stroke штрихування ліній другого типу

public void setSecondLinesTypeStroke (BasicStroke stroke)
  { var oldValue = this.secondLineStroke;
    this.secondLineStroke = stroke;
    repaint();
    fireAll("secondLinesTypeStroke", oldValue, stroke); }

// ============================================================================
/// Повернення рамки, яка відображається коли функція DaD активна
/// @return активна DaD-рамка

public Border getActiveBorder()  { return activeBorder;  }

// ============================================================================
/// Задання рамки, яка відображається коли функція DaD активна
/// @param border активна DaD-рамка

public void setActiveBorder (Border border)
  { var oldValue = this.activeBorder;
    this.activeBorder = border;
    setBorder(dragActive || dragSimulate ? activeBorder : passiveBorder);
    repaint();
    fireAll("activeBorder", oldValue, border); }

// ============================================================================
/// Повернення рамки, яка відображається коли функція DaD неактивна
/// @return неактивна DaD-рамка

public Border getPassiveBorder() { return passiveBorder; }

// ============================================================================
/// Задання рамки, яка відображається коли функція DaD неактивна
/// @param border неактивна DaD-рамка

public void setPassiveBorder (Border border)
  { var oldValue = this.passiveBorder;
    this.passiveBorder = border;
    setBorder(dragActive || dragSimulate ? activeBorder : passiveBorder);
    repaint();
    fireAll("passiveBorder", oldValue, border); }

// ============================================================================
/// Повернення кроку промальовування ліній (в пікселях)
/// @return крок промальовування ліній (в пікселях)

public int getLinesStep() { return linesStep; }

// ============================================================================
/// Задання кроку промальовування ліній (в пікселях)
/// @param linesStep крок промальовування ліній (в пікселях)

public void setLinesStep (int linesStep)
  { if (linesStep > 999) { linesStep = 999; }
    if (linesStep < 3)   { linesStep = 3;   }
    var oldValue = this.linesStep;
    this.linesStep = linesStep;
    repaint();
    fireAll("lineStep", oldValue, linesStep); }

// ============================================================================
/// Повернення відступу промальовування ліній по краях компонента (в пікселях)
/// @return відступ промальовування ліній по краях компонента (в пікселях)

public int getLinesIndent() { return linesIndent; }

// ============================================================================
/// Задання відступу промальовування ліній по краях компонента (в пікселях)
/// @param linesIndent відступ промальовування ліній по краях
/// компонента (в пікселях)

public void setLinesIndent (int linesIndent)
  { if (linesIndent > 99) { linesIndent = 99; }
    if (linesIndent < 0)  { linesIndent = 0;  }
    var oldValue = this.linesIndent;
    this.linesIndent = linesIndent;    
    repaint();
    fireAll("lineIndent", oldValue, linesIndent); }

// ============================================================================
/// Повернення типу відображення допоміжних елементів компонента
/// @return {@link EXTRA_TYPE_TEXT_AND_BUTTON}, {@link #EXTRA_TYPE_TEXT_ONLY}, 
/// {@link #EXTRA_TYPE_BUTTON_ONLY} або {@link #EXTRA_TYPE_NONE}

public int getExtraType() { return extraType; }

// ============================================================================
/// Задання типу відображення допоміжних елементів компонента
/// @param extraType {@link EXTRA_TYPE_TEXT_AND_BUTTON},
/// {@link EXTRA_TYPE_TEXT_ONLY}, {@link EXTRA_TYPE_BUTTON_ONLY}
/// або {@link EXTRA_TYPE_NONE}

public void setExtraType (int extraType) {
    
    var oldValue = this.extraType;
    if (extraType != EXTRA_TYPE_TEXT_AND_BUTTON &&
        extraType != EXTRA_TYPE_TEXT_ONLY &&
        extraType != EXTRA_TYPE_BUTTON_ONLY &&
        extraType != EXTRA_TYPE_NONE)
      { extraType  = EXTRA_TYPE_TEXT_AND_BUTTON; }
    this.extraType = extraType;

    switch (extraType) {
      case EXTRA_TYPE_TEXT_AND_BUTTON
        -> { lbl_info.setVisible(true);
             btn_select.setVisible(true);
             pnl_comp.setVisible(true); }
      case EXTRA_TYPE_TEXT_ONLY
        -> { lbl_info.setVisible(true);
             btn_select.setVisible(false);
             pnl_comp.setVisible(true); }
      case EXTRA_TYPE_BUTTON_ONLY
        -> { lbl_info.setVisible(false);
             btn_select.setVisible(true);
             pnl_comp.setVisible(true); }
      default
        -> { lbl_info.setVisible(false);
             btn_select.setVisible(false);
             pnl_comp.setVisible(false); } }
    
    vflr_gap.setVisible(extraType == EXTRA_TYPE_TEXT_AND_BUTTON);
    setExtraButtonText(getExtraButtonText());
    pnl_comp.revalidate();
    repaint();
    fireAll("extraType", oldValue, extraType);
}

// ============================================================================
/// Повернення тексту інформаційної мітки
/// @return текст інформаційної мітки

public String getExtraLabelText() { return extraLabelText; }

// ============================================================================
/// Задання тексту інформаційної мітки
/// @param labelText текст інформаційної мітки

public void setExtraLabelText (String labelText)
  { if (labelText == null || labelText.isBlank())
        { labelText = new JDropZonePanel().getExtraLabelText(); }
    var oldValue = this.extraLabelText;
    this.extraLabelText = labelText;
    lbl_info.setText(labelText);
    fireAll("extraLabelText", oldValue, labelText); }

// ============================================================================
/// Повернення тексту кнопки вибору файлів
/// @return текст кнопки вибору файлів

public String getExtraButtonText() { return extraButtonText; }

// ============================================================================
/// Задання тексту кнопки вибору файлів
/// @param buttonText текст кнопки вибору файлів

public void setExtraButtonText (String buttonText)
  { if (buttonText == null || buttonText.isBlank())
        { buttonText = new JDropZonePanel().getExtraButtonText(); }
    var newText = buttonText;
    var oldValue = extraButtonText;
    extraButtonText = buttonText;
    if (lbl_info.isVisible() && autoEditText)
        { newText = "або " + buttonText.substring(0, 1).toLowerCase()
                           + buttonText.substring(1); }
    btn_select.setText(newText);
    btn_select.revalidate();
    repaint();
    fireAll("extraButtonText", oldValue, buttonText); }

// ============================================================================
/// Повернення авторедагування тексту кнопки вибору файлів
/// @return якщо true - текст кнопки автоматично редагується

public boolean getExtraAutoEditText() { return autoEditText; }

// ============================================================================
/// Задання авторедагування тексту кнопки вибору файлів
/// @param autoEditText якщо true - текст кнопки буде автоматично редагуватися

public void setExtraAutoEditText (boolean autoEditText)
  { var oldValue = this.autoEditText;
    this.autoEditText = autoEditText;
    setExtraButtonText(getExtraButtonText());
    fireAll("extraButtonText", oldValue, autoEditText); }

// ============================================================================
/// Повернення зовнішнього відступу допоміжних елементів (в пікселях)
/// @return зовнішній відступ допоміжних елементів (в пікселях)

public int getExtraIndent() { return extraIndent; }

// ============================================================================
/// Задання зовнішнього відступу допоміжних елементів (в пікселях)
/// @param extraIndent зовнішній відступ допоміжних елементів (в пікселях)

public void setExtraIndent (int extraIndent)
  { if (extraIndent > 30) { extraIndent = 30; }
    if (extraIndent < 0)  { extraIndent = 0;  } 
    var oldValue = this.extraIndent;
    this.extraIndent = extraIndent;  
    pnl_comp.setBorder(BorderFactory
            .createEmptyBorder(extraIndent, extraIndent,
                               extraIndent, extraIndent));
    pnl_comp.revalidate();
    repaint();
    fireAll("extraIndent", oldValue, linesIndent); }

// ============================================================================
/// Додавання нового прослуховувача подій компонента
/// @param listener новий прослуховувач подій для додавання

public void addJDroppablePanelListener (JDropZonePanelListener listener)
  { getListeners().add(listener); }

// ============================================================================
/// Видалення існуючого прослуховувача подій компонента
/// @param listener існуючий прослуховувач подій для видалення

public void removeJDroppablePanelListener (JDropZonePanelListener listener)
  { getListeners().remove(listener); }

// ============================================================================
/// Повернення списку активних прослуховувачів подій компонента

private ArrayList <JDropZonePanelListener> getListeners()
  { if (listeners == null) { listeners = new ArrayList<>(); }
    return listeners; }

// ============================================================================
/// Додавання нового прослуховувача DaD-подій компонента
/// @param listener новий прослуховувач DaD-подій для додавання

public void addDropTargetListener (DropTargetListener listener)
  { getDaDListeners().add(listener); }

// ============================================================================
/// Видалення існуючого прослуховувача DaD-подій компонента
/// @param listener існуючий прослуховувач DaD-подій для видалення

public void removeDropTargetListener (DropTargetListener listener)
  { getDaDListeners().remove(listener); }

// ============================================================================
/// Повернення списку активних DaD-прослуховувачів

private ArrayList <DropTargetListener> getDaDListeners()
  { if (DaDListeners == null) { DaDListeners = new ArrayList<>(); }
    return DaDListeners; }

// ============================================================================
/// Інформування прослуховувачів про зміну властивостей компонента

private void fireAll (String name, Object oldValue, Object newValue)
  { if (oldValue.equals(newValue)) { return; }
    fireEvent          (name, oldValue, newValue);
    firePropertyChange (name, oldValue, newValue); }

// ============================================================================
/// Інформування прослуховувачів про зміну конкретної властивості компонента

private void fireEvent (String name, Object oldValue, Object newValue) {

var event = new JDropZonePanelEvent(this, oldValue, newValue);

for (var lst : getListeners()) {
  switch (name) {
    case "enableDaD"             -> lst.enableDaDChange(event);
    case "dragSimulate"          -> lst.dragSimulateChange(event);
    case "enableAntialias"       -> lst.enableAntialiasChange(event);
    case "firstLinesTypeDraw"    -> lst.firstLinesTypeDrawChange(event);
    case "secondLinesTypeDraw"   -> lst.secondtLinesTypeDrawChange(event);
    case "firstLinesTypeColor"   -> lst.firstLinesTypeColorChange(event);
    case "secondLinesTypeColor"  -> lst.secondLinesTypeColorChange(event);
    case "firstLinesTypeStroke"  -> lst.firstLinesTypeStrokeChange(event);
    case "secondLinesTypeStroke" -> lst.secondLinesTypeStrokeChange(event);
    case "activeBorder"          -> lst.activeBorderChange(event);
    case "passiveBorder"         -> lst.passiveBorderChange(event);
    case "lineStep"              -> lst.linesStepChange(event);
    case "lineIndent"            -> lst.linesIndentChange(event);
    case "extraType"             -> lst.extraTypeChange(event);
    case "extraLabelText"        -> lst.extraLabelTextChange(event);
    case "extraButtonText"       -> lst.extraButtonTextChange(event);
    case "extraAutoEditText"     -> lst.extraAutoEditTextChange(event);
    case "extraIndent"           -> lst.extraIndentChange(event);
    case "selectedFiles"         -> lst.selectedFilesChange(event); } } }

// ============================================================================
/// Інформування прослуховувачів про зміну конкретної властивості компонента

private void fireDaDEvent (String type, Object event) {

for (var lst : getDaDListeners()) {
  switch (type) {
    case "dragEnter" -> lst.dragEnter((DropTargetDragEvent) event);
    case "dragExit"  -> lst.dragExit((DropTargetEvent) event);
    case "dragOver"  -> lst.dragOver((DropTargetDragEvent) event);
    case "drop"      -> lst.drop((DropTargetDropEvent) event); } } }

// ============================================================================
/// Створення нового об'єкту штрихування
/// @param width ширина штрихування
/// @param pattern паттерн штрихування
/// @return новий об'єкт штрихування

public static BasicStroke createBasicStroke (float width, float ... pattern)
  { return new BasicStroke(width, CAP_SQUARE, JOIN_MITER, 1f, pattern, 0f); }

// ============================================================================
/// Цей метод викликається з конструктора для ініціалізації форми.
/// УВАГА: НЕ змінюйте цей код. Вміст цього методу завжди 
/// перезапишеться редактором форм

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    pnl_comp = new JPanel();
    lbl_info = new JLabel();
    vflr_gap = new Box.Filler(new Dimension(0, 15), new Dimension(0, 15), new Dimension(32767, 15));
    btn_select = new JButton();

    setBorder(passiveBorder);

    pnl_comp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    lbl_info.setHorizontalAlignment(SwingConstants.CENTER);
    lbl_info.setText(extraLabelText);

    btn_select.setText(extraButtonText);
    btn_select.addActionListener(this::onSelectFiles);

    GroupLayout pnl_compLayout = new GroupLayout(pnl_comp);
    pnl_comp.setLayout(pnl_compLayout);
    pnl_compLayout.setHorizontalGroup(pnl_compLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addComponent(lbl_info, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(vflr_gap, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(btn_select, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    pnl_compLayout.setVerticalGroup(pnl_compLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(pnl_compLayout.createSequentialGroup()
        .addComponent(lbl_info)
        .addGap(0, 0, 0)
        .addComponent(vflr_gap, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, 0)
        .addComponent(btn_select, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(pnl_comp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(0, 0, Short.MAX_VALUE)
        .addComponent(pnl_comp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
    );

    setExtraButtonText(getExtraButtonText());
    dropTarget = new DropTarget(this, dropTargetListener);
  }// </editor-fold>//GEN-END:initComponents

// ============================================================================
/// Прослуховування кнопки вибору файлів

  private void onSelectFiles(ActionEvent evt) {//GEN-FIRST:event_onSelectFiles

    var oldValue = fileChooser.getSelectedFiles();

    var result = fileChooser.showOpenDialog(this);
    if (result != APPROVE_OPTION) { return; }

    var selectedFiles = fileChooser.getSelectedFiles();
    fireAll("selectedFiles", oldValue, selectedFiles);
  }//GEN-LAST:event_onSelectFiles

// ===========================================================================\
/// Прослуховувач DaD-подій

private final DropTargetListener dropTargetListener = new DropTargetAdapter() {

    @Override
    public void dragEnter (DropTargetDragEvent e)
      { if (!dragSimulate) { dragActive = true;
                             setBorder(activeBorder); }
        fireDaDEvent("dragEnter", e); }

    @Override
    public void dragExit (DropTargetEvent e)
      { if (!dragSimulate) { dragActive = false;
                             setBorder(passiveBorder); }
        fireDaDEvent("dragExit", e); }

    @Override
    public void dragOver (DropTargetDragEvent e)
      { fireDaDEvent("dragOver", e); }

    @Override
    public void drop (DropTargetDropEvent e)
      { if (!dragSimulate) { dragActive = false;
                             setBorder(passiveBorder); }
        fireDaDEvent("drop", e); }
};

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JButton btn_select;
  private JLabel lbl_info;
  private JPanel pnl_comp;
  private Box.Filler vflr_gap;
  // End of variables declaration//GEN-END:variables

// Кінець класу JDropZonePanel ================================================

}
