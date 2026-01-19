package ro.pub.cs.systems.pdsd.practicaltest02v3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private String address;
    private int port;
    private String word;

    private TextView resultTextView;

    public ClientThread(String address, int port, String word, TextView resultTextView) {

        this.address = address;
        this.port = port;
        this.word = word;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(address, port);
            BufferedReader reader = Utilities.getReader(socket);
            PrintWriter writer = Utilities.getWriter(socket);

            writer.println(word);
            writer.flush();

            String definition = reader.readLine();

            Intent intent = new Intent(Constants.BROADCAST_ACTION);
            intent.putExtra("definition", definition);


            if (resultTextView != null) {
                resultTextView.post(() -> resultTextView.setText(definition));
            }
            socket.close();
        } catch (Exception e) {
            Log.e(Constants.TAG, "Eroare Client: " + e.getMessage());
        }
    }
}