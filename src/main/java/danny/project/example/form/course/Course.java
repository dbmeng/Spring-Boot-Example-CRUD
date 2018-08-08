package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import danny.project.example.form.topic.Topic;
import danny.project.example.form.validation.Validations.ValidatePhoneNumber;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="COURSE")
public class Course {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="course_id_sequence")
  @SequenceGenerator(name="course_id_sequence", sequenceName = "course_id_seq", allocationSize=1)
  private Integer id;

  @Column(name = "name", nullable = false)
  @NotEmpty(message = "Please enter a course name")
  private String courseName;

  @Column(name = "description", nullable = false)
  @NotEmpty(message = "Please enter a course description")
  private String courseDescription;

  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @Column(name="status", nullable = false)
  @Enumerated(EnumType.STRING)
  private ColumnStatus status;

  @Column(name="course_date", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @FutureOrPresent(message = "Course date cannot be in the past")
  @NotNull(message = "Please enter a course date")
  private Date courseDate;

  @Column(name = "updated_datetime", nullable = false)
  private Timestamp updatedDatetime;

  @Column(name = "course_phone_number", nullable = false)
  @ValidatePhoneNumber
  private String coursePhoneNumber;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  private List<CourseFile> courseFile;

  public Course() {}

  public Course(Integer id, String courseName, String courseDescription, Topic topic, ColumnStatus status, Date courseDate, Timestamp updatedDatetime, String coursePhoneNumber) {
    this.id = id;
    this.courseName = courseName;
    this.courseDescription = courseDescription;
    this.topic = topic;
    this.status = status;
    this.courseDate = courseDate;
    this.updatedDatetime = updatedDatetime;
    this.coursePhoneNumber = coursePhoneNumber;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
  public String getCourseName() {
    return courseName;
  }
  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseDescription() {
    return courseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
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

  public Date getCourseDate() {
    return courseDate;
  }

  public void setCourseDate(Date courseDate) {
    this.courseDate = courseDate;
  }

  public Timestamp getUpdatedDatetime() {
    return updatedDatetime;
  }

  public void setUpdatedDatetime(Timestamp updatedDatetime) {
    this.updatedDatetime = updatedDatetime;
  }

  public String getCoursePhoneNumber() {
    return coursePhoneNumber;
  }

  public void setCoursePhoneNumber(String coursePhoneNumber) {
    this.coursePhoneNumber = coursePhoneNumber;
  }

  public List<CourseFile> getCurrentCourseFileForCourse(){
    return courseFile.stream()
        .filter(courseFile -> courseFile.status == ColumnStatus.CURRENT)
        .collect(Collectors.toList());
  }
}
