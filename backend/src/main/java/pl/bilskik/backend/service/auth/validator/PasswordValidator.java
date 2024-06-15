package pl.bilskik.backend.service.auth.validator;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.*;
import me.gosimple.nbvcxz.scoring.Result;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.service.exception.EntropyException;

import java.util.List;

@Component
public class PasswordValidator {

    public Entropy countEntropy(String username, String password) {
        Nbvcxz nbvcxz = new Nbvcxz(configuration(List.of(username)));
        Result result = nbvcxz.estimate(password);
        return getEntropy(result.getBasicScore());
    }

    private Entropy getEntropy(int result) {
        return switch (result) {
            case 4 -> Entropy.GOOD;
            case 3 -> Entropy.REASONABLE;
            case 2 -> Entropy.POOR;
            case 1 -> Entropy.WEAK;
            case 0 -> Entropy.TERRIBLE;
            default -> throw new EntropyException("Cannot count Entropy!");
        };
    }

    private Configuration configuration(List<String> excludedWords) {
        List<Dictionary> dictionaries = buildExcludedDictionary(excludedWords);
        Configuration configuration = new ConfigurationBuilder()
                .setMinimumEntropy(45D)
                .setDictionaries(dictionaries)
                .createConfiguration();
        return configuration;
    }

    private List<Dictionary> buildExcludedDictionary(List<String> excludedWords) {
        List<Dictionary> dictionaryList = ConfigurationBuilder.getDefaultDictionaries();
        dictionaryList.add(new DictionaryBuilder()
                .setDictionaryName("excluded")
                .setExclusion(true)
                .addWords(excludedWords, 0)
                .createDictionary());
        return dictionaryList;
    }

}
