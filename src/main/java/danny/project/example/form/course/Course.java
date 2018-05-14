package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import danny.project.example.form.topic.Topic;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="COURSE")
public class Course {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="course_id_sequence")
	@SequenceGenerator(name="course_id_sequence", sequenceName = "course_id_seq", allocationSize=1)
	private Integer id;

	@Column(name = "name", nullable = false)
	@NotEmpty(message = "Please enter a name")
	private String name;

	@Column(name = "description", nullable = false)
	@NotEmpty(message = "Please enter a description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;

	@Column(name="status", nullable = false)
  @Enumerated(EnumType.STRING)
	private ColumnStatus status;

  @Column(name = "updated_datetime", nullable = false)
  private Timestamp updatedDatetime;

	public Course() {};

	public Course(Integer id, String name, String description, Topic topic, ColumnStatus status, Timestamp updatedDatetime) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.topic = topic;
		this.status = status;
		this.updatedDatetime = updatedDatetime;
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

	public Topic getTopic(Integer topicId) {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
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
