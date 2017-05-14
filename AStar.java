package com.mc.searchalgorithm;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {
	
	static class Cell{  
        int heuristicCost = 0; 
        int finalCost = 0; 
        int i, j;
        Cell parent;       
        Cell(int i, int j){
            this.i = i;
            this.j = j; 
        }        
        @Override
        public String toString(){
            return "["+this.i+", "+this.j+"]";
        }
    }
    
    //Blocked cells are just null Cell values in grid
   public  static Cell [][] grids = new Cell[100][100];
    
   public static PriorityQueue<Cell> open;    
   public static boolean closed[][]=new boolean[50][50];
   
   public static MyCoordinate srcCoord=null;
   public static MyCoordinate destCoord=null;
   public static Cell srcCell=null;
   public static Cell destCell=null;
   
   public static void setBlocked(int i, int j){
       grids[i][j] = null;
   }
   
    public static  void AStar(int grid[][], Map <String,MyCoordinate> srcMap, Map <String,MyCoordinate> destMap,Integer ROW, Integer COL){ 
    	srcCoord=srcMap.get("start");
    	destCoord=destMap.get("goal");
    	PQsort pqs = new PQsort();
    	open= new PriorityQueue<Cell>(pqs);   	
    	grids = new Cell[ROW][COL];
    	
    	//System.out.println("initial grids: ");
    	for(int i=0;i<ROW;++i){
            for(int j=0;j<COL;++j){
                grids[i][j] = new Cell(i, j);
                grids[i][j].heuristicCost = Math.abs(i-destCoord.getX())+Math.abs(j-destCoord.getY());
                //System.out.print(grids[i][j].heuristicCost+" ");
            }
            //System.out.println();
         }
         grids[srcCoord.getX()][srcCoord.getY()].finalCost = 0;         
         for(int i=0;i<ROW;++i){
             for(int j=0;j<COL;++j){
            	 if(grid[i][j]==0){
            		 setBlocked(i, j);
            	}                 
             }             
          }
         
         System.out.println("Grids: ");
         for(int i=0;i<ROW;++i){
             for(int j=0;j<COL;++j){
                if(i==srcCoord.getX() && j==srcCoord.getY())
                	System.out.print("SO  "); //Source
                else if(i==destCoord.getX() && j==destCoord.getY())
                	System.out.print("DE  ");  //Destination
                else if(grids[i][j]!=null)
                	System.out.printf("%-3d ", 0);
                else
                	System.out.print("BL  "); 
             }
             System.out.println();
         } 
         System.out.println();   	
    	
        srcCell= new Cell(srcCoord.getX(),srcCoord.getY());
    	System.out.println("srcCell--"+srcCell);
    	
    	destCell= new Cell(destCoord.getX(),destCoord.getY());
    	System.out.println("destCell--"+destCell);    	
    	tracePathAndCost(grid);
      
        
        System.out.println("\nScores for cells: ");
        for(int i=0;i<ROW;++i){
            for(int j=0;j<COL;++j){            	
                if(grids[i][j]!=null)
                	System.out.printf("%-3d ", grids[i][j].finalCost);
                else 
                	System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
        
        
        if(closed[destCoord.getX()][destCoord.getY()]){             
             System.out.println("Best Path: ");
             Cell current_ = grids[destCoord.getX()][destCoord.getY()];
             System.out.print(current_);
             while(current_.parent!=null){
                 System.out.print(" -> "+current_.parent);
                 current_ = current_.parent;
             } 
             System.out.println();
        }else 
        	System.out.println("No possible path");
    }
    
    ////////////////////////////////////////////////////////////////////////
    
    public static void tracePathAndCost(int grid[][]){    	
         open.add(srcCell);        
         Cell current;
        
        while(true){ 
            current = open.poll();                    
            if(current==null)break;           
            closed[current.i][current.j]=true;            
            if(current.equals(destCell)){
                return; 
            }            
            Cell t;
            
            if(current.i-1>=0){
                t = grids[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+grid[current.i-1][current.j]); 

                if(current.j-1>=0){                      
                    t = grids[current.i-1][current.j-1];                    
                    checkAndUpdateCost(current, t, current.finalCost+grid[current.i-1][current.j-1]); 
                }

                if(current.j+1<grid[0].length){
                    t = grids[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+grid[current.i-1][current.j+1]); 
                }
            }         
            
            if(current.j-1>=0){
                t = grids[current.i][current.j-1];                
                checkAndUpdateCost(current, t, current.finalCost+grid[current.i][(current.j-1)]); 
            }
            if((current.j+1)<grid[0].length){
                t = grids[current.i][current.j+1];               
                checkAndUpdateCost(current,t,current.finalCost+grid[current.i][(current.j+1)]);
            }
            
            if(current.i+1<grid.length){
                t = grids[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+grid[current.i+1][current.j]);
                if(current.j-1>=0){
                    t = grids[current.i+1][current.j-1];                   
                    checkAndUpdateCost(current, t, current.finalCost+grid[current.i+1][current.j-1]); 
                }
                
                if(current.j+1<grid[0].length){
                   t = grids[current.i+1][current.j+1];                  
                   checkAndUpdateCost(current, t, current.finalCost+grid[current.i+1][current.j+1]); 
                }  
            }       
            
        }//while
    	
    }
  ///////////////////////////////////////////////////////////////////////////////////////////////////////  
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
    	if(t == null || closed[t.i][t.j])return;      	
        int t_final_cost = t.heuristicCost+cost;         
        boolean inOpen = open.contains(t);       
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)
              open.add(t);
        }
    }
   /////////////////////////////////////////////////////////////////////////////////////////////////// 
    static class PQsort implements Comparator<Object> {
    public int compare(Object o1, Object o2) {
	Cell c1 = (Cell)o1;
            Cell c2 = (Cell)o2;
            return c1.finalCost<c2.finalCost?-1:
                    c1.finalCost>c2.finalCost?1:0;
		}
	}
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}






