package com.wallet.pojo;

import java.util.Date;

public class Card {
	
	private int id;
	
	private String cardNumber;
	
    private Date expiry;

    private int cvv;

	private int uid;
	
	public Card(){
		
	}

	public Card(int id, String cardNumber, Date expiry, int cvv, int uid) {
		super();
		this.id = id;
		this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
		this.uid = uid;
	}

    public Card(String cardNumber, Date expiry, int cvv, int uid) {
		super();
		this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
		this.uid = uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

    public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

    public int getCVV() {
		return cvv;
	}

	public void setCVV(int cvv) {
		this.cvv = cvv;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	

}
