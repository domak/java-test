package org.domak.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class RemoteInvocationHandler implements InvocationHandler {

	private static Logger logger = Logger.getLogger(Client.class);

	private String host;
	private int serverPort = Server.DEFAULT_LISTENING_PORT;
	private String serviceName;

	public RemoteInvocationHandler(String host, int serverPort, String serviceName) {
		this.host = host;
		this.serverPort = serverPort;
		this.serviceName = serviceName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			Socket socket = new Socket(host, serverPort);

			logger.info("connected from " + socket.getLocalPort() + " to " + socket.getInetAddress() + " - port:"
					+ socket.getPort());

			Message message = new Message(serviceName, method.getName(), args);

			// serialize message
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(message);

			// send serialized message
			socket.getOutputStream().write(byteArrayOutputStream.toByteArray());

			// get result
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			Object result = objectInputStream.readObject();

			logger.info("result:" + result);

			socket.close();

			return result;

		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
