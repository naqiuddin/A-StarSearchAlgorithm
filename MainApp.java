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
		//String filename= "F:\\large_map_test.txt";
		//String filename= "F:\\test_map.txt";
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
		System.out.println("Grid with converted Cost");
		for(int i=0;i<row;++i){
            for(int j=0;j<column;++j){
                System.out.print(" "+grid[i][j]);
            }
            System.out.println();
         }
		
		if(aStarSearch(objUtility.grid, srcMap,  destMap)== true){	
		  AStar.AStar(objUtility.grid, srcMap,  destMap,row,column);
		}else{
			boolean bFound=false;	
			int i=0,j=0;
			for( i=0;i<row;++i){
	            for( j=0;j<column;++j){
	            	if(grid[i][j]==-1){
	            		bFound=true;
	            		break;
	            	}	            	
	            }
	            if(bFound)
            		break;	            
	         }		
			String line=records.get(i);
			System.out.println("Best Path="+line.charAt(j));
		}

	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	static boolean aStarSearch(int grid[][], Map <String,MyCoordinate> srcMap, Map <String,MyCoordinate> destMap)
	{
		
		srcCoord=srcMap.get("start");
		if(srcCoord==null){
		    System.out.println ("Source/Start not found\n");
			return false;
		}		
		if (objUtility.isValid(srcCoord.getX(), srcCoord.getY()) == false)
	    {
	        System.out.println ("Source/Start is invalid\n");
	        return false;
	    }
	    destCoord=destMap.get("goal");
	    if(destCoord==null){
		System.out.println ("Goal/Destinataion not found\n");
		return false ;
	    }	    
	    if (objUtility.isValid(destCoord.getX(), destCoord.getY()) == false)
	    {
	    	System.out.println ("Goal/Destinataion is invalid\n");
	        return false;
	    }
	    
	    if (objUtility.isUnBlocked(grid, srcCoord.getX(),srcCoord.getY()) == false ||objUtility.isUnBlocked(grid, destCoord.getX(), destCoord.getY()) == false)
	    {
	        System.out.println ("Start/Source or the Goal/destination is blocked\n");
	        return false;
	    }    
	    if (objUtility.isDestination(srcMap, destMap) == true)
	    {
	    	System.out.println ("We are already at the Goal/Destination\n");
	        return false;
	    }
		return true;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
