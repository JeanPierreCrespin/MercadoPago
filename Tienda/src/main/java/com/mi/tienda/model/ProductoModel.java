package com.mi.tienda.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductoModel  implements Serializable{

	private static final long serialVersionUID = -2949414671172713476L;
     
	private String p_name;
	private int quantity;
	private String img_url;
	private String description;
	private double price;
}
