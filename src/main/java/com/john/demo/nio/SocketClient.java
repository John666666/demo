package com.john.demo.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
	Socket socket;

	public static void main(String[] args) {
		SocketClient sc = new SocketClient();
		sc.startClient();
	}

	public void startClient() {
		BufferedReader in = null;
		OutputStream out = null;
		try {
			socket = new Socket("127.0.0.1", 10099);

			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			new RequestThread(in).start();

			System.out.println("请输入任意内容，按'q'退出...");
			String readLine = null;
			Scanner sc = new Scanner(System.in);
			while ((readLine = sc.nextLine()) != null) {
				if ("q".equals(readLine)) {
					readLine += "\n";
					new ResponseThread(socket.getOutputStream(), readLine).start();
					sc.close();
					break;
				}
				readLine += "\n";
				new ResponseThread(socket.getOutputStream(), readLine).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuiet(in, out);
		}
	}

	public static void closeQuiet(Reader in, OutputStream out) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ResponseThread extends Thread {
		OutputStream out;
		String response;

		public ResponseThread(OutputStream out, String response) {
			this.out = out;
			this.response = response;
		}

		@Override
		public void run() {
			try {
				out.write((this.response).getBytes());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class RequestThread extends Thread {
		BufferedReader reader;

		public RequestThread(BufferedReader reader) {
			this.reader = reader;
		}

		@Override
		public void run() {
			try {
				String line = null;
				while (socket.isConnected()) {
					if ((line = reader.readLine()) != null) {
						System.out.println("receive from server: " + line);
					}
				}
			} catch (IOException e) {
				System.out.println("连接已断开");
			}
		}
	}

}
