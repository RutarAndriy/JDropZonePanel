package com.rutar.jdropzonepanel;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.beans.*;
import java.awt.dnd.*;
import javax.imageio.*;

import static java.beans.BeanInfo.*;
import static com.rutar.jdropzonepanel.JDropZonePanel.*;

// ............................................................................
/// Реалізація класу-дескриптора для JavaBeans-компонента
/// @author Rutar_Andriy
/// 10.04.2026

public class JDropZonePanelBeanInfo extends SimpleBeanInfo {

/// Дескриптор JavaBeans-компонента
private static BeanDescriptor beanDescriptor;
/// Батьківський клас компонента
private final Class parentClass = JDropZonePanel.class.getSuperclass();
/// Масив перелічуваних значень
private final ArrayList<Object> valuesList = new ArrayList<>();

// ============================================================================
/// Повернення дескриптора JavaBeans-компонента
/// @return дескриптор компонента

@Override
public BeanDescriptor getBeanDescriptor()
  { if (beanDescriptor == null)
      { beanDescriptor = new BeanDescriptor(JDropZonePanel.class);
        beanDescriptor.setValue("isContainer", Boolean.FALSE); }
    return beanDescriptor; }

// ============================================================================
/// Повернення масиву властивостей доступних для JavaBeans-компонента
/// @return масив доступних властивостей

@Override
public PropertyDescriptor[] getPropertyDescriptors() {

PropertyDescriptor property;
ArrayList <PropertyDescriptor> properties = new ArrayList<>();

try {

// ............................................................................
// Додавання властивостей суперкласу до загального списку властивостей

PropertyDescriptor[] descriptors = Introspector.getBeanInfo(parentClass)
                                               .getPropertyDescriptors();

for (var descriptor : descriptors)
  { descriptor.setPreferred(false);
    properties.add(descriptor); }

// ............................................................................
// Додавання нових властивостей та задання їх пріоритетності

// new PropertyDescriptor(String a, Class b, String c, String d)
// a - назва, яка відображається у IDE та описує властивість
// b - клас, який містить дану властивість
// c - назва getter-метода
// d - назва setter-метода

// setBound()     - якщо true, генерує подію PropertyChange
// setPreferred() - якщо true, властивість попадає в список улюблених

// Доступність перетягування
property = new PropertyDescriptor("enableDaD", JDropZonePanel.class,
                                  "isDaDEnable", "setDaDEnable");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Симулювання перетягування
property = new PropertyDescriptor("dragSimulate", JDropZonePanel.class,
                                  "isDragSimulate", "setDragSimulate");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Згладжування ліній
property = new PropertyDescriptor("enableAntialias", JDropZonePanel.class,
                                  "isAntialias", "setAntialias");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Промальовування ліній першого типу
property = new PropertyDescriptor("firstLinesTypeDraw", JDropZonePanel.class,
                                  "isFirstLinesTypeDraw",
                                  "setFirstLinesTypeDraw");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Промальовування ліній другого типу
property = new PropertyDescriptor("secondLinesTypeDraw", JDropZonePanel.class,
                                  "isSecondLinesTypeDraw",
                                  "setSecondLinesTypeDraw");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Колір ліній першого типу
property = new PropertyDescriptor("firstLinesTypeColor", JDropZonePanel.class,
                                  "getFirstLinesTypeColor",
                                  "setFirstLinesTypeColor");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Колір ліній другого типу
property = new PropertyDescriptor("secondLinesTypeColor", JDropZonePanel.class,
                                  "getSecondLinesTypeColor",
                                  "setSecondLinesTypeColor");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Штрихування ліній першого типу
property = new PropertyDescriptor("firstLinesTypeStroke", JDropZonePanel.class,
                                  "getFirstLinesTypeStroke",
                                  "setFirstLinesTypeStroke");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Штрихування ліній другого типу
property = new PropertyDescriptor("secondLinesTypeStroke", JDropZonePanel.class,
                                  "getSecondLinesTypeStroke",
                                  "setSecondLinesTypeStroke");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Активна рамка
property = new PropertyDescriptor("borderActive", JDropZonePanel.class,
                                  "getActiveBorder", "setActiveBorder");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Неактивна рамка
property = new PropertyDescriptor("borderPassive", JDropZonePanel.class,
                                  "getPassiveBorder", "setPassiveBorder");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Крок промальовування ліній
property = new PropertyDescriptor("linesStep", JDropZonePanel.class,
                                  "getLinesStep", "setLinesStep");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Відступи ліній по краях компонента
property = new PropertyDescriptor("linesIndent", JDropZonePanel.class,
                                  "getLinesIndent", "setLinesIndent");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Тип допоміжниї елементів
property = new PropertyDescriptor("extraType", JDropZonePanel.class,
                                  "getExtraType", "setExtraType");
property.setBound(true);
property.setPreferred(true);
addToValuesList("EXTRA_TYPE_TEXT_AND_BUTTON", EXTRA_TYPE_TEXT_AND_BUTTON,
                "JDropZonePanel.EXTRA_TYPE_TEXT_AND_BUTTON");
addToValuesList("EXTRA_TYPE_TEXT_ONLY",       EXTRA_TYPE_TEXT_ONLY,
                "JDropZonePanel.EXTRA_TYPE_TEXT_ONLY");
addToValuesList("EXTRA_TYPE_BUTTON_ONLY",     EXTRA_TYPE_BUTTON_ONLY,
                "JDropZonePanel.EXTRA_TYPE_BUTTON_ONLY");
addToValuesList("EXTRA_TYPE_NONE",            EXTRA_TYPE_NONE,
                "JDropZonePanel.EXTRA_TYPE_NONE");
property.setValue("enumerationValues", getValuesList());
properties.add(property);

// Текст інформаційної мікти
property = new PropertyDescriptor("extraLabelText", JDropZonePanel.class,
                                  "getExtraLabelText", "setExtraLabelText");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Текст кнопки вибору файлів
property = new PropertyDescriptor("extraButtonText", JDropZonePanel.class,
                                  "getExtraButtonText", "setExtraButtonText");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Авторедагування тексту кнопки вибору файлів
property = new PropertyDescriptor("extraAutoEditText", JDropZonePanel.class,
                                  "getExtraAutoEditText",
                                  "setExtraAutoEditText");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Зовнішні відступи допоміжних елементів
property = new PropertyDescriptor("extraIndent", JDropZonePanel.class,
                                  "getExtraIndent", "setExtraIndent");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

}

catch (IntrospectionException _) { }

return properties.toArray(PropertyDescriptor[]::new);

}

// ============================================================================
/// Повернення масиву прослуховувачів доступних для JavaBeans-компонента
/// @return масив доступних прослуховувачів

@Override
public EventSetDescriptor[] getEventSetDescriptors() {

String[] methods;
EventSetDescriptor eventSet;
ArrayList <EventSetDescriptor> eventSets = new ArrayList<>();

try {

// ............................................................................
// Додавання прослуховувачів суперкласу до загального списку прослуховувачів

EventSetDescriptor[] descriptors = Introspector.getBeanInfo(parentClass)
                                               .getEventSetDescriptors();
eventSets.addAll(Arrays.asList(descriptors));

// ............................................................................
// Додавання нових прослуховувачів та задання їх пріоритетності

// new EventSetDescriptor(Class a, String b, Class c, String[] d,
//                        String e, String f)
// a - клас, який відправляє прослуховувачу
// b - назва, яка відображається у IDE та описує прослуховувача
// c - клас або інтерфейс, який містить методи прослуховувача
// d - масив рядків, який містить назви методів для відобреження в IDE
// e - назва метода, який додає прослуховувача
// f - назва метода, який видаляє прослуховувача

// JDropZonePanelListener
methods = new String[] { "enableDaDChange",
                         "dragSimulateChange",
                         "enableAntialiasChange",
                         "firstLinesTypeDrawChange",
                         "secondtLinesTypeDrawChange",
                         "firstLinesTypeColorChange",
                         "secondLinesTypeColorChange",
                         "firstLinesTypeStrokeChange",
                         "secondLinesTypeStrokeChange",
                         "activeBorderChange",
                         "passiveBorderChange",
                         "linesStepChange",
                         "linesIndentChange",
                         "extraTypeChange",
                         "extraLabelTextChange",
                         "extraButtonTextChange",
                         "extraAutoEditTextChange",
                         "extraIndentChange",
                         "selectedFilesChange" };

eventSet = new EventSetDescriptor(JDropZonePanel.class,
                                  "JDroppablePanelListener",
                                  JDropZonePanelListener.class, methods,
                                  "addJDroppablePanelListener",
                                  "removeJDroppablePanelListener");

eventSets.add(eventSet);

// DropTargetListener
methods = new String[] { "dropActionChanged",
                         "dragEnter",
                         "dragExit",
                         "dragOver",
                         "drop" };

eventSet = new EventSetDescriptor(JDropZonePanel.class,
                                  "DropTargetListener",
                                  DropTargetListener.class, methods,
                                  "addDropTargetListener",
                                  "removeDropTargetListener");

eventSets.add(eventSet);

}

catch (IntrospectionException _) { }

return eventSets.toArray(EventSetDescriptor[]::new);

}

// ============================================================================
/// Повернення об'єкту зображення типу Image
/// @param iconType тип зображення - константа класу BeanInfo
/// @return об'єкт типу Image

@Override
public Image getIcon (int iconType) {

return switch (iconType) {

  case ICON_MONO_16x16, ICON_COLOR_16x16 -> loadIcon(16);
  case ICON_MONO_32x32, ICON_COLOR_32x32 -> loadIcon(32);

  default -> null;

};
}

// ============================================================================
/// Повернення об'єкту зображення заданого розміру
/// @param size розмір зображення
/// @return об'єкт типу Image

private Image loadIcon (int size) {

    String res = "icons/icon_%d.png".formatted(size);

    try (InputStream stream = getClass().getResourceAsStream(res))
        { return ImageIO.read(stream); }
    catch (IOException _)
        { return null; } }

// ============================================================================
/// Додавання елемента до списку перелічених значень
/// @param name назва елемента, яка відображатиметься в IDE
/// @param value значення елемента, IDE використовує його для порівняння
/// @param code Java-код, який IDE буде вставляти у setter-метод

private void addToValuesList (String name, Object value, String code)
  { valuesList.add(name);
    valuesList.add(value);
    valuesList.add(code); }

// ============================================================================
/// Повернення масиву перелічених значень та очищення списку
/// @return масив перелічених значень

private Object[] getValuesList()
  { Object[] result = valuesList.toArray();
    valuesList.clear();
    return result; }

// Кінець класу JDropZonePanelBeanInfo ========================================

}
