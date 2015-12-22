package com.yeleid.solutions;

public class Main {

    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.valueOf(args[1]);

        ArchiveServer server = null;
        try {
            server = new ArchiveServer(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            server.close();
        }

        System.exit(0);
    }
}
