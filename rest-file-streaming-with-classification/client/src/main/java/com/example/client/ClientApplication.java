
package com.example.client;

import com.classification.ClassificationSercher;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApplication {

    private static final List<String> filePaths = List.of(
            "files/file1.txt",
            "files/file2.txt"
    );

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            for (String path : filePaths) {
                try {
                    URL url = new URL("http://localhost:8080/read-file");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "text/plain");
                    OutputStream os = connection.getOutputStream();
                    os.write(path.getBytes());
                    os.flush();

                    InputStream in = connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        baos.write(buffer, 0, bytesRead);
                    }

                    InputStream classifiedInput = new ByteArrayInputStream(baos.toByteArray());
                    ClassificationSercher.searchMatches(classifiedInput);

                    System.out.println(Thread.currentThread().getName() + " processed file " + path);

                    in.close();
                    os.close();
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
    }
}
