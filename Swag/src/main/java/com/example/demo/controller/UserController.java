import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")   // Base path for all user endpoints
public class UserController {

    // In-memory storage (for demonstration only)
    private final List<User> userList = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    // GET: retrieve all users
    @GetMapping
    public List<User> getAllUsers() {
        return userList;
    }

    // GET: retrieve a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // POST: create a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(idGenerator.getAndIncrement());
        userList.add(user);
        return user;   // Return the created user (with its generated ID)
    }

    // PUT: update an existing user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User existing = getUserById(id);   // will throw if not found
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        // Update other fields as needed
        return existing;
    }

    // DELETE: delete a user by ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        User user = getUserById(id);   // ensure it exists
        userList.remove(user);
        return "User with id " + id + " deleted successfully.";
    }

    // DELETE: delete all users (optional)
    @DeleteMapping
    public String deleteAllUsers() {
        userList.clear();
        idGenerator.set(1);  // reset ID counter
        return "All users deleted.";
    }

    // ----- Inner User model (for simplicity) -----
    // In a real project, move this to a separate model package.
    public static class User {
        private int id;
        private String name;
        private String email;

        // Default constructor (required for JSON deserialization)
        public User() {}

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}