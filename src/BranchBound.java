import java.util.*;     // Arrays and Collections and . . .

public class BranchBound
{
   static int[][]  wt;                 // Matrix of edge weights
   static String[] city;               // Vector of city names
   static int      n;                  // Dimension for wt and city
   static ArrayList<Tour> soln = new ArrayList<Tour>();
   static int      bestTour;           // Initialized in init()
   static int      blocked;            // Ditto
   static boolean  DEBUG   =  true;    // Show accept/reject decisions
   static boolean  VERBOSE =  true;    // Show all tours discovered

   private static class Tour implements Comparable
   {  int[] soln;
      int   index;      // In branch-and-bound, start of variable
      int   dist;
      static int nTours = 0;
      // Best-first based on dist, or DFS based on maxheap of index
      static boolean DFS =  true;
      static boolean DBG =  true;

      /* Presumable edges up to [index-1] have been verified before
       * this constructor has been called.  So compute the fixed
       * distance from [0] up to [index-1] as dist.
       */
      private Tour(int[] vect, int index, int[][] wt)
      {  dist = 0;
         for (int k = 1; k < index; k++)         // Add edges
            dist += wt[vect[k-1]][vect[k]];
         if ( index == n )
            dist += wt[vect[n-1]][vect[0]];      // Return edge
         soln = new int[n];                      // Deep copy
         System.arraycopy(vect, 0, soln, 0, n);
         this.index = index;                     // Index to permute
         nTours++;                               // Count up # of tours
         if ( DBG )
            System.out.printf("Idx %d: %s\n", index, toString() );
      }

      public int compareTo ( Object o )
      {  Tour rt = (Tour)o;
         int  c1 = rt.index - this.index,
              c2 = this.dist - rt.dist;
         if ( DFS )
            return c1 == 0 ? c2 : c1;
         else
            return c2;
      }

      // For debugging convenience:  show the current state.
      public String toString()
      {  StringBuilder val = new StringBuilder( city[soln[0]] );
         for ( int k = 1; k < n; k++ )
            val.append(", " + city[soln[k]]);
         val.append(", " + city[soln[0]]);
         val.append( String.format(" for %d", dist) );
         return val.toString();
      }
   }

   // For debugging convenience:  show state UP TO this index
   private static void partial(int[] vect, int index)
   {  int dist = 0;

      System.out.print (city[vect[0]]);
      for ( int k = 1; k <= index; k++ )
      {  System.out.print (", " + city[vect[k]]);
         dist += wt[vect[k-1]][vect[k]];
      }
      System.out.println (" for distance " + dist);
   }

   // Initialize the global variables based on the file passed through
   // the Scanner inp.  See the header for the specifications of the
   // input file.
   private static void init(Scanner inp)
   {  int sub1,
          sub2;
      String line;

      n = inp.nextInt();
      wt = new int[n][n];
      city = new String[n];
      // Initially, there are NO edges; hence -1.
      for ( sub1 = 0; sub1 < n; sub1++ )
         Arrays.fill(wt[sub1], -1);

      inp.nextLine();   // Discard rest of first line
      for ( sub1 = 0; sub1 < n; sub1++ )
         city[sub1] = inp.nextLine();
      Arrays.sort(city);     // Just to be sure (binarySearch)

      inp.nextLine();   // Discard blank spacing line;
      blocked = 0;      // Accumulate ALL weights for upper bound

      while ( inp.hasNext() )
      {  int    head, tail;
         int    dist;
         String src, dst;

         line = inp.nextLine();   // E.g.:  "George" "Pasco" 91
         // Chop out the double-quoted substrings.
         head = line.indexOf('"') + 1;
         tail = line.indexOf('"', head);
         src = line.substring(head, tail);

         head = line.indexOf('"', tail+1) + 1;
         tail = line.indexOf('"', head);
         dst = line.substring(head, tail);

         dist = Integer.parseInt( line.substring(tail+1).trim() );
         sub1 = Arrays.binarySearch(city, src);
         sub2 = Arrays.binarySearch(city, dst);
         wt[sub1][sub2] = wt[sub2][sub1] = dist;
         blocked += dist;
      }
      blocked += blocked;    // Double the total
      bestTour = blocked;    // And initialize bestTour
   }

   // Used below in generating permutations.
   private static void swap ( int[] x, int p, int q )
   {  int tmp = x[p];  x[p] = x[q]; x[q] = tmp;  }

   // Generate the available tours by branch-and-bound.
   // Generate the initial permutation vector, then save that state
   // as the first examined in the branch-and-bound.
   public  static void tour()
   {  int[] vect = new int[n];
      int   start;
      Queue<Tour> work = new PriorityQueue<Tour>();

      // First permutation vector.
      for ( int k = 0; k < n; k++ )
         vect[k] = k;
      // We will, however, start from Spokane, not Coulee City
      // --- IF the data file is the one for the inland Pacific NW
      start = Arrays.binarySearch(city, "Spokane");
      if ( start >= 0 )
      {  vect[start] = 0; vect[0] = start;  }
      // Consequently, we start the permutations at [1], NOT [0].
      work.add( new Tour(vect, 1, wt) );

      while ( ! work.isEmpty() )  // Branch-and-bound loop
      {
         Tour current = work.poll();
         int  index = current.index;

         vect  = current.soln;

         if ( index == n )        // I.e., Full permutation vector
         {  if ( wt[vect[n-1]][vect[0]] > 0 )    // Return edge?
            {  if ( current.dist < bestTour )    // Better than earlier?
               {//Save the state in the list
                  bestTour = current.dist;
                  soln.add(current);
                  if ( DEBUG )
                     System.out.println("Accept " + current);
               }
               else if ( DEBUG )
                  System.out.println ("Too long:  " + current);
            }
            else if (DEBUG)
               System.out.println(    "Invalid:   " + current);
         }
         else                     // Continue generating permutations
         {
            int k;      // Loop variable
            int hold;   // Used in regenerating the original state

            for ( k = index; k < n; k++ )
            {
               swap ( vect, index, k );
               if ( wt[vect[index-1]][vect[index]] < 0 )
                  continue;
               work.add ( new Tour(vect, index+1, wt) );
            }
            // Restore original permutation
            hold = vect[index];
            for ( k = index+1; k < n; k++ )
               vect[k-1] = vect[k];
            vect[n-1] = hold;
         }
      }
   }


   public static void main (String[] args) throws Exception
   {
      String filename = args[0].trim().equals("") ? "RoadSet.txt"
                                         : args[0];
      Scanner inp = new Scanner ( new java.io.File(filename) );

      System.out.println("Data read from file " + filename);
      init(inp);
      tour();

      if (VERBOSE)
      {  System.out.println ("Tours discovered:");
         for ( Tour opt : soln )
            System.out.println(opt);
      }
      if ( soln.size() == 0 )
      {  System.out.println("NO tours discovered.  Exiting.");
         System.exit(0);
      }
      System.out.println (Tour.nTours + " Tour objects generated.");
      Collections.sort(soln);
      System.out.println("Best tour:  ");
      System.out.println(soln.get(0));
   }
}