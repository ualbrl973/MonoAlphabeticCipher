package paquete;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

public class Application {

	private JFrame frame;
	
	 String initialCiphertext;
     String ciphertext;
     String reconstructedText;
     CipherKey bestCipherKey;

     private JProgressBar progressBar;
     
     

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Monoalphabetic Cipher");
		frame.setBounds(100, 100, 800, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// En el método initialize()
		progressBar = new JProgressBar();
		progressBar.setBounds(160, 510, 397, 20);
		progressBar.setIndeterminate(false); // Se puede establecer en modo indeterminado si se desea
		progressBar.setValue(0);
		progressBar.setVisible(false);
		frame.getContentPane().add(progressBar);
		
		JLabel lblNewLabel = new JLabel("Breaking a Monoalphabetic Cipher");
		lblNewLabel.setFont(new Font("Roboto Black", Font.BOLD, 35));
		lblNewLabel.setBounds(109, 10, 587, 85);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Key:");
		lblNewLabel_1_1_1.setFont(new Font("Monospaced", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(174, 464, 67, 53);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 151, 397, 100);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea_cipherText = new JTextArea();
		scrollPane.setViewportView(textArea_cipherText);
		textArea_cipherText.setFont(new Font("Roboto", Font.PLAIN, 14));
		textArea_cipherText.setWrapStyleWord(true);
		textArea_cipherText.setLineWrap(true);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(631, 151, 106, 357);
		frame.getContentPane().add(scrollPane_2);
		
		JTextPane textPaneFrequencies = new JTextPane();
		scrollPane_2.setViewportView(textPaneFrequencies);
		textPaneFrequencies.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
		textPaneFrequencies.setEditable(false);
		
		JPanel background = new JPanel();
		background.setBackground(new Color(255, 255, 255));
		background.setBounds(0, 0, 784, 661);
		frame.getContentPane().add(background);
		background.setLayout(null);
		
		JLabel lblNewLabel_FinalKey = new JLabel("");
		lblNewLabel_FinalKey.setBounds(226, 472, 385, 42);
		background.add(lblNewLabel_FinalKey);
		lblNewLabel_FinalKey.setForeground(new Color(0, 0, 160));
		lblNewLabel_FinalKey.setFont(new Font("Roboto Black", Font.BOLD, 18));
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 541, 784, 83);
		panel.setBackground(new Color(255, 151, 151));
		background.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Bruno Ramirez Ledesma");
		lblNewLabel_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(602, 10, 170, 19);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setFont(new Font("Roboto Black", Font.BOLD, 14));
		
		JLabel lblNewLabel_2_1 = new JLabel("Kyungpook National University");
		lblNewLabel_2_1.setBounds(579, 20, 193, 38);
		panel.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_1.setFont(new Font("Roboto Medium", Font.ITALIC, 14));
		
		JLabel lblNewLabel_2_1_1 = new JLabel("정보보호론");
		lblNewLabel_2_1_1.setBounds(675, 39, 97, 38);
		panel.add(lblNewLabel_2_1_1);
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2_1_1.setFont(new Font("SansSerif", Font.ITALIC, 14));
		
		JLabel lblNewLabel_1 = new JLabel("Enter your ciphertext:");
		lblNewLabel_1.setBounds(233, 94, 286, 53);
		background.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_1 = new JLabel("Desencrypted text");
		lblNewLabel_1_1.setBounds(259, 420, 216, 53);
		background.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setFont(new Font("Monospaced", Font.PLAIN, 20));
		

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Frequencies");
		lblNewLabel_1_1_1_1.setBounds(627, 95, 121, 53);
		background.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setFont(new Font("Roboto", Font.BOLD, 20));
		
		JButton btnDescifer = new JButton("Descifer");
		btnDescifer.setForeground(new Color(255, 255, 255));
		btnDescifer.setBackground(new Color(255, 128, 128));
		btnDescifer.setFont(new Font("Roboto", Font.BOLD, 16));
		btnDescifer.setBounds(312, 260, 113, 42);
		background.add(btnDescifer);
		btnDescifer.setEnabled(true);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(99, 75, 604, 2);
		background.add(separator);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(161, 310, 397, 100);
		background.add(scrollPane_1);
		
		JTextPane textPaneDesencryptedText = new JTextPane();
		textPaneDesencryptedText.setFont(new Font("Roboto", Font.PLAIN, 14));
		textPaneDesencryptedText.setEditable(false);
		scrollPane_1.setViewportView(textPaneDesencryptedText);
		btnDescifer.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	textPaneDesencryptedText.setText("");
                lblNewLabel_FinalKey.setText("");
                textPaneFrequencies.setText("");
		    	btnDescifer.setEnabled(false);
		    	progressBar.setVisible(true);
		        initialCiphertext = textArea_cipherText.getText();
		        ciphertext = initialCiphertext.toUpperCase().replaceAll("[^A-Z]", "");
		        int numberOfTests = 25; // Set the number of tests
		        // Create a SwingWorker to run the tests in the background
		        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
		            private CipherKey bestCipherKey; // Declare bestCipherKey here

		            @Override
		            protected Void doInBackground() throws Exception {
		                
		                CipherTester cipherTester = new CipherTester(ciphertext, numberOfTests);

		                // Execute the tests and update progress
		                bestCipherKey = cipherTester.executeTests(progress -> publish(progress)); // Pass the progress consumer

		                return null;
		            }

		            @Override
		            protected void process(java.util.List<Integer> chunks) {
		                for (int value : chunks) {
		                    progressBar.setValue((int) ((double) value / numberOfTests * 100)); // Update the progress bar
		                }
		            }

		            @Override
		            protected void done() {
		                try {
		                    // Check if bestCipherKey is not null before using it
		                    if (bestCipherKey != null) {
		                        String finalBestPlainText = bestCipherKey.decrypt(ciphertext);
		                        TextReconstructor reconstructor = new TextReconstructor(initialCiphertext, finalBestPlainText);
		                        reconstructedText = reconstructor.reconstruct();

		                        textPaneDesencryptedText.setText(reconstructedText);
		                        lblNewLabel_FinalKey.setText(bestCipherKey.toString());
		                        String frequencies = new CipherTester(ciphertext, numberOfTests).getInitialKeyGenerator().cipherFrequency.toString();
		                        frequencies = frequencies.substring(1, frequencies.length()-1);
		                        textPaneFrequencies.setText(frequencies);
		                    } else {
		                        // Handle the case where no best key was found
		                        textPaneDesencryptedText.setText("No valid cipher key found.");
		                        lblNewLabel_FinalKey.setText("N/A");
		                        textPaneFrequencies.setText("N/A");
		                    }
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                }

		                progressBar.setValue(100); // Ensure progress bar is complete
		                progressBar.setVisible(false);
		                progressBar.setValue(0);
		                btnDescifer.setEnabled(true);
		            }
		        };

		        worker.execute(); // Start the SwingWorker
		    }
		});
		
	
		
		
		
		
		
		
		

		
		
		
		
	}
}
