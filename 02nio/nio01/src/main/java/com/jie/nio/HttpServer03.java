package com.jie.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jie.LJ.Liu
 * @date 2021/9/17 15:27
 */
public class HttpServer03 {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        try (ServerSocket serverSocket = new ServerSocket(8803)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                executorService.submit(() -> service(socket));
            }
        }
    }

    private static void service(final Socket socket) {

        try (PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)){
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio3";
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
