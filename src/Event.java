
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Event holds an event with an event name and a time interval. The event can compare and contain
 * sorting methods to organize a list of events.
 */
public class Event implements Comparable<Event> {
	private TimeInterval time;
	private String eventName;
	private String dayOfWeek;
	private String startingMonth;
	private int startingIntMonth;
	private String endingMonth;
	private int endingIntMonth;
	private int startingDayOfMonth;
	private int endingDayOfMonth;
	private int startingTotalDays; // starting total days in a year OR day of the event if not an interval
	private int endingTotalDays; // ending total days in a year
	private int year;
	
	/**
	 * Constructs an event containing event name and a time interval.
	 * @param eventName reads an event name
	 * @param time reads a time interval
	 * precondition: time needs to have the structure of a TimeInterval and cannot be null.
	 */
	public Event(String eventName, TimeInterval time) {
		this.time = time;
		this.eventName = eventName;
		startingMonth = time.getStartingMonth();
		endingMonth = time.getEndingMonth();
		dayOfWeek = time.getDayOfWeek();
		this.startingTotalDays = time.getStartingTotalDay();
		this.endingTotalDays = time.getEndingTotalDay();
		this.startingIntMonth = time.getStartingIntMonth();
		this.endingIntMonth = time.getEndingIntMonth();
		this.startingDayOfMonth = time.getStartingDayOfMonth();
		this.endingDayOfMonth = time.getEndingDayOfMonth();
		this.year = time.getYear();
	}
	/**
	 * Retrieve the day of week in string format
	 * @return dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	/**
	 * Retrieve the starting hour
	 * @return time.getStartingHout
	 */
	public int getStartingHour() {
		return time.getStartingHour();
	}
	/**
	 * Retrieves the event name.
	 * @return the event name
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * Retrieves the event's year.
	 * @return the event's year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Retrieve the interval's starting day in a certain month.
	 * @return the interval's starting day in a certain month
	 */
	public int getStartingDayOfMonth() {
		return startingDayOfMonth;
	}

	/**
	 * Retrieve the interval's ending day in a certain month.
	 * @return the interval's starting day in a certain month
	 */
	public int getEndingDayOfMonth() {
		return endingDayOfMonth;
	}

	/**
	 * Retrieve the interval's starting month.
	 * @return the interval's starting month.
	 */
	public int getStartingIntMonth() {
		return startingIntMonth;
	}

	/**
	 * Retrieve the interval's ending month.
	 * @return the interval's ending month.
	 */
	public int getEndingIntMonth() {
		return endingIntMonth;
	}

	/**
	 * Retrieve the interval's starting day in a certain year.
	 * @return the interval's starting day in a year
	 */
	public int getStartingTotalDays() {
		return startingTotalDays;
	}

	/**
	 * Retrieve the interval's ending day in a certain year.
	 * @return the interval's ending day in a year
	 */
	public int getEndingTotalDays() {
		return endingTotalDays;
	}

	/**
	 * Creates a string format of the event to print out its time interval and name depending
	 * on whether it is a one-time event or a regular event.
	 * @return the string format of the event
	 */
	public String toString() {
		if (time.getStartingTotalDay() != time.getEndingTotalDay()) {
			return dayOfWeek + " " + startingMonth + " " + time.getStartingDayOfMonth() + " " + endingMonth + " "
					+ time.getEndingDayOfMonth() + " " + time.toString() + " " + eventName;
		}
		else {
			return dayOfWeek + " " + startingMonth + " " + time.getStartingDayOfMonth() + " " + time.toString() + " " + eventName;
		}
	}

	public String printToFile() {
		String min="";
		String endMin="";
		if(time.getStartingMinute()==0) {
			min="00";
		}else {
			min=""+time.getStartingMinute();
		}
		if(time.getEndingMinute()==0) {
			endMin="00";
		}else {
			endMin=""+time.getEndingMinute();
		}
		if (time.getStartingTotalDay() != time.getEndingTotalDay()) {
			return eventName+"\n"+dayOfWeek + " " + time.getStartingHour()+":"+min+" "+
					time.getEndingHour()+":"+endMin+" "+time.getStartingIntMonth()+"/"+ time.getStartingDayOfMonth()+"/"+
					time.getYear()+" "+time.getEndingIntMonth()+"/"+ time.getEndingDayOfMonth()+"/"+
					time.getYear();
		}
		else {
			return eventName+"\n" + time.getStartingIntMonth()+"/"+ time.getStartingDayOfMonth()+"/"+
					time.getYear()+" "+time.getStartingHour()+":"+ min+" "+ time.getEndingHour()+":"+
					endMin;
		}
	}
	
	/**
	 * Retrieve the time interval of the event.
	 * @return the time interval of the event
	 */
	public TimeInterval getTimeInterval() {
		return time;
	}
	
	/**
	 * Check and compare an event's time interval with another event's time interval for overlapping.
	 * @param other reads the other time interval to be compared
	 * @return true if an overlapping is found and false if otherwise
	 * precondition other needs to have the structure of TimeInterval and cannot be null
	 */
	public boolean overlappingTime(TimeInterval other) {
		DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
		int year = Integer.valueOf(yearFormatter.format(this.getTimeInterval().getStartingLocalDateTime()));
		if ((this.time.getTotalStartingMinutes() <= other.getTotalStartingMinutes()
				&& this.time.getTotalEndingMinutes() >= other.getTotalStartingMinutes()
				&& other.getTotalStartingMinutes() <= this.time.getTotalEndingMinutes()
				&& other.getTotalEndingMinutes() >= this.time.getTotalEndingMinutes()
				&& this.time.getStartingTotalDay() <= other.getStartingTotalDay()
				&& this.time.getEndingTotalDay() >= other.getStartingTotalDay()
				&& other.getStartingTotalDay() <= this.time.getEndingTotalDay()
				&& other.getEndingTotalDay() >= this.time.getEndingTotalDay()	
				&& (this.time.getYear() == other.getYear() || year == other.getYear()))
				|| (other.getTotalStartingMinutes() <= this.time.getTotalStartingMinutes()
						&& other.getTotalEndingMinutes() >= this.time.getTotalStartingMinutes()
						&& this.time.getTotalStartingMinutes()<=other.getTotalEndingMinutes())
						&& this.time.getTotalEndingMinutes()>=other.getTotalEndingMinutes()
						&& other.getStartingTotalDay() <= this.time.getStartingTotalDay()
						&& other.getEndingTotalDay() >= this.time.getStartingTotalDay()
						&& this.time.getStartingTotalDay() <= other.getEndingTotalDay()
						&& this.time.getEndingTotalDay() >= other.getEndingTotalDay()
						&& (this.time.getYear() == other.getYear() || year == other.getYear())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Calculate and return the hash code for an event for potentially sorting an event in a list.
	 * @return the hash code of the event
	 */
	public int hashCode() {
		return eventName.hashCode() + getStartingTotalDays() + getYear();
	}
	
	/**
	 * Calculate and return an integer used for potentially sorting an event in a list. This method
	 * compares an event with another firstly by year, then by day of year, and finally by event name.
	 * @return a negative integer if this event comes before the other event, a 0 if it is equal to the event,
	 * or a positive integer if it comes after the event.
	 * precondition: that event cannot be null and it needs to have an event's structure
	 */
	public int compareTo(Event that) {
		int compareByYear = that.getYear() - this.getYear();
		if (compareByYear != 0) {
			return compareByYear;
		} else {
			int compareByDay = this.getStartingTotalDays() - that.getStartingTotalDays();
			if (compareByDay != 0)
				return compareByDay;
			else {
				return this.eventName.hashCode() - that.eventName.hashCode();
			}
		}
	}

	/**
	 * Compares an event to another event for sorting purpose.
	 * @return boolean true if this event calls compareTo another event and retrieve a 0 or false if otherwise
	 */
	public boolean equals(Object x) {
		Event that = (Event) x;
		return this.compareTo(that) == 0;
	}

	/**
	 * Compares an event name and event date with another event.
	 * @param x reads an event
	 * @return true if an event is equal to another based on day and name or false if otherwise
	 * precondition: event x must have the structure of an event and it cannot be null.
	 */
	public boolean equalsEvent(Event x) {
		if (this.getYear() == x.getYear() && this.getStartingTotalDays() == x.getStartingTotalDays()
				&& this.getEventName().equals(x.getEventName())) {
			return true;
		}
		return false;
	}

}
