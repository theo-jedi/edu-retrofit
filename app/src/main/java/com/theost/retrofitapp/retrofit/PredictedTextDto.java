package com.theost.retrofitapp.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PredictedTextDto {

    @SerializedName("text")
    private List<String> words;
    @SerializedName("endOfWord")
    private Boolean isEndOfWord;
    @SerializedName("pos")
    private Integer position;

    public PredictedTextDto(List<String> words, Boolean isEndOfWord, Integer position) {
        this.words = words;
        this.isEndOfWord = isEndOfWord;
        this.position = position;
    }

    public PredictedText mapToPredictedText() {
        return new PredictedText(words, isEndOfWord);
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public Boolean getEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(Boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
