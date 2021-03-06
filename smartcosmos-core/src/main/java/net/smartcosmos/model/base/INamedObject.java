package net.smartcosmos.model.base;

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

/**
 * Provides a human readable name and optional descriptive narrative. Named objects typically are marked <i>inactive</i>
 * instead of being physically deleted to preserve historical reporting capabilities.
 *
 * @param <T> Concrete domain object type from the object model.
 */
public interface INamedObject<T> extends IDomainResource<T>
{
    int NAME_MAX_LENGTH = 255;
    int DESCRIPTION_MAX_LENGTH = 1024;

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    boolean isActive();

    void setActive(boolean flag);
}
