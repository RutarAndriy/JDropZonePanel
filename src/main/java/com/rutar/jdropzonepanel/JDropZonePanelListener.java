package com.rutar.jdropzonepanel;

import java.util.*;

// ............................................................................
/// Реалізація прослуховувача подій для JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public interface JDropZonePanelListener extends EventListener {

// ============================================================================
/// Зміна активності функції DragAndDrop
/// @param evt подія типу JDropZonePanelEvent

public void enableDaDChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна симулювання перетягування
/// @param evt подія типу JDropZonePanelEvent

public void dragSimulateChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна згладжування скісних ліній
/// @param evt подія типу JDropZonePanelEvent

public void enableAntialiasChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна відображення ліній першого типу
/// @param evt подія типу JDropZonePanelEvent

public void firstLinesTypeDrawChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна відображення ліній другого типу
/// @param evt подія типу JDropZonePanelEvent

public void secondtLinesTypeDrawChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна кольору ліній першого типу
/// @param evt подія типу JDropZonePanelEvent

public void firstLinesTypeColorChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна кольору ліній другого типу
/// @param evt подія типу JDropZonePanelEvent

public void secondLinesTypeColorChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна штрихування ліній першого типу
/// @param evt подія типу JDropZonePanelEvent

public void firstLinesTypeStrokeChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна штрихування ліній другого типу
/// @param evt подія типу JDropZonePanelEvent

public void secondLinesTypeStrokeChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна рамки, яка відображається коли функція DaD активна
/// @param evt подія типу JDropZonePanelEvent

public void activeBorderChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна рамки, яка відображається коли функція DaD неактивна
/// @param evt подія типу JDropZonePanelEvent

public void passiveBorderChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна кроку промальовування ліній
/// @param evt подія типу JDropZonePanelEvent

public void linesStepChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна відступу промальовування ліній по краях компонента
/// @param evt подія типу JDropZonePanelEvent

public void linesIndentChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна типу відображення допоміжних елементів компонента
/// @param evt подія типу JDropZonePanelEvent

public void extraTypeChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна тексту інформаційної мітки
/// @param evt подія типу JDropZonePanelEvent

public void extraLabelTextChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна тексту кнопки вибору файлів
/// @param evt подія типу JDropZonePanelEvent

public void extraButtonTextChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна авторедагування тексту кнопки вибору файлів
/// @param evt подія типу JDropZonePanelEvent

public void extraAutoEditTextChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна зовнішнього відступу допоміжних елементів
/// @param evt подія типу JDropZonePanelEvent

public void extraIndentChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна файлів, вибраних за допомогою кнопки вибору файлів
/// @param evt подія типу JDropZonePanelEvent

public void selectedFilesChange (JDropZonePanelEvent evt);

// Кінець класу JDropZonePanelListener ========================================

}
