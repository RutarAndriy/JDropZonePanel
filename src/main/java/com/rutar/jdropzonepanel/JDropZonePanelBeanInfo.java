package com.rutar.jdropzonepanel;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.beans.*;
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
// properties.addAll(Arrays.asList(descriptors));

for (var descriptor : descriptors) {
    descriptor.setPreferred(false);
    properties.add(descriptor);
}

// ............................................................................
// Додавання нових властивостей та задання їх пріоритетності

// new PropertyDescriptor(String a, Class b, String c, String d)
// a - назва, яка відображається у IDE та описує властивість
// b - клас, який містить дану властивість
// c - назва getter-метода
// d - назва setter-метода

// setBound()     - якщо true, генерує подію PropertyChange
// setPreferred() - якщо true, властивість попадає в список улюблених

// Тип усмішки
property = new PropertyDescriptor("smile", JDropZonePanel.class,
                                  "isSmile", "setSmile");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Ширина усмішки
property = new PropertyDescriptor("smileWidth", JDropZonePanel.class,
                                  "getSmileWidth", "setSmileWidth");
property.setBound(true);
property.setPreferred(true);
properties.add(property);

// Товщина ліній
property = new PropertyDescriptor("lineWidth", JDropZonePanel.class,
                                  "getLineWidth", "setLineWidth");
property.setBound(true);
property.setPreferred(true);
addToValuesList("THIN",   LINE_WIDTH_THIN, "JBiba.LINE_WIDTH_THIN");
addToValuesList("NORMAL", LINE_WIDTH_NORM, "JBiba.LINE_WIDTH_NORM");
addToValuesList("WIDE",   LINE_WIDTH_WIDE, "JBiba.LINE_WIDTH_WIDE");
property.setValue("enumerationValues", getValuesList());
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

methods = new String[] { "smileTypeChange",
                         "smileWidthChange",
                         "lineWidthChange" };
eventSet = new EventSetDescriptor(JDropZonePanel.class,
                                  "JDropZonePanelListener",
                                  JDropZonePanelListener.class, methods,
                                  "addJDropZonePanelListener",
                                  "removeJDropZonePanelListener");
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
