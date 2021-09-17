package com.jie.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Jie.LJ.Liu
 * @date 2021/9/17 15:19
 */
public class HttpServer01 {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            final Socket socket = serverSocket.accept();
            service(socket);
        }
    }

    private static void service(final Socket socket) {

        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)){
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio1";
            printWriter.println("Content-Length:" + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
