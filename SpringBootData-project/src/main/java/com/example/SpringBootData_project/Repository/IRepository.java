import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringBootData_project.Model.User;

public interface IRepository extends JpaRepository<User, Long> {
    
    private void add(User user) {
        // Implementation for adding a user
    }
}
