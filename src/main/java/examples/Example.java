/*
* Copyright 2015 Kohei Yamamoto
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package examples;

import java.util.List;

import lda.LDA;
import static lda.inference.InferenceMethod.*;

import org.apache.commons.lang3.tuple.Pair;

import dataset.Dataset;

public class Example {
    public static void main(String[] args) throws Exception {
        Dataset dataset = new Dataset("src/test/resources/docword.kos.txt", "src/test/resources/vocab.kos.txt");
        
        final int numTopics = 10;
        LDA lda = new LDA(0.1, 0.1, numTopics, dataset, CGS);
        lda.run();
        System.out.println(lda.computePerplexity(dataset));

        for (int t = 0; t < numTopics; ++t) {
            List<Pair<String, Double>> highRankVocabs = lda.getVocabsSortedByPhi(t);
            System.out.print("t" + t + ": ");
            for (int i = 0; i < 5; ++i) {
                System.out.print("[" + highRankVocabs.get(i).getLeft() + "," + highRankVocabs.get(i).getRight() + "],");
            }
            System.out.println();
        }
    }
}
