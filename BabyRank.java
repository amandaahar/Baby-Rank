
/**
 * Write a description of BabyRank here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
public class BabyRank {
    
    public void totalBirths(FileResource fr)
    {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int boyCount = 0;
        int girlCount = 0;
        for(CSVRecord rec : fr.getCSVParser(false))
        {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if(rec.get(1).equals("M"))
            {
                totalBoys += numBorn;
                boyCount += 1;
            }
            else
            {
                totalGirls += numBorn;
                girlCount += 1;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("total girl births = " + totalGirls);
        System.out.println("total girl names = " + girlCount);
        System.out.println("total boy births = " + totalBoys);
        System.out.println("total boy names = " + boyCount);
    }
    

    
    public void testTotalBirths()
    {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    public int getRank(int year, String name, String gender)
    {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + 
                                            year + ".csv");
        int count = 0;
        
        for(CSVRecord rec : fr.getCSVParser(false))
        { 

            if(rec.get(1).equals(gender))
            {
                count += 1;
                if(rec.get(0).equals(name))
                {
                     return count;
                }
            }
            
                       
        }
        
        return -1;
    }
    
    public void testGetRank()
    {
        int rank = getRank(2014,"Mich","M");
        System.out.println("Rank = " +rank);
        
    }
    
    public String getName(int year, int rank, String gender)
    {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + 
                                            year + ".csv");
        
        int count = 0;
        for(CSVRecord rec : fr.getCSVParser(false))
        {
            if(rec.get(1).equals(gender))
            {
                count += 1;
                if(rank == count)
                {
                    return rec.get(0);
                    
                }
            }
            
        }
        return "NO NAME";
    }
    
    public void testGetName()
    {
        String name = getName(1982, 450,"M");
        System.out.println("Name for this rank is " + name);
    }
    
    public void whatIsNameInYear(String name, int year, 
                                    int newYear, String gender)
    {
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " born in " + year + " would be " +
                            newName + " if he/she were born in " + newYear);
    }
    
    public void testWhatIsNameInYear()
    {
        whatIsNameInYear("Owen" , 1974, 2014, "M");
    }
    
    public int yearOfHighestRank(String name, String gender)
    {
        int highestRank = 0;
        int year = 0;
        int temp;
        DirectoryResource dr = new DirectoryResource();
        File fi = null;
        //CSVParser parser = null;
        for(File f : dr.selectedFiles())
        {
            //FileResource fr = new FileResource(f);
            //parser = fr.getCSVParser(false);
            
            int currentRank = getRank(Integer.parseInt(f.getName().substring(3,7)),
                                        name, gender);                           
            fi = f;                            
            if(highestRank == 0)
            {
                highestRank = currentRank;
                year = Integer.parseInt(f.getName().substring(3,7));
                
            }
            
            if(currentRank < highestRank)
            {
                
                System.out.println(f.getName().substring(3,7));
                System.out.println();
                highestRank = currentRank;
                
                //year = Integer.parseInt(f.getName().substring(3,7));
                
            }

        }
        
        //System.out.println(fi.getName());
        if(highestRank == -1)
        {
            return -1;
        }
        //year = Integer.parseInt(fi.getName().substring(3,7));
        return year;
    }
    
    public void testYearOfHighestRank()
    {
        
        int year = yearOfHighestRank("Mich", "M");
        System.out.println(year + " has the highest rank for this name");
    }
    
    public double getAverageRank(String name, String gender)
    {
        int rank = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles())
        {
            count += 1;
            rank += getRank(Integer.parseInt(f.getName().substring(3,7)),
                                        name, gender);
        }
        
        if(rank < 0)
        {
            return -1;
        }
        
        return ((double) rank)/count;
    }
    
    public void testGetAverageRank()
    {
        double avgRank = getAverageRank("Robert", "M");
        System.out.println("Average rank for this name is " + avgRank);
        
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender)
    {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + 
                                            year + ".csv");
        int totalBirths = 0;                                    
        for(CSVRecord rec : fr.getCSVParser(false))
        {
            
            
            if(rec.get(1).equals(gender))
            {
                int numBorn = Integer.parseInt(rec.get(2));
                totalBirths += numBorn;
                if(rec.get(0).equals(name))
                {
                    return totalBirths - numBorn;
                }
            }
            
        }
        return -1;
    }
    
    public void testGetTotalBirthsRankedHigher()
    {
        int births = getTotalBirthsRankedHigher(1990,"Drew","M");
        System.out.println(births);
    }
    
    public int totalGenderNames(FileResource fr, String gender)
    {
        int boyNames = 0;
        int girlNames = 0;
        int names = 0;
        for(CSVRecord rec : fr.getCSVParser(false))
        {
            if(rec.get(1).equals("M"))
            {
                boyNames += 1;
            }
            else
            {
                girlNames += 1;
            }
        }
        
        if(gender.equals("F"))
        {
            names = girlNames;
        }
        if(gender.equals("M"))
        {
            names = boyNames;
        }
        return names;
    }
    
    
}


