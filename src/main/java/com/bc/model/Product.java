package com.bc.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Embeddable
@Entity
public class Product {

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
	@SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", initialValue = 81)
	private Integer productId;
	private String nombre;
	private String marca;
	private String modelo;
	private Integer precio;
	private Double garantia;
	private String color;
	private String voltaje;
	private Double alto;
	private Double ancho;
	private Double profundidad;
	private String eficienciaEnergetica;
	private Double peso;
	private String imageUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;
}
