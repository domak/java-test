package org.domak.net.sample;

import java.util.Map;

import org.apache.log4j.Logger;

public class ProcessorImpl implements Processor {

	private static Logger logger = Logger.getLogger(ProcessorImpl.class);

	@Override
	public Status process(ProcessorArg processorArg) {
		for (Map.Entry<String, Object> entry : processorArg.getAttributes().entrySet()) {
			logger.info("attribute: " + entry.getKey() + " - " + entry.getValue());
		}
		if (processorArg.getAttributes().size() == 0) {
			return Status.KO;
		}
		return Status.OK;
	}
}
