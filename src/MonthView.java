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
 * Creates a MonthView that is a customized JPanel.
 */
public class MonthView extends JPanel implements ChangeListener {
	private DataModel dataModel;
	private ArrayList<Event> data;
	private LocalDate currentTime;
	private ArrayList<JComponent> components;
	/**
	 * Constructs a MonthView that takes in a dataModel.
	 * @param model receives dataModel
	 */
	public MonthView(DataModel model) {
		setBackground(Color.WHITE);
		this.dataModel = model;
		currentTime = dataModel.getCalendar();
		data = this.dataModel.getData();
		setPreferredSize(new Dimension(440, 200));
		setLayout(new GridLayout(0, 7, 3, 3));
		dataModel.attach(this);
		viewByMonth(currentTime);
	}

	/**
	 * View calendar by month and display dates with existing events using brackets
	 * {day}
	 * 
	 * @param local reads LocalDate
	 * @return a calendar with dates displayed in correct places from Mon-Sun
	 *         precondition: (LocalDate) local needs to be a valid date
	 */
	public void viewByMonth(LocalDate local) {
		components = new ArrayList<>();
		
		ArrayList<String> daysInMonthList = new ArrayList<>();
		LocalDate firstDayOfMonth = LocalDate.of(currentTime.getYear(), currentTime.getMonthValue(), 1);
		int day;
		int month = currentTime.getMonthValue();
		int year = currentTime.getYear();
		// add a space to empty dates
		DayOfWeek enumDayOfWeek = firstDayOfMonth.getDayOfWeek();
		int dayOfWeek = enumDayOfWeek.getValue();
		for (int x = 0; x < dayOfWeek; x++) {
			daysInMonthList.add(" ");
		}
		// add in the dates
		for (int x = 1; x <= currentTime.lengthOfMonth(); x++) {
			day = x;
			boolean addBrackets = false;
			LocalDate currentDate = LocalDate.of(year, month, day);
			for (Event e : data) {
				if (currentDate.getDayOfYear() == e.getStartingTotalDays()
						|| currentDate.getDayOfYear() == e.getEndingTotalDays()) {
					addBrackets = true;
				}
			}
			if (!addBrackets)
				daysInMonthList.add(Integer.toString(x));
			else
				daysInMonthList.add("{" + x + "}");
		}
		final int today=currentTime.getDayOfMonth();
		// skip line every 7 elements
		// Controller
		for (int x = 0; x < daysInMonthList.size(); x++) {
			if (daysInMonthList.get(x) == " ") {
				JLabel newLabel = new JLabel(" ");
				components.add(newLabel);
			} else {
				JButton newButton = new JButton();
				if(currentTime.getDayOfMonth()==Integer.parseInt(daysInMonthList.get(x).replaceAll("[\\D]", ""))) {
					newButton.setBackground(Color.LIGHT_GRAY);
				}
				newButton.setText(daysInMonthList.get(x));
				newButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(Integer.valueOf(newButton.getText().replaceAll("[\\D]", ""))-today>0) {
							dataModel.setCalendar(dataModel.getCalendar().plusDays(Integer.valueOf(newButton.getText().replaceAll("[\\D]", ""))-today));
						} else if(Integer.valueOf(newButton.getText().replaceAll("[\\D]", ""))-today<0) {
							dataModel.setCalendar(dataModel.getCalendar().minusDays(today-Integer.valueOf(newButton.getText().replaceAll("[\\D]", ""))));
						}
					}
				});
				components.add(newButton);
			}
		}
		JLabel title = new JLabel();
		add(title);
		JLabel title1 = new JLabel();
		add(title1);
		JLabel title2 = new JLabel();
		add(title2);
		JLabel title3 = new JLabel();
		add(title3);
		JLabel title4 = new JLabel();
		add(title4);
		JLabel title5 = new JLabel();
		add(title5);
		JLabel title6 = new JLabel();
		add(title6);
		
		title.setFont(new Font("Courier", Font.BOLD, 15));
		add(new JLabel("Su"));
		add(new JLabel("Mo"));
		add(new JLabel("Tu"));
		add(new JLabel("We"));
		add(new JLabel("Th"));
		add(new JLabel("Fr"));
		add(new JLabel("Sa"));
		
		for (int i = 0; i < components.size(); i++) {
			add(components.get(i));
		}
	}
	
	/**
	 * Overrides the paintComponent of the JPanel.
	 * @param g receives graphics
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
		removeAll();
		viewByMonth(dataModel.getCalendar());
		repaint();
	}
}
