// Copyright (c) 2025 WSO2 LLC. (http://www.wso2.org)
//
// WSO2 LLC. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/file;

final string targetDirectory = "./path/to/target/directory/";

public isolated function deleteFile(string fileName) returns string|error {

    // Retrieve the normalized absolute path of the user provided file
    string absoluteUserFilePath = check file:getAbsolutePath(targetDirectory + fileName);
    string normalizedAbsoluteUserFilePath = check file:normalizePath(absoluteUserFilePath, file:CLEAN);

    // Check whether the user provided file exists
    boolean fileExists = check file:test(normalizedAbsoluteUserFilePath, file:EXISTS);
    if !fileExists {
        return "File does not exist!";
    }

    // Retrieve the normalized absolute path of parent directory of the user provided file
    string canonicalDestinationPath = check file:parentPath(normalizedAbsoluteUserFilePath);
    string normalizedCanonicalDestinationPath = check file:normalizePath(canonicalDestinationPath, file:CLEAN);

    // Retrieve the normalized absolute path of the target directory
    string absoluteTargetFilePath = check file:getAbsolutePath(targetDirectory);
    string normalizedTargetDirectoryPath = check file:normalizePath(absoluteTargetFilePath, file:CLEAN);

    // Perform comparison of user provided file path and target directory path
    boolean dirMatch = normalizedTargetDirectoryPath.equalsIgnoreCaseAscii(normalizedCanonicalDestinationPath);
    if !dirMatch {
        return "Entry is not in the target directory!";
    }

    check file:remove(normalizedAbsoluteUserFilePath);
    return "Deleted";
}
