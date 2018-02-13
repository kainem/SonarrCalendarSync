package SonarrCalendarSync;

import java.io.IOException;
import java.net.URISyntaxException;
import types.LogLevel;
import types.SourceHandling;
import java.net.ProtocolException;
import java.util.Calendar;
import types.CalendarApiResults.*;

/**
 *
 * @author Steven Massaro
 */
public class Runner extends Base {

    public Runner() {
    }

    public void Run() {
	log.report("Starting calendar sync", LogLevel.Info);
	try {
	    Calendar startDate = Calendar.getInstance();
	    Calendar endDate = Calendar.getInstance();
	    endDate.add(Calendar.DAY_OF_MONTH, appSettings.DayOffset);

	    types.CalendarApiResults.Calendar[] localResults = readCalendarAPI(localCalendarSettings.Endpoint,
		    startDate,
		    endDate);

	    for (int i = 0; i < localResults.length; i++) {
		String newEventTitle = localResults[i].series.title
			+ " - S" + localResults[i].seasonNumber
			+ "E" + localResults[i].episodeNumber
			+ " - " + localResults[i].title;
		
		String newEventDescription = localResults[i].series.network +
			(localResults[i].overview == null ? "" : ("\n\n" + localResults[i].overview));

		Calendar epEndTime = Calendar.getInstance(); // creates calendar
		epEndTime.setTime(localResults[i].airDateUtc); // sets calendar time/date
		epEndTime.add(Calendar.MINUTE, localResults[i].series.runtime); // adds runtime to starttime
		
		ca.AddEvent(remoteCalendarSettings.Endpoint,
			remoteCalendarSettings.CalendarId,
			String.valueOf(localResults[i].id),
			newEventTitle, 
			newEventDescription,
			localResults[i].airDateUtc,
			epEndTime.getTime(),
			appSettings.TimeZone);
	    }
	} catch (ProtocolException ex) {
	    log.report(ex.toString(), LogLevel.Critical);
	} catch (IOException ex) {
	    log.report(ex.toString(), LogLevel.Critical);
	} catch (Exception ex){
	    log.report(ex.toString(), LogLevel.Critical);
	} finally {
	    log.report("Finished calendar sync", LogLevel.Info);
	}
    }

    private types.CalendarApiResults.Calendar[] readCalendarAPI(String endpoint, Calendar startDate, Calendar endDate) {
	try {
	    return sa.GetCalendar(endpoint, startDate.getTime(), endDate.getTime());
	} catch (URISyntaxException ex) {
	    log.report(ex.getMessage(), LogLevel.Critical, " - " + endpoint, SourceHandling.Append);
	} catch (IOException ex) {
	    log.report(ex.getMessage(), LogLevel.Critical, " - " + endpoint, SourceHandling.Append);
	}
	return null;
    }
}
