package Dictionary.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "English")
public class English {
    @DatabaseField(generatedId = true, index = true)
    private long Id;
    @DatabaseField(canBeNull = false, index = true)
    private String Word;
    @DatabaseField(canBeNull = true, index = true)
    private String Type;
    @DatabaseField(canBeNull = false, index = true)
    private String Meaning;
    @DatabaseField(canBeNull = true, index = true)
    private String Pronunciation;
    @DatabaseField(canBeNull = true, index = true)
    private String Example;
    @DatabaseField(canBeNull = true, index = true)
    private String Synonym;
    @DatabaseField(canBeNull = true, index = true)
    private String Antonyms;
    @DatabaseField(canBeNull = false, index = true)
    private String Vietnamese;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getPronunciation() {
        return Pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        Pronunciation = pronunciation;
    }

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }

    public String getSynonym() {
        return Synonym;
    }

    public void setSynonym(String synonym) {
        Synonym = synonym;
    }

    public String getAntonyms() {
        return Antonyms;
    }

    public void setAntonyms(String antonyms) {
        Antonyms = antonyms;
    }

    public String getVietnamese() {
        return Vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        Vietnamese = vietnamese;
    }

    public English() {
        Word = "";
        Type = "";
        Meaning = "";
        Pronunciation = "";
        Example = "";
        Synonym = "";
        Antonyms = "";
        Vietnamese = "";
    }

    public English(String word, String type, String meaning, String pronunciation, String example, String synonym, String antonyms, String vietnamese) {
        Word = word;
        Type = type;
        Meaning = meaning;
        Pronunciation = pronunciation;
        Example = example;
        Synonym = synonym;
        Antonyms = antonyms;
        Vietnamese = vietnamese;
    }
}
