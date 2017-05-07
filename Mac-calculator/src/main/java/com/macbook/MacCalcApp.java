package com.macbook;

import org.apache.wicket.protocol.http.WebApplication;

public class MacCalcApp extends WebApplication {
	@Override
	protected void init() {
		mountBookmarkablePage("maccalc", MacCalcPage.class);
	}
	@Override
	public Class getHomePage() {
		return MacCalcPage.class;
	}
}
