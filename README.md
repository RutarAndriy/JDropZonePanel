# JDropZonePanel

[![License](https://img.shields.io/github/license/RutarAndriy/JDropZonePanel?label=%D0%9B%D1%96%D1%86%D0%B5%D0%BD%D0%B7%D1%96%D1%8F&color=%23FF5555)](/LICENSE)
[![JitPack](https://jitpack.io/v/RutarAndriy/JDropZonePanel.svg)](https://jitpack.io/#RutarAndriy/JDropZonePanel)

Допоміжний [JavaBeans](https://uk.wikipedia.org/wiki/JavaBeans)-компонент. Реалізує [DragAndDrop](https://uk.wikipedia.org/wiki/Drag-and-drop)-функціонал та деякі допоміжні утиліти

## Додавання компонента в NetBeans GUI Builder

- Додаємо [залежність](https://jitpack.io/#RutarAndriy/JDropZonePanel) у свій проект
- Створюємо нову форму/діалогове вікно \
`New` > `Other` > `Swing GUI Forms` > `JFrame/JDialog`
- Додаємо JavaBeans-компонент \
`Pallete` > `Beans` > `Choose Bean`
- У полі 'Class Name' вводимо назву класу: \
`com.rutar.jdropzonepanel.JDropZonePanel`
- Перетягуємо компонент на форму/діалогове вікно
- Налаштовуємо властивості компонента через меню 'Properties'
</details>

## Використання

```java
// Створення DragAndDrop-панелі
var panel = new JDropZonePanel();

// Задання активності функції DragAndDrop
panel.setDaDEnable(true);
// Задання симулювання перетягування
panel.setDragSimulate(false);
// Задання згладжування скісних ліній
panel.setAntialias(false);
// Задання відображення ліній
panel.setFirstLinesTypeDraw(true);
panel.setSecondLinesTypeDraw(true);
// Задання кольору ліній
panel.setFirstLinesTypeColor(Color.RED);
panel.setSecondLinesTypeColor(Color.BLUE);
// Задання штрихування ліній
panel.setFirstLinesTypeStroke(JDropZonePanel.createBasicStroke(4, 10, 10, 1, 10));
panel.setSecondLinesTypeStroke(JDropZonePanel.createBasicStroke(4, 10, 10, 1, 10));
// Задання рамки, яка відображається коли функція DaD активна
panel.setActiveBorder(BorderFactory.createLineBorder(Color.RED, 3));
// Задання рамки, яка відображається коли функція DaD неактивна
panel.setPassiveBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
// Задання кроку промальовування ліній (в пікселях)
panel.setLinesStep(50);
// Задання відступу промальовування ліній по краях компонента (в пікселях)
panel.setLinesIndent(5);
// Задання типу відображення допоміжних елементів компонента, доступні наступні значення:
// EXTRA_TYPE_TEXT_AND_BUTTON, EXTRA_TYPE_TEXT_ONLY, EXTRA_TYPE_BUTTON_ONLY, EXTRA_TYPE_NONE
panel.setExtraType(JDropZonePanel.EXTRA_TYPE_TEXT_AND_BUTTON);
// Задання тексту інформаційної мітки
panel.setExtraLabelText("Перетягніть будь-які файли на цю панель");
// Задання тексту кнопки вибору файлів
panel.setExtraButtonText("Натисніть цю кнопку для вибору файлів");
// Задання авторедагування тексту кнопки вибору файлів
panel.setExtraAutoEditText(true);
// Задання зовнішнього відступу допоміжних елементів (в пікселях)
panel.setExtraIndent(3);

// Додавання прослуховувача DaD-подій
panel.addDropTargetListener(new DropTargetAdapter() {
  // Drop-подія
  public void drop(DropTargetDropEvent evt) {
    // Отримання рекурсивно списку перетягнутих файлів
    var files = JDropZonePanelUtils.getFiles(evt, true);
    // ...
  }
  // ...
});

// Додавання прослуховувача подій
panel.addJDroppablePanelListener(new JDropZonePanelListener() {
  // Зміна файлів, вибраних за допомогою кнопки вибору файлів
  public void selectedFilesChange(JDropZonePanelEvent evt) {
    // Отримання рекурсивно списку вибраних файлів
    var files = JDropZonePanelUtils.getFiles(evt.getNewValue(), true);
    // ...
  }
  // ...
});
```

<details name="screenshots">
  <summary>Скріншоти</summary>
  <p align="center">
    <img title="Скріншот №1" src="/img/scr_01.png"><br>
    <img title="Скріншот №2" src="/img/scr_02.png"><br>
    <img title="Скріншот №3" src="/img/scr_03.png">
  </p>
</details>
