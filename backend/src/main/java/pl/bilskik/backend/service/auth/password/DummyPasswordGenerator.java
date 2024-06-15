package pl.bilskik.backend.service.auth.password;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DummyPasswordGenerator {

    private final PasswordMapper passwordMapper;
    private final PasswordLengthGenerator passwordLengthGenerator;

    public String generateDummyRange() {
        int baseDummyPasswordRange = passwordLengthGenerator.generateDummyBasePasswordLength();
        int partialPasswordLength = passwordLengthGenerator.generatePartialPasswordLength(baseDummyPasswordRange);

        Random random = new Random();
        TreeSet<Integer> indexTs = new TreeSet<>();

        while(partialPasswordLength != indexTs.size()) {
            indexTs.add(random.nextInt(baseDummyPasswordRange));
        }

        return passwordMapper
                .mapPartialPasswordIndexesToString(indexTs);
    }

}
