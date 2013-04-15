package contradiction.client;

import api.model.Document;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 11:52 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DocumentClientTest {

    @Autowired
    private DocumentClient documentClient;


    @Test
    public void getAllDocuments() {
        List<Document> results = documentClient.findAll();

        Assert.isNull(results);
    }

    @Test
    public void getDocumentById() {
        String docId = "document1";

        Document document = documentClient.findById(docId);

        Assert.isNull(document);
        Assert.isTrue(docId.equals(document.getId()));
    }
}
