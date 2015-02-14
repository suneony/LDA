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

public class LDAInferenceFactory {
    private LDAInferenceFactory() {}

    /**
     * Get the LDAInference instance specified by the argument
     * @param method
     * @return the instance which implements LDAInference
     */
    public static LDAInference getInstance(LDAInferenceMethod method) {
        LDAInference clazz = null;
        try {
            clazz = (LDAInference)Class.forName(method.toString()).newInstance();
        } catch (ClassNotFoundException cnfe) {
            assert false;
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return clazz;
    }
}