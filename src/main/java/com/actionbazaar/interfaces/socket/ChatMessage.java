/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.actionbazaar.interfaces.socket;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessage
        implements Decoder.Text<ChatMessage>, Encoder.Text<ChatMessage> {

    private static final Logger logger = Logger
            .getLogger(ChatMessage.class.getName());

    private String user;
    private String message;

    public ChatMessage() {
        // Nothing to do.
    }

    public ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void init(EndpointConfig config) {
        // Nothing to do.
    }

    @Override
    public ChatMessage decode(String value) {
        logger.log(Level.FINE, "Decoding JSON: {0}", value);

        try (JsonReader jsonReader = Json.createReader(
                new StringReader(value))) {
            JsonObject jsonObject = jsonReader.readObject();
            user = jsonObject.getString("user");
            message = jsonObject.getString("message");
        }

        return this;
    }

    @Override
    public boolean willDecode(String string) {
        return true; // Detect if it's a valid format.
    }

    @Override
    public String encode(ChatMessage chatMessage) {
        logger.log(Level.FINE, "Encoding to JSON: {0}", chatMessage);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("user", chatMessage.user)
                .add("message", chatMessage.message)
                .build();

        return jsonObject.toString();
    }

    @Override
    public void destroy() {
        // Nothing to do.
    }

    @Override
    public String toString() {
        return "ChatMessage{" + "user=" + user + ", message=" + message + '}';
    }
}
