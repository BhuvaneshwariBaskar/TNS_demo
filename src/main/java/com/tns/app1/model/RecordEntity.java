package com.tns.app1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "records")

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "age")
	private Integer age;

	@Column(name = "city")
	private String city;

	public RecordEntity(String name2, String email2, Integer age2, String city2) {
		this.name = name2;
		this.email = email2;
		this.age = age2;
		this.city = city2;
	}
}
