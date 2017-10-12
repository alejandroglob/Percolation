import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Percolation {
   private WeightedQuickUnionUF uf;
   private int matrixN;
   private int matrixSize;
   private int openSites = 0;
   private int[] openSitesArray;
   private int openSitesCount = 0;

   public Percolation(int n) {  // create n-by-n grid, with all sites blocked
     if (n <= 0) {
      throw new IllegalArgumentException();
     }
     this.matrixN = n;
     this.matrixSize =(int) Math.pow(n, 2);
     // System.out.println(matrixSize);
     this.uf = new WeightedQuickUnionUF(matrixSize + 2);
     this.openSitesArray = new int [matrixSize + 2];
     // System.out.println(Arrays.toString(openSitesArray));
     // System.out.println(uf.count());

   }
   public void open(int row, int col){    // open site (row, col) if it is not open already
    if (validPosition(row, col)) {

      //If site has not been opened yet
      if (!isOpen(row, col)) {
        System.out.println("Entro");
        int position = getPosition(row, col);

        //Open site in private array and update openSitesCount
        openSitesArray[position] = 1;
        openSitesCount++;
      }
        
     }
     else {
      throw new IllegalArgumentException();
     }
   }
   
   public boolean isOpen(int row, int col){  // is site (row, col) open?
     if (validPosition(row, col)) {
        int position = getPosition(row, col);
        return openSitesArray[position] == 1;
     }
     else {
      throw new IllegalArgumentException();
     }
   }
   public boolean isFull(int row, int col){  // is site (row, col) full?
     return true;
   }
   
   public int numberOfOpenSites(){       // number of open sites
     return openSites;
   }
   public boolean percolates(){              // does the system percolate?
     return true;
   }

   private boolean validPosition(int row, int col) {
     if (row == 0 || col == 0) {
      return false;
     }
     else if (row > matrixN || col > matrixN) {
      return false;
     }
     else {
      return true;
     }
   }

   private int getPosition(int row, int column){
      return (row - 1) * (int) Math.sqrt(matrixSize) + column;
    }

  private Boolean isFirstRow(int row, int column) {
        return row == 1;
    }

    private Boolean isLastRow(int row, int column) {
        return row == matrixN;
    }

     private Boolean isLeftTopCorner(int row, int column) {
        return  row == 1;
    }

    private Boolean isRightTopCorner(int row, int column) {
        return  row == 1 && column == matrixN;
    }

    private Boolean isLeftBottomCorner(int row, int column) {
        return row == matrixN && column == 1;
    }

    private Boolean isRightBottomCorner(int row, int column) {
        return row == matrixN && column == matrixN;
    }

    private Boolean isLeftEdge(int row, int column) {
        return row == 1;
    }

    private Boolean isRightEdge(int row, int column) {
        return row == matrixN;
    }

    public Location defineLocation(int row, int column) {
        if (isLeftTopCorner(row, column)) {
            return Location.TOP_LEFT;
        }
        else if (isRightTopCorner(row, column)) {
            return Location.TOP_RIGHT;
        }
        else if (isLeftBottomCorner(row, column)) {
            return Location.BOTTOM_LEFT;
        }
        else if (isRightBottomCorner(row, column)) {
            return Location.BOTTOM_RIGHT;
        }
        else {
            if (isFirstRow(row, column)) {
                return Location.TOP_CENTER;
            }
            else if (isLastRow(row, column)) {
                return Location.BOTTOM_CENTER;
            }
            else {
                if (isLeftEdge(row, column)) {
                    return Location.CENTER_LEFT;
                }
                else if (isRightEdge(row, column)) {
                    return Location.CENTER_RIGHT;
                }
                else{
                    return Location.CENTER;
                }
            }
        }
    }

    private static class Neighbor {
     int row;
     int column;

     public Neighbor(int row, int column) {
      this.row = row;
      this.column = column;
     }

     public int getRow() {
      return row;
     }

     public int getColumn() {
      return column;
     }
     
     public String toString() {
         return "(" + Integer.toString(row) + " " + Integer.toString(column) + ")";
     }
    }


    private Neighbor topNeighbor(int row, int column) {
        return new Neighbor(row - 1, column);
    }

    private Neighbor bottomNeighbor(int row, int column) {
        return new Neighbor(row + 1, column);
    }

    private Neighbor leftNeighbor(int row, int column) {
        return new Neighbor(row, column -1);
    }

    private Neighbor rightNeighbor(int row, int column) {
        return new Neighbor(row, column + 1);
    }

    public Neighbor[] getNeighbors(int row, int column, Location location) {
     if (location == Location.TOP_LEFT) {
      return new Neighbor[]{rightNeighbor(row, column), bottomNeighbor(row, column)};
     }
     else if (location == Location.TOP_CENTER) {
      return new Neighbor[]{rightNeighbor(row, column), bottomNeighbor(row, column), leftNeighbor(row, column)};     
     }
     else if (location == Location.TOP_RIGHT) {
      return new Neighbor[]{bottomNeighbor(row, column), leftNeighbor(row, column)};
     }
     else if (location == Location.BOTTOM_LEFT) {
      return new Neighbor[]{topNeighbor(row, column), rightNeighbor(row, column)};
     }
     else if (location == Location.BOTTOM_CENTER) {
      return new Neighbor[]{topNeighbor(row, column), rightNeighbor(row, column), leftNeighbor(row, column)};
     }
     else if (location == Location.BOTTOM_RIGHT) {
      return new Neighbor[]{topNeighbor(row, column), leftNeighbor(row, column)};
     }
     else if (location == Location.CENTER_LEFT) {
      return new Neighbor[]{topNeighbor(row, column), rightNeighbor(row, column), bottomNeighbor(row, column)};
     }
     else if (location == Location.CENTER) {
      return new Neighbor[]{topNeighbor(row, column), rightNeighbor(row, column), bottomNeighbor(row, column), leftNeighbor(row, column)};
     }
     //Center Right
     else{
      return new Neighbor[]{topNeighbor(row, column), bottomNeighbor(row, column), leftNeighbor(row, column)};
     }
    }

    public static enum Location {
      TOP_LEFT, TOP_CENTER,TOP_RIGHT, CENTER_LEFT,CENTER, CENTER_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT;
    }

    public static void main(String[] args) {

     //Test1
     // Percolation p = new Percolation(3);
     // p.open(1,3);
     // p.open(1,3);
     // p.open(2,1);
     // p.open(1,2);

     //Test2
     //Cambiar clases a publicas
     Percolation p = new Percolation(5);
     int topLeft = 1;
     Location locTopLeft = p.defineLocation(1,0);
     System.out.println(Arrays.toString(p.getNeighbors(1,0, locTopLeft)));

     int topCenter = 2;
     Location locTopCenter = p.defineLocation(1,1);
     System.out.println(Arrays.toString(p.getNeighbors(1,1, locTopCenter)));

     int topRight = 5;
     Location locTopRight = p.defineLocation(1,5);
     System.out.println(Arrays.toString(p.getNeighbors(1,5, locTopRight)));

     int bottomLeft = 21;
     Location locBottomLeft = p.defineLocation(5,1);
     System.out.println(Arrays.toString(p.getNeighbors(5,1, locBottomLeft)));

     int bottomCenter = 22;
     Location locBottomCenter = p.defineLocation(5,2);
     System.out.println(Arrays.toString(p.getNeighbors(5,2, locBottomCenter)));

     int bottomRight = 25;
     Location locBottomRight = p.defineLocation(5,5);
     System.out.println(Arrays.toString(p.getNeighbors(5,5, locBottomRight)));

     int centerLeft = 16;
     Location locCenterLeft = p.defineLocation(2,1);
     System.out.println(Arrays.toString(p.getNeighbors(2,1, locCenterLeft)));

     int center = 17;
     Location locCenter = p.defineLocation(2,2);
     System.out.println(Arrays.toString(p.getNeighbors(2,2, locCenter)));

     int center1 = 13;
     Location locCenter1 = p.defineLocation(3,3);
     System.out.println(Arrays.toString(p.getNeighbors(3,3, locCenter1)));


     int centerRight = 15;
     Location locCenterRight = p.defineLocation(2,5);
     System.out.println(Arrays.toString(p.getNeighbors(2,5, locCenterRight)));
   }
}