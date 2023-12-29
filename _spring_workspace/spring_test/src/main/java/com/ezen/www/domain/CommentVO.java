package com.ezen.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class CommentVO {
	private int cno;
	private int bno;
	private String writer;
	private String content;
	private String reg_date;
}
