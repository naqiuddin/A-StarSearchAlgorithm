package com.mc.searchalgorithm;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar {
	
	static class Cell{  
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent; 
        String str;
        Cell(String str){
        	this.str=str;
        }
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
   
    public static  void AStar(int grid[][], Map <String,MyCoordinate> srcMap, Map <String,MyCoordinate> destMap){ 
    	srcCoord=srcMap.get("start");
    	destCoord=destMap.get("goal");
    	PQsort pqs = new PQsort();
    	open= new PriorityQueue<Cell>(pqs);
    	System.out.println("open--"+open);    	    
    	grids = new Cell[grid.length][grid.length];
    	
    	
    	for(int i=0;i<grid.length;++i){
            for(int j=0;j<grid.length;++j){
                grids[i][j] = new Cell(i, j);
                grids[i][j].heuristicCost = Math.abs(i-destCoord.getX())+Math.abs(j-destCoord.getY());
//                System.out.print(grid[i][j].heuristicCost+" ");
            }
//            System.out.println();
         }
         grids[srcCoord.getX()][srcCoord.getY()].finalCost = 0;
    	
    	
        //add the start location to open list.
    	/*System.out.println("grids--"+grids.length);
    	System.out.println("srcCoord.getX()--"+srcCoord.getX());
    	System.out.println("srcCoord.getY()--"+srcCoord.getY());*/
    	Cell srcCell= new Cell(srcCoord.getX(),srcCoord.getY());
    	System.out.println("srcCell--"+srcCell);
    	
    	Cell destCell= new Cell(destCoord.getX(),destCoord.getY());
    	System.out.println("destCell--"+destCell);    	
        open.add(srcCell);
        
        Cell current;
        
        while(true){ 
            current = open.poll();
            System.out.println("current-->"+current);
            
            if(current==null)break;
           // System.out.println("current.i-->"+current.i);
            //System.out.println("current.j-->"+current.j);
            closed[current.i][current.j]=true; 
            //System.out.println(closed[current.i][current.j]);
           
            if(current.equals(destCell)){
                return; 
            }            
            Cell t;  
           // System.out.println("grid[0].length-->"+(grid[0].length));
            
           // System.out.println("current.j+1-->"+(current.j+1));
            /*if(current.i-1>=0){
                t = new Cell((current.i-1),(current.j));
                checkAndUpdateCost(current, t, grid[(current.i-1)][current.j]); 

                if((current.j-1)>=0){                      
                    t = new Cell((current.i-1),(current.j-1));
                    checkAndUpdateCost(current, t, grid[(current.i-1)][(current.j-1)]); 
                }

                if((current.j+1)<grid[0].length){
                    t = new Cell((current.i-1),(current.j+1));
                    checkAndUpdateCost(current, t,  grid[(current.i-1)][(current.j+1)]); 
                }
            } */

            /*if((current.j-1)>=0){
                t = new Cell(current.i,(current.j-1));
                checkAndUpdateCost(current, t, grid[(current.i)][(current.j-1)]); 
            }*/
            
            
            
            if((current.j+1)<grid[0].length){
                t = grids[current.i][current.j+1];
                //t=new Cell(current.i,(current.j+1));
                System.out.println("t--"+t);
                //checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
                checkAndUpdateCost(current,t,grid[current.i][(current.j+1)]);
            }
           // System.out.println("current.i+1-->"+(current.i+1));
            //System.out.println("grid.length-->"+grid.length);
            
            if((current.i+1)<grid.length){
                
                //t=new Cell((current.i+1),current.j);
                t = grids[(current.i+1)][current.j];
                checkAndUpdateCost(current, t, grid[(current.i+1)][current.j]); 

                if(current.j-1>=0){
                   // t = new Cell(current.i+1,current.j-1);
                    t = grids[(current.i+1)][current.j-1];
                    checkAndUpdateCost(current, t, grid[(current.i+1)][current.j-1]); 
                }
                
                if((current.j+1)<grid[0].length){
                   //t = new Cell((current.i+1),(current.j+1));
                   t = grids[(current.i+1)][(current.j+1)];
                  if( grid[(current.i+1)][(current.j+1)]==0){
                	  grids[(current.i+1)][(current.j+1)]=new Cell("BL");
                  }else{
                    checkAndUpdateCost(current, t, grid[(current.i+1)][(current.j+1)]);
                  }
                }  
            }
            
            
        }//while
      
        
        System.out.println("\nScores for cells: ");
        for(int i=0;i<50;++i){
            for(int j=0;j<50;++j){
            	//System.out.println(grid[i][j]);
                if(grids[i][j]!=null)System.out.printf("%-3d ", grids[i][j].finalCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();
        
        
        if(closed[destCoord.getX()][destCoord.getY()]){
            //Trace back the path 
             System.out.println("Path: ");
             Cell current_ = grids[destCoord.getX()][destCoord.getY()];
             System.out.print(current_);
             while(current_.parent!=null){
                 System.out.print(" -> "+current_.parent);
                 current_ = current_.parent;
             } 
             System.out.println();
        }else System.out.println("No possible path");
    }
    
    ////////////////////////////////////////////////////////////////////////
    
    static void checkAndUpdateCost(Cell current, Cell t, int cost){
    	//System.out.println("t.i-->"+t.i);
    	//System.out.println("t.j-->"+t.j);
        if(t == null || closed[t.i][t.j])return;
        //System.out.println("cost--"+cost);
        
        t.heuristicCost = Math.abs(current.i - destCoord.getX()) + Math.abs (current.j -destCoord.getY()) ;
        
      	
        int t_final_cost = t.heuristicCost+cost;
        //System.out.println("t_final_cost--"+t_final_cost);
        
        boolean inOpen = open.contains(t);
        //System.out.println("inOpen--"+inOpen);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)
              open.add(t);
        }
    }
    
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



