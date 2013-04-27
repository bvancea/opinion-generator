package generator.opinion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 11:59 PM
 *
 *
 * Generates opinions in csv format. Its easier to post them to solr this way.
 */
@Component
public class CSVGenerator {

    private String[] holders;
    private String[] target;

    private String[] sentimentWords;

    private static final int ITERATIONS = 6000000;

    private static final int OPINIONS_PER_USER = 40;
    private static final int EXPANSIONS = 10;

    private static final Logger logger = LoggerFactory.getLogger(CSVGenerator.class);

    public void generateCSVFile(String filename) {

        try {
            FileWriter writer = new FileWriter(filename);

            List<String> names = new ArrayList<String>();
            List<String> targets = new ArrayList<String>();
            List<String> sentiments = new ArrayList<String>();
            List<Double> orientations = new ArrayList<Double>();

            parseWords("/opt/solr/words/names.txt", names);
            parseWords("/opt/solr/words/nouns.txt", targets);
            parseSentimentWords("/opt/solr/words/sentiments.txt", sentiments,orientations);

            Random random = new Random();

            logger.debug("Started generating data.");

            System.out.println("Starting to generate " + ITERATIONS + " tuples.");

            writer.append("id,holder,target,sentimentWord,sentimentOrientation,docId\n");

            int percents = ITERATIONS / 10;
            for (int i = 0; i < ITERATIONS; i++) {
                if (i % percents == 0) {
                    System.out.println( "Reached" + i/percents + "0%");
                }
                int index;
                writer.append(i + ",");

                index = random.nextInt(names.size());
                writer.append(names.get(index) + ",");

                index = random.nextInt(targets.size());
                writer.append(targets.get(index) + "," );

                index = random.nextInt(sentiments.size());
                writer.append(sentiments.get(index) + ",");

                index = random.nextInt(orientations.size());
                writer.append(orientations.get(index).toString() + ",");

                writer.append("document" + index + "\n");
            }
            System.out.println("Done. Generated " + ITERATIONS + " tuples.");

            writer.flush();

            writer.close();

        } catch (IOException e) {
            logger.error("Error writing csv file to " + filename, e);
        }
    }

    public void generateCSVFile2(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);

            List<String> names = new ArrayList<String>();
            List<String> targets = new ArrayList<String>();
            List<String> sentiments = new ArrayList<String>();
            List<Double> orientations = new ArrayList<Double>();

            parseWords("/opt/solr/words/names.txt", names);
            parseWords("/opt/solr/words/nouns.txt", targets);
            parseSentimentWords("/opt/solr/words/sentiments.txt", sentiments,orientations);

            Random random = new Random();

            logger.debug("Started generating data.");

            writer.append("id,holder,target,sentimentWord,sentimentOrientation,docId,targetExpansions,sentimentWordExpansions\n");

            int holderNumber = names.size();

            for (int i = 0; i < holderNumber; i++) {
                System.out.println(i + "/" + holderNumber);
                for (int j = 0; j < OPINIONS_PER_USER; j++) {
                    int index;
                    int k = i * OPINIONS_PER_USER + j;

                    writer.append(k + ",");

                    index = random.nextInt(names.size());
                    writer.append(names.get(index) + ",");

                    index = random.nextInt(targets.size());
                    writer.append(targets.get(index) + "," );

                    index = random.nextInt(sentiments.size());
                    writer.append(sentiments.get(index) + ",");

                    index = random.nextInt(orientations.size());
                    writer.append(orientations.get(index).toString() + ",");

                    writer.append("document" + index + ",");

                    writer.append("\"");
                    for (int l = 0; l < EXPANSIONS; l++) {
                        index = random.nextInt(targets.size());
                        writer.append(targets.get(index));
                        if (l + 1 != EXPANSIONS) {
                            writer.append(",");
                        }
                    }
                    writer.append("\",");

                    writer.append("\"");
                    for (int l = 0; l < EXPANSIONS; l++) {
                        index = random.nextInt(targets.size());
                        writer.append(sentiments.get(index));
                        if (l + 1 != EXPANSIONS) {
                            writer.append(",");
                        }
                    }
                    writer.append("\"\n");
                }
            }

            System.out.println("Done. ");

            writer.flush();

            writer.close();

        } catch (IOException e) {
            logger.error("Error writing csv file to " + filename, e);
        }
    }

    public void parseWords(String fileName, List<String> words ){
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void parseSentimentWords(String fileName, List<String> sentiments, List<Double> sentimentOrientations) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\t");

                String sentimentWord = tokens[0];
                Double sentimentOrientation = Double.parseDouble(tokens[1])/5;

                sentiments.add(sentimentWord);
                sentimentOrientations.add(sentimentOrientation);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }



}
