package io.neko.tasks.piglatin;

import java.util.Arrays;

public class Translator {
    public String translate(final String textToTranslate) {

        if (textToTranslate == null) {
            throw new IllegalArgumentException("String for translation must be not null");
        }

        if (textToTranslate.equals("")) {
            return "";
        }

        String[] words = textToTranslate.split(" ");
        String[] translatedWords = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            translatedWords[i] = translateWord(words[i]);
        }
        return String.join(" ", translatedWords);
    }

    private String translateWord(final String wordToTranslate) {
        if (wordToTranslate.equals("-")) {
            return "-";
        }

        String[] wordParts = wordToTranslate.split("-");
        String[] translatedParts = new String[wordParts.length];
        for (int i = 0; i < wordParts.length; i++) {
            translatedParts[i] = translateWord0(wordParts[i]);
        }

        return String.join("-", translatedParts);
    }


    private String translateWord0(final String wordToTranslate) {
        String translatedWord = normalizeWord(wordToTranslate);

        if (wordEndsWithWay(translatedWord)) {
            translatedWord = wordToTranslate;
        } else {
            if (wordStartsWithVowel(translatedWord)) {
                translatedWord = translateWordWithVowel(translatedWord);
            } else {
                translatedWord = translateWordWithConsonant(translatedWord);
            }
        }
        String result = applyCapitalization(wordToTranslate, translatedWord);
        result = applyPunctuation(wordToTranslate, result);
        return result;
    }

    private String applyCapitalization(String originalWord, String translatedWord) {
        boolean[] capitalizationArray = new boolean[originalWord.length()];
        int capitalizedLetterCount = 0;
        for (int i = 0; i < originalWord.length(); i++) {
            char currentChar = originalWord.charAt(i);
            boolean isLetterCapital = Character.isUpperCase(currentChar);
            capitalizationArray[i] = isLetterCapital;
            if (isLetterCapital) {
                capitalizedLetterCount++;
            }
        }

        StringBuilder resultBuilder = new StringBuilder(translatedWord);
        if (capitalizedLetterCount > 0) {
            for (int i = 0; i < capitalizationArray.length; i++) {
                if (capitalizationArray[i]) {
                    char changedChar = Character.toUpperCase(resultBuilder.charAt(i));
                    resultBuilder.setCharAt(i, changedChar);
                }

            }
        }
        return resultBuilder.toString();
    }

    private String applyPunctuation(String originalWord, String translatedWord) {
        char[] punctuationCharacters = {'.', ',', '\'', '\"', '!', '?', ':', ';'};
        char fillChar = 'x';
        char[] punctuationArray = new char[originalWord.length()];
        Arrays.fill(punctuationArray, fillChar);
        int punctuationsCount = 0;
        for (int i = 0; i < originalWord.length(); i++) {
            char currentChar = originalWord.charAt(i);
            for (int j = 0; j < punctuationCharacters.length; j++) {
                if (currentChar == punctuationCharacters[j]) {
                    punctuationsCount++;
                    punctuationArray[i] = currentChar;
                    break;
                }
            }
        }

        if (punctuationsCount == 0) {
            return translatedWord;
        }

        reverseArray(punctuationArray);
        char[] resultArray = new char[translatedWord.length() + punctuationsCount];
        char[] translatedArray = translatedWord.toCharArray();
        reverseArray(translatedArray);
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < punctuationArray.length) {
            if (punctuationArray[i] != 'x') {
                resultArray[k] = punctuationArray[i];
                i++;
                k++;
            } else {
                resultArray[k] = translatedArray[j];
                j++;
                k++;
                i++;
            }
        }

        while (j < translatedArray.length) {
            resultArray[k++] = translatedArray[j++];
        }

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder = resultBuilder.append(resultArray).reverse();

        return resultBuilder.toString();
    }

    private boolean wordStartsWithVowel(final String word) {
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        char firstLetter = Character.toLowerCase(word.charAt(0));
        for (char vowel : vowels) {
            if (firstLetter == vowel) {
                return true;
            }
        }
        return false;
    }

    private boolean wordEndsWithWay(final String word) {
        return word.endsWith("way") ? true : false;
    }

    private String translateWordWithConsonant(final String word) {
        String firstLetter = word.substring(0, 1);
        String restOfTheWord = word.substring(1);
        String result = restOfTheWord + firstLetter + "ay";
        return result;
    }

    private String translateWordWithVowel(final String word) {
        String result = word + "way";
        return result;
    }

    private String normalizeWord(final String word) {
        String normalizedWord = word.toLowerCase();
        normalizedWord = normalizedWord.replaceAll("[.,\'\"!?:;]", "");

        return normalizedWord;
    }

    private void reverseArray(final char[] array) {
        int i = 0;
        int j = array.length - 1;
        char tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }
}
