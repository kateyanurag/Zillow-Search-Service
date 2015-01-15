package com.anurag.zillow_search;

import android.media.Image;
import android.text.Spanned;
import android.widget.Toast;

class ZillowEntry{
	public String label;
	public int image;
	public String value;
	public int button;
	public CharSequence link;
	
	public ZillowEntry(String label, int image, String value) {
		super();
		this.label = label;
		this.image = image;
		this.value = value;
	}
	
	public ZillowEntry(Spanned link) {
		super();
		this.link = link;
		this.image = -1;
		this.value = "";
	}
	
	public CharSequence getLink() {
		return link;
	}

	public void setLink(Spanned link) {
		this.link = link;
	}

	public ZillowEntry(String label, int image, int button,String value) {
		super();
		this.label = label;
		this.image = image;
		this.value = value;
		this.button = button;
	}

	public int getButton() {
		return button;
	}
	public void setButton(int button) {
		this.button = button;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}