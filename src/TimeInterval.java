/**
* MyCalendar
* @EthanHuynh
* @1.0 5/4/19
*/


import java.time.*;
import java.time.format.DateTimeFormatter;
/**
 * A time interval that holds the interval of starting time to ending time. The interval
 * of time allows users to retrieve specific starting and ending's days and hours.
 */
public class TimeInterval {
	private int startingHour;
	private int endingHour;
	private int startingMinute;
	private int endingMinute;
	private int startingTotalMinutes;
	private int endingTotalMinutes;
	private int startingTotalDay;
	private int endingTotalDay;
	private int intDayOfWeek;
	private String dayOfWeek;
	private int startingIntMonth;
	private String startingStringMonth;
	private int endingIntMonth;
	private String endingStringMonth;
	private int startingDayOfMonth;
	private int endingDayOfMonth;
	private LocalDateTime starting;
	private LocalDateTime ending;
	private int year;
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");

	/**
	 * Construct a time interval from starting to ending time in LocalDateTime format.
	 * @param starting reads starting LocalDateTime
	 * @param ending reads ending LocalDateTime
	 * precondition: starting and ending LocalDateTime cannot be null and must have
	 * structure of LocalDateTime.
	 */
	public TimeInterval(LocalDateTime starting, LocalDateTime ending) {
		
		startingTotalDay = starting.getDayOfYear();
		endingTotalDay = ending.getDayOfYear();
		startingTotalMinutes = starting.getHour() * 60 + starting.getMinute();
		endingTotalMinutes = ending.getHour() * 60 + ending.getMinute();
		startingMinute = starting.getMinute();
		endingMinute = ending.getMinute();
		startingHour = starting.getHour();
		endingHour = ending.getHour();
		if (!isLegal()) {
			this.starting = null;
			this.ending = null;
		}
		
		intDayOfWeek = starting.getDayOfWeek().getValue();
		startingIntMonth = starting.getMonth().getValue();
		endingIntMonth = ending.getMonth().getValue();
		startingDayOfMonth = starting.getDayOfMonth();
		endingDayOfMonth = ending.getDayOfMonth();
		year = starting.getYear();
		this.starting = starting;
		this.ending = ending;
	}
	
	/**
	 * Retrieves the LocalDateTime of the starting time in the interval
	 * @return the LocalDateTime of the starting time in the interval
	 */
	public LocalDateTime getStartingLocalDateTime() {
		return starting;
	}
	
	//public void setEndingLocalDateTime(LocalDateTime newEnd) {
	//	ending = newEnd;
	//}
	
	/**
	 * Retrieve the interval's starting day in a year.
	 * @return the interval's starting day in a year
	 */
	public int getStartingTotalDay() {
		return startingTotalDay;
	}
	
	/**
	 * Retrieve the interval's ending day in a year.
	 * @return the interval's ending day in a year
	 */
	public int getEndingTotalDay() {
		return endingTotalDay;
	}

	/**
	 * Checks whether the ending time happens after the starting time.
	 * @return true if ending time happens after starting time or false if otherwise
	 */
	public boolean isLegal() {
		if (endingTotalMinutes - startingTotalMinutes > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Retrieve the interval's year.
	 * @return the interval's year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Retrieve the interval's starting hour in a day.
	 * @return the interval's starting hour in a day.
	 */
	public int getStartingHour() {
		return startingHour;
	}
	
	/**
	 * Retrieve the interval's ending hour in a day.
	 * @return the interval's ending hour in a day
	 */
	public int getEndingHour() {
		return endingHour;
	}
	
	/**
	 * Retrieve the interval's starting minute in a day.
	 * @return the interval's starting minute in a day
	 */
	public int getStartingMinute() {
		return startingMinute;
	}
	
	/**
	 * Retrieve the interval's ending minute in a day.
	 * @return the interval's ending minute in a day
	 */
	public int getEndingMinute() {
		return endingMinute;
	}
	
	/**
	 * Retrieve the interval's starting day in a month.
	 * @return the interval's starting day in a month
	 */
	public int getStartingDayOfMonth() {
		return startingDayOfMonth;
	}
	
	/**
	 * Retrieve the interval's ending day in a month.
	 * @return the interval's ending day in a month
	 */
	public int getEndingDayOfMonth() {
		return endingDayOfMonth;
	}
	
	/**
	 * Retrieve the interval's starting month in integer format.
	 * @return the interval's starting month as an integer
	 */
	public int getStartingIntMonth() {
		return startingIntMonth;
	}
	
	/**
	 * Retrieve the interval's ending month in integer format.
	 * @return the interval's ending month as an integer
	 */
	public int getEndingIntMonth() {
		return endingIntMonth;
	}
	
	/**
	 * Retrieve the interval's starting month in string format.
	 * @return the interval starting month as a string
	 */
	public String getStartingMonth() {
		if (startingIntMonth == 1)
			startingStringMonth="January";
		else if(startingIntMonth ==2)
			startingStringMonth="February";
		else if(startingIntMonth ==3)
			startingStringMonth="March";
		else if(startingIntMonth ==4)
			startingStringMonth="April";
		else if(startingIntMonth == 5)
			startingStringMonth="May";
		else if(startingIntMonth ==6)
			startingStringMonth="June";
		else if(startingIntMonth == 7)
			startingStringMonth="July";
		else if(startingIntMonth == 8)
			startingStringMonth="August";
		else if(startingIntMonth==9)
			startingStringMonth="September";
		else if(startingIntMonth==10)
			startingStringMonth="October";
		else if(startingIntMonth==11)
			startingStringMonth="November";
		else
			startingStringMonth="December";
		return startingStringMonth;
	}
	
	/**
	 * Retrieve the integer's ending month in string format.
	 * @return the ending month as a string
	 */
	public String getEndingMonth() {
		if (endingIntMonth == 1)
			endingStringMonth="- January";
		else if(endingIntMonth ==2)
			endingStringMonth="- February";
		else if(endingIntMonth ==3)
			endingStringMonth="- March";
		else if(endingIntMonth ==4)
			endingStringMonth="- April";
		else if(endingIntMonth == 5)
			endingStringMonth="- May";
		else if(endingIntMonth ==6)
			endingStringMonth="- June";
		else if(endingIntMonth == 7)
			endingStringMonth="- July";
		else if(endingIntMonth == 8)
			endingStringMonth="- August";
		else if(endingIntMonth==9)
			endingStringMonth="- September";
		else if(endingIntMonth==10)
			endingStringMonth="- October";
		else if(endingIntMonth==11)
			endingStringMonth="- November";
		else
			endingStringMonth="- December";
		return endingStringMonth;
	}
	
	/**
	 * Retrieve integer representation of the day of week.
	 * @return the integer representation of the day of week
	 */
	public int getIntDayOfWeek() {
		return intDayOfWeek;
	}
	
	/**
	 * Retrieve the day of week of the interval's starting day in string format.
	 * @return the day of week of the interval's starting day in string format
	 */
	public String getDayOfWeek() {
		if (intDayOfWeek == 1)
			dayOfWeek="Monday";
		else if(intDayOfWeek ==2)
			dayOfWeek="Tuesday";
		else if(intDayOfWeek ==3)
			dayOfWeek="Wednesday";
		else if(intDayOfWeek ==4)
			dayOfWeek="Thursday";
		else if(intDayOfWeek == 5)
			dayOfWeek="Friday";
		else if(intDayOfWeek ==6)
			dayOfWeek="Saturday";
		else
			dayOfWeek="Sunday";
		return dayOfWeek;
	}
	
	/**
	 * Retrieve the interval's starting time in minutes.
	 * @return the interval's starting time in minutes
	 */
	public int getTotalStartingMinutes() {
		return startingTotalMinutes;
	}

	/**
	 * Retrieve the interval's ending time in minutes.
	 * @return the interval's ending time in minutes
	 */
	public int getTotalEndingMinutes() {
		return endingTotalMinutes;
	}

	/**
	 * Retrieve the time interval in string format.
	 * @return the time interval in string format
	 */
	public String toString() {
		return timeFormatter.format(starting)+" - "+timeFormatter.format(ending);
	}
}
