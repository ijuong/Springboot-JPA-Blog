package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //sequence
	private int id;
	
	@Column(nullable = false, length=100)
	private String title;
	
	@Lob //대용량 데이터 베이
	private String content; //섬머노트 라이브러리 <html> 태그가 섞여서 디자인
	
	private int count;
	
	@ManyToOne(fetch = FetchType.EAGER) //many = board , one = userinfo //fetch 전략은 default가 eager -> 데이터를 조인해서 같이 가지고옴
	@JoinColumn(name = "userId")
	private UserInfo userInfo; //db는 오브젝트를 저장할 수 없어 fk를 사용
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다(fk가 아니다)db에 컬럼을 만들지 않음
	@JsonIgnoreProperties({"board"}) //board 모델의reply 에 의해 무한 참조가 되는 것을 막는다 
	@OrderBy("id desc") //reply 의 id 를 내림차순으로 정렬해서가지고 온다
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
