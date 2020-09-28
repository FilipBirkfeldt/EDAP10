import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

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
	private ExecutorService pool = Executors.newFixedThreadPool(4);
	private final JProgressBar mainProgressBar;

	// -----------------------------------------------------------------------

	private CodeBreaker() {
		StatusWindow w = new StatusWindow();
		workList = w.getWorkList();
		progressList = w.getProgressList();
		mainProgressBar = w.getProgressBar();
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

		WorklistItem work = new WorklistItem(n, message);
		SwingUtilities.invokeLater(() -> workList.add(work));

		// Knappar
		JButton bBreak = new JButton("Break");
		JButton jbRemove = new JButton("Remove");

		SwingUtilities.invokeLater(() -> workList.add(bBreak));
		// Future<String> plaintext; 
		// WorkList :)
		Runnable task = () -> {
			progressList.add(jbRemove);
			
		};

		ProgressItem progressItem = new ProgressItem(n, message);
		// BREAK addAction
		bBreak.addActionListener(e -> {
			mainProgressBar.setMaximum(mainProgressBar.getMaximum() + 1000000);
			
			workList.remove(work);
			workList.remove(bBreak);

			SwingUtilities.invokeLater(() -> progressList.add(progressItem));

			ProgressTracker tracker = new Tracker(progressItem, task, mainProgressBar);
			Future<String> plaintext = pool.submit(() -> Factorizer.crack(message, n, tracker));
		});

		// REMOVE ActioListener
		jbRemove.addActionListener(e -> {
			progressList.remove(progressItem);
			progressList.remove(jbRemove);
			mainProgressBar.setValue(mainProgressBar.getValue() - 1000000);
			mainProgressBar.setMaximum(mainProgressBar.getMaximum() - 1000000);
		});

		System.out.println("message intercepted (N=" + n + ")...");
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

		/**
		 * Called by Factorizer to indicate progress. The total sum of ppmDelta from all
		 * calls will add upp to 1000000 (one million).
		 * 
		 * @param ppmDelta portion of work done since last call, measured in ppm (parts
		 *                 per million)
		 */
		@Override
		public synchronized void onProgress(int ppmDelta) {
			totalProgress += ppmDelta;
			progressItem.getProgressBar().setValue(totalProgress);
			mainProgressBar.setValue(ppmDelta + mainProgressBar.getValue());
			
			if (totalProgress == 1000000) {
				SwingUtilities.invokeLater(task);
			}
			
			
			
			
			
			
			
			
			
			
			System.out.print("RÖNK_1");

		}
	}
}
