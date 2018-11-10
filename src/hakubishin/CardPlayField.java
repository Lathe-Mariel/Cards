package hakubishin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CardPlayField extends JFrame {
	CardGameObserver controller;
	ArrayList<Card> cards1;

	public void setController(CardGameObserver go) {
		this.controller = go;
	}

	public void addCard(int field, Card card) {
		//cards1.add(card);
		fieldPanel[field].add(card);
		revalidate();
		repaint();
	}
	
	public void removeCard(int field, Card card) {
		fieldPanel[field].remove(card);
		revalidate();
		repaint();
	}

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardPlayField frame = new CardPlayField();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CardPlayField() {
		cards1 = new ArrayList<Card>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 60));
		contentPane.add(panel, BorderLayout.NORTH);

		JButton button = new JButton("同じカードを捨てる<throw away doubled cards>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.sendCommand(0);
			}
		});
		panel.add(button);
		fieldPanel = new JPanel[3];
		jpanel = new JPanel();
		contentPane.add(jpanel, BorderLayout.CENTER);
		jpanel.setLayout(new GridLayout(0, 3, 0, 0));

		fieldPanel[0] = new JPanel();
		fieldPanel[0].setLayout(new FlowLayout());
		fieldPanel[0].setPreferredSize(new Dimension(150,600));
		fieldPanel[0].setMaximumSize(new Dimension(150,250));

		fieldPanel[1] = new JPanel();
		fieldPanel[1].setLayout(new FlowLayout());
		fieldPanel[1].setPreferredSize(new Dimension(150,600));

		fieldPanel[2] = new JPanel();
		fieldPanel[2].setLayout(new FlowLayout());
		fieldPanel[2].setPreferredSize(new Dimension(150,600));

		JScrollPane scrollPane_2 = new JScrollPane(fieldPanel[2]);
		jpanel.add(scrollPane_2);
		scrollPane_2.setPreferredSize(new Dimension(150,150));

		JScrollPane scrollPane = new JScrollPane(fieldPanel[0]);
		jpanel.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(150,150));

		JScrollPane scrollPane_1 = new JScrollPane(fieldPanel[1]);
		jpanel.add(scrollPane_1);
		scrollPane.setPreferredSize(new Dimension(150,150));

	}
	JPanel[] fieldPanel;
	JPanel jpanel;

}
