import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AlarmSystem implements ChangeListener {
	private DataModel model;
	private ArrayList<Event> data;
	private ArrayList<Alarm> alarms;
	private final int DELAY = 30000;
	private Timer t = new Timer(DELAY, event -> {
		alarms();
	});
	public AlarmSystem(DataModel model) {
		this.model = model;
		data = model.getData();
		alarms = new ArrayList<>();
		createAlarms();
		if (alarms.size()!=0) {
			t.start();
		}
	}

	public void createAlarms() {
		LocalDate today = LocalDate.now();
		for (Event s : data) { // extracts today's event list
			if ((today.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())
					&& today.getDayOfYear() == s.getStartingTotalDays() && today.getYear() == s.getYear())
					|| today.getDayOfWeek().name().equals(s.getDayOfWeek().toUpperCase())
							&& (today.getDayOfYear() >= s.getStartingTotalDays()
									&& today.getDayOfYear() <= s.getEndingTotalDays()
									&& today.getYear() == s.getYear())) {
				// filter only for today's events
				alarms.add(new Alarm(s));
			}
		}
	}

	public void alarms() {
		for (Alarm a : alarms) {
			a.alarm();
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		this.data = model.getData();
		alarms = new ArrayList<>();
		createAlarms();
		t.restart();
	}

}
