package wash.control;
import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

	// FRÅGOR
	// HEAT, FILL, DRAIN, LOCK, SPIN-OFF, SPIN-Left, SPIN-RIGHT, SPIN-Fast 
	// a) which thread would send it? 
	// Lock-main, FILL&DRAIN - WaterController , HEAT-TempController, , SPINs-SpinController
	// b) Which thread should receive it?
	// Ett tvättprogramm, t.ex WP3 
	// c) Does the order need an acknowledgement?
	// 	Nej, trådarna löser det själva. 
	// Låsa innan vi fyller med vatten 
	
	
    public static void main(String[] args) throws InterruptedException {
    	
    	WashingProgram3 wp3 = null; 
    	WashingProgram1 wp1 = null; 
    	
        WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);
        
        WashingIO io = sim.startSimulation();

        TemperatureController temp = new TemperatureController(io);
        WaterController water = new WaterController(io);
        SpinController spin = new SpinController(io);

        temp.start();
        water.start();
        spin.start();

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);
            
            if (n==1) {
            	wp1 = new WashingProgram1(io, temp, water, spin);
            	wp1.start(); 
            }
            
            if (n==3) {
            	wp3 = new WashingProgram3(io, temp, water, spin);
            	wp3.start();
            	
            	
            }
            
            if (n==0) {
            	temp.send(new WashingMessage(wp3, WashingMessage.TEMP_IDLE));
                water.send(new WashingMessage(wp3, WashingMessage.WATER_IDLE));
                spin.send(new WashingMessage(wp3, WashingMessage.SPIN_OFF));
            }

            // TODO:
            // if the user presses buttons 1-3, start a washing program
            // if the user presses button 0, and a program has been started, stop it
        }
        
    }
};
