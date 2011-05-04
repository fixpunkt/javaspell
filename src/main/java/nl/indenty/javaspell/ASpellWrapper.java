/*
 *  Copyright 2011 Tim Stegeman.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package nl.indenty.javaspell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tim Stegeman
 */
public class ASpellWrapper {

    private static final String ASPELL_CMD = "aspell -a --lang=%s --encoding=utf-8 -H --rem-sgml-check=alt";
    private static final Pattern LINE_PAT = Pattern.compile("& (.+) [0-9]+ ([0-9]+): (.+)");
    private final String language;

    public ASpellWrapper(String language) {
        this.language = language;
    }

    public List<SpellCheckResult> checkString(String text) throws IOException, ASpellException {
        String cmd = String.format(ASPELL_CMD, language);
        String line = null;

        Process process = Runtime.getRuntime().exec(cmd);

        PrintStream ps = new PrintStream(process.getOutputStream(), true);
        ps.print(text);
        ps.close();

        BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        while ((line = errorInput.readLine()) != null) {
            throw new ASpellException(line.trim());
        }

        int index = 0;
        List<SpellCheckResult> results = new ArrayList<SpellCheckResult>();
        while ((line = input.readLine()) != null) {
            if (line.startsWith("*")) {
                index++;
            } else if (line.startsWith("&")) {
                results.add(parseLine(index++, line));
            }
        }
        input.close();
        process.destroy();

        return results;
    }

    private SpellCheckResult parseLine(int index, String line) {
        Matcher matcher = LINE_PAT.matcher(line);
        if (matcher.find()) {
            String word = matcher.group(1);
            int start = Integer.parseInt(matcher.group(2));
            String suggestionsStr = matcher.group(3);
            String[] suggestionsArr = suggestionsStr.split(", ");
            List<String> suggestions = new ArrayList<String>();
            for (String suggestion : suggestionsArr) {
                suggestions.add(suggestion.trim());
            }
            return new SpellCheckResult(word, index, start, suggestions);
        }
        return null;
    }
}
