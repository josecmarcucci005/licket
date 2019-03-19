package org.licket.core.view.hippo.vue.annotation;


/**
 * @author josecmarcucci
 */
public enum LicketSecurityType {
  
  
  NO_ROLE(""),
  USER("USER"),
  ADMIN("ADMIN"),
  USER_AND_ADMIN("USER");
  
  private String role;

  LicketSecurityType(String role) {
    this.role = role;
  }
  
  public String getRole() {
    return role;
  }
  
}
