package com.redrock.messageboard.model;

import lombok.Data;

@Data
public class Agree {
    private int textid;

    private int click;

    private int uid;

    private int id;

  public Agree(int uid,int textid,int click){
      this.uid=uid;
      this.click=click;
      this.textid=textid;
  }
  public Agree(){

  }
}
