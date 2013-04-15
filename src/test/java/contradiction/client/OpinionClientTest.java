package contradiction.client;

import api.model.Opinion;
import java.util.List;
import contradiction.client.OpinionClient;
import org.junit.Ignore;
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
 * Time: 10:13 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OpinionClientTest {

    @Autowired
    private OpinionClient opinionClient;

    @Test
    public void testGetOpinions() {
        List<Opinion> result = opinionClient.getAll();

        Assert.notNull(result);
    }

    @Test
    public void testGetOpinionsByHolder() {
        String holder = "Bogdan";

        List<Opinion> result = opinionClient.findByHolder(holder);

        Assert.notNull(result);

        boolean correctHolder = true;
        if (result.size() > 0) {
            correctHolder = holder.equals(result.get(0).getHolder());
        }

        Assert.isTrue(correctHolder);
    }

    @Test
    public void testGetOpinionsByTarget() {
        List<Opinion> result = opinionClient.findByTarget("chocolate");

        Assert.notNull(result);
    }

    @Test
    public void testGetOpinionsByHolderAndTarget() {
        List<Opinion> result = opinionClient.findByHolderAndTarget("Bogdan","chocolate");

        Assert.notNull(result);
    }

}