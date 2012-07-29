package ar.com.hgdeoro.daedalus.client;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

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
 * Log4j appender to send messages to Daedalus.
 * 
 * @author Horacio G. de Oro <hgdeoro@gmail.com>
 * 
 */
public class Log4jSimpleAppender extends AppenderSkeleton {

	protected DaedalusClient daedalusClient = null;

	private String serverHost = null;

	private String serverPort = null;

	private String defaultMessageHost = null;

	private String defaultMessageApplication = null;

	@Override
	protected void append(LoggingEvent event) {
		if (this.daedalusClient == null)
			throw new RuntimeException("Error: daedalusClient is not setted");

		final String message = "" + event.getMessage();
		final Severity severity;
		Level level = event.getLevel();
		if (level.equals(Level.DEBUG) || level.equals(Level.TRACE)
				|| level.equals(Level.ALL))
			severity = Severity.DEBUG;
		else if (level.equals(Level.WARN))
			severity = Severity.WARN;
		else if (level.equals(Level.ERROR) || level.equals(Level.FATAL))
			severity = Severity.ERROR;
		else
			severity = Severity.INFO;

		try {
			this.daedalusClient.sendMessage(message, severity, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void activateOptions() {

		super.activateOptions();
		if (this.daedalusClient != null)
			throw new RuntimeException(
					"Error: daedalusClient is already setted");

		String _serverHost = null;
		int _serverPort = -1;
		String _defaultMessageHost = null;
		String _defaultMessageApplication = null;

		if (this.serverHost != null)
			_serverHost = this.serverHost;
		if (this.serverPort != null)
			_serverPort = Integer.parseInt(this.serverPort);
		if (this.defaultMessageHost != null)
			_defaultMessageHost = this.defaultMessageHost;
		if (this.defaultMessageApplication != null)
			_defaultMessageApplication = this.defaultMessageApplication;

		final DaedalusClient daedalusClient = new DaedalusClient(_serverHost,
				_serverPort, _defaultMessageHost, _defaultMessageApplication);
		this.daedalusClient = daedalusClient;
	}

	/* methods of AppenderSkeleton */

	@Override
	public void close() {
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

	/* getters and setters */

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public void setDefaultMessageHost(String defaultMessageHost) {
		this.defaultMessageHost = defaultMessageHost;
	}

	public void setDefaultMessageApplication(String defaultMessageApplication) {
		this.defaultMessageApplication = defaultMessageApplication;
	}

}
