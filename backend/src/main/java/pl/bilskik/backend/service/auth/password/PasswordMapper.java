package pl.bilskik.backend.service.auth.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.entity.Password;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Service
@RequiredArgsConstructor
public class PasswordMapper {

    private final PasswordEncoder passwordEncoder;

    public String mapPartialPasswordIndexesToString(SortedSet<Integer> indexes) {
        StringBuilder container = new StringBuilder();
        for(var index : indexes) {
            container.append(index);
            container.append(":");
        }
        //remove last ":"
        container.deleteCharAt(container.length() - 1);
        return container.toString();
    }

    public String fromPartialPasswordIndexesToPartialPassword(SortedSet<Integer> indexTs, List<Character> basePassword) {
        StringBuilder container = new StringBuilder();
        for(var i : indexTs) {
            container.append(basePassword.get((i)));
        }
        return container.toString();
    }

    public List<Password> mapFromStringPasswordSetToPasswordList(Set<String> partialPasswordSet, Set<String> indexesSet) {
        List<String> partialPasswordList = partialPasswordSet.stream().toList();
        List<String> indexesList = indexesSet.stream().toList();

        if(partialPasswordList.size() != indexesList.size()) {
            throw new IllegalArgumentException("There is an error in creating passwords!");
        }

        List<Password> passwordList = new ArrayList<>();
        for(int i=0; i<partialPasswordList.size(); i++) {
            passwordList.add(new Password(indexesList.get(i),
                    passwordEncoder.encode(partialPasswordList.get(i))));
        }

        return passwordList;
    }
}
