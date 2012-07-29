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
	 * Default constructor of client.
	 */
	public DaedalusClient() {
	}

	/**
	 * Creates a Daedalus client with parametrized defaults.
	 * 
	 * @param serverHost
	 *            Hostname or IP of the server. If null, it's ignored and the
	 *            default is used.
	 * @param serverPort
	 *            Port of the server. If -1, it's ignored and the default is
	 *            used.
	 * @param defaultMessageHost
	 *            If null, it's ignored and no default is setted.
	 * @param defaultMessageApplication
	 *            If null, it's ignored and no default is setted.
	 */
	public DaedalusClient(String serverHost, int serverPort,
			String defaultMessageHost, String defaultMessageApplication) {
		if (serverHost != null)
			this.serverHost = serverHost;
		if (serverPort > -1)
			this.serverPort = serverPort;
		if (defaultMessageHost != null)
			this.defaultMessageHost = defaultMessageHost;
		if (defaultMessageApplication != null)
			this.defaultMessageApplication = defaultMessageApplication;
	}

	/**
	 * Sends a message to Daedalus, using the current time as the message's
	 * time.
	 * 
	 * @param message
	 *            The message to send
	 * @param severity
	 *            One of the valid severities from the Severity enum
	 * @param host
	 *            The host that originated the message
	 * @param application
	 *            The application that originated the message. May be null if
	 *            <code>defaultMessageHost</code>.
	 * @throws Exception
	 *             If error occurs.
	 * @return True if the messages was sent ok, false otherwhise.
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

	/*
	 * Example use of the java client of Daedalus.
	 */
	public static void main(String args[]) throws Exception {
		new DaedalusClient().sendMessage("TEST MESSAGE FROM JAVA",
				Severity.INFO, "testhost", "testapp");
	}
}
