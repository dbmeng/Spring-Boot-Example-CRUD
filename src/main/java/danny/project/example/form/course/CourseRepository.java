package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Integer> {

	List<Course> findByTopicIdAndStatus(Integer topicId, ColumnStatus status);

}
