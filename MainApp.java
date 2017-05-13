package com.mc.searchalgorithm;

import java.util.List;
import java.util.Map;

public class MainApp {
	
	static Utility objUtility=null;
	static MyCoordinate srcCoord=null;
	static MyCoordinate destCoord=null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename= "F:\\large_map.txt";
		FileReaderOperation readOp= new FileReaderOperation();
		List<String> records=readOp.readLargeMapFile(filename);
		System.out.println(records.size());
		String str=records.get(0);
		System.out.println(str.length());
		
		int row=records.size();
		int column=records.get(0).length();
		
		char start='@';
		char goal='X';
		
		Map <String,MyCoordinate> srcMap =readOp.getStartAndGoalLocation(records,start,"start");
		Map <String,MyCoordinate> destMap=readOp.getStartAndGoalLocation(records,goal,"goal");			
		objUtility= new Utility(row,column);
		readOp.convertGridValue(records, objUtility);
		int grid[][]=objUtility.grid;
		
		for(int i=0;i<row;++i){
            for(int j=0;j<column;++j){
                System.out.print(" "+grid[i][j]);
            }
            System.out.println();
         }
		
		aStarSearch(objUtility.grid, srcMap,  destMap);	
		AStar.AStar(objUtility.grid, srcMap,  destMap);

	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	static void aStarSearch(int grid[][], Map <String,MyCoordinate> srcMap, Map <String,MyCoordinate> destMap)
	{
		
		srcCoord=srcMap.get("start");	        
		// If the source is out of range  
		if (objUtility.isValid(srcCoord.getX(), srcCoord.getY()) == false)
	    {
	        System.out.println ("Source is invalid\n");
	        return;
	    }
		destCoord=destMap.get("goal");	 
	    // If the destination is out of range
	    if (objUtility.isValid(destCoord.getX(), destCoord.getY()) == false)
	    {
	    	System.out.println ("Destination is invalid\n");
	        return;
	    }
	    
	    if (objUtility.isUnBlocked(grid, srcCoord.getX(),srcCoord.getY()) == false ||objUtility.isUnBlocked(grid, destCoord.getX(), destCoord.getY()) == false)
	    {
	        System.out.println ("Source or the destination is blocked\n");
	        return;
	    }
	 
	    // If the destination cell is the same as source cell
	    if (objUtility.isDestination(srcMap, destMap) == true)
	    {
	    	System.out.println ("We are already at the destination\n");
	        return;
	    }
		
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
