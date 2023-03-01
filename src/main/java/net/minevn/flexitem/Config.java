package net.minevn.flexitem;

import java.util.List;

public class Config {
	private static int amountShow;
	private static long timeout;
	private static List<String> excludeNames;

	public static void loadConfig() {
		var main = FlexItem.getInstance();
		main.saveDefaultConfig();
		var config = main.getConfig();
		amountShow = config.getInt("amount_show",  3);
		timeout = config.getLong("timeout");
		excludeNames = config.getStringList("exclude_names");
	}

	public static int getAmountShow() {
		return amountShow;
	}

	public static List<String> getExcludeNames() {
		return excludeNames;
	}

	public static long getTimeout() {
		return timeout;
	}
}
