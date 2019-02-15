package edu.xinan.demo.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hoaven on 2019/2/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String id;
  private String name;
  private String email;
}
