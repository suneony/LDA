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

package lda;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class LDATest {
    public static class WhenReadMockData {
        LDA sut;

        @Before
        public void setUp() throws Exception {
            InputStream stream = new ByteArrayInputStream("2\n3\n6\n1 1 1\n1 2 2\n2 3 3\n".getBytes());
            DatasetLoader mockLoader = mock(DatasetLoader.class);
            when(mockLoader.getInputStream("test")).thenReturn(stream);

            BagOfWords bow = new BagOfWords("test", mockLoader);

            sut = new LDA(0.1, 0.1, 10, bow, LDAInferenceMethod.CGS);
        }

        @Test
        public void getAlpha_returns_0point1() throws Exception {
            for (int t = 0; t < sut.getNumTopics(); ++t) {
                assertThat(sut.getAlpha(t), is(0.1));
            }
        }
        
        @Test
        public void getBeta_returns_0point1() {
            assertThat(sut.getBeta(), is(0.1));
        }

        @Test
        public void getNumTopics_returns_10() throws Exception {
            assertThat(sut.getNumTopics(), is(10));
        }
        
        @Test
        public void getBow_returns_bow_numDocs_2_numVocabs_3_numWords_6() throws Exception {
            assertThat(sut.getBow().getNumDocs(),   is(2));
            assertThat(sut.getBow().getNumVocabs(), is(3));
            assertThat(sut.getBow().getNumWords(),  is(6));
        }
    }
    
    public static class WhenReadKosDataset {
        LDA sut;
        
        @Before
        public void setUp() throws Exception {
            BagOfWords bow = new BagOfWords("src/test/resources/docword.kos.txt");
            sut = new LDA(0.1, 0.1, 10, bow, LDAInferenceMethod.CGS);
        }
        
        @After
        public void tearDown() throws Exception {
           sut = null;
        }
        
        @Test
        public void getBow_returns_bow_numDocs_3430_numVocabs_6906_numWords_353160() throws Exception {
            assertThat(sut.getBow().getNumDocs(),   is(3430));
            assertThat(sut.getBow().getNumVocabs(), is(6906));
            assertThat(sut.getBow().getNumNNZ(),  is(353160));
        }
    }
}