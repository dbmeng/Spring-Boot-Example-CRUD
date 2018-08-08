package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseFileService {

  @Autowired
  private CourseFileRepository courseFileRepository;

  public CourseFile getCourseFile(Integer courseId, ColumnStatus status) {
    return courseFileRepository.findByCourseIdAndStatus(courseId,status);
  }

  public void addCourseFile(CourseFile courseFile) {
    courseFileRepository.save(courseFile);
  }

  public void updateCourseFile(CourseFile courseFile) {
    courseFileRepository.save(courseFile);
  }
}
