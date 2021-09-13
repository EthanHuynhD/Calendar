import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.time.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.*;
import java.util.*;
/**
 * @author EthanHuynh
 * This is a customized JPanel that class comprises of the MonthView.java and the create button as well as its functionality.
 */
public class MonthViewPanel extends JPanel implements ChangeListener {
	private DataModel dataModel;
	private ArrayList<Event> data;
	private LocalDate currentTime;
	private DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("M/d/yyyy");
	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("k:m");
	/**
	 * Construct a MonthView panelto create a button and a MonthView, which is a JPanel to show month view.
	 * @param model takes in the dataModel
	 */
	public MonthViewPanel(DataModel model) {
		this.dataModel = model;
		setBackground(Color.WHITE);
		currentTime = dataModel.getCalendar();
		data = this.dataModel.getData();
		setPreferredSize(new Dimension(440, 100));
		add(BorderLayout.NORTH, new JLabel(month(currentTime), SwingConstants.CENTER));
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame newFrame = new JFrame();
				JPanel n = new JPanel();
				n.setPreferredSize(new Dimension(450, 50));
				n.add(BorderLayout.NORTH, new JLabel("Please Enter the following to create an event:"));
				JTextField eventName = new JTextField("Untitled Event");
				eventName.setPreferredSize(new Dimension(450, 20));
				n.add(BorderLayout.CENTER, eventName);
				newFrame.add(BorderLayout.NORTH, n);
				JPanel pane = new JPanel();
				JLabel date = new JLabel(
						currentTime.getMonthValue() + "/" + currentTime.getDayOfMonth() + "/" + currentTime.getYear());
				date.setPreferredSize(new Dimension(100, 20));
				pane.add(date);
				JTextField from = new JTextField();
				from.setPreferredSize(new Dimension(100, 20));
				pane.add(from);
				JLabel l = new JLabel(" to ");
				pane.add(l);
				JTextField to = new JTextField();
				to.setPreferredSize(new Dimension(100, 20));
				pane.add(to);
				newFrame.add(BorderLayout.WEST, pane);
				JButton b = new JButton("SAVE");
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						LocalDate inputDate = LocalDate.parse(date.getText(), formattedDate);
						String[] fromTime = from.getText().split(":|\\-");
						String[] toTime = to.getText().split(":|\\-");
						if (((from.getText().length() > 10 || from.getText().length() < 8) && fromTime.length != 2)
								|| ((to.getText().length() > 10 || to.getText().length() < 8) && toTime.length != 2)) {
							JOptionPane.showMessageDialog(new JFrame(), "Incorrect input.");
						} else {
							try {
								int beginHour = Integer.valueOf(fromTime[0]);
								int beginMin = Integer.valueOf(fromTime[1]);
								int endHour = Integer.valueOf(toTime[0]);
								int endMin = Integer.valueOf(toTime[1]);
								LocalTime beginTime = LocalTime.parse(from.getText(), timeFormatter);
								LocalTime endTime = LocalTime.parse(to.getText(), timeFormatter);
								if (beginHour != beginTime.getHour() || beginMin != beginTime.getMinute()
										|| endHour != endTime.getHour() || endMin != endTime.getMinute()) {
									JOptionPane.showMessageDialog(new JFrame(), "Incorrect input.");
								} else {
									LocalDateTime beginning = LocalDateTime.of(inputDate, beginTime);
									LocalDateTime ending = LocalDateTime.of(inputDate, endTime);
									TimeInterval interval = new TimeInterval(beginning, ending);
									if (create(eventName.getText(), interval)) {
										JOptionPane.showMessageDialog(new JFrame(), "Event Created!");
										newFrame.dispose();
									} else {
										JOptionPane.showMessageDialog(new JFrame(), "Event already Exists during this time!");
									}
								}
							} catch (NumberFormatException er) {
								JOptionPane.showMessageDialog(new JFrame(), "Incorrect input.");
							}

						}
					}
				});
				newFrame.add(BorderLayout.EAST, b);
				newFrame.pack();
				newFrame.setVisible(true);
			}
		});
		add(BorderLayout.CENTER, createButton);
		dataModel.attach(this);
		MonthView view = new MonthView(dataModel);
		add(BorderLayout.SOUTH, view);
	}

	/**
	 * Create a new event to be added to the calendar.
	 * 
	 * @param title    reads title
	 * @param interval reads TimeInterval
	 * @return boolean true if an event is created and false if an event is not
	 *         created Precondition: interval needs to be valid according to
	 *         TimeInterval and cannot be null
	 */
	public boolean create(String title, TimeInterval interval) {
		Event anEvent = new Event(title, interval);
		boolean overlap = false;
		for (Event e : data) {
			if (anEvent.overlappingTime(e.getTimeInterval())) {
				overlap = true;
			}
		}
		if (overlap) {
			return false;
		} else {
			dataModel.addEvent(anEvent);
			return true;
		}
	}

	/**
	 * View calendar by month and display dates with existing events using brackets
	 * {day}
	 * 
	 * @param local reads LocalDate
	 * @return a calendar with dates displayed in correct places from Mon-Sun
	 *         precondition: (LocalDate) local needs to be a valid date
	 */
	public String month(LocalDate local) {
		currentTime = local;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, yyyy");
		int month = currentTime.getMonthValue();
		int year = currentTime.getYear();
		LocalDate currentYear = LocalDate.of(year, month, 1);
		return formatter.format(currentYear);
	}
	/**
	 * Overrides the stateChanged method from the implementation of
	 * the ChangeListener's method. Receives notification from the data source.
	 * @param g receives the ChangeEvent from the dataModel.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		this.data = dataModel.getData();
		this.currentTime = dataModel.getCalendar();
		((JLabel) getComponent(0)).setText(month(currentTime));
		repaint();
	}
}
