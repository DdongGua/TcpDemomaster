package com.example.tcpdemo_master;

import android.content.SyncStatusObserver;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 亮亮 on 2017/11/6.
 */  //服务端

public class TcpServer {

    private final Socket clientSocket;
    private final Scanner scanner;
    private final BufferedReader br;
    private final BufferedWriter bw;

    public TcpServer() throws IOException {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        ServerSocket serverSocket = new ServerSocket(9999);
        clientSocket = serverSocket.accept();
        //输入流是客户端发过来的数据
        InputStream inputStream = clientSocket.getInputStream();
        br = new BufferedReader(new InputStreamReader(inputStream));
        //输出流是要给客户端发的数据
        OutputStream outputStream = clientSocket.getOutputStream();
        bw = new BufferedWriter(new OutputStreamWriter(outputStream));


    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    String info = "";
                    try {
                        while ((info = br.readLine()) != null) {
                            System.out.print(info + clientSocket.getPort() + clientSocket.getInetAddress() + "----server");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String next = scanner.next();
                    try {
                        bw.write(next);
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    public static void main(String[] args) throws IOException {

        TcpServer tcpServer = new TcpServer();
        tcpServer.start();
    }


}
