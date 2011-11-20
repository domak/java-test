package org.domak.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.domak.net.sample.ProcessorImpl;

public class Server {

	public static final int DEFAULT_LISTENING_PORT = 9123;
	private static final int THREAD_POOL_SIZE = 20;
	private static Logger logger = Logger.getLogger(Server.class);

	public static void main(String[] args) {
		// Layout layout = new PatternLayout("%r [%t] %-5p %c %x - %m\n");
		// Logger.getRootLogger().addAppender(new ConsoleAppender(layout));
		BasicConfigurator.configure();

		Server server = new Server(DEFAULT_LISTENING_PORT);
		server.register("processor", new ProcessorImpl());
		server.start();
	}

	// -------------------------------------------------------------------------
	private int listeningPort = DEFAULT_LISTENING_PORT;
	private Map<String, Object> serviceMap = new HashMap<String, Object>();

	public Server(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	private void register(String serviceName, Object serviceImpl) {
		serviceMap.put(serviceName, serviceImpl);
	}

	public void start() {
		try {
			logger.info("Starting server");
			ServerSocket serverSocket = new ServerSocket(listeningPort);
			ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

			while (true) {
				Socket socket = serverSocket.accept();
				Handler handler = new Handler(socket);
				executorService.execute(handler);

			}

		} catch (IOException e) {
			logger.error("connection error", e);
		}
	}

	// -------------------------------------------------------------------------
	// inner class
	// -------------------------------------------------------------------------
	private class Handler implements Runnable {
		// private static Logger logger = Logger.getLogger(Handler.class);
		private Socket socket;

		private Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// logger.info("Accept client: " + socket.getInetAddress() + " - port: " + socket.getPort()
				// + " - local Address: " + socket.getLocalAddress() + " - local port: " + socket.getLocalPort());
				InputStream inputStream = socket.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

				Message message = (Message) objectInputStream.readObject();
				logger.debug("receveid:" + message);

				Object serviceImpl = serviceMap.get(message.getServiceName());
				Class<?>[] argClasses = new Class[message.getArgs().length];
				int i = 0;
				for (Object arg : message.getArgs()) {
					argClasses[i++] = arg.getClass();
				}
				Method method = serviceImpl.getClass().getMethod(message.getMethodName(), argClasses);

				Object result = method.invoke(serviceImpl, message.getArgs());

				// serialize result
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(result);

				// send serialized result
				socket.getOutputStream().write(byteArrayOutputStream.toByteArray());

				socket.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
