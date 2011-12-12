/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.lib.wsrs;

import org.apache.hadoop.lib.util.Check;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public abstract class StringParam extends Param<String> {
  private Pattern pattern;

  public StringParam(String name, String str) {
    this(name, str, null);
  }

  public StringParam(String name, String str, Pattern pattern) {
    this.pattern = pattern;
    value = parseParam(name, str);
  }

  public String parseParam(String name, String str) {
    String ret = null;
    Check.notNull(name, "name");
    try {
      if (str != null) {
        str = str.trim();
        if (str.length() > 0) {
          return parse(str);
        }
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException(
        MessageFormat.format("Parameter [{0}], invalid value [{1}], value must be [{2}]",
                             name, str, getDomain()));
    }
    return ret;
  }

  protected String parse(String str) throws Exception {
    if (pattern != null) {
      if (!pattern.matcher(str).matches()) {
        throw new IllegalArgumentException("Invalid value");
      }
    }
    return str;
  }

  @Override
  protected String getDomain() {
    return (pattern == null) ? "a string" : pattern.pattern();
  }
}
