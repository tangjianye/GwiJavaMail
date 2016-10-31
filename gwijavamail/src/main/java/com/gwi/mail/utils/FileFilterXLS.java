package com.gwi.mail.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterXLS extends FileFilter {

    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return f.getName().endsWith(".xls");
    }

    public String getDescription() {
        return ".xls";
    }

}
