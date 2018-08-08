package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COURSE_FILE_UPLOADS")
public class CourseFile {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO, generator="course_file_id_sequence")
  @SequenceGenerator(name="course_file_id_sequence", sequenceName = "course_file_uploads_seq", allocationSize=1)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "course_id",nullable = false)
  private Course course;

  @Column(name = "filename", nullable = false)
  private String fileName;

  @Column(name = "file_data",nullable = false)
  @Lob
  private byte[] fileData;

  @Column(name="content_type", nullable = false)
  private String contentType;

  @Column(name = "file_byte_size", nullable = false)
  private int fileByteSize;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  public ColumnStatus status;

  @Column(name = "uploaded_datetime",nullable = false)
  private Timestamp uploadedDatetime;

  public CourseFile(){}

  public CourseFile(MultipartFile file, String fileName, String contentType, Course course, ColumnStatus status, LocalDateTime uploadedDatetime) throws IOException {
    byte[] data = file.getBytes();
    this.fileData = data;
    this.fileName = fileName;
    this.fileByteSize = data.length;
    this.contentType = contentType;
    this.course = course;
    this.status = status;
    this.uploadedDatetime = Timestamp.valueOf(uploadedDatetime);
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public int getFileByteSize() {
    return fileByteSize;
  }

  public void setFileByteSize(int fileByteSize) {
    this.fileByteSize = fileByteSize;
  }

  public ColumnStatus getStatus() {
    return status;
  }

  public void setStatus(ColumnStatus status) {
    this.status = status;
  }

  public Timestamp getUploadedDatetime() {
    return uploadedDatetime;
  }

  public void setUploadedDatetime(Timestamp uploadedDatetime) {
    this.uploadedDatetime = uploadedDatetime;
  }
}
