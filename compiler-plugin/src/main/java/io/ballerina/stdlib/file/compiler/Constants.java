/*
 * Copyright (c) 2025, WSO2 LLC. (http://www.wso2.org)
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.file.compiler;

import java.util.List;

/**
 * Constants related to compiler plugin implementation.
 */
public class Constants {
    private Constants() {}

    public static final String SCANNER_CONTEXT = "ScannerContext";
    public static final String OS = "os";
    public static final String GET_ENV = "getEnv";
    public static final String FILE = "file";
    public static final String BALLERINA_ORG = "ballerina";

    public static final List<String> FILE_FUNCTIONS = List.of(
            "getAbsolutePath",
            "isAbsolutePath",
            "basename",
            "parentPath",
            "normalizePath",
            "splitPath",
            "joinPath",
            "relativePath",
            "joinPath",
            "test",
            "copy",
            "readDir",
            "read",
            "write",
            "remove",
            "create",
            "getMetaData",
            "createTemp",
            "createTempDir"
    );

    // Pure path-string utilities that do not themselves touch the filesystem. They are commonly
    // used to canonicalize/validate a path (e.g. getAbsolutePath + normalizePath) before it is
    // passed to a filesystem operation, so building their argument via concatenation is not, by
    // itself, a path injection vulnerability.
    public static final List<String> FILE_PATH_UTIL_FUNCTIONS = List.of(
            "getAbsolutePath",
            "isAbsolutePath",
            "basename",
            "parentPath",
            "normalizePath",
            "splitPath",
            "joinPath",
            "relativePath"
    );

    // Filesystem operations that actually read, write, or otherwise act on the given path.
    // These are the sinks the path injection check should apply to.
    public static final List<String> FILE_PATH_INJECTION_SINK_FUNCTIONS = FILE_FUNCTIONS.stream()
            .filter(function -> !FILE_PATH_UTIL_FUNCTIONS.contains(function))
            .distinct()
            .toList();

    public static final List<String> PUBLIC_DIRECTORIES = List.of(
            "\"TMP\"",
            "\"TEMP\"",
            "\"TMPDIR\""
    );
}
