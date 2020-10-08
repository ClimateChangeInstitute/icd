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

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * @author Modified by Mark Royer
 * 
 */
public class StartMenu extends JFrame {

	/**
	 * For serializing.
	 */
	private static final long serialVersionUID = 5357731128318727391L;

	JButton jButton4 = new JButton(new ImageIcon(getClass().getResource(
			IceCoreDating.imageDir + "/about.gif")));
	JPanel jPanel1 = new JPanel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JButton jButton1 = new JButton(new ImageIcon(getClass().getResource(
			IceCoreDating.imageDir + "/new.gif")));
	JButton jButton2 = new JButton(new ImageIcon(getClass().getResource(
			IceCoreDating.imageDir + "/open.gif")));
	JButton jButton5 = new JButton(new ImageIcon(getClass().getResource(
			IceCoreDating.imageDir + "/exit.gif")));

	public StartMenu() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		jButton4.setText("About");
		jButton4.addActionListener(new StartMenu_jButton4_actionAdapter(this));
		jButton4.setBounds(new Rectangle(69, 218, 107, 27));
		jButton4.setFont(new java.awt.Font("Arial", 1, 11));
		jButton4.setForeground(UIManager.getColor("textText"));
		jButton4.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton4.setToolTipText("About");
		this.setTitle("Start Menu");
		this.setResizable(false);
		jPanel1.setForeground(Color.pink);
		jPanel1.setPreferredSize(new Dimension(756, 500));
		jPanel1.setBounds(new Rectangle(-2, 0, 415, 286));
		jPanel1.setLayout(null);
		jLabel2.setBounds(new Rectangle(116, 45, 176, 29));
		jLabel2.setEnabled(true);
		jLabel2.setFont(new java.awt.Font("Arial", 1, 11));
		jLabel2.setForeground(UIManager.getColor("menuText"));
		jLabel2.setText("Start A New Dating Session");
		jLabel4.setBounds(new Rectangle(113, 96, 201, 29));
		jLabel4.setFont(new java.awt.Font("Arial", 1, 11));
		jLabel4.setForeground(UIManager.getColor("menuText"));
		jLabel4.setToolTipText("");
		jLabel4.setText("Continue An Existing Dating Session");
		jButton1.setBounds(new Rectangle(61, 46, 42, 24));
		jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton1.setOpaque(false);
		jButton1.setBorderPainted(true);
		jButton1.addActionListener(new StartMenu_jButton1_actionAdapter(this));
		jButton2.setBounds(new Rectangle(61, 99, 41, 24));
		jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton2.setOpaque(false);
		jButton2.addActionListener(new StartMenu_jButton2_actionAdapter(this));
		jButton5.setToolTipText("Exit");
		jButton5.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton5.setFont(new java.awt.Font("Arial", 1, 11));
		jButton5.setForeground(UIManager.getColor("textText"));
		jButton5.setBounds(new Rectangle(222, 217, 107, 29));
		jButton5.addActionListener(new StartMenu_jButton5_actionAdapter(this));
		jButton5.setText("Exit");
		jButton5.addActionListener(new StartMenu_jButton5_actionAdapter(this));
		jPanel1.add(jButton1, null);
		jPanel1.add(jLabel2, null);
		jPanel1.add(jButton2, null);
		jPanel1.add(jLabel4, null);
		jPanel1.add(jButton4, null);
		jPanel1.add(jButton5, null);
		this.getContentPane().add(jPanel1, null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		this.setSize(new Dimension(410, 285));
		Dimension dim = getToolkit().getScreenSize();
		Rectangle abounds = this.getBounds();
		this.setLocation((dim.width - abounds.width) / 2,
				(dim.height - abounds.height) / 2);
		this.setVisible(true);
		this.requestFocus();
	}

	void jButton4_actionPerformed(ActionEvent e) {
		IceCoreDating splash = new IceCoreDating();
		splash.createSplashScreen(IceCoreDating.imageDir + "/splashScreen.jpg");
		splash.showSplashScreen();
	}

	void jButton3_actionPerformed(ActionEvent e) {

	}

	void jButton1_actionPerformed(ActionEvent e) {

		dispose();
		FileOpener opener = new FileOpener();
		opener.newSession = true;
		opener.open();

	}

	void jButton2_actionPerformed(ActionEvent e) {
		dispose();
		FileOpener opener = new FileOpener();
		opener.newSession = false;
		opener.open();
	}

	void jButton5_actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}

class StartMenu_jButton4_actionAdapter implements java.awt.event.ActionListener {
	StartMenu adaptee;

	StartMenu_jButton4_actionAdapter(StartMenu adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton4_actionPerformed(e);
	}
}

class StartMenu_jButton3_actionAdapter implements java.awt.event.ActionListener {
	StartMenu adaptee;

	StartMenu_jButton3_actionAdapter(StartMenu adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton3_actionPerformed(e);
	}
}

class StartMenu_jButton1_actionAdapter implements java.awt.event.ActionListener {
	StartMenu adaptee;

	StartMenu_jButton1_actionAdapter(StartMenu adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}

class StartMenu_jButton2_actionAdapter implements java.awt.event.ActionListener {
	StartMenu adaptee;

	StartMenu_jButton2_actionAdapter(StartMenu adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}

class StartMenu_jButton5_actionAdapter implements java.awt.event.ActionListener {
	StartMenu adaptee;

	StartMenu_jButton5_actionAdapter(StartMenu adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton5_actionPerformed(e);
	}
}
