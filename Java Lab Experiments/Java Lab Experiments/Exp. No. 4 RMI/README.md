# Remote Calculator Using Java RMI

A simple Java Remote Method Invocation (RMI) project. The client calls calculator methods that are implemented by a remote object registered with an RMI registry.

## Features

- Remote addition and subtraction methods
- RMI remote interface
- Remote object implementation
- RMI registry started by the server
- Client lookup using `Naming.lookup()`

## Requirements

- JDK 8 or later (JDK 17 or 21 recommended)
- Visual Studio Code (optional)

## Project Structure

```text
RMI-Calculator/
├── Calculator.java
├── CalculatorImpl.java
├── Server.java
├── Client.java
├── README.md
└── .gitignore
```

## Run in VS Code on Windows

Open this folder in VS Code, then select **Terminal → New Terminal**.

### 1. Compile the source files

```powershell
javac Calculator.java CalculatorImpl.java Server.java Client.java
```

### 2. Start the server

In Terminal 1, run:

```powershell
java Server
```

The server starts an RMI registry on port `1099` and registers the calculator service. Keep this terminal open.

### 3. Run the client

Open a second terminal in the same folder and run:

```powershell
java Client
```

## Expected Client Output

```text
Addition (10 + 20) = 30
Subtraction (20 - 10) = 10
```

The server terminal should show that the RMI server started and the calculator service was registered.

## How It Works

- `Calculator.java` defines the remote methods.
- `CalculatorImpl.java` implements the methods and exports the remote object.
- `Server.java` starts the registry and binds the object under the name `Calculator`.
- `Client.java` looks up the service and invokes its remote methods.

## Troubleshooting

- **Connection refused:** Start `Server.java` before running `Client.java`.
- **Port 1099 already in use:** Stop the other RMI server or Java process using that port.
- **Class not found:** Run the compile command from the folder containing all four `.java` files.
- Run both programs on the same computer for this example. For different computers, configure the host address and network/firewall accordingly.

## Limitations

This is an educational example with addition and subtraction only. It does not implement authentication or production-grade security.

## Author

Anirudh Paul
