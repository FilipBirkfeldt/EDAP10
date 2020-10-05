import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import client.view.ProgressItem;
import client.view.StatusWindow;
import client.view.WorklistItem;
import network.Sniffer;
import network.SnifferCallback;
import rsa.Factorizer;
import rsa.ProgressTracker;

public class CodeBreaker implements SnifferCallback {
	private final JPanel workList;
	private final JPanel progressList;
	private ExecutorService pool = Executors.newFixedThreadPool(2);
	private final JProgressBar mainProgressBar;

	// -----------------------------------------------------------------------
	private CodeBreaker() {
		StatusWindow w = new StatusWindow();
		workList = w.getWorkList();
		progressList = w.getProgressList();
		mainProgressBar = w.getProgressBar();
		w.enableErrorChecks();
	}

	// -----------------------------------------------------------------------
	public static void main(String[] args) {
		/*
		 * Most Swing operations (such as creating view elements) must be performed in
		 * the Swing EDT (Event Dispatch Thread).
		 * 
		 * That's what SwingUtilities.invokeLater is for.
		 */
		SwingUtilities.invokeLater(() -> {
			CodeBreaker codeBreaker = new CodeBreaker();
			new Sniffer(codeBreaker).start();

		});
	}

	// -----------------------------------------------------------------------
	/** Called by a Sniffer thread when an encrypted message is obtained. */
	@Override
	public void onMessageIntercepted(String message, BigInteger n) {
		SwingUtilities.invokeLater(() -> {

			WorklistItem work = new WorklistItem(n, message);
			workList.add(work);

			// Knappar
			JButton bBreak = new JButton("Break");
			JButton jbRemove = new JButton("Remove");
			JButton bCancel = new JButton("Cancel");
			workList.add(bBreak);

			// WorkList 🙂
			Runnable task = () -> {
				progressList.add(jbRemove);
			};

			ProgressItem progressItem = new ProgressItem(n, message);

			// FutureTask to factorize & run
			ProgressTracker tracker = new Tracker(progressItem, task, mainProgressBar);
			FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
				public String call() {
					try {
						String s = Factorizer.crack(message, n, tracker);
						System.out.println("\nDecryption complete. The message is:\n\n  " + s);
						SwingUtilities.invokeLater(() -> {
							progressItem.getTextArea().setText(s);
							progressList.remove(bCancel);
						});
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

					}
					return null;
				}
			});

			// BREAK addAction
			bBreak.addActionListener(e -> {
				mainProgressBar.setMaximum(mainProgressBar.getMaximum() + 1000000);
				workList.remove(work);
				workList.remove(bBreak);
				progressList.add(progressItem);
				progressList.add(bCancel);
				pool.submit(future);
			});

			// REMOVE ActioListener
			jbRemove.addActionListener(e -> {
				progressList.remove(progressItem);
				progressList.remove(jbRemove);
				progressList.remove(bCancel);
				mainProgressBar.setValue(mainProgressBar.getValue() - 1000000);
				mainProgressBar.setMaximum(mainProgressBar.getMaximum() - 1000000);
			});

			// Cancel ActioListener
			bCancel.addActionListener(e -> {
				future.cancel(true);
				progressList.remove(bCancel);
				progressItem.getTextArea().setText("AVBRUTEN");
				mainProgressBar.setValue(mainProgressBar.getValue() - progressItem.getProgressBar().getValue());
				mainProgressBar.setMaximum(mainProgressBar.getMaximum() - 1000000);
			});
		});
	}

	private static class Tracker implements ProgressTracker {
		private int totalProgress = 0;
		private ProgressItem progressItem;
		private Runnable task;
		private JProgressBar mainProgressBar;

		public Tracker(ProgressItem progressItem, Runnable task, JProgressBar mainProgressBar) {
			this.progressItem = progressItem;
			this.task = task;
			this.mainProgressBar = mainProgressBar;
		}

		@Override
		public void onProgress(int ppmDelta) {
			totalProgress += ppmDelta;

			SwingUtilities.invokeLater(() -> {
				progressItem.getProgressBar().setValue(totalProgress);
				mainProgressBar.setValue(ppmDelta + mainProgressBar.getValue());
			});

			if (totalProgress == 1000000) {
				SwingUtilities.invokeLater(task);
			}
		}

	}

}
// R1. 2 från Thread-Pool, en Sniffer-thread. En Main
// R2. Tracker - Factorizer -> factorizer gör en callback. PoolThread 
// R3. MainProgressBar
// R4. Då blockeras allt annat, allt som är i EDTn 
// R5. Man sätter själv hur många trådar som ska köras för det aktulla programmet. T.ex två processorkärnor i datorn -> två trådar. Optimalt 
// R6. Om man cancellar och den är klar 
// R7. Vi låter bara en tråd köra factorizer med ett encrypted message   





