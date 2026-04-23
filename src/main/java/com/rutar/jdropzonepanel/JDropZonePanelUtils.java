package com.rutar.jdropzonepanel;

import java.io.*;
import java.util.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

// ............................................................................
/// Допоміжні методи для роботи з JDropZonePanel
/// @author Rutar_Andriy
/// 21.04.2026

public class JDropZonePanelUtils {

// ============================================================================
/// Повернення списку перетягнутих/вибраних файлів та папок
/// @param source об'єкт типу File[] або DropTargetDropEvent
/// @param recursive якщо true - повернення підпапок та внутрішніх файлів
/// @return список перетягнутих/вибраних файлів та папок

public static File[] getFilesAndFolders (Object source, boolean recursive) {

    // Знінна для збереження списку файлів та папок
    var result = new File[0];

    // Якщо джерело null - повертаємо пустий масив
    if (source == null)
      { return null; }
    // Обробка масиву файлів
    else if (source instanceof File[] files)
      { // Обробляємо масив файлів та папок
        return recursive ? processRecursion(files) : files; }
    // Обробка об'єкту типу DropTargetDropEvent
    else if (source instanceof DropTargetDropEvent evt)
      { // Перевірка можливості отримати список перетягнутих файлів
        if (!evt.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
          { return null; }
        // Спроба отримати список перетягутих файлів
        try {
          // Оброблення DaD-події
          evt.acceptDrop(DnDConstants.ACTION_COPY);
          // Отримання перетягнутих даних
          var transferable = evt.getTransferable();
          // Отримання категорій перетягнутих даних
          var flavors = transferable.getTransferDataFlavors();
          // Обробка категорій у циклі
          for (var flavor : flavors)
            { // Якщо категорія - список файлів, то зберігаємо результат
              if (flavor.isFlavorJavaFileListType())
                { result = ((List<File>) transferable.getTransferData(flavor))
                                                     .toArray(File[]::new);
                  break; } }
          // Якщо true - рекурсивна обробка папок
          if (recursive) { result = processRecursion(result); }
          // Підтвердження обробки події
          evt.dropComplete(true);
          // Повернення результату
          return result; }
        // Якщо відбулася помилка - повертаємо null
        catch (UnsupportedFlavorException | IOException _) { return null; } }
    // Непідтримуваний тип об'єкту - кидаємо виняток
    else
      { throw new IllegalArgumentException("Illegal argument: source"); } }

// ============================================================================
/// Повернення списку перетягнутих/вибраних файлів
/// @param source об'єкт типу File[] або DropTargetDropEvent
/// @param recursive якщо true - повернення внутрішніх файлів
/// @return список перетягнутих/вибраних файлів

public static File[] getFiles (Object source, boolean recursive)
  { return filterResult(source, true, recursive); }

// ============================================================================
/// Повернення списку перетягнутих/вибраних папок
/// @param source об'єкт типу File[] або DropTargetDropEvent
/// @param recursive якщо true - повернення підпапок
/// @return список перетягнутих/вибраних папок

public static File[] getFolders (Object source, boolean recursive)
  { return filterResult(source, false, recursive); }

// ============================================================================
/// Фільтрування списку перетягнутих файлів

private static File[] filterResult (Object source,
                                    boolean filterFiles, boolean recursive) {
    
    var result = new ArrayList<File>();
    var files = getFilesAndFolders(source, recursive);
    
    for (var file : files)
      { if ((filterFiles && file.isFile()) ||
           (!filterFiles && file.isDirectory())) { result.add(file); } }
    
    return result.toArray(File[]::new);
}

// ============================================================================
/// Рекурсивна обробка масиву файлів та папок

private static File[] processRecursion (File[] files)
  { var result = new ArrayList<File>();
    for (var file : files)
      { result.addAll(getSubfiles(file)); }
    return result.toArray(File[]::new); }

// ============================================================================
/// Повернення внутрішніх файлів та підпапок

private static ArrayList<File> getSubfiles (File file) {

    if (file == null || !file.exists()) { return null; }
    
    var result = new ArrayList<File>();
    var files = file.listFiles();
    if (files == null) { return result; }
    
    for (var f : files)
      { if (f.isFile()) { result.add(f); }
        else { if (!hasSubfolders(f)) { result.add(f); }
               result.addAll(getSubfiles(f)); } }
    
    return result;
}

// ============================================================================
/// Перевірка, чи папка містить підпапки

private static boolean hasSubfolders (File dir)
  { for (var file : dir.listFiles())
      { if (file.isDirectory()) { return true; } }
    return false; }

// Кінець класу JDropZonePanelUtils ===========================================

}
