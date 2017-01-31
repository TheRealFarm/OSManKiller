package script;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.powerbot.script.rt4.ClientContext;
import Fighting.Wrapper.*;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClientContext ctx;
	
	private JPanel panel;
	public boolean guiDone = false;
	
	public GUI(ClientContext ctx){
		this.ctx = ctx;
		
		setTitle("TheRealFarm");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		setBounds(400, 200, 350, 250);
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		
		final JLabel title = new JLabel("Welcome to the Real Man Killer");
        title.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        title.setBounds(80, 11, 240, 15);
        panel.add(title);
		
		final JCheckBox chkBox1 = new JCheckBox("Eating");
        chkBox1.setVisible(true);
        chkBox1.setBounds(25, 49, 97, 23);
        panel.add(chkBox1);
        
        final JCheckBox chkBox2 = new JCheckBox("Banking");
        chkBox2.setVisible(true);
        chkBox2.setEnabled(false);
        chkBox2.setBounds(25, 78, 72, 23);
        panel.add(chkBox2);
        
        final JButton start = new JButton("Start");
        start.setEnabled(true);
        start.setBounds(230, 180, 90, 23);
        panel.add(start);
        
        final JLabel idLabel = new JLabel("id: ", JLabel.TRAILING);
        idLabel.setBounds(160, 49, 97, 23);
        panel.add(idLabel);
        
        final JTextField idTxtField = new JTextField("");
        idTxtField.setBounds(260, 49, 47, 23);
        idTxtField.setEnabled(false);
        idTxtField.setVisible(true);
        panel.add(idTxtField);
        
        final JLabel amountLabel = new JLabel("Amount: ", JLabel.TRAILING);
        amountLabel.setBounds(160,78, 97, 23);
        panel.add(amountLabel);
        
        final JTextField amntTxtField = new JTextField("0");
        amntTxtField.setBounds(260, 78, 47, 23);
        amntTxtField.setEnabled(false);
        amntTxtField.setVisible(true);
        panel.add(amntTxtField);
        
        final JTextArea infoMessage = new JTextArea();
        infoMessage.setEditable(false);
        infoMessage.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        infoMessage.setLineWrap(true);
        infoMessage.setWrapStyleWord(true);
        infoMessage.setRows(10);
        infoMessage.setText("");
        infoMessage.setBounds(25, 110, 200, 90);
        panel.add(infoMessage);
          
        
        chkBox1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent a) {
				if (chkBox1.isSelected())
				{
					idTxtField.setEnabled(true);
					chkBox2.setEnabled(true);
					infoMessage.setText("Fighting, eating food, no banking");
				}
				else
				{
					idTxtField.setEnabled(false);
					chkBox2.setEnabled(false);
					infoMessage.setText("Fighting,no banking or eating");
				}
			}
        	
        });
        
        chkBox2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (chkBox2.isSelected())
				{
					amntTxtField.setEnabled(true);
					infoMessage.setText("Fighting, eating, and banking to restock");
				}
				else
				{
					amntTxtField.setEnabled(false);
					infoMessage.setText("Fighting, eating food, no banking");
				}
				
			}
        });
        
        
        
        start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// eating checked
				if (chkBox1.isSelected())
				{
					// food id error
					if (idTxtField.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please enter food ID or uncheck Eating");
					}
					// banking checked
					else if (chkBox2.isSelected())
					{
						
						int amount = Integer.parseInt(amntTxtField.getText());
						// amount error
						if (amntTxtField.getText().equals("0"))
						{
							JOptionPane.showMessageDialog(null, "Enter a food withdrawal amount between 0-28");
						}
						// amount error
						else if (amount > 28)
						{
							JOptionPane.showMessageDialog(null, "Enter a food withdrawal amount between 0-28");
						}
						else
						{
							// set banking amount
							Fighting.setBanking(true);
							Fighting.setFoodAmount(amount);
							int id = Integer.parseInt(idTxtField.getText());
							Fighting.setEating(true);
							Fighting.setFoodID(id);
							guiDone = true;
						}
					}
					else
					{
						int id = Integer.parseInt(idTxtField.getText());
						Fighting.setEating(true);
						Fighting.setFoodID(id);
						guiDone = true;
					}
					
				}
				else
				{
					
					guiDone = true;
				}
				
			}
        	
        });
	}
}
