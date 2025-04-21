
package com.example.server;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.io.*;

@RestController
public class FileController {

    @PostMapping(value = "/read-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<DataBuffer> readFile(@RequestBody String filePath) throws IOException {
        File file = new File(filePath.trim());
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        InputStream inputStream = new FileInputStream(file);
        return DataBufferUtils.readInputStream(
                () -> inputStream,
                new DefaultDataBufferFactory(),
                4096
        );
    }
}
