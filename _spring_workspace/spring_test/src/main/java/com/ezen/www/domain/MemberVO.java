package com.ezen.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class MemberVO {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String home;
	private int age;
	private String reg_date;
	private String last_login;
}
