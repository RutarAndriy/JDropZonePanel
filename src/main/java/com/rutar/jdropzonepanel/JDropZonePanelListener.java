package com.rutar.jdropzonepanel;

import java.util.*;

// ............................................................................
/// Реалізація прослуховувача подій для JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public interface JDropZonePanelListener extends EventListener {

// ============================================================================
/// Зміна типу усмішки
/// @param evt подія типу JDropZonePanelEvent

public void smileTypeChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна ширини усмішки (в градусах)
/// @param evt подія типу JDropZonePanelEvent

public void smileWidthChange (JDropZonePanelEvent evt);

// ============================================================================
/// Зміна товщини ліній
/// @param evt подія типу JDropZonePanelEvent

public void lineWidthChange (JDropZonePanelEvent evt);

// Кінець класу JDropZonePanelBeanInfo ========================================

}
