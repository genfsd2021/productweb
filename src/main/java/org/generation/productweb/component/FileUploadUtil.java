package org.generation.productweb.component;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

    public static void saveFile(String uploadDir1, String fileName,
                                MultipartFile multipartFile) throws IOException
    {
        //this utility class is only responsible for save the uploaded file from MultipartFile object to a file in the file system.
        //https://docs.oracle.com/javase/7/docs/api/java/nio/file/Paths.html
        //Paths.get(url) : Converts a path string, or a sequence of strings that when joined form a path string, to a Path.
        Path uploadPath1 = Paths.get(uploadDir1);

        //Here we're using the getInputStream() method to get the InputStream, read the bytes from the InputStream,
        // and store them in the byte[] buffer. Then we create a File and OutputStream to write the buffer contents.
        //This abstract class is the superclass of all classes representing an input stream of bytes.
        //Applications that need to define a subclass of InputStream must always provide a method that returns the next byte of input.
        try (InputStream inputStream = multipartFile.getInputStream()) {

            //String fileName : e.g. T-shirtNew.jpg
            //used to converts a given path string to a Path
            //https://www.geeksforgeeks.org/path-resolve-method-in-java-with-examples/
            Path filePath1 = uploadPath1.resolve(fileName);

            //Copy a file to a target file.
            Files.copy(inputStream, filePath1, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
