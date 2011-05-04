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

import java.util.List;

/**
 *
 * @author Tim Stegeman
 */
public class SpellCheckResult {

    private final String word;
    private final int wordIndex;
    private final int startIndex;
    private final List<String> suggestions;

    public SpellCheckResult(String word, int wordIndex, int startIndex, List<String> suggestions) {
        this.word = word;
        this.wordIndex = wordIndex;
        this.startIndex = startIndex;
        this.suggestions = suggestions;
    }

    public String getWord() {
        return word;
    }

    public int getWordIndex() {
        return wordIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    @Override
    public String toString() {
        return "Word: " + word + ", Index: " + wordIndex + ", Start: " + startIndex + ", Suggestions: " + suggestions.toString();
    }
}
