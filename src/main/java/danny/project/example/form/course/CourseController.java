package danny.project.example.form.course;


import danny.project.example.form.Enums.ColumnStatus;
import danny.project.example.form.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/topics")
@SessionAttributes("course")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @Autowired
  private TopicService topicService;

  @Autowired
  private CourseFileService courseFileService;

  @GetMapping("/{topicId}/courses")
  public String getAllCourses(Model model, @PathVariable Integer topicId) {
    List<Course> courses = courseService.getAllCourses(topicId);
    model.addAttribute("courses", courses);
    model.addAttribute("topicId", topicId);
    model.addAttribute("title", "All Courses");
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
    model.addAttribute("title", "Add Course");
    return "courses/add";
  }

  @PostMapping(value = "/{topicId}/courses/add", params = "submit")
  public String saveNewCourse(@RequestParam(value = "courseFileUpload") Optional<MultipartFile> multipartFile, @PathVariable Integer topicId, @Valid @ModelAttribute("course") Course course, BindingResult bindingResult) throws IOException {

    if (bindingResult.hasErrors()) {
      return "courses/add";
    }

    course.setTopic(topicService.getTopic(topicId));
    course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
    courseService.addCourse(course);

    if (multipartFile.isPresent() && multipartFile.get().getSize() > 0){
      upload(multipartFile.get(),course);
    }

    return "redirect:/topics/" + topicId + "/courses";
  }

  @PostMapping(value = "/{topicId}/courses/add", params = "cancel")
  public String cancelNewCourse(@PathVariable Integer topicId) {
    return "redirect:/topics/" + topicId + "/courses";
  }

  @GetMapping("/{topicId}/courses/{courseId}/edit")
  public String editExistingCourse(Model model, @PathVariable Integer topicId, @PathVariable Integer courseId) {
    Course course = courseService.getCourse(courseId);
    model.addAttribute("title", "Edit Course: " + course.getCourseName());
    model.addAttribute("course", course);
    model.addAttribute("topicId", topicId);
    model.addAttribute("courseFileExists",courseFileExists(courseId));
    return "courses/edit";
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "submit")
  public String updateExistingCourse(@RequestParam(value = "courseFileUpload") Optional<MultipartFile> multipartFile, @PathVariable Integer topicId, SessionStatus sessionStatus, @Valid @ModelAttribute("course") Course course, BindingResult bindingResult) throws IOException {

    if (bindingResult.hasErrors()) {
      return "courses/edit";
    }

    course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));

    if (multipartFile.isPresent() && multipartFile.get().getSize() > 0){
      upload(multipartFile.get(),course);
    }

    courseService.updateCourse(course);
    sessionStatus.setComplete();

    return "redirect:/topics/" + topicId + "/courses";
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "cancel")
  public String cancelUpdateToCourse(@PathVariable Integer topicId) {
    return "redirect:/topics/" + topicId + "/courses";
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "downloadFile")
  public void downloadCourseMaterialEdit(@PathVariable Integer courseId, HttpServletResponse httpServletResponse) throws IOException {
    downloadCourseFile(courseId,httpServletResponse);
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}/edit", params = "removeFile")
  public String removeCourseMaterial(@PathVariable Integer topicId, @PathVariable Integer courseId){
    CourseFile courseFile = courseFileService.getCourseFile(courseId,ColumnStatus.CURRENT);
    courseFile.setStatus(ColumnStatus.DELETED);
    courseFileService.updateCourseFile(courseFile);
    return "redirect:/topics/" + topicId + "/courses/" + courseId + "/edit/";
  }

  @PostMapping("/{topicId}/courses/{courseId}/delete")
  public String deleteCourse(@PathVariable Integer topicId, @PathVariable Integer courseId) {
    Course course = courseService.getCourse(courseId);
    course.setStatus(ColumnStatus.DELETED);
    course.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
    courseService.updateCourse(course);
    return "redirect:/topics/" + topicId + "/courses";
  }

  @PostMapping(value = "/{topicId}/courses/{courseId}", params = "downloadCourseMaterial")
  public void downloadCourseMaterial(@PathVariable Integer courseId, HttpServletResponse httpServletResponse) throws IOException {
    downloadCourseFile(courseId,httpServletResponse);
  }

  private void upload(MultipartFile multipartFile, Course course) throws IOException {
    String fileName = multipartFile.getOriginalFilename();
    String contentType = multipartFile.getContentType();
    byte [] fileData = multipartFile.getBytes();
    int byteSize = fileData.length;
    CourseFile courseFile = new CourseFile();
    courseFile.setFileName(fileName);
    courseFile.setContentType(contentType);
    courseFile.setFileData(fileData);
    courseFile.setFileByteSize(byteSize);
    courseFile.setCourse(course);
    courseFile.setStatus(ColumnStatus.CURRENT);
    courseFile.setUploadedDatetime(Timestamp.valueOf(LocalDateTime.now()));
    courseFileService.updateCourseFile(courseFile);
  }

  private void downloadCourseFile(Integer courseId, HttpServletResponse httpServletResponse) throws IOException {
    CourseFile courseFile = courseFileService.getCourseFile(courseId,ColumnStatus.CURRENT);
    httpServletResponse.setHeader("Content-Disposition","attachment; filename="+ URLEncoder.encode(courseFile.getFileName(),"UTF-8"));
    FileCopyUtils.copy(courseFile.getFileData(),httpServletResponse.getOutputStream());
  }

  private Boolean courseFileExists(Integer courseId){
    CourseFile courseFile = courseFileService.getCourseFile(courseId,ColumnStatus.CURRENT);
    return courseFile != null;
  }

}
