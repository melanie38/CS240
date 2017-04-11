package cs240.byu.edu.spellcorrector_startingcode_android.StudentPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by melanie on 04/05/2016.
 */
public class MyspellCorrector implements ISpellCorrector {
    Trie myTrie = new Trie();
    TreeSet<String> found = new TreeSet<>();
    TreeSet<String> notFound = new TreeSet<>();

    /**
     * Tells this <code>ISpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @param dictionaryFile File containing the words to be used
     * @throws IOException If the file cannot be read
     */
    public void useDictionary(InputStreamReader dictionaryFile) throws IOException {

        BufferedReader toRead = new BufferedReader(dictionaryFile);
        String line = toRead.readLine();

        while (line != null) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                myTrie.add(words[i]);
            }
            line = toRead.readLine();
        }
    }

    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>
     * @param inputWord
     * @return The suggestion
     * @throws NoSimilarWordFoundException If no similar word is in the dictionary
     */
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
        found.clear();
        notFound.clear();

        if (myTrie.find(inputWord) != null) {
            return inputWord.toLowerCase();
        }

        distanceEdit(inputWord, found, notFound);

        if (!found.isEmpty()) {
            return similarWord(found);
        }
        else {
            TreeSet<String> foundBis = new TreeSet<>();
            TreeSet<String> notFoundBis = new TreeSet<>();
            for (String toFind : notFound) {
                distanceEdit(toFind, foundBis, notFoundBis);
            }
            if (!foundBis.isEmpty()) {
                return similarWord(foundBis);
            }
        }
        throw new NoSimilarWordFoundException();
    }
    public void distanceEdit(String inputWord, TreeSet<String> isFound, TreeSet<String> isNotFound) {
        deletion(inputWord, isFound, isNotFound);
        transposition(inputWord, isFound, isNotFound);
        alteration(inputWord, isFound, isNotFound);
        insertion(inputWord, isFound, isNotFound);

    }
    public void deletion(String inputWord, TreeSet<String> isFound, TreeSet<String> isNotFound) {
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder str = new StringBuilder(inputWord);
            str.deleteCharAt(i);
            String toFind = str.toString();
            checkString(toFind, isFound, isNotFound);
        }
    }
    public void transposition(String inputWord, TreeSet<String> isFound, TreeSet<String> isNotFound) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder str = new StringBuilder(inputWord);
            char a = str.charAt(i);
            char b = str.charAt(i+1);
            str.setCharAt(i, b);
            str.setCharAt(i+1, a);
            String toFind = str.toString();
            checkString(toFind, isFound, isNotFound);
        }
    }
    public void alteration(String inputWord, TreeSet<String> isFound, TreeSet<String> isNotFound) {
        for (int i = 0; i < inputWord.length(); i++ ) {
            char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            for (int j = 0; j < alpha.length; j++) {
                StringBuilder str = new StringBuilder(inputWord);
                str.setCharAt(i, alpha[j]);
                String toFind = str.toString();
                checkString(toFind, isFound, isNotFound);
            }
        }
    }
    public void insertion(String inputWord, TreeSet<String> isFound, TreeSet<String> isNotFound) {
        for (int i = 0; i < inputWord.length() + 1; i++) {
            char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            for (int j = 0; j < alpha.length; j++) {
                StringBuilder str = new StringBuilder(inputWord);
                str.insert(i, alpha[j]);
                String toFind = str.toString();
                checkString(toFind, isFound, isNotFound);
            }
        }
    }
    public String similarWord(TreeSet<String> isFound) {
        int ref = 0;
        String reff = new String();
        for (String toFind : isFound) {
            int freq1 = 0;
            if (myTrie.find(toFind) != null) {
                freq1 = myTrie.find(toFind).getValue();
            }
            if (freq1 < ref) {
                isFound.remove(toFind);
            } else if (freq1 > ref) {
                if ( reff != null) {
                    isFound.remove(reff);
                }
                ref = freq1;
                reff = toFind;
            }
        }
        return isFound.first().toString();
    }
    public void checkString(String toFind, TreeSet<String> found, TreeSet<String> notFound) {
        ITrie.INode ptr = myTrie.find(toFind);
        if (ptr != null){
            found.add(toFind);
        }
        else {
            notFound.add(toFind);
        }

    }
}
