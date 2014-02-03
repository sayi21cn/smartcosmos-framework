/*
 * SnapBundle SDK
 * (C) Copyright 2013-2014 Tag Dynamics, LLC (http://tagdynamics.com/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.snapbundle.util.json;

/**
 * Defines a view hierarchy for SnapBundle JSON serialization that selectively includes (or excludes)
 * certain verbose fields when perform a JSON conversion operation.
 *
 * @see com.snapbundle.util.json.JsonUtil
 * @see com.snapbundle.util.json.ViewType
 */
public class JsonGenerationView
{
    public static class Published
    {

    }

    public static class Minimum extends Published
    {
    }

    public static class Standard extends Minimum
    {
    }

    public static class Full extends Standard
    {
    }

    public static class Restricted extends Full
    {
    }
}
