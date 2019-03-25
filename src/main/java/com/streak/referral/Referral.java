package com.streak.referral;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Referral {

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("close_date")
  @Expose
  private String closeDate;

  @SerializedName("referred_by")
  @Expose
  private String referredBy;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCloseDate() {
    return closeDate;
  }

  public void setCloseDate(String closeDate) {
    this.closeDate = closeDate;
  }

  public String getReferredBy() {
    return referredBy;
  }

  public void setReferredBy(String referredBy) {
    this.referredBy = referredBy;
  }
}
