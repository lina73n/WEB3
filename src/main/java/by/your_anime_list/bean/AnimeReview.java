package by.your_anime_list.bean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * AnimeReview is a record class that represents an anime review.
 * It contains the properties' id, userId, userLogin, animeId, rate, and comment.
 */

@Entity
@Table(name = "review")
public class AnimeReview {

    @Id
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "anime_id")
    private int animeId;

    private float rate;

    private String comment;

    @Column(name = "user_login")
    private String userLogin;

    public AnimeReview() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public AnimeReview(int userId, String userLogin, int animeId, float rate, String comment) {
        this.userId = userId;
        this.userLogin = userLogin;
        this.animeId = animeId;
        this.rate = rate;
        this.comment = comment;
    }
}
