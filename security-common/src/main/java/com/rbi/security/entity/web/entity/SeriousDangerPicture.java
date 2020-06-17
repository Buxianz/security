package com.rbi.security.entity.web.entity;


import lombok.Data;

@Data
public class SeriousDangerPicture {
  /**
   * ID
   */
  private Integer id;
  /**
   * 重大危险源ID
   */
  private Integer seriousDangerId;
  /**
   * 危险源图片路径
   */
  private String seriousDangerPicturePath;

}
