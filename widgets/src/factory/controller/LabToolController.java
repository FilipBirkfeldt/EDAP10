package factory.controller;

import factory.model.DigitalSignal;
import factory.model.WidgetKind;
import factory.simulation.Painter;
import factory.simulation.Press;
import factory.swingview.Factory;

/**
 * Implementation of the ToolController interface, to be used for the Widget
 * Factory lab.
 * 
 * @see ToolController
 */
public class LabToolController implements ToolController {
	private final DigitalSignal conveyor, press, paint;
	private final long pressingMillis, paintingMillis;
	private boolean pressStatus = false;
	private boolean paintStatus = false;

	public LabToolController(DigitalSignal conveyor, DigitalSignal press, DigitalSignal paint, long pressingMillis,
			long paintingMillis) {
		this.conveyor = conveyor;
		this.press = press;
		this.paint = paint;
		this.pressingMillis = pressingMillis;
		this.paintingMillis = paintingMillis;
	}

	@Override
	public synchronized void onPressSensorHigh(WidgetKind widgetKind) throws InterruptedException {
		// TODO: you will need to modify this method.
		//
		// Note that this method can be called concurrently with onPaintSensorHigh
		// (that is, in a separate thread).
		if (widgetKind == WidgetKind.BLUE_RECTANGULAR_WIDGET) {
			conveyOff();
			pressOn(); // Sätter pressStatus till true ;
			// Thread.sleep(pressingMillis);
			// wait(pressingMillis);
			waitOutside(pressingMillis); 
			pressOff(); // Stänger av pressen
			// Thread.sleep(pressingMillis); // press needs this time to retract
			// wait(pressingMillis);
			waitOutside(pressingMillis); 
			pressDone(); // sätter pressStatus till false
			conveyOn(); // Metod som kollar om är sanna - sätter bara på banden om både signalerna är
						// false

		}
	}

	@Override
	public synchronized void onPaintSensorHigh(WidgetKind widgetKind) throws InterruptedException {
		// TODO: you will need to modify this method.
		// Note that this method can be called concurrently with onPressSensorHigh
		// (that is, in a separate thread).
		if (widgetKind == WidgetKind.ORANGE_ROUND_WIDGET) {
			conveyOff();
			paintOn(); // Sätter på paint och paintStatus till True
			// Thread.sleep(paintingMillis);
			//wait(paintingMillis);
			waitOutside(paintingMillis); 
			paintOff(); // stänger av paint
			paintDone(); // paintStatus till off
			conveyOn(); // Metod som kollar om är sanna - sätter bara på banden om både signalerna är
						// false
		}
	}

	private synchronized void pressOn() {
		pressStatus = true;
		press.on();
	}

	private synchronized void pressOff() {
		press.off();
	}

	private synchronized void pressDone() {
		pressStatus = false;
	}

	private synchronized void paintOn() throws InterruptedException {
		paintStatus = true;
		paint.on();
	}

	private synchronized void paintOff() throws InterruptedException {
		paint.off();
	}

	private synchronized void paintDone() throws InterruptedException {
		paintStatus = false;
	}

	// Metod som kollar om är sanna - sätter bara på banden om både signalerna är
	// false
	// Om ngn är sann så är bandet stilla
	private synchronized void conveyOn() {
		if (paintStatus || pressStatus) {

		} else {
			conveyor.on();
		}
	}

	private synchronized void conveyOff() {
		conveyor.off();
	}

	/** Helper method: wait outside of monitor for 'millis' milliseconds. */
	private void waitOutside(long millis) throws InterruptedException { 
	long timeToWakeUp = System.currentTimeMillis() + millis;
	// 
	while (System.currentTimeMillis()<timeToWakeUp) {
		long dt = timeToWakeUp-System.currentTimeMillis(); 
		wait(dt);
	// ...
		} 
	}

	// -----------------------------------------------------------------------

	public static void main(String[] args) {
		Factory factory = new Factory();
		ToolController toolController = new LabToolController(factory.getConveyor(), factory.getPress(),
				factory.getPaint(), Press.PRESSING_MILLIS, Painter.PAINTING_MILLIS);
		factory.startSimulation(toolController);

	}
}

// REFLEKTION _ TÅG 
// 1. Segments - för att kolla om tågen kunde åka eller ej (om det fanns plats) 
// 2. De väntar: i run metoden kallas metoden checkFreeWay. om ej free -> wait(); 
// 3. För att tågens attribut var exklusiva för dem själva; de skapades som tågobjekt! (tågklassen) 
// 4. Om två tåg båda kallar på waitUntilFree() samtidigt -> Då kan de komma vidare till nästa -> krock 
// 		Vänta med att sätta markSegmentBusy() i waitUntilFree() -> synchronized. 
// 5. För att banan inte tillåter det! (deadlock)
// 		4st - då tilläts dem att köra förbi (kan) 
// 6. För att koden just nu kollar bara på ett segment frammåt i tiden. Om de åker åt olika håll så 
//	 kommer båda se att det är fritt fram till nästa segment -> sen smäll 

// REFLEKTION - FACTORY !
// 7. För då är det bara en tråd som behöver göra ngt 
// 8. De tävlar både om att nå conveyor 
// 9. Speed. De väntade på vanrandra när de hade kunnat köra samtidigt 
// 10. De lägger den ena tråden utanför monitorn, sleep låser tråden så att andra trådar inte kan köra  
// fråga om det erroret som uppkom då man använde wait(tid); 

// GENERAL 
// 11. 
// - Race condition : exemplen på bankkonton (när man ser att olika trådar tävlar i kritiska regioner/ den delade datan inte uppför sig korrekt) 
// - DeadLock : när tråd1 går in i en metod som gör att tråd 2 väntar men tråd 1 inte gör så att tråd 2 kan fortsätta! Inte releasar låset 
// - Busy-Wait : En tråd låser monitorn, & väntar på att tillståndet ska ändras!  

// 12. En tråd som ska vänta på en annan notify() /hjälp på denna
// 13. Man måste köra testet väldigt länge: många olika kombinationer  












