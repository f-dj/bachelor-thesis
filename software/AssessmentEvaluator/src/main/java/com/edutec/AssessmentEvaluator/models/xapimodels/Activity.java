/*
    Copyright 2013 Rustici Software

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/
package com.edutec.AssessmentEvaluator.models.xapimodels;

import com.edutec.AssessmentEvaluator.models.xapimodels.json.JSONBase;
import com.edutec.AssessmentEvaluator.models.xapimodels.json.Mapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Activity model class
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Activity extends JSONBase implements QueryableStatementTarget {
    private final String objectType = "Activity";

    private URI id;
    private ActivityDefinition definition;

    public Activity(URI id) {
        this.id = id;
    }
//
//    public Activity(String id) throws URISyntaxException {
//        this(new URI(id));
//    }
//
//    public Activity(JsonNode jsonNode) throws URISyntaxException {
//        this();
//
//        JsonNode idNode = jsonNode.path("id");
//        if (! idNode.isMissingNode()) {
//            this.setId(new URI(idNode.textValue()));
//        }
//
//        JsonNode definitionNode = jsonNode.path("definition");
//        if (! definitionNode.isMissingNode()) {
//            this.setDefinition(new ActivityDefinition(definitionNode));
//        }
//    }
//
//    public Activity(String id, String name, String description) throws URISyntaxException {
//        this(id);
//
//        this.setDefinition(new ActivityDefinition(name, description));
//    }
//
//    public Activity(StringOfJSON jsonStr) throws URISyntaxException, IOException {
//        this(jsonStr.toJSONNode());
//    }

    @Override
    public ObjectNode toJSONNode(TCAPIVersion version) {
        ObjectNode node = Mapper.getInstance().createObjectNode();
        node.put("objectType", this.getObjectType());

        if (this.id != null) {
            node.put("id", this.getId().toString());
        }
        if (this.definition != null) {
            node.put("definition", this.getDefinition().toJSONNode());
        }

        return node;
    }

    public void setId(URI id) {
        this.id = id;
    }

    public void setId(String id) throws URISyntaxException {
        this.setId(new URI(id));
    }
}
