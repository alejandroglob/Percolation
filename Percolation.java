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

        Location location = defineLocation(position);
        int[] neighborsArray = getNeighbors(position, location);
        for (int neighbor: neighborsArray) {
        	if (neighbor.is)
        }
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

  private Boolean isFirstRow(int position) {
        return IntStream.rangeClosed(1, matrixN).boxed().collect(Collectors.toList()).contains(position);
    }

    private Boolean isLastRow(int position) {
        return IntStream.rangeClosed(matrixSize - matrixN + 1, matrixSize).boxed().collect(Collectors.toList()).contains(position);
    }

     private Boolean isLeftTopCorner(int position) {
        return  position == 1;
    }

    private Boolean isRightTopCorner(int position) {
        return  position == matrixN;
    }

    private Boolean isLeftBottomCorner(int position) {
        return position == matrixSize - matrixN + 1;
    }

    private Boolean isRightBottomCorner(int position) {
        return position == matrixSize;
    }

    private Boolean isLeftEdge(int position) {
        return position % matrixN == 1;
    }

    private Boolean isRightEdge(int position) {
        return position % matrixN == 0;
    }

    private Location defineLocation(int position) {
        if (isLeftTopCorner(position)) {
            return Location.TOP_LEFT;
        }
        else if (isRightTopCorner(position)) {
            return Location.TOP_RIGHT;
        }
        else if (isLeftBottomCorner(position)) {
            return Location.BOTTOM_LEFT;
        }
        else if (isRightBottomCorner(position)) {
            return Location.BOTTOM_RIGHT;
        }
        else {
            if (isFirstRow(position)) {
                return Location.TOP_CENTER;
            }
            else if (isLastRow(position)) {
                return Location.BOTTOM_CENTER;
            }
            else {
                if (isLeftEdge(position)) {
                    return Location.CENTER_LEFT;
                }
                else if (isRightEdge(position)) {
                    return Location.CENTER_RIGHT;
                }
                else{
                    return Location.CENTER;
                }
            }
        }
    }
    private int topNeighbor(int position) {
        return position - matrixN;
    }

    private int bottomNeighbor(int position) {
        return position + matrixN;
    }

    private int leftNeighbor(int position) {
        return position -1;
    }

    private int rightNeighbor(int position) {
        return position +1;
    }

    private int[] getNeighbors(int position, Location location) {
     if (location == Location.TOP_LEFT) {
      return new int[]{rightNeighbor(position), bottomNeighbor(position)};
     }
     else if (location == Location.TOP_CENTER) {
      return new int[]{rightNeighbor(position), bottomNeighbor(position), leftNeighbor(position)};     
     }
     else if (location == Location.TOP_RIGHT) {
      return new int[]{bottomNeighbor(position), leftNeighbor(position)};
     }
     else if (location == Location.BOTTOM_LEFT) {
      return new int[]{topNeighbor(position), rightNeighbor(position)};
     }
     else if (location == Location.BOTTOM_CENTER) {
      return new int[]{topNeighbor(position), rightNeighbor(position), leftNeighbor(position)};
     }
     else if (location == Location.BOTTOM_RIGHT) {
      return new int[]{topNeighbor(position), leftNeighbor(position)};
     }
     else if (location == Location.CENTER_LEFT) {
      return new int[]{topNeighbor(position), rightNeighbor(position), bottomNeighbor(position)};
     }
     else if (location == Location.CENTER) {
      return new int[]{topNeighbor(position), rightNeighbor(position), bottomNeighbor(position), leftNeighbor(position)};
     }
     //Center Right
     else{
      return new int[]{topNeighbor(position), bottomNeighbor(position), leftNeighbor(position)};
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
     // Percolation p = new Percolation(5);
     // int topLeft = 1;
     // Location locTopLeft = p.defineLocation(topLeft);
     // System.out.println(Arrays.toString(p.getNeighbors(topLeft, locTopLeft)));

     // int topCenter = 3;
     // Location locTopCenter = p.defineLocation(topCenter);
     // System.out.println(Arrays.toString(p.getNeighbors(topCenter, locTopCenter)));

     // int topRight = 5;
     // Location locTopRight = p.defineLocation(topRight);
     // System.out.println(Arrays.toString(p.getNeighbors(topRight, locTopRight)));

     // int bottomLeft = 21;
     // Location locBottomLeft = p.defineLocation(bottomLeft);
     // System.out.println(Arrays.toString(p.getNeighbors(bottomLeft, locBottomLeft)));

     // int bottomCenter = 22;
     // Location locBottomCenter = p.defineLocation(bottomCenter);
     // System.out.println(Arrays.toString(p.getNeighbors(bottomCenter, locBottomCenter)));

     // int bottomRight = 25;
     // Location locBottomRight = p.defineLocation(bottomRight);
     // System.out.println(Arrays.toString(p.getNeighbors(bottomRight, locBottomRight)));

     // int centerLeft = 16;
     // Location locCenterLeft = p.defineLocation(centerLeft);
     // System.out.println(Arrays.toString(p.getNeighbors(centerLeft, locCenterLeft)));

     // int center = 17;
     // Location locCenter = p.defineLocation(center);
     // System.out.println(Arrays.toString(p.getNeighbors(center, locCenter)));

     // int center1 = 13;
     // Location locCenter1 = p.defineLocation(center1);
     // System.out.println(Arrays.toString(p.getNeighbors(center1, locCenter1)));


     // int centerRight = 15;
     // Location locCenterRight = p.defineLocation(centerRight);
     // System.out.println(Arrays.toString(p.getNeighbors(centerRight, locCenterRight)));
   }
}