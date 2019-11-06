package cs.sbu.Processing;

import java.util.ArrayList;

public class Word {
    private ArrayList<String> words;
    private long id;
    private ArrayList<Long> relations;

    public Word(ArrayList<String> words, long id, ArrayList<Long> relations) {
        this.words = words;
        this.id = id;
        this.relations = relations;
    }

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

    public ArrayList<Long> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Long> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {

        return this.getWords().toString() + "_&#&_" + this.getId() + "_&#&_" + this.getRelations().toString();
    }

}
