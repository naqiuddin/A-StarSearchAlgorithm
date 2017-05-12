package com.mc.searchalgorithm;

import java.util.Map;

public class Utility {	
	int ROW;
	int COL;
	int grid[][];
	Utility(int ROW, int COL){
		this.ROW=ROW;
		this.COL=COL;
		grid=new int [ROW][COL];
		
	}
	
	public boolean  isValid(int row, int col)
	{
	    // Returns true if row number and column number is in range
	    return (row >= 0) && (row < ROW) && (col >= 0) && (col < COL);
	}
	
	public boolean isUnBlocked(int grid[][], int row, int col)
	{
	    // Returns true if the cell is not blocked else false
	    if (grid[row][col] == 1)
	        return (true);
	    else
	        return (false);
	}
	
	public boolean isDestination(Map <String,MyCoordinate> srcMap, Map <String,MyCoordinate> destMap)
	{
		MyCoordinate destCoord=destMap.get("goal");	 
		MyCoordinate srcCoord=srcMap.get("start");
	    if (srcCoord.getX() == destCoord.getX() && srcCoord.getY() == destCoord.getY())
	        return (true);
	    else
	        return (false);
	}	 
	 
} 
