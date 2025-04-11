package thelazycoder.school_expenditure_management.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import thelazycoder.school_expenditure_management.Configuration.CustomUserDetail;
import thelazycoder.school_expenditure_management.Model.User;
import thelazycoder.school_expenditure_management.Repository.UserRepository;

@Component
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
   private final UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
       this.userRepository = userRepository;
   }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetail(user);
    }
}
