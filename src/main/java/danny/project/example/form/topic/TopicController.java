package danny.project.example.form.topic;

import danny.project.example.form.Enums.ColumnStatus;
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
@SessionAttributes("topic")
public class TopicController {

	@Autowired
	private TopicService topicService;

//	@GetMapping("/")
//	public String index(){
//		return "index";
//	}

	@GetMapping("")
	public String allTopics(Model model) {
		List<Topic> topics = topicService.getAllTopics();
		model.addAttribute("topics",topics);
		model.addAttribute("title","All Topics");
		return "topics/topics";
	}

//	@GetMapping("/{id}")
//	public Topic getTopic(@PathVariable Integer id) {
//		return topicService.getTopic(id);
//	}

	@GetMapping("/add")
	public String addNewTopic(Model model) {
		Topic topic = new Topic();
		topic.setStatus(ColumnStatus.CURRENT);
		topic.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
		model.addAttribute("topic", topic);
		model.addAttribute("title","Add Topic");
		return "topics/add";
	}

	@PostMapping(value = "/add",params = "submit")
	public String saveNewTopic(@Valid @ModelAttribute("topic") Topic topic, BindingResult bindingResult){

		if (bindingResult.hasErrors()){
			return "topics/add";
		}

		topicService.addTopic(topic);
		return "redirect:/topics";
	}

	@PostMapping(value = "/add", params = "cancel")
	public String cancelNewTopic(){
		return "redirect:/topics";
	}

	@GetMapping("/edit/{id}")
	public String editExistingTopic(Model model, @PathVariable Integer id){
		Topic topic = topicService.getTopic(id);
		model.addAttribute("title", "Edit Topic: " + id.toString());
		model.addAttribute("topic", topic);
		return "topics/edit";
	}

	@PostMapping(value = "/edit/{id}", params = "submit")
	public String updateExistingTopic(@Valid @ModelAttribute("topic") Topic topic, BindingResult bindingResult, SessionStatus sessionStatus) {

		if (bindingResult.hasErrors()){
			return "topics/edit";
		}

    topic.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
		topicService.updateTopic(topic);
		sessionStatus.setComplete();
		return "redirect:/topics";
	}

	@PostMapping(value = "/edit/{id}", params = "cancel")
	public String cancelEditToTopic(){
		return "redirect:/topics";
	}

	@PostMapping("/delete/{id}")
	public String deleteTopic(@PathVariable Integer id) {
	  Topic topic = topicService.getTopic(id);
	  topic.setStatus(ColumnStatus.DELETED);
    topic.setUpdatedDatetime(Timestamp.valueOf(LocalDateTime.now()));
		topicService.updateTopic(topic);
		return "redirect:/topics";
	}
}
