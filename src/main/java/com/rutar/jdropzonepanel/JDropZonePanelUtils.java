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
/// Повернення списку перетягнутих файлів та папок
/// @param evt подія типу DropTargetDropEvent
/// @return список перетягнутих файлів та папок

public static File[] getDroppableFilesAndFolders (DropTargetDropEvent evt)
  { return getDroppableFilesAndFolders(evt, false); }

// ============================================================================
/// Повернення списку перетягнутих файлів та папок
/// @param evt подія типу DropTargetDropEvent
/// @param recursive якщо true - повернення підпапок та внутрішніх файлів
/// @return список перетягнутих файлів та папок

public static File[] getDroppableFilesAndFolders (DropTargetDropEvent evt,
                                                  boolean recursive) {

    // Перевірка можливості отримати список перетягнутих файлів
    if (!evt.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
      { return null; }
    // Спроба отримати список перетягутих файлів
    try {
      // Знінна для збереження списку файлів та папок
      var files = new File[0];
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
            { files = ((List<File>) transferable.getTransferData(flavor))
                                                .toArray(File[]::new);
              break; } }
      // Якщо true - рекурсивна обробка папок
      if (recursive) { files = processRecursion(files); }
      // Підтвердження обробки події
      evt.dropComplete(true);
      // Повернення результату
      return files; }
    // Якщо відбулася помилка - повертаємо null
    catch (UnsupportedFlavorException | IOException _) { return null; }
}

// ============================================================================
/// Повернення списку перетягнутих файлів
/// @param evt подія типу DropTargetDropEvent
/// @return список перетягнутих файлів

public static File[] getDroppableFiles (DropTargetDropEvent evt)
  { return filterResult(evt, true, false); }

// ============================================================================
/// Повернення списку перетягнутих файлів
/// @param evt подія типу DropTargetDropEvent
/// @param recursive якщо true - повернення внутрішніх файлів
/// @return список перетягнутих файлів

public static File[] getDroppableFiles (DropTargetDropEvent evt,
                                        boolean recursive)
  { return filterResult(evt, true, recursive); }

// ============================================================================
/// Повернення списку перетягнутих папок
/// @param evt подія типу DropTargetDropEvent
/// @return список перетягнутих папок

public static File[] getDroppableFolders (DropTargetDropEvent evt)
  { return filterResult(evt, false, false); }

// ============================================================================
/// Повернення списку перетягнутих папок
/// @param evt подія типу DropTargetDropEvent
/// @param recursive якщо true - повернення підпапок
/// @return список перетягнутих папок

public static File[] getDroppableFolders (DropTargetDropEvent evt,
                                          boolean recursive)
  { return filterResult(evt, false, recursive); }

// ============================================================================
/// Фільтрування списку перетягнутих файлів

private static File[] filterResult (DropTargetDropEvent evt,
                                    boolean filterFiles, boolean recursive) {
    
    var result = new ArrayList<File>();
    var files = getDroppableFilesAndFolders(evt, recursive);
    
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
