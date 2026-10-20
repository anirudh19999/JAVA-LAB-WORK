# Java TCP Client-Server Program

A simple Java networking project that demonstrates communication between a client and a server using TCP sockets from the `java.net` package.

## Features

- Server listens on port `5000`.
- Client connects to `localhost` by default.
- Client sends `Hello Server`.
- Server prints the message and replies with `Hello Client!`.
- Both sides close their connections safely.
- Uses UTF-8 for text communication and try-with-resources to release sockets and streams.

## Requirements

- JDK 8 or later
- Visual Studio Code (optional)

## Project Structure

```text
JavaSocket/
├── Client.java
├── Server.java
└── README.md
```

**Important:** Start the server before starting the client. The server accepts one client and then exits after replying.

## Expected Output

### Server terminal

```text
Server started on port 5000.
Waiting for a client...
Client connected: /127.0.0.1
Client says: Hello Server
Server stopped.
```

The client address can vary by computer.

### Client terminal

```text
Connected to server at localhost:5000
Server says: Hello Client!
```

## Test Cases

1. **Normal run:** Start the server, then the client. Confirm the client receives `Hello Client!`.
2. **Client before server:** Run the client without starting the server. It should show a connection error rather than crash.
3. **Port conflict:** If port `5000` is already in use, the server reports an error. Stop the other program or change the port in both files.
4. **Repeat test:** Start `java Server` again for each new client run, because this example handles one client per server launch.

## How It Works

- `ServerSocket` waits for incoming TCP connections.
- `Socket` represents the connection between the client and server.
- `BufferedReader` reads text lines.
- `PrintWriter` sends text lines.
- `readLine()` waits until it receives a line ending, so `println()` is used to send messages.

## Limitations

- This is a basic request-and-reply demonstration, not a continuous chat application.
- The server handles one client and one message per launch.
- It is intended for learning Java networking, not production use.

<!-- ## Run in VS Code on Windows


 1  ``
   javac Server.java Client.java
   ```

  2 ```
   java Server
   ```

6. In the second terminal, run the client:

   ```powershell
   java Client
   ``` -->

## Author

Anirudh Paul
