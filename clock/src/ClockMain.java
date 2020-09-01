import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

import java.time.LocalTime; 
import java.util.concurrent.Semaphore;

public class ClockMain {
	
	// Trådar - Generella tiden, 

    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();

        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        LocalTime now = java.time.LocalTime.now(); 
        int currentTime = (now.getHour()*10000 + now.getMinute()*100+now.getSecond()); 
        
        
        // Tråd för att låta tiden rulla 
        TimeThread = timeThread = new TimeThread(clockMinitor, out); 
        
        
        
        out.displayTime(15, 2, 37);   // arbitrary time: just an example

        
        // Del I1
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
