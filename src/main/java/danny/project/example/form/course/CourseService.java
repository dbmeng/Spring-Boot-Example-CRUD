package danny.project.example.form.course;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> getAllCourses(Integer topicId){
		List<Course> courses = new ArrayList<>();
    ColumnStatus status = ColumnStatus.CURRENT;
		courseRepository.findByTopicIdAndStatus(topicId,status)
		.forEach(courses::add);

		return courses;
	}

	public Course getCourse(Integer courseId) {
		return courseRepository.findById(courseId).orElse(null);
	}

	public void addCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(Course course) {
		courseRepository.save(course);
	}

	public void deleteCourse(Integer courseId) {
		courseRepository.deleteById(courseId);
	}


}
