package adcms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import it6020002_tx1.ConnectionPool;

public class ChangePassword extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;
    private Connection con;

  	private ConnectionPool cp;
    /**
     * Create the frame.
     */
    public ChangePassword(final String name) {
        setBounds(450, 360, 1024, 234);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        textField.setBounds(373, 35, 609, 67);
        contentPane.add(textField);
        textField.setColumns(10);
        final JButton btnSearch = new JButton("Enter");
        btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String pstr = textField.getText();
                try {
                    System.out.println("update password name " + name);
                    System.out.println("update password");
                    Connection con = cp.getConnection("actionPerformed");
                    PreparedStatement st = con
                            .prepareStatement("Update student set password=? where name=?");
                    st.setString(1, pstr);
                    st.setString(2, name);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(btnSearch, "Password has been successfully changed");
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        }); 
            
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(438, 127, 170, 59);
        contentPane.add(btnSearch);
        lblEnterNewPassword = new JLabel("Enter New Password :");
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewPassword.setBounds(45, 37, 326, 67);
        contentPane.add(lblEnterNewPassword);
    }

}
