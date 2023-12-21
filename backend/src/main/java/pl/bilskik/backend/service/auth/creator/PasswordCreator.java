package pl.bilskik.backend.service.auth.creator;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.entity.Password;

import java.util.*;

@Component
@Slf4j
public class PasswordCreator {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordCreator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<Password> createPasswords(String password) {
        List<Character> chars = password.chars()
                .mapToObj(e -> (char) e).toList();
        Set<String> indiciesList = new HashSet<>();
        Set<String> partialPasswordList = new HashSet<>();
        int passLen = password.length();
        int partPassAmount = 20;

        for(int i=0; i<partPassAmount; i++) {
            int partPassLen = generatePartPassLen(passLen);
            SortedSet<Integer> indexTs = generateIndicies(partPassLen, chars);
            indiciesList.add(mapIndicies(indexTs));
            partialPasswordList.add(mapToPassword(indexTs, chars));
        }
        List<Password> passwordList = mapToPasswordObj(partialPasswordList, indiciesList);
        return passwordList;
    }

    public SortedSet<Integer> generateIndicies(int partPassLen, List<Character> chars) {
        Random rand = new Random();
        SortedSet<Integer> indexTs = new TreeSet<>();
        while(indexTs.size() != partPassLen) {
            int randomIndex = rand.nextInt(chars.size());
            indexTs.add((Integer) randomIndex);
        }
        return indexTs;
    }

    public int generatePartPassLen(int passLen) {
        int minPartPassLen = 6;
        int maxPartPassLen = countMaxPartPassLen(passLen);
        Random random = new Random();
        return random.nextInt(maxPartPassLen - minPartPassLen + 1) + minPartPassLen;
    }

    private List<Password> mapToPasswordObj(Set<String> partialPasswordList, Set<String> indiciesList) {
        List<String> convertedPartialPasswordList = partialPasswordList.stream().toList();
        List<String> convertedIndiciesList = indiciesList.stream().toList();
        if(partialPasswordList.size() != indiciesList.size()) {
            throw new IllegalArgumentException("There is an error in creating passwords!");
        }
        List<Password> passwordList = new ArrayList<>();
        for(int i=0; i<partialPasswordList.size(); i++) {
            passwordList.add(new Password(convertedIndiciesList.get(i),
                    passwordEncoder.encode(convertedPartialPasswordList.get(i))));
            log.info(passwordList.get(i).getPassword());
            log.warn(passwordList.get(i).getRanges());

        }
        return passwordList;
    }
    private String mapIndicies(SortedSet<Integer> indexTs) {
        StringBuilder container = new StringBuilder();
        for(var i : indexTs) {
            container.append(i);
        }
        return container.toString();
    }

    private String mapToPassword(SortedSet<Integer> indexTs, List<Character> chars) {
        StringBuilder container = new StringBuilder();
        for(var i : indexTs) {
            container.append(chars.get((i)));
        }
        return container.toString();
    }



    private int countMaxPartPassLen(int passLen) {
        if(passLen <= 14) {
            return passLen - 2;
        } else if(passLen <= 17) {
            return passLen - 4;
        } else {
            return passLen - 6;
        }
    }

    public String generateDummyRange(int dummyPassLen, int dummyPartPassLen) {
        Random random = new Random();
        TreeSet<Integer> indexTs = new TreeSet<>();
        while(dummyPartPassLen != indexTs.size()) {
            indexTs.add((Integer) random.nextInt(dummyPassLen));
        }
        return mapIndicies(indexTs);
    }
}
