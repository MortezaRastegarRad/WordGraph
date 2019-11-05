package cs.sbu.Processing;

import java.util.ArrayList;

public class Text {
    private String text;
    private long id;
    private ArrayList<Long> relations;

    Text(String text, long id, ArrayList<Long> relations) {
        this.text = text;
        this.id = id;
        this.relations = relations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
