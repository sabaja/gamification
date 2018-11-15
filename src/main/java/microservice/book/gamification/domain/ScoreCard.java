package microservice.book.gamification.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "SCORE_CARD")
public class ScoreCard implements Serializable{

	private static final long serialVersionUID = -2454646969873103024L;
	
}
