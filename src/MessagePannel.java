import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class MessagePannel extends JPanel implements ActionListener {
    final int SCREEN_WIDTH = 600;
    final int SCREEN_HEIGHT = 800;
    final int CONTACT_WIDTH = 60;
    final int CONTACT_HEIGHT = 30;
    final int MSG_BOX_WIDTH = 400;
    final int MSG_BOX_HEIGHT = 50;
    final int NAME_BOX_WIDTH = 200;
    final int NAME_BOX_HEIGHT = 50;
    final int ARCWIDTH = 20;
    final int ARCHEIGHT = 20;
    int lastMessageHeight = 75;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    ArrayList<MessageBubble> messages = new ArrayList<>();
    JTextField usernameEnter = new JTextField(10);
    JTextField messageField = new JTextField(25);
    static String username;
    static String target;
    String currentMessage = "";
    boolean newMessage = false;


    MessagePannel() {
//        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        start();
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (target != null && !target.equals("")) {
            g.drawLine(0,50,SCREEN_WIDTH,50);
            for (MessageBubble message : messages) {
                g.setColor(message.color);
                g.fillRoundRect(message.x,message.y,message.width,message.height,message.arcWidth,message.arcHeight);
            }

        }
    }
    public void start() {
        initAccount();

    }

    public void drawClients() {
        clients = ClientHandler.clientHandlers;
        System.out.println(clients.size());
        int x = 0;
        int y = 100;
        for (int i = 0; i < clients.size() ; i++) {
            JButton button;
            if (!(x + CONTACT_WIDTH <= SCREEN_WIDTH)) {
                x = 0;
                y+= CONTACT_HEIGHT;
            }
            button = new JButton(clients.get(i).getClientUsername());
            button.setBounds(x,y,CONTACT_WIDTH,CONTACT_HEIGHT);
            button.setVisible(true);
            int finalI = i;
            button.addActionListener(event -> {
                System.out.println(clients.get(finalI).getClientUsername());
            });
            add(button);
            x+=CONTACT_WIDTH;

        }
    }
//    public void setCurrentMessage(String currentMessage) {
//        this.currentMessage = currentMessage;
//    }

    public void setMessageBool(boolean bool) {
        newMessage = bool;
    }
    public String getMessage() {
        return currentMessage;
    }

    public void makeUsername() {
        JLabel text = new JLabel(target);
        text.setFont(new Font("Arial", Font.BOLD, 16));

        // Get Dimensions of the msg
        Dimension size = text.getPreferredSize();
        text.setBounds(SCREEN_WIDTH / 2 - (int) size.getWidth() / 2,10, (int) size.getWidth(), (int) size.getHeight());
        System.out.println(size.getWidth());
        add(text);
        text.setVisible(true);
    }
    public void initAccount() {
        //JLabel to direct the user
        JLabel usernameText = new JLabel("Enter your username: ");
        usernameText.setBounds(100,200, 300, NAME_BOX_HEIGHT);
        usernameText.setFont(new Font("Arial", Font.BOLD, 16));
        usernameText.setVisible(true);
        this.add(usernameText);
        usernameEnter.setBounds(300,200,NAME_BOX_WIDTH, NAME_BOX_HEIGHT);
        usernameEnter.setVisible(true);
        usernameEnter.setFont(new Font("Arial", Font.BOLD, 16));
        usernameEnter.setFocusable(true);
        usernameEnter.setMargin(new Insets(5,10,5,10));
        this.add(usernameEnter);
        usernameEnter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checking what text box it was pressed for
                if (usernameEnter.isVisible()) {
                    if(username == null) {
                        username = usernameEnter.getText();
                        System.out.println(username);
                        usernameEnter.setText("");
                        usernameText.setText("Who is Your message directed to: ");
                        usernameText.setBounds(25,200, 300, NAME_BOX_HEIGHT);
                    }
                    else if (target == null) {
                        target = usernameEnter.getText();
                        usernameEnter.setVisible(false);
                        usernameText.setVisible(false);
                        System.out.println(target);
                        msg_Box();

                    }
                }
                makeUsername();
                repaint();

            }
        });
    }
    public void msg_Box() {
//        drawClients();
        messageField.setBounds(SCREEN_WIDTH / 2 - MSG_BOX_WIDTH / 2, 650, MSG_BOX_WIDTH, MSG_BOX_HEIGHT);
        messageField.setVisible(true);
        messageField.setFont(new Font("Arial", Font.BOLD, 16));
        messageField.setFocusable(true);
        messageField.setMargin(new Insets(5,10,5,10));
        this.add(messageField);
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                JLabel msg = new JLabel(messageField.getText());
                currentMessage = messageField.getText();

                //Simple loop to calculate the line break in msg bubbles

                newMessage = true;
//                System.out.println("msg: " + messageField.getText());
                messageField.setText("");
//                msg.setFont(new Font("Arial", Font.BOLD, 16));
//
//                // Get Dimensions of the msg
//                Dimension size = msg.getPreferredSize();
//                msg.setBounds(SCREEN_WIDTH - (int) size.getWidth() - 25, lastMessageHeight, (int) size.getWidth(), (int) size.getHeight());
                ArrayList<JLabel> message = messageDivider(currentMessage);
                Dimension size = message.get(0).getPreferredSize();
                messages.add(new MessageBubble(SCREEN_WIDTH - (int) size.getWidth() - 35, lastMessageHeight + 5, (int) size.getWidth() +20, ((int) size.getHeight() - 3) * message.size() + 8,
                        ARCWIDTH, ARCHEIGHT, new Color(3, 162, 233)));

                lastMessageHeight += size.height * message.size() + 10;


//                msg.setVisible(true);
//                add(msg);
                for (JLabel msg: message) {
                    msg.setVisible(true);
                    add(msg);
                }
                repaint();
            }
        });
    }

    public ArrayList<JLabel> messageDivider(String message) {
        ArrayList<JLabel> ret = new ArrayList<>();
        int messageHeight = lastMessageHeight;
        if (message.length() > 26){
            for (int i = 0; i < message.length() - 26;) {
                int index = i+26;
                for (int j = index; j >= index; j--) {
                    if (message.charAt(j) == ' ') {
                        index = j;
                        break;
                    }
                }

                System.out.println("message: " + message.substring(i, index));
                JLabel lineMessage = new JLabel(message.substring(i, index));
                lineMessage.setFont(new Font("Arial", Font.BOLD, 16));
                Dimension size = lineMessage.getPreferredSize();
                System.out.println(SCREEN_WIDTH - (int) size.getWidth() - 25);
                lineMessage.setBounds(365, messageHeight + 7, (int) size.getWidth() + 100, (int) size.getHeight());
                ret.add(lineMessage);
                messageHeight+=15;
//                lastMessageHeight+=10;

                i = index;
            }
        }
        else {// Message is less than 26 chars
            System.out.println(message);
            JLabel lineMessage = new JLabel(message);
            lineMessage.setFont(new Font("Arial", Font.BOLD, 16));
            Dimension size = lineMessage.getPreferredSize();
            lineMessage.setBounds(SCREEN_WIDTH - (int) size.getWidth() - 25, lastMessageHeight + 7, (int) size.getWidth(), (int) size.getHeight());
            ret.add(lineMessage);
//            lastMessageHeight+=10;
        }
//        lastMessageHeight = messageHeight;
        return ret;
    }

    public void receiveMessage(String message) {
        JLabel msg = new JLabel(message);
        msg.setFont(new Font("Arial", Font.BOLD, 16));

        // Get Dimensions of the msg
        Dimension size = msg.getPreferredSize();
        msg.setBounds(35, lastMessageHeight, (int) size.getWidth(), (int) size.getHeight());

        messages.add(new MessageBubble(25, lastMessageHeight - 5, (int) size.getWidth() + 20, (int) size.getHeight() + 8,
                ARCWIDTH, ARCHEIGHT, Color.GRAY));

        lastMessageHeight += size.height + 10;

        msg.setVisible(true);
        add(msg);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

//    public class MyKeyAdapter extends KeyAdapter {
//        @Override
//        public void keyPressed(KeyEvent e){
//            System.out.println("Hello!");
//            // Checking if the enter key has been pressed
//
//
//
//        }
//    }
}


