/*
 * SMART COSMOS SDK
 * (C) Copyright 2013-2014, Smartrac Technology Fletcher, Inc.
 * 267 Cane Creek Rd, Fletcher, NC, 28732, USA
 * All Rights Reserved.
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

package net.smartcosmos.builder;

/**
 * Generic Builder pattern base class that includes a path for validation and minimum creation definition.
 *
 * @param <T>
 */
public class AbstractBuilder<T>
{
    protected final T instance;

    protected AbstractBuilder(T instance)
    {
        this.instance = instance;
    }

    public T build()
    {
        onInject();
        onValidate();
        return instance;
    }

    protected void onValidate()
    {

    }

    protected void onInject()
    {

    }

    public boolean meetsCreationMinimum()
    {
        return true;
    }
}
