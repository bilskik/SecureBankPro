package pl.bilskik.backend.service.auth.creator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PasswordCreator {

    private final PasswordEncoder passwordEncoder;

    public List<Password> createPasswords(String password, Users users) { //create partial passwords, each returned PasswordObj store user
        List<Password> passwordList = createPasswords(password);
        for(var p : passwordList) {
            p.setUser(users);
        }
        return passwordList;
    }

    public List<Password> createPasswords(String password) { //create partial passwords, each returned PasswordObj doesn't store specific user
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

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
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
        }
        return passwordList;
    }

    private String mapIndicies(SortedSet<Integer> indexTs) {
        StringBuilder container = new StringBuilder();
        for(var i : indexTs) {
            container.append(i);
            container.append(":");
        }
        container.deleteCharAt(container.length() - 1);
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

    public String generateDummyRange(int dummyPassRange, int dummyPartPassLen) {
        Random random = new Random();
        TreeSet<Integer> indexTs = new TreeSet<>();
        while(dummyPartPassLen != indexTs.size()) {
            indexTs.add((Integer) random.nextInt(dummyPassRange));
        }
        return mapIndicies(indexTs);
    }

    public int generateDummyPassLen() {
        Random random = new Random();
        boolean decision = true;
        for(int i=0; i<4; i++) {
            boolean currDecision = random.nextBoolean();
            decision = currDecision && decision;
        }
        if(decision) {
            return random.nextInt(20 - 12  + 1) + 12;
        } else {
            return random.nextInt(14 - 12 + 1) + 12;
        }
    }
}
