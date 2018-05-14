package danny.project.example.form.topic;

import danny.project.example.form.Enums.ColumnStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="TOPIC")
public class Topic {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="topic_id_sequence")
	@SequenceGenerator(name="topic_id_sequence", sequenceName = "topic_id_seq", allocationSize=1)
	private Integer id;

	@Column(name="name", nullable = false)
	@NotEmpty(message = "Please enter a name")
	private String name;

	@Column(name="description", nullable = false)
	@NotEmpty(message = "Please enter a description")
	private String description;

	@Column(name="status", nullable = false)
  @Enumerated(EnumType.STRING)
	private ColumnStatus status;

	@Column(name = "updated_datetime", nullable = false)
  private Timestamp updatedDatetime;

	public Topic() {};

	public Topic(Integer id, String name, String description, ColumnStatus status, LocalDateTime updatedDatetime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.updatedDatetime = Timestamp.valueOf(updatedDatetime);
	}

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ColumnStatus getStatus() {
    return status;
  }

  public void setStatus(ColumnStatus status) {
    this.status = status;
  }

  public Timestamp getUpdatedDatetime() {
    return updatedDatetime;
  }

  public void setUpdatedDatetime(Timestamp updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }
}
