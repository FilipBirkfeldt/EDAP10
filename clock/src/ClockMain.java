import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import clock.io.MonitorThreadHandler;

import java.time.LocalTime; 
import java.util.concurrent.Semaphore;

public class ClockMain {
	
	// 1.  Trådar - Generell_TidsTråd, AlarmTråd, UserInputThread
	// 2.  Datan som delas - Alarmet 
	// 3.  Check 
	// - Alarmet, kolla på tiden & userInput (Get/set) 
	// - I Monitor
	// 4. Main-metoden 
	// 5. Semafore behövs för att sätta tid & alarm. 
	
    public static void main(String[] args) throws InterruptedException {
        
    	// Emulator - GUI (fick den här koden)  
    	AlarmClockEmulator emulator = new AlarmClockEmulator();
        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        
        LocalTime now = java.time.LocalTime.now(); 
        int currentTime = (now.getHour()*10000 + now.getMinute()*100+now.getSecond()); 
        
        
        // Tråd för att låta tiden rulla 
        MonitorThreadHandler disp_time = new MonitorThreadHandler(out);
        disp_time.displayTime(); 
        
        
        
        
        out.displayTime(15, 2, 37);   // arbitrary time: just an example

        
        // Del I1
        // Semaphore - acquire() & release()  ( in-user input) 
        Semaphore sem = in.getSemaphore();
        
        while (true) {
        	// vänta på user-input
        	sem.acquire();
            UserInput userInput = in.getUserInput();
            int choice = userInput.getChoice();
            int h = userInput.getHours();
            int m = userInput.getMinutes();
            int s = userInput.getSeconds();

            System.out.println("choice=" + choice + " h=" + h + " m=" + m + " s=" + s);
        }
    }
}
