package KN07.M319_KN07;

public class Snippet {
    
    private String text;
    private String songName;

    public Snippet(String text, String songName) {
        this.text = text;
        this.songName = songName;
    }

    public String getText() { return this.text; }
    public String getSongName() { return this.songName; }

    @Override
    public String toString() {
        return String.format("%s: %s", this.songName, this.text);
    }
}

