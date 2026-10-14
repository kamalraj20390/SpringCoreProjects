package com.adda.app.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.adda.app.custom.MyCustomGenrator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "stdtab")
public class Student {
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "test")
	@GenericGenerator(name = "test",type = MyCustomGenrator.class)
	private Integer stdId;
	@NonNull
	private String stdName;
	@NonNull
	private Double stdFee;
	//@NonNull
	@CreationTimestamp
	private LocalDate stdDoj;

}
