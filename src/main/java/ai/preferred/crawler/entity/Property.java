package ai.preferred.crawler.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * This is an entity class for storing the Property Attributes.
 * 
 * @author Zhei Wei
 * @author Aneesha
 * 
 */
public class Property {
 private String title;
 private String price;
 private String type;
 private String area;
 private String psf;
 private String numBeds;
 /**
  * parent constructor
  * @param Title is title
  * @param Price is price
  * @param Type is type
  * @param Area is area
  * @param Psf is per square feet
  * @param NumBeds is number of beds
  */
 public Property(String Title, String Price, String Type, String Area, String Psf, String NumBeds) {
  title = Title;
  price = Price;
  type = Type;
  area = Area;
  psf = Psf;
  numBeds = NumBeds;
 }
 /**
  * calls the parent constructor with no arguments
  */
 public Property() {
  super();
 }
 /**
  * get Title of property
  * @return title is the title of property
  */
 public String getTitle() {
  return title;
 }
 /**
  * set Title of property
  * @param title is the title of property
  */
 public void setTitle(String title) {
  this.title = title;
 }
 /**
  * get price of property
  * @return price is the price of property
  */
 public String getPrice() {
  return price;
 }
 /**
  * set price of property
  * @param price is the price of property
  */
 public void setPrice(String price) {
  this.price = price;
 }
 /**
  * get Type of property
  * @return type is the type of property
  */
 public String getType() {
  return type;
 }
 /**
  * set Type of property
  * @param type is the type of property
  */
 public void setType(String type) {
  this.type = type;
 }
 /**
  * get Area of property
  * @return area is the area of property
  */
 public String getArea() {
  return area;
 }
 /**
  * set Area of property
  * @param area is the area of property
  */
 public void setArea(String area) {
  this.area = area;
 }
 /**
  * get number of beds
  * @return numBeds is the number of beds in property
  */
 public String getNumBeds() {
  return numBeds;
 }
 /**
  * set number of beds as the first number in the element
  * @param numBeds is the number of beds in property
  */
 public void setNumBeds(String numBeds) {
  if (numBeds.length() >1) {
   this.numBeds = numBeds.substring(0,1);
  } else {
   this.numBeds = numBeds; 
  } 
 }
 /**
  * get price per square feet of property
  * @return psf is the price per square feet of property
  */
 public String getPsf() {
  return psf;
 }
 /**
  * set price per square feet of property
  * @param psf is the price per square feet of property
  */
 public void setPsf(String psf) {
  this.psf = psf;
 }
 /**
  * convert element to string value
  * @return value as string
  */
 @Override
 public String toString() {
  return ToStringBuilder.reflectionToString(this);
 }
}