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
package com.edutec.DiscussionEvaluator.models.xapimodels;

import com.edutec.DiscussionEvaluator.models.xapimodels.internal.StatementBase;
import com.edutec.DiscussionEvaluator.models.xapimodels.json.MyDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.UUID;

//import com.edutec.xapi.generic.moodle.models.http.HTTPPart;

/**
 * Statement Class
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true)
public class Statement extends StatementBase {
    private UUID id;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = MyDateTimeDeserializer.class)
    private DateTime stored;
    private Agent authority;
    private TCAPIVersion version;

    @Deprecated
    private Boolean voided;

    @Override
    public ObjectNode toJSONNode(TCAPIVersion version) {
        ObjectNode node = super.toJSONNode(version);
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime().withZoneUTC();

        if (this.id != null) {
            node.put("id", this.getId().toString());
        }
        if (this.stored != null) {
            node.put("stored", fmt.print(this.getStored()));
        }
        if (this.authority != null) {
            node.put("authority", this.getAuthority().toJSONNode(version));
        }

        //Include 0.95 specific fields if asking for 0.95 version
        if (TCAPIVersion.V095.equals(version)) {
            if (this.getVoided() != null) {
                node.put("voided", this.getVoided());
            }
        }

        //Include 1.0.x specific fields if asking for 1.0.x version
        if (version.ordinal() <= TCAPIVersion.V100.ordinal()) {
            if (this.getVersion() != null) {
                node.put("version", this.getVersion().toString());
            }
        }

        return node;
    }

    /**
     * Method to set a random ID and the current date/time in the 'timestamp'
     */
    public void stamp() {
        if (this.getId() == null) {
            this.setId(UUID.randomUUID());
        }
        if (this.getTimestamp() == null) {
            this.setTimestamp(new DateTime());
        }
    }
}
