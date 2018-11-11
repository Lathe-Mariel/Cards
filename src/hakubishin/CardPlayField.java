package hakubishin;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CardPlayField extends JFrame {
	CardGameObserver controller;
	ArrayList<Card> cards1;
	int numberOfPlayers = 0;

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

	public int addNewPlayer(String name) {
		if(playerName.length > numberOfPlayers) {
		playerName[numberOfPlayers].setText(name);}
		return numberOfPlayers++;
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
		playerName = new JLabel[3];
		playerPanel = new JPanel[3];
		northArea = new JPanel[3];

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

for(int i =0; i < 3; i++) {
		playerPanel[i] = new JPanel(new BorderLayout());
		 northArea[i] = new JPanel();
		playerPanel[i].add(northArea[i], BorderLayout.NORTH);
		fieldPanel[i] = new JPanel();
		fieldPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
		fieldPanel[i].setPreferredSize(new Dimension(420,400));
		fieldPanel[i].setMaximumSize(new Dimension(420,400));
		playerName[i] = new JLabel();
		northArea[i].add(playerName[i]);

		JScrollPane scrollPane = new JScrollPane(fieldPanel[i]);
		scrollPane.getVerticalScrollBar().setUnitIncrement(25);
		playerPanel[i].add(scrollPane, BorderLayout.CENTER);
		scrollPane.setPreferredSize(new Dimension(420, 300));
		jpanel.add(playerPanel[i]);
}


	}
	JPanel[] playerPanel;
	JPanel[] fieldPanel;
	JPanel jpanel;

	JLabel[] playerName;
	JPanel[] northArea;

}
