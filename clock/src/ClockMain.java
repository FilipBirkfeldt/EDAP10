import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import clock.io.MonitorThreadHandler;
import clock.io.TimeThread;

import java.time.LocalTime;
import java.util.concurrent.Semaphore;

public class ClockMain {

	// 1. Trådar - Generell_TidsTråd som kollar tiden och alarmtiden
	// 2. Datan som delas via monitor - tiden som ska stå på displayen, alarmtid och alarmet
	// 3. Lock operations for race conditions 
	// - I Monitor
	// 4. Monitor, speciellt uppdatera tiden
	// 5. Semafore behövs för att sätta tid & alarm. Även behövs fler semaphores om man skulle ha två trådar
	// -  för att kolla tid och kolla alarm.
	
	// R1 - För att de delade metoderna inte ska köras samtidigt
	//  2 - Se monitor. 
	//	3 - Mellan trådar kan användas genom en Semaphore(0) där ena tråden releasar och andra acquirar.
	//	4 - För att ha kolla på all data och sköta all signalering. 
	//	5 - En låst tråd körs inte tills någon releasar en Semaphore. 
	// 	6 - Två trådar försökte nå samma metod samtidigt. Löstes med mutual exclusion.
	//	7 - För att tiden det tar för att köra metoderna är väldigt kort och då behövs mycket data för att
	//	  - kunna hitta ett eventuellt fel. Tiden räcker ej. 

	public static void main(String[] args) throws InterruptedException {

		// Emulator - GUI (fick den här koden)
		AlarmClockEmulator emulator = new AlarmClockEmulator();
		ClockInput in = emulator.getInput();
		ClockOutput out = emulator.getOutput();
		boolean alarm = false;

		// Tråd för att låta tiden rulla
		MonitorThreadHandler disp_time = new MonitorThreadHandler(out);
		//Semaphore sem1 = new Semaphore(0);
		TimeThread t1 = new TimeThread(disp_time);
		t1.start();

		// out.displayTime(15, 2, 37); // arbitrary time: just an example

		// Del I1
		// Semaphore - acquire() & release() ( in-user input)
		
		Semaphore sem = in.getSemaphore();

		while (true) {
			// vänta på user-input
			
			sem.acquire();
			UserInput userInput = in.getUserInput();
			int choice = userInput.getChoice();

			int h = userInput.getHours();
			int m = userInput.getMinutes();
			int s = userInput.getSeconds();

			if (choice == 1) {
				disp_time.setTime(h, m, s);
			}
			if (choice == 2) {
				disp_time.setAlarmTime(h, m, s);

			}
			if (choice == 3) {
				alarm = !alarm;
				out.setAlarmIndicator(alarm);
				disp_time.alarmOn(alarm);
			}

			System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);
		}
	}
}
