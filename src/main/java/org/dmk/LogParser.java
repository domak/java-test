package org.dmk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogParser {
	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader("/home/domak/dev/workspace/gradbook/src/perf.csv"));

		Map<String, Map<String, Record>> driverMap = new HashMap<String, Map<String, Record>>();
		String line = null;
		while ((line = in.readLine()) != null) {
			Record record = new Record();
			String[] it = line.split(",");
			record.driverName = it[0];
			record.timeStamp = Long.valueOf(it[1]);
			record.ud = it[2];
			record.action = it[3];
			record.type = it[4];
			record.response = (it.length == 6) ? it[5] : null;
			System.out.print(record);

			Map<String, Record> actionMap = null;
			if (record.type.equals("COMMAND")) {
				actionMap = driverMap.get(record.driverName);
				if (actionMap == null) {
					actionMap = new HashMap<String, Record>();
					driverMap.put(record.driverName, actionMap);
				}
				actionMap.put(record.ud, record);
				System.out.println();
			} else {
				actionMap = driverMap.get(record.driverName);
				long time = record.timeStamp - actionMap.get(record.ud).timeStamp;
				System.out.println(" -> " + record.timeStamp + " - " + actionMap.get(record.ud).timeStamp + " = "
						+ time + " ns | " + time / 1000000 + "ms");
			}

		}
	}

	static class Record {
		String driverName;
		long timeStamp;
		String ud;
		String action;
		String type;
		String response;

		@Override
		public String toString() {
			return driverName + ',' + timeStamp + ',' + ud + ',' + action + ',' + type + ',' + response;
		}
	}
}
