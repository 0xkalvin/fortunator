package com.fortunator.api.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonIgnore
	@OneToOne
	private User user;
    
    private Integer level;
    private String levelName;
    private BigDecimal levelScore;
    private BigDecimal maxLevelScore;
    
    public Level() {
    }

	public Level(User user, Integer level, String levelName, BigDecimal levelScore) {
		this.user = user;
		this.level = level;
		this.levelName = levelName;
		this.levelScore = levelScore;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public void calculateLevel(BigDecimal score) {
		level = score.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP).intValue();
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public BigDecimal getLevelScore() {
		return levelScore;
	}
	
	public void setLevelScore(BigDecimal levelScore) {
		this.levelScore = levelScore;
	}

	public void calculateLevelScore(boolean levelUp, BigDecimal toAdd) {
		if(levelUp) {
			setMaxLevelScore();
			BigDecimal nextLevelScore = maxLevelScore.subtract(levelScore);
			BigDecimal levelBalance = toAdd.subtract(nextLevelScore);
			levelScore = levelBalance;
		} else {
			levelScore = levelScore.add(toAdd);
		}
	}
		
	public BigDecimal getMaxLevelScore() {
		return maxLevelScore;
	}

	public void setMaxLevelScore() {
		maxLevelScore = BigDecimal.valueOf(level * 100);
	}

	public void verifyLevelName() {
		if(level < 50) {
			levelName = LevelNameEnum.INICIANTE.getDescription();
		} else if(level < 100) {
			levelName = LevelNameEnum.INTERMEDIARIO.getDescription();
		} else if(level < 150) {
			levelName = LevelNameEnum.AVANCADO.getDescription();
		} else {
			levelName = LevelNameEnum.SUPER_AVANCADO.getDescription();
		}
	}
}
