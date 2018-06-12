package danny.project.example.form.course;


import danny.project.example.form.Enums.ColumnStatus;
import danny.project.example.form.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;

@Controller
@RequestMapping("/topics")
@SessionAttributes("course")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private TopicService topicService;

	@GetMapping("/{topicId}/courses")
	public String getAllCourses(Model model, @PathVariable Integer topicId) {
		List<Course> courses = courseService.getAllCourses(topicId);
		model.addAttribute("courses",courses);
		model.addAttribute("topicId", topicId);
		model.addAttribute("title","All Courses");
		return "courses/courses";
	}

//	@GetMapping("/{topicId}/courses/{courseId}")
//	public Course getCourse(@PathVariable Integer topicId, @PathVariable Integer courseId) {
//		return courseService.getCourse(courseId);
//	}

	@GetMapping("/{topicId}/courses/add")
	public String addNewCourse(Model model, @PathVariable Integer topicId) {
		Course course = new Course();
		course.setStatus(ColumnStatus.CURRENT);
		course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
		model.addAttribute("course", course);
		model.addAttribute("topicId", topicId);
		model.addAttribute("title","Add Course");
		return "courses/add";
	}

	@PostMapping(value = "/{topicId}/courses/add", params = "submit")
	public String saveNewCourse(@PathVariable Integer topicId, @Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {

		if (bindingResult.hasErrors()){
			return "courses/add";
		}

	  course.setTopic(topicService.getTopic(topicId));
		course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
		courseService.addCourse(course);
		return "redirect:/topics/" + topicId + "/courses";
	}

	@PostMapping(value = "/{topicId}/courses/add", params = "cancel")
	public String cancelNewCourse(@PathVariable Integer topicId){
		return "redirect:/topics/" + topicId + "/courses";
	}

  @GetMapping("/{topicId}/courses/{courseId}/edit")
  public String editExistingCourse(Model model, @PathVariable Integer topicId, @PathVariable Integer courseId){
    Course course = courseService.getCourse(courseId);
    model.addAttribute("title", "Edit Course: " + course.getCourseName());
    model.addAttribute("course", course);
    model.addAttribute("topicId", topicId);
    return "courses/edit";
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "submit")
  public String updateExistingCourse(@PathVariable Integer topicId, SessionStatus sessionStatus, @Valid @ModelAttribute("course") Course course, BindingResult bindingResult) {

		if (bindingResult.hasErrors()){
			return "courses/edit";
		}

		course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
    courseService.updateCourse(course);
		sessionStatus.setComplete();
    return "redirect:/topics/" + topicId + "/courses";
  }

	@PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "cancel")
	public String cancelUpdateToCourse(@PathVariable Integer topicId){
		return "redirect:/topics/" + topicId + "/courses";
	}

  @PostMapping("/{topicId}/courses/{courseId}/delete")
  public String deleteCourse(@PathVariable Integer topicId, @PathVariable Integer courseId) {
	  Course course = courseService.getCourse(courseId);
	  course.setStatus(ColumnStatus.DELETED);
    course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
    courseService.updateCourse(course);
    return "redirect:/topics/" + topicId + "/courses";
  }
}
