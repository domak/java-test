package org.dmk.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.dmk.net.sample.Processor;
import org.dmk.net.sample.ProcessorArg;
import org.dmk.net.sample.Status;

public class Client {

	private static Logger logger = Logger.getLogger(Client.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();

		InvocationHandler invocationHandler = new RemoteInvocationHandler("localhost", Server.DEFAULT_LISTENING_PORT,
				"processor");
		Processor processor = (Processor) Proxy.newProxyInstance(Client.class.getClassLoader(),
				new Class[] {Processor.class}, invocationHandler);

		ProcessorArg processorArg = new ProcessorArg();
		processorArg.setAttribute("call at", new Date());
		Status status = processor.process(processorArg);
		logger.info("client call result: " + status);

		// ExecutorService executorService = Executors.newCachedThreadPool();
		// for (int i = 0; i < 10; i++) {
		// Client client = new Client();
		// executorService.execute(client);
		//

	}
}
