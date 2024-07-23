package rs.raf.userservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.raf.userservice.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class BootstrapDev implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
