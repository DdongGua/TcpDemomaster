package com.example.tcpdemo_master;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by 亮亮 on 2017/11/6.
 */  //客户端
//socket是tcp协议中的最小的传输单元，他是通过流进行写入的
public class TcpClient {

    private final Scanner scanner;
    private final Socket socket;
    private final BufferedReader br;
    private final BufferedWriter bw;

    public TcpClient() throws IOException {
        //创建scaner对象，然后监听回车键
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        //创socket指向主机地址和端口
        socket = new Socket("192.168.33.116", 9999);
        InputStream inputStream = socket.getInputStream();
        //把字节流转化成字符流，
        br = new BufferedReader(new InputStreamReader(inputStream));
        OutputStream outputStream = socket.getOutputStream();
        bw = new BufferedWriter(new OutputStreamWriter(outputStream));


    }

    //往server端发送数据
    public void sendServer() throws IOException {
        String next = scanner.next();
        bw.write(next);
        bw.newLine();
        bw.flush();
        bw.close();

    }

    public void receive() throws IOException {
        String msg = "";
        while ((msg = br.readLine()) != null) {
            System.out.print(msg);

        }
    }

    public static void main(String[] args) throws Exception {
        final TcpClient tcpClient = new TcpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        tcpClient.sendServer();
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


                    try {
                        System.out.print("1---------+System.currentTimeMillis()");
                        tcpClient.receive();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
