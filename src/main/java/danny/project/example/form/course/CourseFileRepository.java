package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.data.repository.CrudRepository;

public interface CourseFileRepository extends CrudRepository<CourseFile,Integer> {

  CourseFile findByCourseIdAndStatus(Integer courseId, ColumnStatus status);

}
