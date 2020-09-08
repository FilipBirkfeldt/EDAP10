import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import clock.io.MonitorThreadHandler;
import clock.io.TimeThread;

import java.time.LocalTime;
import java.util.concurrent.Semaphore;

public class ClockMain {

	// 1. Trådar - Generell_TidsTråd, AlarmTråd, UserInputThread
	// 2. Datan som delas - Alarmet
	// 3. Check
	// - Alarmet, kolla på tiden & userInput (Get/set)
	// - I Monitor
	// 4. Main-metoden
	// 5. Semafore behövs för att sätta tid & alarm.

	public static void main(String[] args) throws InterruptedException {

		// Emulator - GUI (fick den här koden)
		AlarmClockEmulator emulator = new AlarmClockEmulator();
		ClockInput in = emulator.getInput();
		ClockOutput out = emulator.getOutput();
		boolean alarm = false;

		// Tråd för att låta tiden rulla
		MonitorThreadHandler disp_time = new MonitorThreadHandler(out);
		Semaphore sem1 = new Semaphore(0);
		TimeThread t1 = new TimeThread(disp_time, out, sem1);
		//AlarmThread t2 = new AlarmThread(disp_time, in, out, sem1); // kan ta bort denna
		t1.start();
		//t2.start(); // bort

		//

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
				disp_time.alarmOn(false);
			}

			System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);
		}
	}
}
