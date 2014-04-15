package com.sendify.server;

public class LogUtils {

	 @SuppressWarnings("unchecked")
	    public static String makeLogTag(Class cls) {
	        return ("Sendify_" + cls.getSimpleName());
	    }
}
