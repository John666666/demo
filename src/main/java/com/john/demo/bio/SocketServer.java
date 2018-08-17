package com.john.demo.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于BIO Socket的CS通信
 * 尚存在的问题：客户端断开连接时的处理不友好，理想中是客户端发起断开申请， 服务端回复确认断开。然后双方关闭Socket通道。
 * @author John
 *
 */
public class SocketServer {

	private ExecutorService threadPool = new ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS,
			new SynchronousQueue<Runnable>());

	public static void main(String[] args) {
		SocketServer ss = new SocketServer();
		System.out.println("start socket server. on 127.0.0.1:10099");
		ss.startServer();
	}

	public void startServer() {
		try {
			ServerSocket ss = new ServerSocket(10099);
			Socket socket = null;
			while ((socket = ss.accept()) != null) {
				handleSocket(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleSocket(final Socket socket) {
		threadPool.execute(new Runnable() {
			public void run() {
				new ResponseThread(socket).start();
				new RequestThread(socket).start();

				String readLine = null;
				Scanner sc = new Scanner(System.in);
				while ((readLine = sc.nextLine()) != null) {
					readLine += "\n";
					new ResponseThread(socket, readLine).start();
				}
			}
		});
	}

	class ResponseThread extends Thread {
		Socket socket;
		String message;

		public ResponseThread(Socket socket) {
			this.socket = socket;
		}

		public ResponseThread(Socket socket, String message) {
			this.socket = socket;
			this.message = message;
		}

		@Override
		public void run() {
			OutputStream out = null;
			try {
				out = socket.getOutputStream();
				if (this.message == null) {
					this.message = "欢迎进入聊天室.";
				}
				out.write(this.message.getBytes());
				out.flush();
			} catch (IOException e) {
				System.out.println("连接已断开");
			}
		}
	}

	class RequestThread extends Thread {
		Socket socket;

		public RequestThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
				String line = null;
				while ((line = in.readLine()) != null) {
					if ("q".equals(line.trim())) {
						System.out.println("client disconnect from server.");
						new ResponseThread(socket, "bye").start();
						;
						socket.close();
						break;
					}
					System.out.println("receive from client: " + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
