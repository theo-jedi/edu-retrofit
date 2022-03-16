package com.theost.retrofitapp.retrofit;

import java.util.List;

public class PredictedText {

    private List<String> words;
    private Boolean isEndOfWord;

    public PredictedText(List<String> words, boolean isEndOfWord) {
        this.words = words;
        this.isEndOfWord = isEndOfWord;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
}
