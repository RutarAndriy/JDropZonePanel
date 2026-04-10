package com.rutar.jdropzonepanel;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import static java.awt.RenderingHints.*;

// ............................................................................
/// Реалізація користувацького JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public class JDropZonePanel extends JPanel {

private int lineWidth = LINE_WIDTH_NORM;                       // товщина ліній
private int mouthWidth = 120;                     // ширина усмішки, в градусах
private boolean smile = true;                                // усмішка/гримаса

private Graphics2D g2;                                        // об'єкт графіки
private int w, h, cx, cy, cw, ch, sw, sh, lw;               // допоміжні змінні
private BasicStroke basicStroke = new BasicStroke(lineWidth);   // базова кисть

// Масив прослуховувачів подій компонента
private static ArrayList <JDropZonePanelListener> listeners = null;

private final int PADDING = 2;                   // відступ по краях компонента
private final int EYES_SIZE = 4;                     // мінімальний розмір очей

// ............................................................................
// Список доступних компоненту констант

/// Ширина ліній - тонка
public static final int LINE_WIDTH_THIN =  1;
/// Ширина ліній - нормальна
public static final int LINE_WIDTH_NORM =  5;
/// Ширина ліній - широка
public static final int LINE_WIDTH_WIDE = 18;

// ============================================================================
/// Промальовування компонента

@Override
public void paintComponent (Graphics g) {

super.paintComponent(g);

g2 = (Graphics2D)g;
g2.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

// Розрахунок значень допоміжних змінних
w = getWidth()  - 1;
h = getHeight() - 1;
cx = PADDING + lineWidth/2;
cy = PADDING + lineWidth/2;
cw = w - PADDING * 2 - lineWidth;
ch = h - PADDING * 2 - lineWidth;
lw = (lineWidth - 1) / 2;
sw = cw / 2;
sh = ch / 2;

g2.setColor(getForeground());
g2.setStroke(basicStroke);

// Промальовування загального контуру
g2.drawArc(cx, cy, cw, ch, 0, 360);

// Промальовування роту
g2.drawArc(w/2 - sw/2, h/2 + (smile ? -sh/2 : sh/3), sw, sh,
                             (smile ? 270 : 90) - mouthWidth/2, mouthWidth);

// Промальовування очей
for (int z = 0; z < 2; z++)
    { g2.fillArc(w/2 + cw/8 * (z == 0 ? 1 : -1) - EYES_SIZE/2 - lw,
                 h/2 - ch/4 - EYES_SIZE - lw, EYES_SIZE + lw*2,
                                              EYES_SIZE + lw*2, 0, 360); } }

// ============================================================================
/// Повернення типу усмішки
/// @return якщо true - усмішка, false - гримаса

public boolean isSmile() { return smile; }

// ============================================================================
/// Задання типу усмішки
/// @param smile новий тип усмішки

public void setSmile (boolean smile)
    { var oldValue = this.smile;
      this.smile = smile;
      repaint();
      fireAll("smileType", oldValue, smile); }

// ============================================================================
/// Повернення ширини усмішки
/// @return ширина усмішки (в градусах)

public int getSmileWidth() { return mouthWidth; }

// ============================================================================
/// Задання ширини усмішки
/// @param mouthWidth нова ширина усмішки (в градусах)

public void setSmileWidth (int mouthWidth)
    { if (mouthWidth > 175) { mouthWidth = 175; }
      if (mouthWidth <   0) { mouthWidth = 0;   }
      var oldValue = this.mouthWidth;
      this.mouthWidth = mouthWidth;
      repaint(); 
      fireAll("smileWidth", oldValue, mouthWidth); }

// ============================================================================
/// Повернення товщини ліній компонента
/// @return товщина ліній компонента

public int getLineWidth() { return lineWidth; }

// ============================================================================
/// Задання товщини ліній компонента
/// @param lineWidth нова товщина ліній компонента

public void setLineWidth (int lineWidth)
    { if (lineWidth > 18) { lineWidth = 18; }
      if (lineWidth <  1) { lineWidth = 1;  }
      var oldValue = this.lineWidth;
      this.lineWidth = lineWidth;
      basicStroke = new BasicStroke(lineWidth);
      repaint(); 
      fireAll("lineWidth", oldValue, lineWidth); }

// ============================================================================
/// Додавання нового прослуховувача подій компонента
/// @param listener новий прослуховувач подій для додавання

public void addJDropZonePanelListener (JDropZonePanelListener listener)
    { getListeners().add(listener); }

// ============================================================================
/// Видалення існуючого прослуховувача подій компонента
/// @param listener існуючий прослуховувач подій для видалення

public void removeJDropZonePanelListener (JDropZonePanelListener listener)
    { getListeners().remove(listener); }

// ============================================================================
/// Повернення списку активних прослуховувачів

private ArrayList <JDropZonePanelListener> getListeners()
    { if (listeners == null) { listeners = new ArrayList<>(); }
      return listeners; }

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

for (var listener : getListeners())
    { switch (name)
          { case "smileType"  -> listener.smileTypeChange(event);
            case "smileWidth" -> listener.smileWidthChange(event);
            case "lineWidth"  -> listener.lineWidthChange(event); } } }

// ============================================================================
/// Перевизначення оптимального розміру компонента

@Override
public Dimension getPreferredSize() { return new Dimension(100, 100); }

// Кінець класу JDropZonePanel ================================================

}
