package cs.sbu.Processing;

import java.util.ArrayList;

public class Word {
    private ArrayList<String> words;
    private long id;

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Word(ArrayList<String> words, long id) {
        this.words = words;
        this.id = id;
    }
}
