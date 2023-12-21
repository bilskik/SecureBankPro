package pl.bilskik.backend.service.auth.validator.impl;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.resources.*;
import me.gosimple.nbvcxz.scoring.Result;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.service.auth.validator.enumeration.Entropy;
import pl.bilskik.backend.service.auth.exception.PasswordException;

import java.util.List;

@Component
public class PasswordValidator {
    public boolean validatePassword(String username, String password) {
        if(password == null || password.equals("")) {
            throw new PasswordException("Password is inValid! { Null or Empty }");
        }

        Nbvcxz nbvcxz = new Nbvcxz(configuration(List.of(username)));
        Result result = nbvcxz.estimate(password);
        Entropy entropy = getEntropy(result.getBasicScore());
        if(entropy == Entropy.GOOD && result.isMinimumEntropyMet()) {
            return true;
        } else {
            throw new PasswordException("Password is too weak!");
        }
    }
    public Entropy countEntropy(String username, String password) {
        Nbvcxz nbvcxz = new Nbvcxz(configuration(List.of(username)));
        Result result = nbvcxz.estimate(password);
        return getEntropy(result.getBasicScore());

    }
    private Entropy getEntropy(int result) {
        if(result == 4) {
            return Entropy.GOOD;
        }
        else if(result == 3) {
            return Entropy.REASONABLE;
        } else if(result == 2) {
            return Entropy.POOR;
        }
        else if(result == 1){
            return Entropy.WEAK;
        }
        else if(result == 0) {
            return Entropy.TERRIBLE;
        } else {
            throw new PasswordException("Cannot get Entropy!");
        }

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
    private Configuration configuration(List<String> excludedWords) {
        List<Dictionary> dictionaries = buildExcludedDictionary(excludedWords);
        Configuration configuration = new ConfigurationBuilder()
                .setMinimumEntropy(45D)
                .setDictionaries(dictionaries)
                .createConfiguration();
        return configuration;
    }

    private Entropy countEntropy(String password, int letterRange) {
        double entropyValue = password.length()*log2(letterRange);
        //checking dictionary
        if(entropyValue > 75) {
            return Entropy.GOOD;
        }
        else if(entropyValue > 50 && entropyValue < 75) {
            return Entropy.REASONABLE;
        } else if(entropyValue < 50 && entropyValue > 25) {
            return Entropy.POOR;
        }
        else if(entropyValue < 25 && entropyValue > 0){
            return Entropy.WEAK;
        } else {
            throw new PasswordException("Cannot count valid entropy value!");
        }
    }
    private double log2(int val) {
        return Math.log(val) / Math.log(2);
    }


}
