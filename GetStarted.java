package dropwizard.DropWizard;

import com.codahale.metrics.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GetStarted {
  static final MetricRegistry metrics = new MetricRegistry();
  public static void main(String args[]) {
    startReport();
    try {
		readFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
  }

  static void readFile() throws IOException{
	  String outputfilename = "output.txt";
	  BufferedWriter bufferedWriter = null;
	  Meter linereads = metrics.meter("linereads");
	  Meter reads = metrics.meter("reads");
	  Meter writes = metrics.meter("writes");
	  int nooflineread=0;
	   File file = new File("data.txt");
      try {
          Scanner scanner = new Scanner(file);
          bufferedWriter = new BufferedWriter(new FileWriter(outputfilename));
          while (scanner.hasNextLine()) {
        	  int noofinputchars=0;
        	  nooflineread++;
        	  linereads.mark();
        	 reads.mark(0);
              String line = scanner.nextLine();
             System.out.println("no of lines read " +nooflineread);
              noofinputchars = line.toCharArray().length;
             
              
             reads.mark(noofinputchars);
             System.out.println("getcount ----"+reads.getCount());
             // reads.mark(reads.getCount());
             
              System.out.println(" nof input chars read"+noofinputchars);
              
             
              wait5Seconds();
                line = line+"output";
                int noofoutputchars = line.toCharArray().length;
              //Start writing
                
               // writes.mark(0);
              bufferedWriter.write(line);
              bufferedWriter.newLine();
              bufferedWriter.flush();
              writes.mark(noofoutputchars);
              System.out.println(" nof output chars written"+noofoutputchars);
              wait5Seconds();
              
              
              System.out.println(line);
          }
          
          
          
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
      finally{bufferedWriter.close();}
  } 
	  
	  
  
  
static void startReport() {
    /*ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
    reporter.start(1, TimeUnit.SECONDS);
    */
    final JmxReporter reporter1 = JmxReporter.forRegistry(metrics).build();
    reporter1.start();

    
    
}

static void wait5Seconds() {
    try {
        Thread.sleep(10*1000);
    }
    catch(InterruptedException e) {}
}
}