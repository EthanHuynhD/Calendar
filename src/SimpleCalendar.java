/**
* MyCalendar
* @EthanHuynh
* @1.0 5/4/19
*/


import java.util.*;
import java.util.concurrent.*;

import javax.swing.JFrame;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * A calendar tester that runs and call methods from the MyCalendar, Event, and
 * TimeInterval classes.
 */
public class SimpleCalendar {
	public static void main(String[]args) {
		File file = new File("C:/Users/trung/OneDrive/Desktop/Java/CS151HW4/events.txt");
		try {
			ExecutorService service=Executors.newCachedThreadPool();
			DataModel model = new DataModel(file);
	        service.execute(calenderRun(model));	
			service.execute(alarmRun(model));
			service.shutdown();
		} catch (FileNotFoundException x ){
			DataModel model = new DataModel();
			MainFrame main = new MainFrame(model);
		} finally {
			System.out.println("Done");
		}
	}
	public static Runnable calenderRun(DataModel model) {
		
		return()->{
			MainFrame main = new MainFrame(model);
				
		};
	}
	public static Runnable alarmRun(DataModel model){
		return()->{
			AlarmSystem alarmSystem=new AlarmSystem(model);
			model.attach(alarmSystem);
		};
	}
}