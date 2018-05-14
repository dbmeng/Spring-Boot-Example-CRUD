package danny.project.example.form.topic;

import danny.project.example.form.Enums.ColumnStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic,Integer> {

  List<Topic> findByStatus(ColumnStatus status);

}
