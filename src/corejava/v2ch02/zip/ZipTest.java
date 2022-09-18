package corejava.v2ch02.zip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @version 1.41 2012-06-01
 * @author Cay Horstmann
 */
public class ZipTest {
    public static void main(String[] args) throws IOException {
        String zipname = args[0];
        showContents(zipname);
        System.out.println("---");
        showContents2(zipname);
//        writeContent("test.zip", filename);
    }

    public static void showContents(String zipname) throws IOException {
        // Классическое чтение содержимого ZIP-файла
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipname))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                System.out.println(entry.getName());

                Scanner in = new Scanner(zin, StandardCharsets.UTF_8);
                while (in.hasNextLine())
                    System.out.println("   " + in.nextLine());
                // DO NOT CLOSE in
                zin.closeEntry();
            }
        }
    }

    public static void showContents2(String zipname) throws IOException {
        // Here, we make a Java SE 7 file system
        FileSystem fs = FileSystems.newFileSystem(Paths.get(zipname), (ClassLoader) null);
        Files.walkFileTree(fs.getPath("/"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                System.out.println(path);
                for (String line : Files.readAllLines(path, StandardCharsets.ISO_8859_1))
                    System.out.println("   " + line);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void writeContent(String zipname, String filename) throws IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipname));
             FileInputStream fis = new FileInputStream(filename)) {
            ZipEntry ze = new ZipEntry("test.java");
            zout.putNextEntry(ze);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
    }
}