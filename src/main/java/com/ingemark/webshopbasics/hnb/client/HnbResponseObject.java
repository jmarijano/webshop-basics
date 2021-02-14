package com.ingemark.webshopbasics.hnb.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HnbResponseObject {

	@JsonProperty(value = "Broj tečajnice")
	private Integer brojTecajnice;

	@JsonProperty(value = "Datum primjene")
	private String datumPromjene;

	@JsonProperty(value = "Država")
	private String drzava;

	@JsonProperty(value = "Šifra valute")
	private Integer sifraValute;

	@JsonProperty(value="Valuta")
	private String valuta;

	@JsonProperty(value="Jedinica")
	private Integer jedinica;

	@JsonProperty(value = "Kupovni za devize")
	private String kupovniZaDevize;

	@JsonProperty(value = "Srednji za devize")
	private String srednjiZaDevize;

	@JsonProperty(value = "Prodajni za devize")
	private String prodajniZaDevize;

}
