package panda;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * Yulong Fan
 * OOP Project
 */
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrimeNumberDriver 
{
	static int windowX = 800;
	static int windowY = 500;

	static ArrayList<Integer> threadInfo = new ArrayList<>();
	static ArrayList<TimeToThreadIndex> index = new ArrayList<>();
	static int threadEnded = 0;
	
	static int startNum;
	static int endNum;
	static int numOfThread;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Multi-threading prime number calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowX, windowY);
		frame.setBackground(Color.DARK_GRAY);
		frame.setLocationRelativeTo(null);
		
		Font font = new Font("Verdana", Font.PLAIN, 18);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBackground(Color.DARK_GRAY);
		basePanel.setPreferredSize(new Dimension(windowX*1/5, windowY*1/5));	
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
		
		//basePanel.add(inputPanel, BorderLayout.CENTER);
		
		
		JLabel label0 = new JLabel("Input the range and the number of threads to be used");
		label0.setFont(font);
		label0.setPreferredSize(new Dimension(windowX*1/5, windowY*1/5));
		label0.setHorizontalAlignment(JLabel.CENTER);
		inputPanel.add(label0);
		
		JLabel label1 = new JLabel("start Number");
		label1.setFont(font);
		label1.setHorizontalAlignment(JLabel.CENTER);
		inputPanel.add(label1);
		
		JTextField startNumInput = new JTextField();
		startNumInput.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(startNumInput);
		
		JLabel label2 = new JLabel("end Number");
		label2.setFont(font);
		inputPanel.add(label2);
		
		JTextField endNumInput = new JTextField();
		endNumInput.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(endNumInput);
		
		JLabel label3 = new JLabel("number of threads");
		label3.setFont(font);
		inputPanel.add(label3);
		
		JTextField threadNum = new JTextField();
		threadNum.setHorizontalAlignment(JTextField.CENTER);
		inputPanel.add(threadNum);
		
		
		
		basePanel.add(new JPanel(), BorderLayout.NORTH);
		basePanel.add(new JPanel(), BorderLayout.WEST);
		basePanel.add(new JPanel(), BorderLayout.EAST);
		basePanel.add(new JPanel(), BorderLayout.SOUTH);
		basePanel.add(inputPanel, BorderLayout.CENTER);

		JLabel console = new JLabel("     ");
		console.setPreferredSize(new Dimension(windowX*1/8, windowY*1/8));
		console.setFont(font);
		console.setForeground(Color.RED);
		inputPanel.add(console);
		
		JButton calculateButton = new JButton("Calculate");
		calculateButton.setPreferredSize(new Dimension(windowX*1/5, windowY*1/5));
		
		
		
		calculateButton.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						//console.setText("all input must be integers");
						
						try
						{
							startNum = Integer.parseInt(startNumInput.getText());
							endNum = Integer.parseInt(endNumInput.getText());
							numOfThread = Integer.parseInt(threadNum.getText());
							
							basePanel.remove(inputPanel);
							frame.repaint();
							
							System.out.println("pressed");
						}
						catch(NumberFormatException error)
						{
							
							if(
								(startNumInput.getText().equals("")) 
								|| (endNumInput.getText().equals(""))
								|| (threadNum.getText().equals(""))
								)
							{
								console.setText("One or more parameters is empty");
							}
							else
							{
								console.setText("All input must be integers");
							}
							
						}
						
					}
			
				});
		
		inputPanel.add(calculateButton);
		
//		calculateButton.setHorizontalAlignment(JLabel.CENTER);
		
		frame.getContentPane().add(basePanel);
//		long[] list = new long[3];
//		
//		list[0] = findPrime(startNum, endNum, numOfThread);
//		System.out.println();
//		
//		list[1] = findPrime(1, 10000, 100);
//		System.out.println();
//		
//		list[2] = findPrime(1, 10000, 1000);
//		System.out.println();
//		
//		Arrays.sort(list);
//		
//		System.out.println("The speed goes as follows:");
//		for(int i = 0; i < list.length; i++)
//		{
//			for(int a = 0; a < index.size(); a++) 
//			{
//				if(list[i] == index.get(a).time)
//				{
//					System.out.println("       " +index.get(a).threadNum +" thread with " +index.get(a).time +" ms");
//				}
//			}
//		}
//		
//		System.out.println("\ncomment: ");
//		System.out.println("       Based on my computer, the 100 thread took least amount of time.");
//		System.out.println("       1000 thread took the most time due to the needed to allocated significantly more threads to do slightly less amount of work per thread.");
		
		
		frame.setVisible(true);
	}
	
	public static long findPrime(int startNum, int endNum, int threadNum) 
	{

		System.out.println("Issuing " +threadNum +" threads");
		
		int threadShareNum = (endNum - startNum + 1)/threadNum;
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < threadNum; i++)
		{
			int threadShareStart = startNum + threadShareNum * i;
			int threadShareEnd = threadShareNum * (i + 1 );
			
			if(i + 1 == threadNum)
			{
				threadShareEnd = endNum;
			}
			
			new Thread(new PrimeThread(threadShareStart, threadShareEnd)).start();
			

		}
		
		while(true)
		{
			System.out.print("");

			if(threadEnded == threadNum)
			{
				long totalTime = System.currentTimeMillis() - startTime;
				
				System.out.println("prime numbers are: " +threadInfo);
				System.out.println("time took is : " + totalTime +" ms");
				index.add(new TimeToThreadIndex(totalTime, threadNum));
				
				threadInfo = new ArrayList<>();
				threadEnded = 0;
				
				return totalTime;

			}
		}
		
	}

}

class Panel extends JPanel
{
	
}


class PrimeThread implements Runnable
{
	int startNum;
	int endNum;
	
	public PrimeThread(int a, int b)
	{
		startNum = a;
		endNum = b;
	}

	@Override
	public void run() 
	{
		
		findPrime();
		
	}
	
	public void findPrime()
	{
	
		
		for(int i = startNum; i <= endNum; i++)
		{
			boolean isPrime = true;
			
			for(int r = 2; r < (i/2 + 1); r++)
			{
				if( i % r == 0)
				{
					isPrime = false;				
				}
				
			
			}
			
			if(isPrime == true)
			{

				if(( i != 1) && (i != endNum))
				{
					PrimeNumberDriver.threadInfo.add(i);

				}
				
			}
		}

		
		PrimeNumberDriver.threadEnded++;
		
	}
	

	
}

class TimeToThreadIndex
{
	long time;
	int threadNum;
	
	public TimeToThreadIndex(long a, int b)
	{
		time = a;
		threadNum = b;
	}
}

