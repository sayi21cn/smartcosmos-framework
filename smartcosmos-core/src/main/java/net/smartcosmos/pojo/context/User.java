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
package net.smartcosmos.pojo.context;

import com.fasterxml.jackson.annotation.JsonView;
import net.smartcosmos.model.context.IAccount;
import net.smartcosmos.model.context.IUser;
import net.smartcosmos.model.context.RoleType;
import net.smartcosmos.pojo.base.AccountDomainResource;
import net.smartcosmos.util.UuidUtil;
import net.smartcosmos.util.json.JsonGenerationView;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class User extends AccountDomainResource< IUser > implements IUser
{
    @JsonView(JsonGenerationView.Minimum.class)
    @NotNull
    protected RoleType roleType;

    @JsonView(JsonGenerationView.Minimum.class)
    @NotNull
    @Email
    @Size(max = EMAIL_ADDRESS_MAX_LENGTH)
    private String emailAddress;

    @JsonView(JsonGenerationView.Full.class)
    @Size(max = GIVEN_NAME_MAX_LENGTH)
    private String givenName;

    @JsonView(JsonGenerationView.Full.class)
    @Size(max = SURNAME_MAX_LENGTH)
    private String surname;

    @Override
    public String getEmailAddress()
    {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    @Override
    public IAccount getAccount()
    {
        return account;
    }

    @Override
    public void setAccount(IAccount account)
    {
        this.account = account;
    }

    @Override
    public String getGivenName()
    {
        return givenName;
    }

    @Override
    public void setGivenName(String givenName)
    {
        this.givenName = givenName;
    }

    @Override
    public String getSurname()
    {
        return surname;
    }

    @Override
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    @Override
    public RoleType getRoleType()
    {
        return roleType;
    }

    @Override
    public void setRoleType(RoleType roleType)
    {
        this.roleType = roleType;
    }

    @Override
    public void copy(IUser target)
    {
        if (target.getUrn() != null)
        {
            setUrn(UuidUtil.getUrn());
        }
        this.lastModifiedTimestamp = target.getLastModifiedTimestamp();
        this.moniker = target.getMoniker();

        this.emailAddress = target.getEmailAddress();
        this.account = target.getAccount();

        this.givenName = target.getGivenName();
        this.surname = target.getSurname();

        this.roleType = target.getRoleType();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!account.equals(user.account)) return false;
        if (!emailAddress.equals(user.emailAddress)) return false;
        if (givenName != null ? !givenName.equals(user.givenName) : user.givenName != null) return false;
        if (roleType != user.roleType) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = 31 * result + (givenName != null ? givenName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + ((roleType == null) ? 0 : roleType.hashCode());
        return result;
    }

}
