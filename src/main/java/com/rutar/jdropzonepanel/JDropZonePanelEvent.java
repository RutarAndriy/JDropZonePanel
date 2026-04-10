package com.rutar.jdropzonepanel;

import java.awt.*;

// ............................................................................
/// Реалізація події зміни властивостей для JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public class JDropZonePanelEvent extends AWTEvent {

private final Object oldValue;                    // старе значення властивості
private final Object newValue;                     // нове значення властивості

/// Ідентифікатор події користувацького компонента
public static int JDROPZONEPANEL_EVENT = AWTEvent.RESERVED_ID_MAX + 333;

// ============================================================================
/// Конструктор за замовчуванням
/// @param source джерело події
/// @param oldValue старе значення властивості
/// @param newValue нове значення властивості

public JDropZonePanelEvent (Object source, Object oldValue, Object newValue)
    { super(source, JDROPZONEPANEL_EVENT);
      this.oldValue = oldValue;
      this.newValue = newValue; }

// ============================================================================
/// Повернення старого значення властивості компонента
/// @return старе значення властивості

public Object getOldValue() { return oldValue; }

// ============================================================================
/// Повернення нового значення властивості компонента
/// @return нове значення властивості

public Object getNewValue() { return newValue; }

// ============================================================================
/// Повернення текстового представлення події
/// @return текстове представлення події

@Override
public String toString()
    { return "%s[oldValue=%s; newValue=%s]".formatted(getClass()
                                           .getName(), oldValue, newValue); }

// Кінець класу JDropZonePanelBeanInfo ========================================

}
