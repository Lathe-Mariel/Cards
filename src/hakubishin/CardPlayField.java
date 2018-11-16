package hakubishin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class CardPlayField extends JFrame {
	CardGameObserver controller;
	int currentPlayerNumber = 0;
	int playerNumber = 3;

	public void setLevel(int level) {
		comboBox.setSelectedIndex(level);
	}


	public JPanel getCardContainer(int index) {
		return fieldPanel[index];
	}
	public void setTurn(int playerSerialNumber, int score) {
			playerScore[playerSerialNumber].setText(score + "");
		setTurn(playerSerialNumber);
	}
	public void setTurn(int playerSerialNumber) {
		for (int j = 0; j < playerNumber; j++) {
			playerName[j].setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
			playerName[j].setForeground(Color.BLACK);
		}
		playerName[playerSerialNumber].setFont(new Font(Font.DIALOG, Font.BOLD, 24));
		playerName[playerSerialNumber].setForeground(Color.BLUE);
		repaint();
	}

	public void setController(CardGameObserver go) {
		this.controller = go;
	}

	public void addCard(int field, Card card) {
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
		if (playerName.length > currentPlayerNumber) {
			playerName[currentPlayerNumber].setText(name);
		}
		return currentPlayerNumber++;
	}

	public void clearDialog(String playerName) {
		JOptionPane.showMessageDialog(this, playerName + "の勝ち～");
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
		setMaximumSize(new Dimension(2147483647, 1024));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setMaximumSize(new Dimension(32767, 1024));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 60));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		menuBar = new JMenuBar();
		panel.add(menuBar, BorderLayout.NORTH);

		mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		menuItem = new JMenuItem("再戦<New Game>");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.sendCommand(9);
			}
		});
		mnMenu.add(menuItem);

		mnGeneral = new JMenu("General");
		menuBar.add(mnGeneral);

		mntmOption = new JMenuItem("オプション<Option>");
		mntmOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnGeneral.add(mntmOption);

		panel_1 = new JPanel();
		panel_1.setName("");
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.add(panel_1, BorderLayout.CENTER);

				button = new JButton("カード処理");
				panel_1.add(button);
				button.setFont(new Font("HG創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 14));
				button.setMargin(new Insets(2, 2, 2, 2));

				label = new JLabel("レベル");
				label.setAlignmentX(0.5f);
				label.setFont(new Font("HG創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 14));
				panel_1.add(label);

				comboBox = new JComboBox();
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.sendNumberCommand(comboBox.getSelectedIndex());
					}
				});
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18"}));
				comboBox.setFont(new Font("HG創英角ﾎﾟｯﾌﾟ体", Font.PLAIN, 14));
				panel_1.add(comboBox);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controller.sendCommand(0);
					}
				});

	}

	void createSmallField(int number) {
		this.playerNumber = number;

		fieldPanel = new JPanel[playerNumber];
		playerName = new JButton[playerNumber];
		playerPanel = new JPanel[playerNumber];
		northArea = new JPanel[playerNumber];
		jpanel = new JPanel();
		contentPane.add(jpanel, BorderLayout.CENTER);

		for (int i = 0; i < playerNumber; i++) {
			playerPanel[i] = new JPanel(new BorderLayout());
			northArea[i] = new JPanel();
			playerPanel[i].add(northArea[i], BorderLayout.NORTH);
			fieldPanel[i] = new JPanel();
			fieldPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			fieldPanel[i].setPreferredSize(new Dimension(400, 400));
			fieldPanel[i].setMaximumSize(new Dimension(420, 400));
			fieldPanel[i].setBackground(Preference.BACKGROUND);
			playerName[i] = new JButton();
			northArea[i].add(playerName[i]);

			JScrollPane scrollPane = new JScrollPane(fieldPanel[i]);
			scrollPane.getVerticalScrollBar().setUnitIncrement(15);
			playerPanel[i].add(scrollPane, BorderLayout.CENTER);
			scrollPane.setPreferredSize(new Dimension(420, 300));
			jpanel.add(playerPanel[i]);
		}
	}

	void createCenterField(int number) {
		button.setEnabled(false);
		this.playerNumber = number;
		jpanel = new JPanel(new BorderLayout());
		jpanel.setMaximumSize(new Dimension(0, 900));
		fieldPanel = new JPanel[playerNumber];
		playerName = new JButton[playerNumber];
		playerPanel = new JPanel[playerNumber];
		northArea = new JPanel[playerNumber];
		playerScore = new JTextField[playerNumber];

		contentPane.add(jpanel, BorderLayout.CENTER);
		playerName[0] = new JButton();
		for(int i = 1; i < playerNumber; i++) {
			playerPanel[i] = new JPanel(new FlowLayout());
			fieldPanel[i] = new JPanel();
			northArea[i] = new JPanel();
			playerName[i]= new JButton();
			playerName[i].setMargin(new Insets(0,0,0,0));
			northArea[i].add(playerName[i]);
			playerPanel[i].add(northArea[i]);
			playerScore[i] = new JTextField(3);
			northArea[i].add(playerScore[i]);
			playerPanel[i].setMaximumSize(new Dimension(300,300));
			playerPanel[i].setPreferredSize(new Dimension(190,250));
			fieldPanel[i].setPreferredSize(new Dimension(0, 900));
			fieldPanel[i].setLayout(new CardStocker(5));
			fieldPanel[i].setBackground(Preference.BACKGROUND);
			JScrollPane scrollPane = new JScrollPane(fieldPanel[i]);
			scrollPane.getVerticalScrollBar().setUnitIncrement(15);
			playerPanel[i].add(scrollPane);
			scrollPane.setPreferredSize(new Dimension(200, 200));
			Border border = scrollPane.getBorder();
		    Border margin = new EmptyBorder(0,0,0,5);
		    scrollPane.setBorder(new CompoundBorder(border, margin));
			if(i%2 == 0) {
				jpanel.add(playerPanel[i], BorderLayout.WEST);
			}else {
				jpanel.add(playerPanel[i], BorderLayout.EAST);
			}
			fieldPanel[0] = new JPanel(new CardsLayout(6,9));
			fieldPanel[0].setPreferredSize(new Dimension(700, 600));
			fieldPanel[0].setMaximumSize(new Dimension(0,900));
			jpanel.add(fieldPanel[0]);
		}
		playerName[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jpanel, "レベル：" + comboBox.getSelectedIndex() +"　　　　攻撃力：1000000　　　　防御力：1");
			}
		});
	}

	JPanel[] playerPanel;
	JPanel[] fieldPanel;
	JPanel jpanel;

	JButton[] playerName;
	JTextField[] playerScore;
	JPanel[] northArea;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenuItem menuItem;
	private JMenu mnGeneral;
	private JMenuItem mntmOption;
	private JComboBox comboBox;
	private JPanel panel_1;
	private JLabel label;
	private JButton button;
}
