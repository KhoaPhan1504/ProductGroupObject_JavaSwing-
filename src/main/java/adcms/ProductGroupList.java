package adcms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import it6020002_tx1.ConnectionPool;
import it6020002_tx1.ConnectionPoolImpl;
import it6020002_tx1.objects.ProductGroupObject;
import it6020002_tx1.productgroup.ProductGroup;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;


public class ProductGroupList extends JFrame {

	private ConnectionPool connectionPool;
	
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    
    
    //Hàm lấy dữ liệu ra items
    private ArrayList<ProductGroupObject> fetchDataFromSQL() {
        ProductGroup pg = new ProductGroup();
        ArrayList<ProductGroupObject> items = pg.getPgObject(null, (byte) 30);
        return items;
    }
    
    
    
    

//  //***********************************HIỂN THỊ DANH SÁCH***********************************************
    public void ViewPgList(ArrayList<ProductGroupObject> items) {

        // Xóa dữ liệu cũ từ bảng
        tableModel.setRowCount(0);
        
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Tên");
        tableModel.addColumn("ID Parent");
        tableModel.addColumn("ID Manager");
        tableModel.addColumn("Ghi chú");
        tableModel.addColumn("Đã xóa");
        tableModel.addColumn("Ngày xóa");
        tableModel.addColumn("Tác giả xóa");
        tableModel.addColumn("Ngày sửa đổi");
        tableModel.addColumn("Ngày tạo");
        tableModel.addColumn("Kích hoạt");
        tableModel.addColumn("Tên (tiếng Anh)");
        tableModel.addColumn("ID Tác giả tạo");
        tableModel.addColumn("Ngôn ngữ");
        

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane);
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    
    // Hàm hiển thị dữ liệu ( dùng lại nhiều lần mỗi khi có thay đổi về dữ liệu )
    private void displayPgList(ArrayList<ProductGroupObject> items) {
        // Xóa dữ liệu cũ từ bảng
        tableModel.setRowCount(0);

        // Hiển thị danh sách mới
        for (ProductGroupObject item : items) {
            tableModel.addRow(new Object[]{
                    item.getPg_id(), 
                    item.getPg_name(), 
                    item.getPg_ps_id(), 
                    item.getPg_manager_id(),
                    item.getPg_notes(), 
                    item.isPg_delete(), 
                    item.getPg_deleted_date(),
                    item.getPg_deleted_author(), 
                    item.getPg_modified_date(), 
                    item.getPg_created_date(),
                    item.isPg_enable(), 
                    item.getPg_name_en(), 
                    item.getPg_created_author_id(),
                    item.getPg_language()
            });
        }
    }
//  //***********************************END*****************************************
    
    
    

    
    
    
    
    
    
    
//  //***********************************THÊM SẢN PHẨM*************************************
    //view
    private JTextField nameField;
    private JTextField psIdField;
    private JTextField managerIdField;
    private JTextField notesField;
    private JTextField deletedField;
    private JTextField deletedDateField;
    private JTextField deletedAuthorField;
    private JTextField modifiedDateField;
    private JTextField createdDateField;
    private JTextField enableField;
    private JTextField nameEnField;
    private JTextField createdAuthorIdField;
    private JTextField languageField;
    
    // Hiển thị bảng thêm dữ liệu
    private void showAddDialog() {
        // Tạo JFrame cho cửa sổ nhập liệu
        JFrame addFrame = new JFrame("Thêm sản phẩm mới");
        addFrame.setSize(400, 300);
        addFrame.getContentPane().setLayout(new GridLayout(15, 2));

        // Tạo các thành phần nhập liệu và nhãn
        nameField = new JTextField();
        psIdField = new JTextField();
        managerIdField = new JTextField();
        notesField = new JTextField();
        deletedField = new JTextField();
        deletedDateField = new JTextField();
        deletedAuthorField = new JTextField();
        modifiedDateField = new JTextField();
        createdDateField = new JTextField();
        enableField = new JTextField();
        nameEnField = new JTextField();
        createdAuthorIdField = new JTextField();
        languageField = new JTextField();

        JLabel nameLabel = new JLabel("Tên:");
        JLabel psIdLabel = new JLabel("ID Parent:");
        JLabel managerIdLabel = new JLabel("ID Manager:");
        JLabel notesLabel = new JLabel("Ghi chú:");
        JLabel deletedLabel = new JLabel("Đã xóa:");
        JLabel deletedDateLabel = new JLabel("Ngày xóa:");
        JLabel deletedAuthorLabel = new JLabel("Tác giả xóa:");
        JLabel modifiedDateLabel = new JLabel("Ngày sửa đổi:");
        JLabel createdDateLabel = new JLabel("Ngày tạo:");
        JLabel enableLabel = new JLabel("Kích hoạt:");
        JLabel nameEnLabel = new JLabel("Tên (tiếng Anh):");
        JLabel createdAuthorIdLabel = new JLabel("ID Tác giả tạo:");
        JLabel languageLabel = new JLabel("Ngôn ngữ:");

        // Tạo nút "Đồng ý" để xác nhận nhập liệu
        JButton addButton = new JButton("Đồng ý");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy dữ liệu từ các trường nhập liệu
                String name = nameField.getText();
                String psId = psIdField.getText();
                String managerId = managerIdField.getText();
                String notes = notesField.getText();
                String deleted = deletedField.getText();
                String deletedDate = deletedDateField.getText();
                String deletedAuthor = deletedAuthorField.getText();
                String modifiedDate = modifiedDateField.getText();
                String createdDate = createdDateField.getText();
                String enable = enableField.getText();
                String nameEn = nameEnField.getText();
                String createdAuthorId = createdAuthorIdField.getText();
                String language = languageField.getText();

                // Thêm một bản ghi mới
                if (!addRecord(name, psId, managerId, notes, deleted, deletedDate, deletedAuthor,
                        modifiedDate, createdDate, enable, nameEn, createdAuthorId, language)) {
                    System.out.println("\n-----Không thành công-----\n");
                } else {
                    System.out.println("\\n-----Thành công-----\\n");

                    // Lấy danh sách chuyên mục
                    ArrayList<ProductGroupObject> items = fetchDataFromSQL();
                    displayPgList(items);
                }

                // Đóng cửa sổ nhập liệu
                addFrame.dispose();
            }
        });

        // Thêm các thành phần vào JFrame
        addFrame.getContentPane().add(nameLabel);
        addFrame.getContentPane().add(nameField);
        addFrame.getContentPane().add(psIdLabel);
        addFrame.getContentPane().add(psIdField);
        addFrame.getContentPane().add(managerIdLabel);
        addFrame.getContentPane().add(managerIdField);
        addFrame.getContentPane().add(notesLabel);
        addFrame.getContentPane().add(notesField);
        addFrame.getContentPane().add(deletedLabel);
        addFrame.getContentPane().add(deletedField);
        addFrame.getContentPane().add(deletedDateLabel);
        addFrame.getContentPane().add(deletedDateField);
        addFrame.getContentPane().add(deletedAuthorLabel);
        addFrame.getContentPane().add(deletedAuthorField);
        addFrame.getContentPane().add(modifiedDateLabel);
        addFrame.getContentPane().add(modifiedDateField);
        addFrame.getContentPane().add(createdDateLabel);
        addFrame.getContentPane().add(createdDateField);
        addFrame.getContentPane().add(enableLabel);
        addFrame.getContentPane().add(enableField);
        addFrame.getContentPane().add(nameEnLabel);
        addFrame.getContentPane().add(nameEnField);
        addFrame.getContentPane().add(createdAuthorIdLabel);
        addFrame.getContentPane().add(createdAuthorIdField);
        addFrame.getContentPane().add(languageLabel);
        addFrame.getContentPane().add(languageField);
        addFrame.getContentPane().add(new JLabel()); // Khoảng trắng
        addFrame.getContentPane().add(new JLabel()); // Khoảng trắng
        addFrame.getContentPane().add(new JLabel()); // Khoảng trắng
        addFrame.getContentPane().add(addButton);

        // Đặt JFrame ở giữa màn hình
        addFrame.setLocationRelativeTo(null);
        // Hiển thị JFrame
        addFrame.setVisible(true);
    }
    
    // add dữ liệu vào Pg
    private boolean addRecord(String name, String psId, String managerId, String notes, String deleted,
		            String deletedDate, String deletedAuthor, String modifiedDate, String createdDate,
		            String enable, String nameEn, String createdAuthorId, String language) {
    	ProductGroupObject npg = new ProductGroupObject();
		npg.setPg_name(name);
		npg.setPg_ps_id(Byte.parseByte(psId));
		npg.setPg_manager_id(Integer.parseInt(managerId));
		npg.setPg_notes(notes);
		npg.setPg_delete(Boolean.parseBoolean(deleted));
		npg.setPg_deleted_date(deletedDate);
		npg.setPg_deleted_author(deletedAuthor);
		npg.setPg_modified_date(modifiedDate);
		npg.setPg_created_date(createdDate);
		npg.setPg_enable(Boolean.parseBoolean(enable));
		npg.setPg_name_en(nameEn);
		npg.setPg_created_author_id(Integer.parseInt(createdAuthorId));
		npg.setPg_language(Byte.parseByte(language));
		
		ProductGroup pg = new ProductGroup();
		return pg.addPg(npg);
	}
//  //***********************************END***************************************************************
    
 
    
    
    
    
    
    
//  //***********************************CẬP NHẬT**********************************************************
    
    private void showUpdateDialog(int selectedRow) {
        JDialog updateDialog = new JDialog(this, "Cập nhật", true);
        updateDialog.setSize(400, 300);
        updateDialog.getContentPane().setLayout(new GridLayout(15, 2));
        
        JLabel nameLabel = new JLabel("Tên:");
        JTextField nameField = new JTextField();
        nameField.setText(table.getValueAt(selectedRow, 1).toString());

        JLabel psIdLabel = new JLabel("ID Parent:");
        JTextField psIdField = new JTextField();
        psIdField.setText(table.getValueAt(selectedRow, 2).toString());

        JLabel managerIdLabel = new JLabel("ID Manager:");
        JTextField managerIdField = new JTextField();
        managerIdField.setText(table.getValueAt(selectedRow, 3).toString());

     // Tạo JLabel và JTextField cho trường dữ liệu "Ghi chú"
        JLabel notesLabel = new JLabel("Ghi chú:");
        JTextField notesField = new JTextField();
        notesField.setText(table.getValueAt(selectedRow, 4).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Đã xóa"
        JLabel deletedLabel = new JLabel("Đã xóa:");
        JTextField deletedField = new JTextField();
        deletedField.setText(table.getValueAt(selectedRow, 5).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Ngày xóa"
        JLabel deletedDateLabel = new JLabel("Ngày xóa:");
        JTextField deletedDateField = new JTextField();
        deletedDateField.setText(table.getValueAt(selectedRow, 6).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Tác giả xóa"
        JLabel deletedAuthorLabel = new JLabel("Tác giả xóa:");
        JTextField deletedAuthorField = new JTextField();
        deletedAuthorField.setText(table.getValueAt(selectedRow, 7).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Ngày sửa đổi"
        JLabel modifiedDateLabel = new JLabel("Ngày sửa đổi:");
        JTextField modifiedDateField = new JTextField();
        modifiedDateField.setText(table.getValueAt(selectedRow, 8).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Ngày tạo"
        JLabel createdDateLabel = new JLabel("Ngày tạo:");
        JTextField createdDateField = new JTextField();
        createdDateField.setText(table.getValueAt(selectedRow, 9).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Kích hoạt"
        JLabel enableLabel = new JLabel("Kích hoạt:");
        JTextField enableField = new JTextField();
        enableField.setText(table.getValueAt(selectedRow, 10).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Tên (tiếng Anh)"
        JLabel nameEnLabel = new JLabel("Tên (tiếng Anh):");
        JTextField nameEnField = new JTextField();
        nameEnField.setText(table.getValueAt(selectedRow, 11).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "ID Tác giả tạo"
        JLabel createdAuthorIdLabel = new JLabel("ID Tác giả tạo:");
        JTextField createdAuthorIdField = new JTextField();
        createdAuthorIdField.setText(table.getValueAt(selectedRow, 12).toString());

        // Tạo JLabel và JTextField cho trường dữ liệu "Ngôn ngữ"
        JLabel languageLabel = new JLabel("Ngôn ngữ:");
        JTextField languageField = new JTextField();
        languageField.setText(table.getValueAt(selectedRow, 13).toString());

        // Tạo nút "OK" cho cửa sổ cập nhật
        JButton okButton = new JButton("Đồng ý");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị từ các JTextField và cập nhật dữ liệu vào bảng
            	String newName = nameField.getText();
                String newPsId = psIdField.getText();
                String newManagerId = managerIdField.getText();

                // Lấy giá trị từ các JTextField
                String newNotes = notesField.getText();
                String newDeleted = deletedField.getText();
                String newDeletedDate = deletedDateField.getText();
                String newDeletedAuthor = deletedAuthorField.getText();
                String newModifiedDate = modifiedDateField.getText();
                String newCreatedDate = createdDateField.getText();
                String newEnable = enableField.getText();
                String newNameEn = nameEnField.getText();
                String newCreatedAuthorId = createdAuthorIdField.getText();
                String newLanguage = languageField.getText();

                // Cập nhật dữ liệu trong bảng
                tableModel.setValueAt(newName, selectedRow, 1);
                tableModel.setValueAt(newPsId, selectedRow, 2);
                tableModel.setValueAt(newManagerId, selectedRow, 3);
                tableModel.setValueAt(newNotes, selectedRow, 4);
                tableModel.setValueAt(newDeleted, selectedRow, 5);
                tableModel.setValueAt(newDeletedDate, selectedRow, 6);
                tableModel.setValueAt(newDeletedAuthor, selectedRow, 7);
                tableModel.setValueAt(newModifiedDate, selectedRow, 8);
                tableModel.setValueAt(newCreatedDate, selectedRow, 9);
                tableModel.setValueAt(newEnable, selectedRow, 10);
                tableModel.setValueAt(newNameEn, selectedRow, 11);
                tableModel.setValueAt(newCreatedAuthorId, selectedRow, 12);
                tableModel.setValueAt(newLanguage, selectedRow, 13);

                // Cập nhật dữ liệu trong MySQL sử dụng ConnectionPool
                updateDataInDatabase(selectedRow, newName, newPsId, newManagerId, newNotes, newDeleted,
                    newDeletedDate, newDeletedAuthor, newModifiedDate, newCreatedDate, newEnable,
                    newNameEn, newCreatedAuthorId, newLanguage);

                updateDialog.dispose();
            }
        });

     // Thêm các JLabel và JTextField vào updateDialog
        updateDialog.getContentPane().add(nameLabel);
        updateDialog.getContentPane().add(nameField);
        updateDialog.getContentPane().add(psIdLabel);
        updateDialog.getContentPane().add(psIdField);
        updateDialog.getContentPane().add(managerIdLabel);
        updateDialog.getContentPane().add(managerIdField);
        updateDialog.getContentPane().add(notesLabel);
        updateDialog.getContentPane().add(notesField);
        updateDialog.getContentPane().add(deletedLabel);
        updateDialog.getContentPane().add(deletedField);
        updateDialog.getContentPane().add(deletedDateLabel);
        updateDialog.getContentPane().add(deletedDateField);
        updateDialog.getContentPane().add(deletedAuthorLabel);
        updateDialog.getContentPane().add(deletedAuthorField);
        updateDialog.getContentPane().add(modifiedDateLabel);
        updateDialog.getContentPane().add(modifiedDateField);
        updateDialog.getContentPane().add(createdDateLabel);
        updateDialog.getContentPane().add(createdDateField);
        updateDialog.getContentPane().add(enableLabel);
        updateDialog.getContentPane().add(enableField);
        updateDialog.getContentPane().add(nameEnLabel);
        updateDialog.getContentPane().add(nameEnField);
        updateDialog.getContentPane().add(createdAuthorIdLabel);
        updateDialog.getContentPane().add(createdAuthorIdField);
        updateDialog.getContentPane().add(languageLabel);
        updateDialog.getContentPane().add(languageField);
        updateDialog.getContentPane().add(new JLabel()); // Khoảng trắng
        updateDialog.getContentPane().add(new JLabel()); // Khoảng trắng
        updateDialog.getContentPane().add(new JLabel()); // Khoảng trắng
        updateDialog.getContentPane().add(okButton); // Thêm nút "OK" vào cửa sổ cập nhật
        
        

        updateDialog.setLocationRelativeTo(this);
        updateDialog.setVisible(true);
    }

    private void updateDataInDatabase(int selectedRow, String newName, String newPsId, String newManagerId,
		            String newNotes, String newDeleted, String newDeletedDate, String newDeletedAuthor,
		            String newModifiedDate, String newCreatedDate, String newEnable, String newNameEn,
		            String newCreatedAuthorId, String newLanguage) {
		Connection conn = null;
		try {
		// Lấy kết nối từ ConnectionPool
		conn = connectionPool.getConnection("viewPg");
		
		// Tạo câu truy vấn UPDATE SQL dựa trên thông tin nhập từ form
		String updateQuery = "UPDATE tblpg SET pg_name = ?, pg_ps_id = ?, pg_manager_id = ?, pg_notes = ?, " +
		"pg_delete = ?, pg_deleted_date = ?, pg_deleted_author = ?, pg_modified_date = ?, pg_created_date = ?, " +
		"pg_enable = ?, pg_name_en = ?, pg_created_author_id = ?, pg_language = ? WHERE pg_id = ?";
		
		PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
		preparedStatement.setString(1, newName);
		preparedStatement.setByte(2, Byte.parseByte(newPsId));
		preparedStatement.setInt(3, Integer.parseInt(newManagerId));
		preparedStatement.setString(4, newNotes);
		preparedStatement.setBoolean(5, Boolean.parseBoolean(newDeleted));
		preparedStatement.setString(6, newDeletedDate);
		preparedStatement.setString(7, newDeletedAuthor);
		preparedStatement.setString(8, newModifiedDate);
		preparedStatement.setString(9, newCreatedDate);
		preparedStatement.setBoolean(10, Boolean.parseBoolean(newEnable));
		preparedStatement.setString(11, newNameEn);
		preparedStatement.setInt(12, Integer.parseInt(newCreatedAuthorId));
		preparedStatement.setByte(13, Byte.parseByte(newLanguage));
		preparedStatement.setShort(14, (short) tableModel.getValueAt(selectedRow, 0));
		
		// Thực hiện truy vấn UPDATE
		int rowsUpdated = preparedStatement.executeUpdate();
		
		// Kiểm tra nếu cập nhật thành công
		if (rowsUpdated > 0) {
		JOptionPane.showMessageDialog(null, "Dữ liệu đã được cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else {
		JOptionPane.showMessageDialog(null, "Không có dữ liệu nào được cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		
		// Trả kết nối về ConnectionPool
		connectionPool.releaseConnection(conn, "viewPg");
		} catch (SQLException e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}
//  //***********************************END***************************************************************
    
    
    
    
    
    
    
    
//  //***********************************TÌM KIẾM***********************************************
    // Xử lí name, nếu có thì chạy hàm tìm kiếm dữ liệu theo name và hiển thị ra dữ liệu
    private void handleSearchButton() {
        // Hiển thị hộp thoại để nhập tên sản phẩm
        String pgName = JOptionPane.showInputDialog(this, "Nhập tên sản phẩm:");
        if (pgName != null) {
            // Thực hiện tìm kiếm trong dữ liệu
            ArrayList<ProductGroupObject> searchResults = searchProductByName(pgName);

            // Cập nhật bảng hiển thị với kết quả tìm kiếm
            displayPgList(searchResults);
        }
    }

    // Tìm dữ liệu theo name
    private ArrayList<ProductGroupObject> searchProductByName(String pgName) {
        ArrayList<ProductGroupObject> searchResults = new ArrayList<>();
        ConnectionPoolImpl connectionPool = new ConnectionPoolImpl();
        Connection connection = null;

        try {
            // Lấy kết nối từ pool
            connection = connectionPool.getConnection("SearchProductByName");

            // Thực hiện truy vấn SQL để tìm kiếm sản phẩm theo tên
            String sql = "SELECT * FROM tblpg WHERE Pg_name = ?";
            //String sql = "SELECT * FROM your_table WHERE Pg_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pgName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	ProductGroupObject result = new ProductGroupObject();
                result.setPg_id(resultSet.getShort("pg_id")); 
                result.setPg_name(resultSet.getString("Pg_name"));
                result.setPg_ps_id(resultSet.getByte("pg_ps_id"));
                result.setPg_manager_id(resultSet.getInt("pg_manager_id"));
                result.setPg_notes(resultSet.getString("pg_notes"));
                result.setPg_delete(resultSet.getBoolean("pg_delete"));
                result.setPg_deleted_date(resultSet.getString("pg_deleted_date"));
                result.setPg_deleted_author(resultSet.getString("pg_deleted_author"));
                result.setPg_modified_date(resultSet.getString("pg_modified_date"));
                result.setPg_created_date(resultSet.getString("pg_created_date"));
                result.setPg_enable(resultSet.getBoolean("pg_enable"));
                result.setPg_name_en(resultSet.getString("pg_name_en"));
                result.setPg_created_author_id(resultSet.getInt("pg_created_author_id"));
                result.setPg_language(resultSet.getByte("pg_language"));
                
                searchResults.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Trả kết nối vào pool sau khi hoàn thành truy vấn
            if (connection != null) {
                try {
                    connectionPool.releaseConnection(connection, "SearchProductByName");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return searchResults;
    }
//  //***********************************END****************************************************

    
    
    
    
    
    
//  //***********************************XÓA SẢN PHẨM***********************************************
    private void handleDeleteButton() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            short pgId = (short) tableModel.getValueAt(selectedRow, 0);

            boolean deleteSuccess = deleteProductFromDatabase(pgId);

            if (deleteSuccess) {
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng hiển thị bảng sau đó chọn một sản phẩm để xóa.");
        }
    }
    
    // Gọi vào hàm xóa sản phẩm theo id trong Pg
    private boolean deleteProductFromDatabase(int pgId) {
        ProductGroup s = new ProductGroup();
        return s.deletePg((short) pgId);
    }
//  //***********************************END*************************************
    
    
    
    
    
    
    
//  //***********************************THỐNG KÊ*************************************
    private void calculateStatistics() {
        int rowCount = tableModel.getRowCount(); // Số dòng trong bảng
        searchField.setText("Số sản phẩm: " + rowCount); // Hiển thị kết quả thống kê
    }
    
    
    
    
//  //***********************************Constructor ViewPG*************************************
    public ProductGroupList() {
    	connectionPool = new ConnectionPoolImpl();
    	
        setTitle("Product Group");
        //setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton displayButton = new JButton("SHOW LIST");
        displayButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        displayButton.setBackground(Color.RED);
        displayButton.setForeground(Color.YELLOW);
        buttonPanel.add(displayButton);
        
        JButton addButton = new JButton("ADD");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        addButton.setBackground(Color.RED);
        addButton.setForeground(Color.YELLOW);
        buttonPanel.add(addButton);
        
        JButton updateButton = new JButton("EDIT");
        updateButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        updateButton.setBackground(Color.RED);
        updateButton.setForeground(Color.YELLOW);
        buttonPanel.add(updateButton);

        JButton searchButton = new JButton("SEARCH BY NAME");
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        searchButton.setBackground(Color.RED);
        searchButton.setForeground(Color.YELLOW);
        buttonPanel.add(searchButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.YELLOW);
        buttonPanel.add(deleteButton);

        JButton statsButton = new JButton("STATISTIC");
        statsButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        statsButton.setBackground(Color.RED);
        statsButton.setForeground(Color.YELLOW);
        buttonPanel.add(statsButton);

        searchField = new JTextField(20);
        buttonPanel.add(searchField);
        
        JButton btnExit = new JButton("EXIT");
        btnExit.setBackground(Color.RED);
        btnExit.setForeground(Color.YELLOW);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 14));
        buttonPanel.add(btnExit);
        btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (JOptionPane.showConfirmDialog( btnExit,"Confirm if you want to Exit","Product Group List",
			            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			            System.exit(0);
			}
		});
        
        JLabel lblTitle = new JLabel("PRODUCT GROUP LIST");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setForeground(Color.BLUE);
        getContentPane().add(lblTitle, BorderLayout.NORTH);


        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ProductGroup pg = new ProductGroup();
            	ArrayList<ProductGroupObject> items = fetchDataFromSQL();
                // Gọi hàm hiển thị dữ liệu lên bảng
                displayPgList(items);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	showAddDialog();
            }
        });
        

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    showUpdateDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng hiển thị bảng sau đó chọn sản phẩm để cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            	
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	handleSearchButton();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	handleDeleteButton();
            }
        });

        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	calculateStatistics();
            }
        });
    }

    public static void main(String[] args) {
        ProductGroup pg = new ProductGroup();
        ArrayList<ProductGroupObject> items = pg.getPgObject(null, (byte) 30);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ProductGroupList viewPg = new ProductGroupList();
                viewPg.setVisible(true);

                // ViewPgList để hiển thị bảng
                viewPg.ViewPgList(items);
            }
        });
    }


}
