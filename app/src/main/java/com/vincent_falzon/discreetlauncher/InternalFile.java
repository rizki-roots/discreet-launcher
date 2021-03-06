package com.vincent_falzon.discreetlauncher ;

// License
/*

	This file is part of Discreet Launcher.

	Copyright (C) 2019-2021 Vincent Falzon

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

// Imports
import android.content.Context ;
import java.io.BufferedReader ;
import java.io.File ;
import java.io.FileReader ;
import java.io.FileWriter ;
import java.util.ArrayList ;

/**
 * Manage the storage of an internal file.
 */
class InternalFile
{
	// Attributes
	private final File file ;


	/**
	 * Constructor to create or open an internal file.
	 * @param context To get the folder path
	 * @param filename Name of the file
	 */
	InternalFile(Context context, String filename)
	{
		file = new File(context.getFilesDir().getAbsolutePath() + "/" + filename) ;
	}


	/**
	 * Check if the file already exists on the system.
	 * @return <code>true</code> if it exists, <code>false</code> otherwise
	 */
	boolean isExisting()
	{
		return file.exists() ;
	}


	/**
	 * Read the internal file line by line and store the result in an array of lines.
	 * @return Content of the file or <code>null</code> if an error happened
	 */
	ArrayList<String> readAllLines()
	{
		// Check if the file exists
		if(!isExisting()) return null ;

		// Prepare the table used to store the lines
		ArrayList<String> content = new ArrayList<>() ;
		String buffer ;

		try
		{
			// Read the content from the file line by line
			BufferedReader reader = new BufferedReader(new FileReader(file)) ;
			while((buffer = reader.readLine()) != null) content.add(buffer) ;
			reader.close() ;
		}
		catch (Exception e)
		{
			// An error happened while reading the file
			return null ;
		}

		// Return the content of the file
		return content ;
	}


	/**
	 * Check if a specific line exists in the file.
	 * @param search Text of the line
	 * @return <code>true</code> if it exists, <code>false</code> otherwis
	 */
	boolean isLineExisting(String search)
	{
		ArrayList<String> content = readAllLines() ;
		for(String line : content)
			if(line.equals(search)) return true ;
		return false ;
	}


	/**
	 * Write a new line at the end of the file (create it if not existing yet).
	 * After the writing, a new line character is added.
	 * @param added_line To write in the file
	 * @return <code>true</code> if successful, <code>false</code> otherwise
	 */
	boolean writeLine(String added_line)
	{
		try
		{
			// Write the line at the end of the file followed by a new line character
			FileWriter writer = new FileWriter(file, true) ;
			writer.write(added_line) ;
			writer.write(System.lineSeparator()) ;
			writer.close() ;
			return true ;
		}
		catch (Exception e)
		{
			// An error happened while writing the line
			return false ;
		}
	}


	/**
	 * Remove the internal file (if it doesn't exist it is considered as removed).
	 * @return <code>true</code> if successful, <code>false</code> otherwise
	 */
	boolean remove()
	{
		// Check if the file exists before trying to remove it
		if(!file.exists()) return true ;

		// Remove the file and return the result
		return file.delete() ;
	}
}
