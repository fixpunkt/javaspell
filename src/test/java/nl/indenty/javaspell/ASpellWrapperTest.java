package nl.indenty.javaspell;

import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;




/**
 * Unit test for simple App.
 */
public class ASpellWrapperTest {

    private ASpellWrapper aSpell;

    public ASpellWrapperTest() {
        aSpell = new ASpellWrapper("nl");
    }


    @Test
    public void testCheckString() throws IOException, ASpellException{
        List<SpellCheckResult> result = aSpell.checkString("Dit is eeb test");
        Assert.assertEquals(1, result.size());
        if (result.size() == 1){
            System.out.println(result);
            Assert.assertEquals("eeb", result.get(0).getWord());
            Assert.assertEquals(2, result.get(0).getWordIndex());
            Assert.assertEquals(7, result.get(0).getStartIndex());
        }
    }



}
