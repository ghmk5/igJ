package com.github.ghmk5.igj.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class Util {

  public static FileFilter getReadableImageFilter() {
    List<String> readableSuffixList = Arrays.asList(ImageIO.getReaderFileSuffixes());
    FileFilter fileFilter = new FileFilter() {

      @Override
      public boolean accept(File pathname) {
        String fileName = pathname.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (readableSuffixList.contains(suffix)) {
          return true;
        }
        return false;
      }
    };

    return fileFilter;
  }

  public static File[] getReadableImageFiles(File dir) throws IllegalArgumentException {
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException("argument must be a directory.");
    }
    List<String> readableSuffixList = Arrays.asList(ImageIO.getReaderFileSuffixes());
    List<File> imageFileList = new ArrayList<File>();
    String fileName;
    String suffix;
    for (File entry : dir.listFiles()) {
      if (entry.isFile()) {
        fileName = entry.getName();
        suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (readableSuffixList.contains(suffix)) {
          imageFileList.add(entry);
        }
      } else if (entry.isDirectory()) {
        imageFileList.addAll(Arrays.asList(getReadableImageFiles(entry)));
      }
    }
    return imageFileList.toArray(new File[imageFileList.size()]);
  }

  public static void main(String[] args) {
    File dir = new File("./reference/thumbnail(160)/2019-10-28");
    for (File entry : dir.listFiles(getReadableImageFilter())) {
      System.out.println(entry.getName());
    }
  }

}
