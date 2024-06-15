package pl.bilskik.backend.service.auth.password;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.entity.Password;

import java.util.*;

import static pl.bilskik.backend.service.auth.password.PasswordConstraints.PARTIAL_PASSWORDS_NUMBER;

@Service
@RequiredArgsConstructor
public class PasswordCreatorImpl implements PasswordCreator {

    private final PasswordLengthGenerator passwordLengthGenerator;
    private final PasswordMapper passwordMapper;

    public List<Password> createPartialPasswords(String password) { //create all partial passwords
        List<Character> basePasswordAsChars = password.chars()
                .mapToObj(c -> (char) c).toList();

        Set<String> indexesSet = new HashSet<>();
        Set<String> partialPasswordSet = new HashSet<>();

        int basePasswordLength = password.length();
        int MAX_PARTIAL_PASSWORD_LENGTH = passwordLengthGenerator
                .calculateMaxPartialPasswordLength(basePasswordLength);

        for(int i=0; i<PARTIAL_PASSWORDS_NUMBER; i++) {
            int partialPasswordLength = passwordLengthGenerator
                    .generatePartialPasswordLength(MAX_PARTIAL_PASSWORD_LENGTH);
            SortedSet<Integer> indexTs = generatePartialPasswordIndexes(partialPasswordLength, basePasswordLength);

            String partialPasswordIndexes = passwordMapper
                    .mapPartialPasswordIndexesToString(indexTs);
            indexesSet.add(partialPasswordIndexes);

            String partialPassword = passwordMapper
                    .fromPartialPasswordIndexesToPartialPassword(indexTs, basePasswordAsChars);
            partialPasswordSet.add(partialPassword);
        }

        List<Password> passwordList = passwordMapper
                .mapFromStringPasswordSetToPasswordList(partialPasswordSet, indexesSet);

        return passwordList;
    }

    private SortedSet<Integer> generatePartialPasswordIndexes(int partPassLen, int basePasswordLength) {
        Random rand = new Random();
        SortedSet<Integer> indexes = new TreeSet<>();

        while(indexes.size() != partPassLen) {
            int randomIndex = rand.nextInt(basePasswordLength);
            indexes.add(randomIndex);
        }

        return indexes;
    }

}
