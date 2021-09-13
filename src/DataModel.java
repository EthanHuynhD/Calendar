import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.event.*;
/**
 * @author EthanHuynh
 * The DataModel class contains a collection of events (data), a collection of ChangeListener,
 * accessors, and mutators.
 */
public class DataModel {
	private ArrayList<Event> data = new ArrayList<>();;
	private ArrayList<ChangeListener> listeners;
	private Calendar current = new GregorianCalendar();;
	private String eventName;
	private String secondLine;
	private String parts[];
	private LocalDateTime starting;
	private LocalTime startingTime;
	private LocalDate startingDate;
	private LocalDateTime ending;
	private LocalDate endingDate;
	private LocalTime endingTime;
	private LocalDate currentTime = LocalDate.now();;
	private String dayOfWeek;
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/y");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("k:m");
	private DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yy");
	private TimeInterval eventTime; // need this to save the current month
	private Event anEvent;
	/**
	 * Construct a dataModel that takes in a file and retrieve data from the file.
	 * @param file
	 * @throws FileNotFoundException
	 */
	public DataModel(File file) throws FileNotFoundException {
		Scanner in = new Scanner(file);
		while (in.hasNextLine()) {
			eventName = in.nextLine();
			secondLine = in.nextLine();
			parts = secondLine.split(" ");
			if (parts.length > 3) {
				dayOfWeek = parts[0];
				startingDate = LocalDate.parse(parts[3], dateFormatter);
				startingTime = LocalTime.parse(parts[1], timeFormatter);
				starting = LocalDateTime.of(startingDate, startingTime);
				endingDate = LocalDate.parse(parts[4], dateFormatter);
				endingTime = LocalTime.parse(parts[2], timeFormatter);
				ending = LocalDateTime.of(endingDate, endingTime);
				eventTime = new TimeInterval(starting, ending);
				anEvent = new Event(eventName, eventTime);
				data.add(anEvent);
			} else {
				startingDate = LocalDate.parse(parts[0], dateFormatter);
				startingTime = LocalTime.parse(parts[1], timeFormatter);
				starting = LocalDateTime.of(startingDate, startingTime);
				endingDate = LocalDate.parse(parts[0], dateFormatter);
				endingTime = LocalTime.parse(parts[2], timeFormatter);
				ending = LocalDateTime.of(endingDate, endingTime);
				eventTime = new TimeInterval(starting, ending);
				anEvent = new Event(eventName, eventTime);
				data.add(anEvent);
			}
		}
		in.close();
		Collections.sort(data);
		System.out.println("Done");
		listeners = new ArrayList<>();
	}
	/**
	 * Construct blank dataModel without taking in a file.
	 */
	public DataModel() {
		listeners = new ArrayList<>();
	}
	/**
	 * Retrieve the data source (events).
	 * @return data
	 */
	public ArrayList<Event> getData() {
		return data;
	}
	/**
	 * Attach a ChangeListener to the collection fo ChangeListeners
	 * @param l takes in a ChangeListener
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}
	/**
	 * Retrieve the current time of the data source.
	 * @return currentTime
	 */
	public LocalDate getCalendar() {
		return currentTime;
	}
	/**
	 * Change the current time and notify the collection of ChangeListeners
	 * @param c takes in a LocalDate
	 */
	public void setCalendar(LocalDate c) {
		currentTime = c;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	/**
	 * Add an event to the data of the data source.
	 * @param e takes in an Event
	 */
	public void addEvent(Event e) {
		data.add(e);
		Collections.sort(data);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	/**
	 * Remove an event from the data source.
	 */
	public void removeEvent(Event e) {
		data.remove(e);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}
