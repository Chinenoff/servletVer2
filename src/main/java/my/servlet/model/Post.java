package my.servlet.model;

import com.google.gson.annotations.Expose;

public class Post {

  private long id;

  private String content;

  private transient boolean remove;

  public Post() {
  }

  public Post(long id, String content) {
    this.id = id;
    this.content = content;
    this.remove = false;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isRemove() {
    return remove;
  }

  public void setRemove(boolean remove) {
    this.remove = remove;
  }
}
