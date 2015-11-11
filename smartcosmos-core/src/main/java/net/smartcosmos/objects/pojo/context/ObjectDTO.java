package net.smartcosmos.objects.pojo.context;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMART COSMOS Platform Core SDK
 * ===============================================================================
 * Copyright (C) 2013 - 2015 SMARTRAC Technology Fletcher, Inc.
 * ===============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#
 */

import com.fasterxml.jackson.annotation.JsonView;
import com.google.auto.value.AutoValue;

import net.smartcosmos.util.json.JsonGenerationView;

@AutoValue
abstract class ObjectDTO implements IObjectDTO
{
    static Builder builder()
    {
        // Define default values here.
        return new AutoValue_ObjectDTO.Builder().activeFlag(true);
    }

    @JsonView(JsonGenerationView.Minimum.class)
    public abstract String objectUrn();

    @Nullable
    @JsonView(JsonGenerationView.Standard.class)
    public abstract Long lastModifiedTimestamp();

    @Nullable
    @JsonView(JsonGenerationView.Full.class)
    @Size(max = 1024)
    public abstract String moniker();

    @Nullable
    @JsonView(JsonGenerationView.Minimum.class)
    public abstract String urn();

    @JsonView(JsonGenerationView.Published.class)
    public abstract String name();

    @Nullable
    @JsonView(JsonGenerationView.Standard.class)
    public abstract String description();

    @JsonView(JsonGenerationView.Standard.class)
    public abstract Boolean activeFlag();

    @Nullable
    @JsonView(JsonGenerationView.Full.class)
    public abstract String accountUrn();

    @JsonView(JsonGenerationView.Minimum.class)
    @Size(max = 255)
    public abstract String type();

    @AutoValue.Builder
    abstract static class Builder
    {
        abstract Builder objectUrn(String objectUrn);

        @Nullable
        abstract Builder lastModifiedTimestamp(Long lastModifiedTimestamp);

        @Nullable
        abstract Builder moniker(String moniker);

        @Nullable
        abstract Builder urn(String urn);

        abstract Builder name(String name);

        @Nullable
        abstract Builder description(String description);

        abstract Builder activeFlag(Boolean activeFlag);

        @Nullable
        abstract Builder accountUrn(String accountUrn);

        abstract Builder type(String type);

        abstract ObjectDTO build();
    }
}
