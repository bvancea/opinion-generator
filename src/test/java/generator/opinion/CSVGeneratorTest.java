package generator.opinion;

import java.util.List;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/12/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CSVGeneratorTest {

    private static final String OUTPUT_DIR = "/opt/solr/";
    private static final String OUTPUT_FILE = "opinions_3.csv";

    @Autowired
    private CSVGenerator generator;

    @Test
    public void testCreateCSVDocument() {

        generator.generateCSVFile2(OUTPUT_DIR + OUTPUT_FILE);
    }


}
