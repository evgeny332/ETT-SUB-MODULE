package com.rh.persistence.domain;

import java.util.Date;

public class PopUpSheduled
{
  private Long id;
  private Long ettId;
  private Date popUpTime;
  private Boolean status;

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long paramLong)
  {
    this.id = paramLong;
  }

  public Long getEttId()
  {
    return this.ettId;
  }

  public void setEttId(Long paramLong)
  {
    this.ettId = paramLong;
  }

  public Date getPopUpTime()
  {
    return this.popUpTime;
  }

  public void setPopUpTime(Date paramDate)
  {
    this.popUpTime = paramDate;
  }

  public Boolean getStatus()
  {
    return this.status;
  }

  public void setStatus(Boolean paramBoolean)
  {
    this.status = paramBoolean;
  }
}