package ar.com.hgdeoro.daedalus.client;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/* **********************************************************************

 daedalus - Centralized log server
 Copyright (C) 2012 - Horacio Guillermo de Oro <hgdeoro@gmail.com>

 This file is part of daedalus.

 daedalus is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation version 2.

 daedalus is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License version 2 for more details.

 You should have received a copy of the GNU General Public License
 along with daedalus; see the file LICENSE.txt.

 ********************************************************************** */

/**
 * Daedalus client, to send messages to a Daedalus server using HTTP and JSON.
 * 
 * @author Horacio G. de Oro <hgdeoro@gmail.com>
 * 
 */
public class DaedalusClient {

	private String serverHost = "localhost";

	private int serverPort = 8084;

	private String defaultMessageHost;

	private String defaultMessageApplication;

	/**
	 * Sends a message to Daedalus, using the current time as the message's
	 * time.
	 * 
	 * @param message
	 * @param severity
	 * @param host
	 * @param application
	 * @throws Exception
	 * @return
	 */
	public boolean sendMessage(String message, Severity severity, String host,
			String application) throws Exception {

		// Setup default values
		if (host == null)
			host = this.defaultMessageHost;
		if (application == null)
			application = this.defaultMessageApplication;

		// Create params for POST
		String params = "message=" + URLEncoder.encode(message, "UTF-8");
		params += "&host=" + URLEncoder.encode(host, "UTF-8");
		params += "&severity="
				+ URLEncoder.encode(severity.toString(), "UTF-8");
		params += "&timestamp="
				+ URLEncoder.encode(Double.toString(((double) System
						.currentTimeMillis()) / 1000.0), "UTF-8");
		params += "&application=" + URLEncoder.encode(application, "UTF-8");

		final byte paramsAsBytes[] = params.getBytes();

		// Send the request
		URL url = new URL("http://" + this.serverHost + ":" + this.serverPort
				+ "/backend/save/");
		HttpURLConnection httpConnection = null;
		httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestMethod("POST");
		httpConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpConnection.setRequestProperty("Content-Length",
				Integer.toString(paramsAsBytes.length));
		httpConnection.setUseCaches(false);
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);

		httpConnection.getOutputStream().write(paramsAsBytes);
		httpConnection.getOutputStream().flush();
		httpConnection.getOutputStream().close();

		int status = httpConnection.getResponseCode();

		if (status == 201)
			return true;
		else
			return false;
	}

	public static void main(String args[]) throws Exception {
		new DaedalusClient().sendMessage("TEST MESSAGE FROM JAVA",
				Severity.INFO, "testhost", "testapp");
	}
}
