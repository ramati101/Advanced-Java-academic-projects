package q2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class PhoneBookFrame extends JFrame implements Serializable {

	private JButton search, add, delete, update, save, upload ;
	private JTextField nameField, phoneField;
	private JTable table;
	private Map<String, String> myMap; //the phonebook list
	
	// Constructor for the phone book frame.
	public PhoneBookFrame() {
		setLayout(null);
		
		myMap = new HashMap<>();
		
		handleJtable();
		handleTextFields();
		handleButtons();
		
		setTitle("My Phone Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 500);
		setVisible(true);
	}
	
	private void handleTextFields() {
		nameField = new JTextField("name");
		phoneField = new JTextField("number");
		nameField.setBounds(120, 50, 120, 30);
		phoneField.setBounds(260, 50, 120, 30);
		add(nameField);
		add(phoneField);
	}
	
	// build the JTable area.
	private void handleJtable() {
		table = buildTable(myMap); 
		add(table); 
		
		// sort the table.
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int columnIndexToSort = 1;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));	 
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		setVisible(true);
	}
	
	// build the table from the hashmap.
	private JTable buildTable(Map<String,String> map) {
		String columnNames[] = { "name", "phone number" };
		String[][] data = new String[myMap.size()][2];

		int i = 0;
		for(Map.Entry<String, String> entry : map.entrySet()) {

		  data[i][0] = entry.getKey();
		  data[i][1] = entry.getValue();
		  i++;
		}
		
		JTable myTable = new JTable(data, columnNames);
		myTable.setBounds(50, 150, 400, 250);
		
		return myTable;
	}
	
	// handle the buttons.
	private void handleButtons() {
		search = new JButton("search");
		add = new JButton("add");
		delete = new JButton("delete");
		update = new JButton("update");
		save = new JButton("save");
		upload = new JButton("upload");
		
		search.setBounds(20, 50, 80, 30);
		add.setBounds(400, 20, 80, 30);
		delete.setBounds(400, 55, 80, 30);
		update.setBounds(400, 90, 80, 30);
		save.setBounds(120, 410, 80, 30);
		upload.setBounds(270, 410, 80, 30);
		
		add(search);
		add(add);
		add(delete);
		add(update);
		add(save);
		add(upload);
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addContact();
			}
		});
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				phoneField.setText(getContact()); 
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteContact(); 
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveContacts();
			}
		});
		
		upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				uploadContacts();
			}
		});
	}
	
	// add contact to the phonebook.
	private void addContact() {
		String phone = phoneField.getText().toString();
		String name = nameField.getText().toString();
		
		myMap.put(name, phone);
		handleJtable();
	}
	
	// return the contact number.
	private String getContact() {
		String name = nameField.getText();
		return myMap.getOrDefault(name, " ");
	}
	
	// delete the contact from the phonebook.
	private void deleteContact() {
		String phone = phoneField.getText().toString();
		String name = nameField.getText().toString();
		
		myMap.remove(name, phone);
		handleJtable();
	}
	
	// update the contact number.
	private void updateContact() {
		String name = nameField.getText().toString();
		String phone = phoneField.getText().toString();
		
		myMap.replace(name, phone);
		handleJtable();
	}
	
	// upload the phonebook from file.
	private void uploadContacts() {
		this.readFromFile("phoneBook.txt");
		this.handleJtable();
	}
	
	// save the phonebook on file.
	private void saveContacts() {
		this.writeToFile("phoneBook.txt");
	}
	
	public static void main(String[] args) {
		new PhoneBookFrame();

	}
	
	// save the class on file.
	public void writeToFile(String fileName) {
		try {
			FileOutputStream fo = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fo);
			out.writeObject(this);
			out.close();
			fo.close();
		} catch (IOException e) {
			System.out.println("Error in writeToFile");
		}
	}

	// import the class from file.
	public void readFromFile(String fileName) {
		try {
			FileInputStream fi = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fi);
			PhoneBookFrame pbf = (PhoneBookFrame)ois.readObject();
			this.myMap = pbf.myMap;
			ois.close();
			fi.close();
		} catch (IOException | ClassNotFoundException e) { 
			System.out.println("Error in readFromFile"); 
		} 
	}

}
