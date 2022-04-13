/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import hr.algebra.factory.UrlConnectionFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Petra
 */
public class FileUtils {
    
    private static final String UPLOAD = "Upload";

    public static File uploadFile(String description, String...extensions) {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
        chooser.setDialogTitle(UPLOAD);
        chooser.setApproveButtonText(UPLOAD);
        chooser.setApproveButtonToolTipText(UPLOAD);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
            return Arrays.asList(extensions).contains(extension.toLowerCase()) ? selectedFile : null;            
        }
        return null;
    }

    public static void copyFromUrl(String source, String destination) throws IOException {
        //1. osigurati da postoje direktoriji
        //assets/file/aaa/23332.jpg
        createDirHierarchy(destination);
        //2.otvori konekciju i prekopiraj sliku s interneta u file
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(source); //otvori konekciju na file na internetu
        try (InputStream is = con.getInputStream()){ //otvori input stream
            Files.copy(is, Paths.get(destination)); //iskopiraj ga
        }
    }

    //za lokalno kopiranje, ne za internet
    public static void copy(String source, String destination) throws IOException {
        createDirHierarchy(destination); //kreiraj direktorij ako ti treba na destinationu
        Files.copy(Paths.get(source), new FileOutputStream(destination)); //kopiraj source u target
    }

    
    //assets/file/aaa/23332.jpg
    public static void createDirHierarchy(String destination) throws IOException {

        String dir = destination.substring(0, destination.lastIndexOf(File.separator)); //assets/file/aaa/
        if (!Files.exists(Paths.get(dir))) {
            Files.createDirectories(Paths.get(dir));
        }
    }
    
}
