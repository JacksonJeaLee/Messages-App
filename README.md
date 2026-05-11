# Messages App

A simple Java desktop messaging application built with Swing, sockets, and threads. The project includes a socket server that accepts multiple clients and a graphical client interface for sending direct messages to another connected user.

## Overview

Messages App demonstrates the core pieces behind a real-time chat system:

- A Java server listens for incoming socket connections.
- Each connected client is handled on its own thread.
- Users enter a username and the username of the person they want to message.
- Messages are sent through the server and delivered to the matching target user.
- The client uses a Swing interface with styled message bubbles for sent and received messages.

## Features

- Multi-client socket server
- Direct user-to-user messaging
- Threaded client handling
- Swing-based desktop chat window
- Username and recipient entry flow
- Visual message bubbles for outgoing and incoming messages
- Lightweight project structure with no external dependencies

## Tech Stack

- Java
- Java Swing
- Java Sockets
- Java Threads

## Project Structure

```text
Messages-App/
+-- src/
|   +-- Server.java          # Starts the socket server on port 1234
|   +-- ClientHandler.java   # Handles each connected client on a separate thread
|   +-- Client.java          # Connects the GUI client to the server
|   +-- MessageApp.java      # Main Swing application window
|   +-- MessagePannel.java   # Chat UI, input fields, and message display
|   +-- MessageBubble.java   # Message bubble data model
|   +-- Main.java            # Opens the UI without connecting to the server
+-- Messages.iml             # IntelliJ IDEA module file
+-- .gitignore
+-- README.md
```

## Requirements

- Java Development Kit (JDK) 8 or newer
- IntelliJ IDEA, another Java IDE, or a terminal that can run Java commands

## How to Run

### Option 1: Run in IntelliJ IDEA

1. Open the project folder in IntelliJ IDEA.
2. Make sure the `src` folder is marked as the source folder.
3. Run `Server.java` first.
4. Run `Client.java` once for each user you want to connect.
5. In each client window, enter:
   - Your username
   - The username of the person you want to message
6. Type a message and press Enter to send it.

### Option 2: Run from the Terminal

Compile the source files:

```bash
javac -d out src/*.java
```

Start the server:

```bash
java -cp out Server
```

Open a second terminal and start a client:

```bash
java -cp out Client
```

Open another terminal and start a second client:

```bash
java -cp out Client
```

The server runs on port `1234`, and the client connects to `localhost`.

## How Messaging Works

When a client connects, it sends two values to the server:

1. The client's username
2. The username of the intended message recipient

The server stores each connected client in a shared list. When a message is sent, the server checks the connected clients and forwards the message only to the client whose username matches the sender's selected target.

## Current Limitations

- The server address and port are hard-coded as `localhost:1234`.
- Users must know the recipient's username before sending messages.
- There is no account system or persistent message history.
- The app does not currently show a live contact list.
- The UI class is named `MessagePannel`; this appears to be a spelling typo, but it is left unchanged in the code.

## Future Improvements

- Add a visible online users list
- Allow users to change recipients without restarting the client
- Show connection and delivery errors in the UI
- Store message history locally or in a database
- Add timestamps to messages
- Improve layout resizing and scrolling

## Author

Created as a Java socket programming project to practice desktop UI development, networking, and multithreaded client handling.
