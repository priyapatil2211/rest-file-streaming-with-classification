
# 📁 Rest File Streaming with Classification (Java)

This project demonstrates a simple Java-based RESTful client-server application. The **client** sends file paths to the **server**, the server reads the file content and streams it back. The client receives the stream and sends it to a custom classifier (`ClassificationSercher` from a custom JAR).

---

## 📦 Project Structure

```
rest-file-streaming-with-classification/
├── server/    → Spring WebFlux server
└── client/    → Java client using multithreading
```

---

## 🛠️ Prerequisites

- Java 11 or later
- Maven 3.6+
- [VS Code](https://code.visualstudio.com/) with Java & Spring extensions (optional)
- Custom JAR: `classification-sdk-1.0-release-221.9.jar`

> ⚠️ Place the custom JAR inside:
> `client/libs/classification-sdk-1.0-release-221.9.jar`

---

## 🚀 Getting Started

### 🖥️ 1. Run the Server

```bash
cd server
mvn spring-boot:run
```

> Server starts at: `http://localhost:8080`

---

### 📡 2. Run the Client

```bash
cd client
mvn compile exec:java -Dexec.mainClass="com.example.client.ClientApplication"
```

> The client uses **two threads** to simulate concurrent file classification.

---

### Debugging
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.YourMainClass" \
  -Dexec.args="" \
  -Dexec.cleanupDaemonThreads=false \
  -Dexec.jvmArgs="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
```

## 📂 How it Works

1. Client reads a list of local file paths (e.g., `files/file1.txt`, `files/file2.txt`)
2. It POSTs each file path to the server (`/read-file`)
3. Server reads the file and returns it as a streamed response
4. Client captures the stream, builds it into an InputStream
5. That stream is passed to:
   ```java
   ClassificationSercher.searchMatches(streamData);
   ```
   from the custom JAR.

---

## 🧠 Features

- Java-based REST API (Spring WebFlux)
- Multithreaded Java client
- Chunked file transfer over HTTP
- Integration with external classification SDK

---

## 📌 Notes

- Files referenced by the client **must exist locally**.
- You can modify the list of files in:
  `client/src/main/java/com/example/client/ClientApplication.java`

---

## 📄 License

This project is for demonstration purposes. The external `classification-sdk` JAR is assumed to be proprietary and is **not included** in the repository.

---

## 🙌 Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Project Reactor](https://projectreactor.io/)
- Java ExecutorService for multithreading

---

Feel free to fork, modify, or extend this project!
