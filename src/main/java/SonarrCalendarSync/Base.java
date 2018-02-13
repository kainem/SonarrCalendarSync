package SonarrCalendarSync;

import Log.EndpointLog;
import com.google.gson.Gson;
import cronofy.CronofyAPI;
import SonarrConnector.SonarrAPI;
import types.ReportingLevel;
import types.Settings.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Steven Massaro
 */
abstract public class Base {

	public EndpointLog log;
	public SonarrAPI sa;
	public CronofyAPI ca;
	public Gson g = new Gson();
	private Properties prefs;

	// --- SETTINGS ---
	public Logging logSettings = new Logging();
	public Calendar localCalendarSettings = new Calendar();
	public Calendar remoteCalendarSettings = new Calendar();
	public Application appSettings = new Application();

	public static String workingDirectory = System.getProperty("user.dir");

	public Base() {
		// load preferences file
		this.prefs = new Properties();
		try {
//			this.prefs.load(new FileInputStream(new File(workingDirectory + "\\src\\SonarrCalendarSync\\prefs.properties")));
			this.prefs.load(new FileInputStream(new File(workingDirectory + "\\prefs.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// load application settings
		this.appSettings.DayOffset = Integer.parseInt(prefs.getProperty("DayOffset").trim());
		this.appSettings.TimeZone = prefs.getProperty("TimeZone");

		// load logging settings
		this.logSettings.LogAPIEndpoint = prefs.getProperty("L_APIEndpoint");
		this.logSettings.LogAPIKey = prefs.getProperty("L_APIKey");
		this.logSettings.LogReportingLevel = ReportingLevel.valueOf(prefs.getProperty("L_ReportingLevel").trim());

		// load calendar settings
		this.localCalendarSettings.Name = prefs.getProperty("LC_Name");
		this.localCalendarSettings.APIKey = prefs.getProperty("LC_APIKey");
		this.localCalendarSettings.Endpoint = prefs.getProperty("LC_Endpoint");
		this.remoteCalendarSettings.Name = prefs.getProperty("RC_Name");
		this.remoteCalendarSettings.APIKey = prefs.getProperty("RC_APIKey");
		this.remoteCalendarSettings.Endpoint = prefs.getProperty("RC_Endpoint");
		this.remoteCalendarSettings.CalendarId = prefs.getProperty("RC_CalendarId");

		this.log = new EndpointLog(this.logSettings.LogAPIEndpoint,
				this.logSettings.LogAPIKey,
				"Sonarr Calendar Sync",
				this.logSettings.LogReportingLevel);

		this.sa = new SonarrAPI(this.localCalendarSettings.APIKey, log);

		this.ca = new CronofyAPI(this.remoteCalendarSettings.APIKey, log);
	}
}
