package edu.umaine.cs.icecoredater;

/*
 Copyright 2005 The Climate Change Institute At The University Of Maine.

 JFreeChart: http://www.jfree.org/jfreechart/ was created by David Gilbert
 and is being distributed under the terms of the GNU
 Lesser General Public Licence: http://www.object-refinery.com/lgpl.html

 This file is part of IceCoreDating software.

 IceCoreDating is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 IceCoreDating is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with IceCoreDating; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 */

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Modified by Mark Royer
 * 
 */
public class MainMenu extends JFrame {

	/**
	 * For serializing.
	 */
	private static final long serialVersionUID = -1555470601012682214L;

	File tempFile;
	String[] elements;
	String[] plottingMethods;
	int numOfElements;
	int i, j, topYear;
	String selectedMethod;
	private JLabel label1;
	private JComboBox choices;
	private List<Checkbox> selectedElements;
	private JList list;

	public MainMenu(File tempFile) {
		super("Main Menu");
		if (tempFile != null) {
			this.tempFile = tempFile;
		}
		run("");
	}

	public void readFile() {
		try {
			BufferedReader br = new BufferedReader(new java.io.FileReader(
					tempFile));
			String line = br.readLine();
			StringTokenizer tokenizer = null;
			if (tempFile.getName().substring(
					tempFile.getName().lastIndexOf("."),
					tempFile.getName().length()).equals(".csv")) {
				tokenizer = new StringTokenizer(line, ",");
			} else {
				tokenizer = new StringTokenizer(line);
			}

			tokenizer.nextToken();
			tokenizer.nextToken();
			tokenizer.nextToken();
			tokenizer.nextToken();
			elements = new String[tokenizer.countTokens()];
			int i = 0;
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				elements[i] = token;
				i++;
			}
			numOfElements = i;

			br.close();
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "IO Exception", "Exception",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public void run(String arg) {
		try {

			this.setResizable(false);
			this.getContentPane().setLayout(new BorderLayout());
			readFile();
			JPanel topPanel = new JPanel(new BorderLayout());
			label1 = new JLabel(
					"\n\n\t\t\t\t\tSelect Elements to plot against:");
			label1.setFont(new java.awt.Font("Serif", 1, 12));
			label1.setForeground(UIManager.getColor("menuText"));
			topPanel.add(label1, BorderLayout.NORTH);
			JPanel checkBoxesPanel = new JPanel(new FlowLayout());
			checkBoxesPanel.setOpaque(true);
			// cbg = new CheckboxGroup();
			selectedElements = new ArrayList<Checkbox>();
			// JOptionPane.showMessageDialog(null, "" + elements[0]);
			for (j = 0; j < elements.length; j++) {
				selectedElements.add(new Checkbox(elements[j], false));
				// checkBoxesPanel.add((Checkbox) selectedElements.get(j));
			}
			list = new JList(selectedElements.toArray());
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					for (int j = 0; j < selectedElements.size(); j++)
						selectedElements.get(j).setState(false);
					int[] values = list.getSelectedIndices();
					for (int i = 0; i < values.length; i++)
						((Checkbox) list.getModel().getElementAt(values[i]))
								.setState(true);
				}
			});
			list.setCellRenderer(new MyCellRenderer());
			list.setSize(new Dimension(50, 50));
			list
					.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.setAutoscrolls(true);
			list.setToolTipText("Hold Ctrl to select multiple items");
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setSize(new Dimension(50, 50));
			scrollPane.getViewport().setView(list);
			topPanel.add(scrollPane, BorderLayout.CENTER);
			this.getContentPane().add(topPanel, BorderLayout.NORTH);
			// gd.addCheckboxGroup(1,numOfElements,elements,initialValues);
			// final Panel main = new Panel(new BorderLayout());
			JPanel middlePanel = new JPanel(new BorderLayout());
			JPanel middlePanel1 = new JPanel(new FlowLayout());
			JLabel topYearLabel = new JLabel("Top Year of The core:");
			topYearLabel.setFont(new java.awt.Font("Serif", 1, 12));
			topYearLabel.setForeground(UIManager.getColor("menuText"));
			final JTextField topYearField = new JTextField();
			topYearField.setToolTipText("Enter your top year here");
			topYearField.setBounds(new Rectangle(88, 144, 88, 22));
			topYearField.setText("2008");
			JButton ok = new JButton("OK");
			JButton cancel = new JButton("Cancel");
			JPanel buttonsPanel = new JPanel(new FlowLayout());
			buttonsPanel.add(ok);
			buttonsPanel.add(cancel);
			middlePanel1.add(topYearLabel);
			middlePanel1.add(topYearField);
			middlePanel.add(middlePanel1, BorderLayout.CENTER);
			middlePanel.add(buttonsPanel, BorderLayout.SOUTH);
			this.getContentPane().add(middlePanel, BorderLayout.SOUTH);
			// gd.addNumericField("Top year of the core: ", 0, 2);
			plottingMethods = new String[3];
			plottingMethods[0] = "Midpoint between top and bottom";
			plottingMethods[1] = "Top point";
			plottingMethods[2] = "Bottom point";
			JPanel bottomPanel = new JPanel(new BorderLayout());
			choices = new JComboBox(plottingMethods);
			choices.setToolTipText("Select your plotting method");
			JLabel choicesLabel = new JLabel("Choose your plotting Method: ");
			choicesLabel.setFont(new java.awt.Font("Serif", 1, 12));
			choicesLabel.setForeground(UIManager.getColor("menuText"));
			JPanel choicesPanel = new JPanel(new FlowLayout());
			choicesPanel.add(choicesLabel, FlowLayout.LEFT);
			choicesPanel.add(choices);
			// gd.addChoice("Choose your plotting method: ",plottingMethods,
			// "Midpoint between top and bottom");
			bottomPanel.add(choicesPanel, BorderLayout.CENTER);
			this.getContentPane().add(bottomPanel, BorderLayout.CENTER);
			cancel.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					dispose();
					new StartMenu();
					return;
				}

			});

			// List selectedElements = gd.getCheckboxes();

			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						topYear = Integer.parseInt(topYearField.getText());
						int topYearConfirm = JOptionPane.showConfirmDialog(
								null,
								"Please confirm that this is your top year: "
										+ topYear, "Top year",
								JOptionPane.YES_NO_OPTION);
						if (topYearConfirm == JOptionPane.YES_OPTION) {
							selectedMethod = plottingMethods[choices
									.getSelectedIndex()];
							if (check()) {
								dispose();
								new InitialPlot(tempFile, topYear,
										numOfElements, selectedElements,
										selectedMethod);
							} else {
								JOptionPane
										.showMessageDialog(
												null,
												"Please select at least one element to plot against",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								return;
							}

						} else {
							return;

						}

					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid top year", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			});

			this.setSize(new Dimension(300, 400));
			Dimension dim = getToolkit().getScreenSize();
			Rectangle abounds = this.getBounds();
			this.setLocation((dim.width - abounds.width) / 2,
					(dim.height - abounds.height) / 2);
			this.setVisible(true);
			this.requestFocus();
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});

		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error\n " + e.getCause());
		}
	}

	private boolean check() {
		for (int i = 0; i < selectedElements.size(); i++) {
			Checkbox element = selectedElements.get(i);
			if (element.getState()) {
				return true;
			}
		}
		return false;
	}

	private class MyCellRenderer extends JLabel implements ListCellRenderer {

		/**
		 * For serializing. 
		 */
		private static final long serialVersionUID = -7845258907793683406L;

		/**
		 * This is the only method defined by ListCellRenderer. We just
		 * reconfigure the JLabel each time we're called.
		 */
		public Component getListCellRendererComponent(JList list, Object value, // value
				// to
				// display
				int index, // cell index
				boolean isSelected, // is the cell selected
				boolean cellHasFocus) // the list and the cell have the focus
		{
			String s = ((Checkbox) value).getLabel();
			setText(s);

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
	}

}
