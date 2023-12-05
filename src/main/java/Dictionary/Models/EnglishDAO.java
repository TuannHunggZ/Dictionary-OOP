package Dictionary.Models;

import Dictionary.API.TranslateText;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnglishDAO extends BaseDaoImpl<English, Long> {
    public EnglishDAO(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, English.class);
    }

    public String getDefinition(English english) {
        if (english == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        if (!english.getWord().isEmpty()) {
            stringBuilder.append("Word: ").append(english.getWord()).append("\n\n");
        }

        if (english.getVietnamese().isEmpty()) {
            try {
                String vietnameseMeaning = TranslateText.translate(english.getWord(), "en", "vi");
                stringBuilder.append("Vietnamese meaning: ").append(vietnameseMeaning).append("\n\n");
                english.setVietnamese(vietnameseMeaning);
                this.update(english);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            stringBuilder.append("Vietnamese meaning: ").append(english.getVietnamese()).append("\n\n");
        }

        if (!english.getType().isEmpty()) {
            stringBuilder.append("Part of Speech: ").append(english.getType()).append("\n\n");
        } else {
            stringBuilder.append("Part of Speech: ").append("No part of speech found").append("\n\n");
        }
        if (!english.getMeaning().isEmpty()) {
            stringBuilder.append("Definition: ").append(english.getMeaning()).append("\n\n");
        } else {
            stringBuilder.append("Definition: ").append("No definition found").append("\n\n");
        }

        if (!english.getPronunciation().isEmpty()) {
            stringBuilder.append("Pronunciation: ").append(english.getPronunciation()).append("\n\n");
        } else {
            stringBuilder.append("Pronunciation: ").append("No pronunciation found").append("\n\n");
        }

        if (!english.getSynonym().isEmpty()) {
            stringBuilder.append("Synonym: ").append(english.getSynonym()).append("\n\n");
        } else {
            stringBuilder.append("Synonym: ").append("No synonym found").append("\n\n");
        }
        if (!english.getAntonyms().isEmpty()) {
            stringBuilder.append("Antonym: ").append(english.getAntonyms()).append("\n\n");
        } else {
            stringBuilder.append("Antonym: ").append("No antonym found").append("\n\n");
        }
        if (!english.getExample().isEmpty()) {
            stringBuilder.append("Example: ").append(english.getExample());
        } else {
            stringBuilder.append("Example: ").append("No example found");
        }
        return stringBuilder.toString().trim();
    }

    public English equalQuery(String word) throws SQLException{
        QueryBuilder<English, Long> queryBuilder = this.queryBuilder();
        Where<English, Long> where = queryBuilder.where();
        where.eq("Word", word);
        return where.queryForFirst();
    }

    public List<English> likeQuery(String word) throws SQLException{
        QueryBuilder<English, Long> queryBuilder = this.queryBuilder();
        Where<English, Long> where = queryBuilder.where();
        where.like("Word", word + "%");
        queryBuilder.orderBy("Word", true);
        return new ArrayList<>(where.query());
    }

    public boolean deleteWord(English english) {
        try {
            this.delete(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateType(English english, String type) {
        try {
            if (!type.isEmpty() && type.isBlank()) {
                return false;
            }
            english.setType(type.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateVietnamese(English english, String vietnamese) {
        try {
            if (!vietnamese.isEmpty() && vietnamese.isBlank()) {
                return false;
            }
            english.setVietnamese(vietnamese.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateMeaning(English english, String meaning) {
        try {
            if (!meaning.isEmpty() && meaning.isBlank()) {
                return false;
            }
            english.setMeaning(meaning.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updatePronunciation(English english, String pronunciation) {
        try {
            if (!pronunciation.isEmpty() && pronunciation.isBlank()) {
                return false;
            }
            english.setPronunciation(pronunciation.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateExample(English english, String example) {
        try {
            if (!example.isEmpty() && example.isBlank()) {
                return false;
            }
            english.setExample(example.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSynonym(English english, String synonym) {
        try {
            if (!synonym.isEmpty() && synonym.isBlank()) {
                return false;
            }
            english.setSynonym(synonym.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAntonyms(English english, String antonyms) {
        try {
            if (!antonyms.isEmpty() && antonyms.isBlank()) {
                return false;
            }
            english.setAntonyms(antonyms.trim());
            this.update(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean addEnglish(English english) {
        try {
            this.create(english);
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<English> queryWordle() throws SQLException {
        String query = "SELECT * FROM English WHERE LENGTH(word) = 5 AND word NOT LIKE '%-%' AND word NOT LIKE '% %' ORDER BY word";
        GenericRawResults<English> rawResults = this.queryRaw(query, this.getRawRowMapper());
        List<English> words = rawResults.getResults();
        return new ArrayList<>(words);
    }
}
