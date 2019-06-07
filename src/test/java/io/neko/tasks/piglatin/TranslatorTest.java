package io.neko.tasks.piglatin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TranslatorTest {
    @Test(expected = IllegalArgumentException.class)
    public void translateNullString() {
        Translator translator = new Translator();

        String result = translator.translate(null);
    }

    @Test
    public void translateWordThatStartsWithConsonant() {
        Translator translator = new Translator();
        String stringToTranslate = "Hello";

        String result = translator.translate(stringToTranslate);

        assertEquals("Ellohay", result);
    }

    @Test
    public void translateWordThatStartsWithVowel() {
        Translator translator = new Translator();
        String stringToTranslate = "apple";

        String result = translator.translate(stringToTranslate);

        assertEquals("appleway", result);
    }

    @Test
    public void translateWordThatEndsWithWay() {
        Translator translator = new Translator();
        String wordThatEndsWithWay = "stairway";

        String result = translator.translate(wordThatEndsWithWay);

        assertEquals("stairway", result);
    }

    @Test
    public void translateWordWithCapitalization() {
        Translator translator = new Translator();
        String wordWithCapitalization = "McCloud";

        String result = translator.translate(wordWithCapitalization);

        assertEquals("CcLoudmay", result);
    }

    @Test
    public void translateWordWithPunctuationInTheMiddle() {
        Translator translator = new Translator();
        String wordWithPunctuation = "can\'t";

        String result = translator.translate(wordWithPunctuation);

        assertEquals("antca\'y", result);
    }

    @Test
    public void translateWordWithPunctuationInTheEnd() {
        Translator translator = new Translator();
        String wordWithPunctuation = "end.";

        String result = translator.translate(wordWithPunctuation);

        assertEquals("endway.", result);
    }

    @Test
    public void translateWordWithHyphen() {
        Translator translator = new Translator();
        String wordWithHyphen = "this-thing";

        String result = translator.translate(wordWithHyphen);

        assertEquals("histay-hingtay", result);
    }

    @Test
    public void translateWordWithOnlyHyphen() {
        Translator translator = new Translator();
        String wordWithHyphen = "-";

        String result = translator.translate(wordWithHyphen);

        assertEquals("-", result);
    }

    @Test
    public void translateEmptyString() {
        Translator translator = new Translator();
        String emptyString = "";

        String result = translator.translate(emptyString);

        assertEquals("", result);
    }

    @Test
    public void translateTextWithAllCases() {
        Translator translator = new Translator();
        String text = "Hello McCloud. Please, use stairway - because you can't get apple with tips-and-tricks";

        String result = translator.translate(text);

        assertEquals("Ellohay CcLoudmay. Leasepay, useway stairway - ecausebay ouyay antca'y etgay appleway ithway ipstay-andway-rickstay", result);
    }
}
