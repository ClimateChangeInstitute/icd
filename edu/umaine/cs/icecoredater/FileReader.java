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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * @author Modified by Mark Royer
 * 
 */
public class FileReader {
	boolean valid = true;
	File tempFile1 = null;
	FileOpener opener;
	String message = "";
	boolean negatives = false;
	List<String> header;
	List<String> negativeElements;

	public FileReader(FileOpener opener) {
		this.opener = opener;
	}

	public File read(File f) {

		try {
			negativeElements = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new java.io.FileReader(f),
					(int) f.length() * 2);
			int lineNumber = 0;
			String line = "";
			int numberOfZeroes = 0;

			boolean minus99 = false;
			boolean emptyValues = false;

			File path = new File("Files" + File.separator + "Corrected");

			if (!path.exists())
				path.mkdirs();

			tempFile1 = new File(path, f.getName().substring(0,
					f.getName().indexOf("."))
					+ "Corrected"
					+ f.getName().substring(f.getName().indexOf("."),
							f.getName().length()));
			if (!tempFile1.exists())
				tempFile1.createNewFile();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new java.io.FileWriter(tempFile1), (int) f.length() * 2));

			while ((line = br.readLine()) != null) {
				// read file line by line

				valid = true;
				lineNumber++;
				// header line
				if (lineNumber == 1) {
					// String[] headers =
					// {"tube","top","bottom","length","Na","NH4"
					// ,"K","Mg","Ca","Cl","NO3","SO4"};
					StringTokenizer st = new StringTokenizer(line, ",");
					int tokenCount = st.countTokens();
					int tokenNumber = 0;
					int i = 0;
					header = new ArrayList<String>();
					while (st.hasMoreTokens()) {

						String token = st.nextToken();
						header.add(token);
						tokenNumber++;
						/*
						 * if (!token.trim().equalsIgnoreCase(headers[i])){
						 * valid = false; break; }
						 */
						if (tokenNumber < tokenCount)
							out.write(token + ",");
						else
							out.write(token);
						i++;
					}
					out.flush();
					out.println();
				} else {
					StringTokenizer st = new StringTokenizer(line, ",\t\n\r\f");
					int tokenCount = st.countTokens();
					int tokenNumber = 0;
					while (st.hasMoreTokens()) {
						String token = "";
						token = st.nextToken();
						tokenNumber++;

						if (token.trim().equals("")) {
							token = "0.00";
							emptyValues = true;
						}

						else if (Double.parseDouble(token) == -99) {
							minus99 = true;
							token = "0.00";
						}

						if (Double.parseDouble(token) == 0) {
							numberOfZeroes++;
						}

						else if (Double.parseDouble(token) < 0
								&& Double.parseDouble(token) != -99) {
							negatives = true;
							negativeElements.add(header.get(tokenNumber - 1));

						}

						if (tokenNumber < tokenCount)
							out.write(token + ",");
						else
							out.write(token);
					}
					out.flush();
					out.println();
				}
				line = "";
			}
			out.close();
			br.close();

			if (negatives) {
				message += "\n The following elements contain negative values, do you want to proceed ?\n";
				for (int i = 0; i < negativeElements.size(); i++) {
					if (message.indexOf((String) negativeElements.get(i)) != -1)
						continue;
					message += "- " + (String) negativeElements.get(i) + "\n";
				}
				int answer = JOptionPane.showConfirmDialog(null, message,
						"Negative Values Found", JOptionPane.YES_NO_OPTION);
				if (answer != JOptionPane.YES_OPTION) {
					tempFile1.delete();
					opener.open();
					// Macro.abort();
					return null;
				}
			}

			else if (numberOfZeroes > 0) {
				if (emptyValues && !minus99)
					message += "\n- Your file contains epmty values, they have been replaced with zeros";
				// IJ.showMessage("Info",
				// "You file contains epmty values, they have been replaced with zeros"
				// );
				else if (!emptyValues && minus99)
					message += "\n- Your file contains the value -99. It has been replaced with a zero";
				// IJ.showMessage("Info",
				// "Your file contains the value -99. It has been replaced with a zero"
				// );
				else if (emptyValues && minus99)
					message += "\n- Your file contains empty values and the value -99. They have been replaced with zeroes";
				// IJ.showMessage("Info",
				// "Your file contains empty values and the value -99. They have been replaced with zeroes"
				// );
				message += "\n- Your file contains " + numberOfZeroes
						+ " zeros";
				// IJ.showMessage("Info","Your file contains " + numberOfZeroes
				// + " zeros");

			}

		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(null, "IO Error!", "IO Exception",
					JOptionPane.ERROR_MESSAGE);
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"File Cant be read, please choose another file\n"
							+ e.getMessage(), "Invalid",
					JOptionPane.ERROR_MESSAGE);
			tempFile1.delete();
			opener.open();

			return null;
		}
		if (negatives)
			return tempFile1;
		if (valid && !negatives) {
			message += "\n File is in valid format, you may proceed";
			JOptionPane.showMessageDialog(null, message, "Valid",
					JOptionPane.PLAIN_MESSAGE);
			return tempFile1;
		} else {
			message += "\n File is in invalid format, you cant proceed";
			JOptionPane.showMessageDialog(null, message, "Invalid",
					JOptionPane.ERROR_MESSAGE);
			tempFile1.delete();
			FileOpener fo = new FileOpener();
			fo.open();
			// Macro.abort();
			return null;

		}

	}
}
